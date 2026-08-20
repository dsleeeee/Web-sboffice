package kr.co.solbipos.adi.sms.smsUserRegist.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.adi.sms.kcp.service.KcpCertRegistration;
import kr.co.solbipos.adi.sms.kcp.service.KcpCertResult;
import kr.co.solbipos.adi.sms.kcp.service.KcpCertService;
import kr.co.solbipos.adi.sms.kcp.service.KcpCertTransaction;
import kr.co.solbipos.adi.sms.kcp.web.KcpPopupResponse;
import kr.co.solbipos.adi.sms.smsUserRegist.service.SmsUserRegistService;
import kr.co.solbipos.adi.sms.smsUserRegist.service.SmsUserRegistVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : SmsUserRegistController.java
 * @Description : 부가서비스 > SMS관리 > SMS 사용 등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.03  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.31
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/sms/smsUserRegist")
public class SmsUserRegistController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsUserRegistController.class);
    private static final String RETURN_PATH =
            "/adi/sms/smsUserRegist/smsUserRegist/updateVerify.sb";

    private final SessionService sessionService;
    private final SmsUserRegistService smsUserRegistService;
    private final KcpCertService kcpCertService;

    @Autowired
    public SmsUserRegistController(SessionService sessionService,
                                   SmsUserRegistService smsUserRegistService,
                                   KcpCertService kcpCertService) {
        this.sessionService = sessionService;
        this.smsUserRegistService = smsUserRegistService;
        this.kcpCertService = kcpCertService;
    }

    @RequestMapping(value = "/smsUserRegist/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "adi/sms/smsUserRegist/smsUserRegist";
    }

    @RequestMapping(value = "/smsUserRegist/getUserRegistInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getUserRegistInfo(HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        DefaultMap<Object> result = smsUserRegistService.getUserRegistInfo(sessionInfoVO);

        if (result != null && result.getStr("telNo") != null && !result.getStr("telNo").isEmpty()) {
            result.put("telNo", maskTelNo(result.getStr("telNo")));
        }
        return returnJson(Status.OK, result);
    }

    /**
     * KcpCertService.register()로 SMS 사용등록 거래를 만들고 팝업 호출값을 반환한다.
     */
    @RequestMapping(value = "/smsUserRegist/getVerifyVal.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVerifyVal(SmsUserRegistVO smsUserRegistVO, HttpServletRequest request) {
        DefaultMap<String> result = new DefaultMap<>();
        // 필수 약관 동의 여부 검증
        if (!"Y".equals(smsUserRegistVO.getAgree1Yn()) ||
                !"Y".equals(smsUserRegistVO.getAgree2Yn())) {
            result.put("error", "필수 약관에 모두 동의해야 본인인증을 진행할 수 있습니다.");
            return returnJson(Status.OK, result);
        }

        try {
            SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
            // KCP 거래등록
            KcpCertRegistration registration = kcpCertService.register(
                    KcpCertService.PURPOSE_SMS_USER_REGIST, RETURN_PATH, sessionInfoVO);
            // 인증 팝업 호출값 구성
            putRegistration(result, registration);
        } catch (Exception e) {
            LOGGER.warn("KCP V2 transaction registration failed for SMS user registration: {}", e.getMessage());
            result.put("error", errorMessage(e, "본인인증 거래등록에 실패했습니다."));
        }
        return returnJson(Status.OK, result);
    }

    /**
     * KCP 인증 결과를 조회·복호화하고 DI 중복검사 후 SMS 사용등록을 저장한다.
     */
    @RequestMapping(value = "/smsUserRegist/updateVerify.sb", method = RequestMethod.POST)
    public void updateVerify(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        // 콜백 결과코드와 거래키 수신
        String resCd = trim(request.getParameter("res_cd"));
        String regCertKey = trim(request.getParameter("reg_cert_key"));
        // 콜백 수신 확인용(개인정보 아님). site_cd/cert_no/enc_cert_data2/up_hash/dn_hash는 V2에서 없어졌다.
        LOGGER.info("KCP V2 콜백 수신 [화면=SMS_USER_REGIST, res_cd={}, res_msg={}, reg_cert_key={}]",
                resCd, trim(request.getParameter("res_msg")), regCertKey);

        // 인증창 단계에서 실패한 경우에는 결과조회 없이 일회용 Redis 거래만 정리한다.
        if (!"0000".equals(resCd)) {
            // 거래정보 삭제
            removeQuietly(regCertKey);
            // 인증 실패 정보를 부모 창에 전달
            callback(response, "fail", "본인인증이 완료되지 않았습니다." + codeSuffix(resCd));
            return;
        }

        try {
            // 인증 결과 조회 및 복호화
            KcpCertResult certResult = kcpCertService.getResult(
                    regCertKey, KcpCertService.PURPOSE_SMS_USER_REGIST);
            KcpCertTransaction transaction = certResult.getTransaction();
            Map<String, Object> certData = certResult.getCertData();

            String di = value(certData, "DI", "di");
            String telNo = value(certData, "phone_no");
            String userNm = value(certData, "user_name");
            if (isBlank(di) || isBlank(telNo)) {
                // 거래정보 삭제
                removeQuietly(regCertKey);
                // 인증 실패 정보를 부모 창에 전달
                callback(response, "fail", "본인인증 결과에 필수 정보가 없습니다.");
                return;
            }

            // 거래 요청자 정보 복원
            SessionInfoVO sessionInfoVO = toSessionInfo(transaction);
            SmsUserRegistVO lookupVO = new SmsUserRegistVO();
            lookupVO.setDi(di);
            // DI 중복 사용자 조회
            String existingUserId = smsUserRegistService.getUserIdByDi(lookupVO);

            // DI는 동일인을 식별하므로 다른 사용자에게 이미 연결된 DI는 새 사용자에게 저장하지 않는다.
            if (existingUserId != null && !existingUserId.equals(sessionInfoVO.getUserId())) {
                // 거래정보 삭제
                kcpCertService.removeByRegCertKey(regCertKey);
                Map<String, String> payload = new LinkedHashMap<>();
                payload.put("dupUserId", existingUserId);
                // 중복 사용자 정보를 부모 창에 전달
                KcpPopupResponse.callback(response, "smsUserRegistVerifyCallback", "duplicate", payload);
                return;
            }

            SmsUserRegistVO saveVO = new SmsUserRegistVO();
            saveVO.setDi(di);
            saveVO.setTelNo(telNo);
            // SmsUserRegistController.getVerifyVal()에서 필수동의를 검증했으므로 saveVO 동의값을 Y로 저장한다.
            saveVO.setAgree1Yn("Y");
            saveVO.setAgree2Yn("Y");
            // SMS 사용등록 저장
            smsUserRegistService.saveUserRegist(saveVO, sessionInfoVO);

            // 거래정보 삭제
            kcpCertService.removeByRegCertKey(regCertKey);
            Map<String, String> payload = new LinkedHashMap<>();
            payload.put("userNm", userNm);
            payload.put("telNo", maskTelNo(telNo));
            payload.put("verifyDt", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            // 인증 성공 정보를 부모 창에 전달
            KcpPopupResponse.callback(response, "smsUserRegistVerifyCallback", "success", payload);
        } catch (Exception e) {
            LOGGER.error("KCP V2 result processing failed for SMS user registration", e);
            // 처리 오류를 부모 창에 전달
            callback(response, "error", "본인인증 결과 처리 중 오류가 발생했습니다. 다시 시도해주세요.");
        }
    }

    @RequestMapping(value = "/smsUserRegist/deleteUserRegist.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteUserRegist(@RequestBody SmsUserRegistVO smsUserRegistVO,
                                   HttpServletRequest request,
                                   HttpServletResponse response,
                                   Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = smsUserRegistService.deleteUserRegist(smsUserRegistVO, sessionInfoVO);
        return returnJson(Status.OK, result);
    }

    /**
     * KCP 거래등록 결과에서 인증 팝업 호출에 필요한 값을 화면 응답에 담는다.
     */
    private void putRegistration(DefaultMap<String> target, KcpCertRegistration registration) {
        target.put("call_url", registration.getCallUrl());
        target.put("reg_cert_key", registration.getRegCertKey());
        target.put("ordr_idxx", registration.getOrdrIdxx());
        target.put("kcp_page_submit_yn", registration.getKcpPageSubmitYn());
    }

    /** Redis에 보관한 최초 요청자 정보로 업무 세션 객체를 복원한다. */
    private SessionInfoVO toSessionInfo(KcpCertTransaction transaction) {
        SessionInfoVO sessionInfoVO = new SessionInfoVO();
        sessionInfoVO.setSessionId(transaction.getSessionId());
        sessionInfoVO.setUserId(transaction.getUserId());
        sessionInfoVO.setOrgnCd(transaction.getOrgnCd());
        return sessionInfoVO;
    }

    /** 인증 상태와 실패 사유를 부모 창 콜백으로 전달한다. */
    private void callback(HttpServletResponse response, String status, String reason) throws IOException {
        Map<String, String> payload = new LinkedHashMap<>();
        payload.put("reason", reason);
        // 부모 창 콜백 응답
        KcpPopupResponse.callback(response, "smsUserRegistVerifyCallback", status, payload);
    }

    /** 콜백 응답을 방해하지 않도록 Redis 거래 정리 중 발생한 예외를 로그로만 남긴다. */
    private void removeQuietly(String regCertKey) {
        if (isBlank(regCertKey)) {
            return;
        }
        try {
            // 거래정보 삭제
            kcpCertService.removeByRegCertKey(regCertKey);
        } catch (Exception e) {
            LOGGER.warn("Failed to remove KCP V2 transaction after rejected callback: {}", e.getMessage());
        }
    }

    /** 예외 메시지가 있으면 기본 오류 안내 문구에 덧붙여 반환한다. */
    private String errorMessage(Exception e, String fallback) {
        String message = e.getMessage();
        return isBlank(message) ? fallback : fallback + " (" + message + ")";
    }

    /** KCP 결과 코드가 있을 때만 사용자 안내 문구 뒤에 붙일 문자열을 만든다. */
    private String codeSuffix(String code) {
        return isBlank(code) ? "" : " (" + code + ")";
    }

    /** 복호화 결과에서 대소문자 표기가 다른 후보 키를 순서대로 조회한다. */
    private String value(Map<String, Object> values, String... keys) {
        // 복호화 결과에서 필요한 업무 필드만 꺼낸다.
        for (String key : keys) {
            Object value = values.get(key);
            if (value != null && !String.valueOf(value).isEmpty()) {
                return String.valueOf(value);
            }
        }
        return "";
    }

    /** 콜백 파라미터의 null을 빈 문자열로 바꾸고 앞뒤 공백을 제거한다. */
    private String trim(String value) {
        return value == null ? "" : value.trim();
    }

    /** 값이 null이거나 공백뿐인지 확인한다. */
    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    /** 전화번호 마스킹(010-****-5821). */
    private String maskTelNo(String telNo) {
        if (telNo == null || telNo.length() < 8) {
            return telNo;
        }
        int len = telNo.length();
        return telNo.substring(0, 3) + "-****-" + telNo.substring(len - 4);
    }
}
