package kr.co.solbipos.adi.sms.marketingSmsSend.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.adi.sms.kcp.service.KcpCertRegistration;
import kr.co.solbipos.adi.sms.kcp.service.KcpCertResult;
import kr.co.solbipos.adi.sms.kcp.service.KcpCertService;
import kr.co.solbipos.adi.sms.kcp.service.KcpCertTransaction;
import kr.co.solbipos.adi.sms.kcp.web.KcpPopupResponse;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.marketingSmsSend.service.MarketingSmsSendService;
import kr.co.solbipos.adi.sms.marketingSmsSend.service.MarketingSmsSendVO;
import kr.co.solbipos.adi.sms.smsUserRegist.service.SmsUserRegistService;
import kr.co.common.service.message.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : MarketingSmsSendController.java
 * @Description : 부가서비스 > SMS관리 > 마케팅용 SMS전송
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.08.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.08.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/sms/marketingSmsSend")
public class MarketingSmsSendController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarketingSmsSendController.class);
    private static final String RETURN_PATH =
            "/adi/sms/marketingSmsSend/marketingSmsSend/updateVerify.sb";
    private static final String RETURN_PATH2 =
            "/adi/sms/marketingSmsSend/marketingSmsSend/updateVerify2.sb";

    private final SessionService sessionService;
    private final MarketingSmsSendService marketingSmsSendService;
    private final CmmCodeUtil cmmCodeUtil;
    private final SmsUserRegistService smsUserRegistService;
    private final MessageService messageService;
    private final KcpCertService kcpCertService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MarketingSmsSendController(SessionService sessionService,
                                      MarketingSmsSendService marketingSmsSendService,
                                      CmmCodeUtil cmmCodeUtil,
                                      SmsUserRegistService smsUserRegistService,
                                      MessageService messageService,
                                      KcpCertService kcpCertService) {
        this.sessionService = sessionService;
        this.marketingSmsSendService = marketingSmsSendService;
        this.cmmCodeUtil = cmmCodeUtil;
        this.smsUserRegistService = smsUserRegistService;
        this.messageService = messageService;
        this.kcpCertService = kcpCertService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/marketingSmsSend/list.sb", method = RequestMethod.GET)
    public String marketingSmsSendView(HttpServletRequest request, HttpServletResponse response, Model model) {

//        MarketingSmsSendVOdudfhd marketingSmsSendVO = new MarketingSmsSendVO();
//        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

//        // 마케팅용 SMS전송 - 메세지그룹 조회
//        List<DefaultMap<String>> msgGrpAddColList = marketingSmsSendService.getMsgGrpColList(marketingSmsSendVO, sessionInfoVO);
//        model.addAttribute("msgGrpAddColList", msgGrpAddColList);
//
//        // 마케팅용 SMS전송 - 회원등급 리스트 조회
//        List membrClassList = marketingSmsSendService.getMembrClassList(marketingSmsSendVO, sessionInfoVO);
//        String membrClassListAll = cmmCodeUtil.assmblObj(membrClassList, "name", "value", UseYn.ALL);
//        model.addAttribute("memberClassList", membrClassListAll);

        return "adi/sms/marketingSmsSend/marketingSmsSend";
    }

    /**
     * 메세지관리 - 메세지서식 조회(최근이력)
     *
     * @param marketingSmsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 08. 10.
     */
    @RequestMapping(value = "/marketingSmsSend/getMarketingSmsSendMsgManageDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMarketingSmsSendMsgManageDtlList(MarketingSmsSendVO marketingSmsSendVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = marketingSmsSendService.getMarketingSmsSendMsgManageDtlList(marketingSmsSendVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, marketingSmsSendVO);
    }

    /**
     * 마케팅용 SMS전송 - 회원 조회
     *
     * @param marketingSmsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 08. 10.
     */
    @RequestMapping(value = "/marketingSmsSend/getMarketingSmsSendList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMarketingSmsSendList(MarketingSmsSendVO marketingSmsSendVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = marketingSmsSendService.getMarketingSmsSendList(marketingSmsSendVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, marketingSmsSendVO);
    }

    /**
     * 마케팅용 SMS전송 - 검색 결과 저장
     *
     * @param marketingSmsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 10. 07.
     */
    @RequestMapping(value = "/marketingSmsSend/getMarketingSmsSendListSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMarketingSmsSendListSave(@RequestBody MarketingSmsSendVO marketingSmsSendVO, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = marketingSmsSendService.getMarketingSmsSendListSave(marketingSmsSendVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 마케팅용 SMS전송 - 1000건 이상 전송시 전송테이블에 몇건 Insert 됬는지 조회
     *
     * @param marketingSmsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 10. 22.
     */
    @RequestMapping(value = "/marketingSmsSend/getSmsSendInsert1000Count.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsSendInsert1000Count(MarketingSmsSendVO marketingSmsSendVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = marketingSmsSendService.getSmsSendInsert1000Count(marketingSmsSendVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }


    /**
     * 본인인증 여부 조회
     *
     * @param marketingSmsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2021.12.10
     */
    @RequestMapping(value = "/marketingSmsSend/getVerifyChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVerifyChk(MarketingSmsSendVO marketingSmsSendVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = marketingSmsSendService.getVerifyChk(marketingSmsSendVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 마케팅 발신번호 본인인증용 KCP 거래를 등록한다.
     * 복호화된 휴대폰 번호의 중복을 검사하고 인증 대기 행을 완료한다.
     *
     * @param
     * @param
     * @param
     * @param
     * @return  Object
     * @author  권지현
     * @since   2021.12.10
     */
    @RequestMapping(value = "/marketingSmsSend/getVerifyVal.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVerifyVal(MarketingSmsSendVO marketingSmsSendVO, HttpServletRequest request) {
        // KCP 거래등록
        return registerVerification(request, KcpCertService.PURPOSE_MARKETING_VERIFY, RETURN_PATH);
    }

    /**
     * 마케팅 발신번호 본인인증 대기 요청 저장
     * KCP 팝업 제출 전에 거래등록 주문번호를 CERT_ID로 저장하며 VERIFY/VERIFY2가 함께 사용한다.
     *
     * @param marketingSmsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2021.12.10
     */
    @RequestMapping(value = "/marketingSmsSend/saveVerify.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveVerify(@RequestBody MarketingSmsSendVO marketingSmsSendVO, HttpServletRequest request,
                             HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 인증 대기 행 저장
        int result = marketingSmsSendService.saveVerify(marketingSmsSendVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 마케팅 발신번호 본인인증 KCP 결과 콜백
     * 휴대폰 번호 중복검사 후 팝업 전에 만든 인증 대기 행을 완료한다.
     *
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2021.12.10
     */
    @RequestMapping(value = "/marketingSmsSend/updateVerify.sb", method = RequestMethod.POST)
    public void updateVerify(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        // 인증 콜백 처리
        processVerificationCallback(
                request,
                response,
                KcpCertService.PURPOSE_MARKETING_VERIFY,
                false);
    }

    /**
     * 마케팅용 SMS전송 - 발신번호 공통코드에 등록되 있는지 확인(특수부가사업자 승인 전 임시사용)
     *
     * @param marketingSmsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 16.
     */
//    @RequestMapping(value = "/marketingSmsSend/getTelNoNmCodeChk.sb", method = RequestMethod.POST)
//    @ResponseBody
//    public Result getTelNoNmCodeChk(MarketingSmsSendVO marketingSmsSendVO, HttpServletRequest request,
//                                            HttpServletResponse response, Model model) {
//
//        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
//
//        DefaultMap<String> result = marketingSmsSendService.getTelNoNmCodeChk(marketingSmsSendVO, sessionInfoVO);
//
//        DefaultMap<Object> resultMap = new DefaultMap<Object>();
//        resultMap.put("result", result);
//
//        return returnJson(Status.OK, resultMap);
//    }

    /**
     * 발신번호추가2용 KCP 거래를 등록한다.
     * SMS 사용등록 사용자와 KCP 결과의 DI를 확인하는 전용 목적을 사용한다.
     *
     * @param
     * @param
     * @param
     * @param
     * @return  Object
     * @author  김설아
     * @since   2024. 11. 19
     */
    @RequestMapping(value = "/marketingSmsSend/getVerifyVal2.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVerifyVal2(MarketingSmsSendVO marketingSmsSendVO, HttpServletRequest request) {
        // SMS 사용자 확인용 KCP 거래등록
        return registerVerification(request, KcpCertService.PURPOSE_MARKETING_VERIFY2, RETURN_PATH2);
    }

    /**
     * 발신번호추가2 KCP 결과 콜백
     * 인증 휴대폰 번호뿐 아니라 DI를 SMS 사용등록 정보와 대조한 뒤 두 DB 갱신을 함께 완료한다.
     *
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 11. 19
     */
    @RequestMapping(value = "/marketingSmsSend/updateVerify2.sb", method = RequestMethod.POST)
    public void updateVerify2(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        // 인증 콜백 및 DI 일치 여부 처리
        processVerificationCallback(
                request,
                response,
                KcpCertService.PURPOSE_MARKETING_VERIFY2,
                true);
    }

    /**
     * 전송 URL 관리(화이트리스트 등록요청) - 본인 요청 목록 조회
     */
    @RequestMapping(value = "/marketingSmsSend/getRegSendUrlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRegSendUrlList(MarketingSmsSendVO marketingSmsSendVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = marketingSmsSendService.getRegSendUrlList(marketingSmsSendVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, marketingSmsSendVO);
    }

    /**
     * 전송 URL 관리(화이트리스트 등록요청) - 요청 등록
     */
    @RequestMapping(value = "/marketingSmsSend/saveRegSendUrl.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveRegSendUrl(@RequestBody MarketingSmsSendVO marketingSmsSendVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = marketingSmsSendService.saveRegSendUrl(marketingSmsSendVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }



    /**
     * {@link KcpCertService#register(String, String, SessionInfoVO)} 호출과 화면 응답 변환을 묶는다.
     */
    private Result registerVerification(HttpServletRequest request, String purpose, String returnPath) {
        DefaultMap<String> result = new DefaultMap<>();
        try {
            // KCP 거래등록
            KcpCertRegistration registration = kcpCertService.register(
                    purpose, returnPath, sessionService.getSessionInfo(request));
            // 인증 팝업 호출값 구성
            putRegistration(result, registration);
        } catch (Exception e) {
            LOGGER.warn("KCP V2 transaction registration failed for sender number verification: {}", e.getMessage());
            result.put("error", registrationError(e));
        }
        return returnJson(Status.OK, result);
    }

    /**
     * updateVerify.sb/updateVerify2.sb의 Redis 복원·KCP 결과조회·DB 처리를 묶는다.
     */
    private void processVerificationCallback(HttpServletRequest request,
                                             HttpServletResponse response,
                                             String purpose,
                                             boolean verifySmsUser) throws IOException {
        // 콜백 결과코드와 거래키 수신
        String resCd = trim(request.getParameter("res_cd"));
        String regCertKey = trim(request.getParameter("reg_cert_key"));
        // 콜백 수신 확인용(개인정보 아님). site_cd/cert_no/enc_cert_data2/up_hash/dn_hash는 V2에서 없어졌다.
        LOGGER.info("KCP V2 콜백 수신 [화면={}, res_cd={}, res_msg={}, reg_cert_key={}]",
                purpose, resCd, trim(request.getParameter("res_msg")), regCertKey);

        // 실패 거래 복원 및 결과 저장
        if (!"0000".equals(resCd)) {
            // 실패한 거래정보 조회 및 DB 결과 저장
            updateRejectedTransaction(regCertKey, purpose, resCd);
            // 인증 실패 응답
            respondVerification(response, verifySmsUser,
                    "본인인증이 완료되지 않았습니다." + codeSuffix(resCd), false);
            return;
        }

        try {
            // 인증 결과 조회 및 복호화
            KcpCertResult certResult = kcpCertService.getResult(regCertKey, purpose);
            KcpCertTransaction transaction = certResult.getTransaction();
            // 거래 요청자 정보 복원
            SessionInfoVO sessionInfoVO = toSessionInfo(transaction);
            // 인증 결과 항목 조회
            Map<String, Object> certData = certResult.getCertData();

            String telNo = value(certData, "phone_no");
            String di = value(certData, "DI", "di");
            if (isBlank(telNo) || (verifySmsUser && isBlank(di))) {
                // 거래정보 삭제
                removeQuietly(regCertKey);
                // 인증 실패 응답
                respondVerification(response, verifySmsUser,
                        "본인인증 결과에 필수 정보가 없습니다.", false);
                return;
            }

            MarketingSmsSendVO verifyVO = new MarketingSmsSendVO();
            verifyVO.setCertId(transaction.getOrdrIdxx());
            verifyVO.setResCd("0000");
            verifyVO.setTelNo(telNo);

            if (verifySmsUser) {
                // SMS 사용자 등록정보 및 DI 일치 여부 확인
                DefaultMap<Object> registInfo = smsUserRegistService.getUserRegistInfo(sessionInfoVO);
                if (registInfo == null || isBlank(registInfo.getStr("userId"))) {
                    // 거래정보 삭제
                    kcpCertService.removeByRegCertKey(regCertKey);
                    // 미등록 사용자 응답
                    respondVerification(response, true,
                            messageService.get("smsUserRegist.notRegistAlert"), false);
                    return;
                }
                if (!di.equals(registInfo.getStr("di"))) {
                    // 거래정보 삭제
                    kcpCertService.removeByRegCertKey(regCertKey);
                    // DI 불일치 응답
                    respondVerification(response, true,
                            messageService.get("smsUserRegist.diMismatchAlert"), false);
                    return;
                }
            } else if (
                    // 인증된 휴대폰 번호 중복 확인
                    marketingSmsSendService.getVerifyChk(verifyVO, sessionInfoVO) != 0) {
                // 거래정보 삭제
                kcpCertService.removeByRegCertKey(regCertKey);
                // 중복 번호 응답
                respondVerification(response, false, "기존에 등록된 전화번호입니다.", false);
                return;
            }

            // 인증 목적에 맞는 DB 결과 저장
            if (verifySmsUser) {
                MarketingSmsSendVO diSaveVO = new MarketingSmsSendVO();
                diSaveVO.setCertId(transaction.getOrdrIdxx());
                diSaveVO.setDi(di);
                // 인증 결과와 DI 저장
                marketingSmsSendService.completeVerify2(verifyVO, diSaveVO, sessionInfoVO);
            } else if (
                    // 발신번호 인증 결과 저장
                    marketingSmsSendService.updateVerify(verifyVO, sessionInfoVO) != 1) {
                // 저장 실패 응답
                respondVerification(response, false,
                        "본인인증은 성공했으나 저장 중 오류가 발생했습니다.", false);
                return;
            }

            // 거래정보 삭제
            kcpCertService.removeByRegCertKey(regCertKey);
            // 인증 성공 응답
            respondVerification(response, verifySmsUser, "정상등록되었습니다.", true);
        } catch (Exception e) {
            LOGGER.error("KCP V2 sender number verification callback failed", e);
            // 처리 오류 응답
            respondVerification(response, verifySmsUser,
                    "본인인증 결과 처리 중 오류가 발생했습니다. 다시 시도해주세요.", false);
        }
    }

    /** 인증이 거절된 KCP 거래를 복원해 인증 대기 행에 실패 코드를 저장하고 거래를 정리한다. */
    private void updateRejectedTransaction(String regCertKey, String purpose, String resCd) {
        if (isBlank(regCertKey)) {
            return;
        }
        try {
            // 거래정보 조회
            KcpCertTransaction transaction = kcpCertService.getTransaction(regCertKey, purpose);
            MarketingSmsSendVO verifyVO = new MarketingSmsSendVO();
            verifyVO.setCertId(transaction.getOrdrIdxx());
            verifyVO.setResCd(resCd);
            verifyVO.setTelNo("");
            // 최초 요청자 정보 복원 및 실패 결과 저장
            marketingSmsSendService.updateVerify(verifyVO, toSessionInfo(transaction));
            // 거래정보 삭제
            kcpCertService.removeByRegCertKey(regCertKey);
        } catch (Exception e) {
            LOGGER.warn("Unable to persist rejected KCP V2 callback: {}", e.getMessage());
        }
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
            LOGGER.warn("Unable to remove rejected KCP V2 transaction: {}", e.getMessage());
        }
    }

    /** 인증 목적에 맞는 부모 창 콜백 또는 알림 스크립트로 처리 결과를 응답한다. */
    private void respondVerification(HttpServletResponse response,
                                     boolean verifySmsUser,
                                     String message,
                                     boolean success) throws IOException {
        if (verifySmsUser) {
            // 부모 창 콜백 응답
            KcpPopupResponse.callback(response, "smsTelNoRegister2VerifyCallback", message);
        } else {
            // 인증 결과 알림 후 팝업 종료
            KcpPopupResponse.alertAndClose(response, message, success);
        }
    }

    /** KCP 거래등록 결과에서 인증 팝업 호출에 필요한 값만 화면 응답에 담는다. */
    private void putRegistration(DefaultMap<String> target, KcpCertRegistration registration) {
        // 인증 팝업 호출에 필요한 거래등록 결과만 반환한다.
        target.put("call_url", registration.getCallUrl());
        target.put("reg_cert_key", registration.getRegCertKey());
        target.put("ordr_idxx", registration.getOrdrIdxx());
        target.put("kcp_page_submit_yn", registration.getKcpPageSubmitYn());
    }

    /** Redis 거래에 저장된 최초 요청자 정보로 업무 처리용 세션 객체를 복원한다. */
    private SessionInfoVO toSessionInfo(KcpCertTransaction transaction) {
        // Redis 거래의 사용자·소속·세션 정보를 복원한다.
        SessionInfoVO sessionInfoVO = new SessionInfoVO();
        sessionInfoVO.setSessionId(transaction.getSessionId());
        sessionInfoVO.setUserId(transaction.getUserId());
        sessionInfoVO.setOrgnCd(transaction.getOrgnCd());
        return sessionInfoVO;
    }

    /** 거래등록 예외 메시지가 있으면 기본 안내 문구에 덧붙여 반환한다. */
    private String registrationError(Exception e) {
        return isBlank(e.getMessage())
                ? "본인인증 거래등록에 실패했습니다."
                : "본인인증 거래등록에 실패했습니다. (" + e.getMessage() + ")";
    }

    /** 복호화 결과에서 대소문자 표기가 다른 후보 키를 순서대로 조회한다. */
    private String value(Map<String, Object> values, String... keys) {
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

    /** KCP 결과 코드가 있을 때만 사용자 안내 문구 뒤에 붙일 문자열을 만든다. */
    private String codeSuffix(String code) {
        return isBlank(code) ? "" : " (" + code + ")";
    }

}
