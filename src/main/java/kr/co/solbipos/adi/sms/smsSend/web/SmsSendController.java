package kr.co.solbipos.adi.sms.smsSend.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.AuthService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.auth.service.SmsVfcResultVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.adi.sms.smsSend.service.SmsSendService;
import kr.co.solbipos.adi.sms.smsSend.service.SmsSendVO;
import kr.co.solbipos.adi.sms.smsUserRegist.service.SmsUserRegistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : SmsSendController.java
 * @Description : 부가서비스 > SMS관리 > SMS전송
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.06.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/sms/smsSend")
public class SmsSendController {

    private final SessionService sessionService;
    private final SmsSendService smsSendService;
    private final SmsUserRegistService smsUserRegistService;
    private final AuthService authService;

    /** 마케팅 SMS C20 인증번호를 발송한 로그인 사용자 ID */
    private static final String SMS_SEND_VFC_USER_ID = "SMS_SEND_VFC_USER_ID";
    /** C20 인증번호를 실제 발송한 서버 시각 */
    private static final String SMS_SEND_VFC_SENT_AT = "SMS_SEND_VFC_SENT_AT";
    /** C20 인증번호를 요청한 전송구분(0: 즉시전송, 1: 예약전송) */
    private static final String SMS_SEND_VFC_REQUEST_RESERVE_YN = "SMS_SEND_VFC_REQUEST_RESERVE_YN";
    /** C21 성공 후 최종 저장에 한 번만 사용할 인증 토큰 */
    private static final String SMS_SEND_VFC_TOKEN = "SMS_SEND_VFC_TOKEN";
    /** C21 인증에 성공한 로그인 사용자 ID */
    private static final String SMS_SEND_VFC_VERIFIED_USER_ID = "SMS_SEND_VFC_VERIFIED_USER_ID";
    /** C21 인증에 성공한 서버 시각 */
    private static final String SMS_SEND_VFC_VERIFIED_AT = "SMS_SEND_VFC_VERIFIED_AT";
    /** C21 인증에 성공한 전송구분(0: 즉시전송, 1: 예약전송) */
    private static final String SMS_SEND_VFC_VERIFIED_RESERVE_YN = "SMS_SEND_VFC_VERIFIED_RESERVE_YN";
    /** 인증번호와 최종 저장 토큰의 서버 유효시간 3분 */
    private static final long SMS_SEND_VFC_VALID_MILLIS = 3L * 60L * 1000L;
    /** C20 추가인증번호 재전송 제한시간 1분 */
    private static final long SMS_SEND_VFC_RESEND_MILLIS = 60L * 1000L;

    /**
     * Constructor Injection
     */
    @Autowired
    public SmsSendController(SessionService sessionService, SmsSendService smsSendService,
                             SmsUserRegistService smsUserRegistService, AuthService authService) {
        this.sessionService = sessionService;
        this.smsSendService = smsSendService;
        this.smsUserRegistService = smsUserRegistService;
        this.authService = authService;
    }

    /**
     * 마케팅 SMS 전송 전 C20 추가인증번호 요청
     * 화면 요청 -> 로그인/전송구분 확인 -> 이전 인증토큰 초기화 -> C20 발송
     * -> 발송 사용자·시각·전송구분 저장 -> 화면 타이머 정보 반환 순서
     */
    @RequestMapping(value = "/smsSend/requestMarketingSmsVfcCode.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result requestMarketingSmsVfcCode(String reserveYn, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        if (sessionInfoVO == null || sessionInfoVO.getUserId() == null) {
            return returnJson(Status.FAIL, "로그인 정보를 확인할 수 없습니다.");
        }
        if (!"0".equals(reserveYn) && !"1".equals(reserveYn)) {
            return returnJson(Status.FAIL, "SMS 전송구분을 확인할 수 없습니다.");
        }

        // 이전 C21 성공 토큰·검증정보 제거 및 새 인증 요청과 혼용 방지
        clearSmsSendVfcToken(request);

        // 로그인 사용자 대상으로 DB C20 호출 및 추가 인증번호 발송 요청
        SmsVfcResultVO result = authService.requestAdditionalSmsVfcCode(sessionInfoVO.getUserId());
        if (result.isSent()) {
            request.getSession().setAttribute(SMS_SEND_VFC_USER_ID, sessionInfoVO.getUserId());
            request.getSession().setAttribute(SMS_SEND_VFC_SENT_AT, System.currentTimeMillis());
            // 실제 새 번호 발송 시 해당 번호의 전송/예약 용도 확정
            request.getSession().setAttribute(SMS_SEND_VFC_REQUEST_RESERVE_YN, reserveYn);
        } else if ("03".equals(result.getCode()) || "04".equals(result.getCode())) {
            // 잠금 또는 만료 결과 시 이전 C20 발송정보 제거
            clearSmsSendVfcRequest(request);
        }

        // DB 함수 결과를 화면용 코드·메시지·발송 여부 형태로 변환
        HashMap<String, Object> responseData = smsVfcResponseData(result);
        // 현재 세션의 C20 발송시각 기준 인증·재전송 남은 시간 응답 추가
        appendSmsVfcRemainTime(responseData, sessionInfoVO.getUserId(), reserveYn, request);
        return returnJson(Status.OK, responseData);
    }

    /**
     * 마케팅 SMS 전송 전 C21 추가인증번호 검증
     * 화면 검증 요청 -> 로그인/발송정보 확인 -> C21 검증 -> 1회용 토큰 발급
     * -> C20 발송정보 제거 순서
     */
    @RequestMapping(value = "/smsSend/verifyMarketingSmsVfcCode.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result verifyMarketingSmsVfcCode(String smsVfcNo, String reserveYn,
                                            HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        if (sessionInfoVO == null || sessionInfoVO.getUserId() == null) {
            return returnJson(Status.FAIL, "로그인 정보를 확인할 수 없습니다.");
        }

        // C21 호출 전 발송 사용자·전송구분·3분 유효시간 일치 여부 확인
        SmsVfcResultVO result = validateSmsVfcRequest(sessionInfoVO.getUserId(), reserveYn, request);
        if (result == null) {
            String verificationNo = smsVfcNo == null ? "" : smsVfcNo.trim();
            // 로그인 사용자와 입력 인증번호로 DB C21 호출
            result = authService.verifyAdditionalSmsVfcCode(sessionInfoVO.getUserId(), verificationNo);
        }

        // DB 함수 결과를 화면용 코드·메시지·발송 여부 형태로 변환
        HashMap<String, Object> responseData = smsVfcResponseData(result);
        responseData.put("verified", result.isSuccess());

        if (result.isSuccess()) {
            // 최종 마케팅 SMS 저장 API에서 사용자·전송구분을 재검증할 1회용 토큰 생성
            String smsVfcToken = UUID.randomUUID().toString();
            request.getSession().setAttribute(SMS_SEND_VFC_TOKEN, smsVfcToken);
            request.getSession().setAttribute(SMS_SEND_VFC_VERIFIED_USER_ID, sessionInfoVO.getUserId());
            request.getSession().setAttribute(SMS_SEND_VFC_VERIFIED_AT, System.currentTimeMillis());
            request.getSession().setAttribute(SMS_SEND_VFC_VERIFIED_RESERVE_YN, reserveYn);
            // C21 검증 완료 후 인증번호 발송정보 제거
            clearSmsSendVfcRequest(request);
            responseData.put("smsVfcToken", smsVfcToken);
        } else if ("03".equals(result.getCode()) || "04".equals(result.getCode())) {
            // 잠금 또는 만료된 C20 발송정보 세션 제거
            clearSmsSendVfcRequest(request);
        }

        return returnJson(Status.OK, responseData);
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
//    @RequestMapping(value = "/smsSend/list.sb", method = RequestMethod.GET)
//    public String smsSendView(HttpServletRequest request, HttpServletResponse response, Model model) {
//
//        return "adi/sms/smsSend/smsSend";
//    }

    /**
     * 발신번호 조회
     *
     * @param smsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 06. 14.
     */
    @RequestMapping(value = "/smsSend/getSmsTelNoComboList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsTelNoComboList(SmsSendVO smsSendVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = smsSendService.getSmsTelNoComboList(smsSendVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, smsSendVO);
    }

    /**
     * 관리자/총판/본사/매장 명칭 조회
     *
     * @param smsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 06. 14.
     */
    @RequestMapping(value = "/smsSend/getStoreNmList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreNmList(SmsSendVO smsSendVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<Object> result = smsSendService.getStoreNmList(smsSendVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 잔여금액 조회
     *
     * @param smsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 06. 14.
     */
    @RequestMapping(value = "/smsSend/getSmsAmtList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsAmtList(SmsSendVO smsSendVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<Object> result = smsSendService.getSmsAmtList(smsSendVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 전송,예약 저장
     *
     * @param smsSendVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 06. 14.
     */
    @RequestMapping(value = "/smsSend/getSmsSendReserveSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsSendReserveSave(@RequestBody SmsSendVO[] smsSendVOs, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = smsSendService.getSmsSendReserveSave(smsSendVOs, sessionInfoVO);

        if (result == -1) {
            HashMap<String, Object> blocked = new HashMap<String, Object>();
            blocked.put("blocked", true);
            // URL이 있으면 URL부터 확인
            if ("URL".equals(smsSendVOs[0].getBlockType())) {
                blocked.put("urlBlocked", true);
            } else {
                blocked.put("keyword", smsSendVOs[0].getKeyword());
            }
            return returnJson(Status.OK, blocked);
        }
        return returnJson(Status.OK, result);
    }

    /**
     * 데스크톱 마케팅 SMS 전송/예약 저장 - C21 추가인증 토큰 필수
     * 화면 저장 요청 -> 수신자별 토큰/전송구분 일치 확인 -> C21 토큰 검증·소비
     * -> 기존 SMS 전송/예약 저장 서비스 호출 순서
     */
    @RequestMapping(value = "/smsSend/getMarketingSmsSendReserveSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMarketingSmsSendReserveSave(@RequestBody SmsSendVO[] smsSendVOs,
                                                 HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        if (smsSendVOs == null || smsSendVOs.length == 0 || smsSendVOs[0] == null) {
            return returnJson(Status.FAIL, "전송할 SMS 정보가 없습니다.");
        }

        // 선택된 모든 수신자의 인증 토큰·전송구분 동일 여부 확인
        String smsVfcToken = smsSendVOs[0].getSmsVfcToken();
        String reserveYn = smsSendVOs[0].getReserveYn();
        for (SmsSendVO smsSendVO : smsSendVOs) {
            // 첫 번째 수신자와 현재 수신자의 토큰·전송구분 비교
            if (smsSendVO == null || !"marketingSmsSend".equals(smsSendVO.getPageGubun())
                    || !sameValue(smsVfcToken, smsSendVO.getSmsVfcToken())
                    || !sameValue(reserveYn, smsSendVO.getReserveYn())) {
                return returnJson(Status.FAIL, "SMS 전송정보가 올바르지 않습니다.");
            }
        }
        // 사용자·전송구분·3분 유효시간 확인 및 1회용 C21 토큰 즉시 소비
        if (!consumeSmsSendVfcToken(smsVfcToken, reserveYn, sessionInfoVO, request)) {
            return returnJson(Status.FAIL, "추가인증이 만료되었습니다. 다시 인증해주세요.");
        }

        int result = smsSendService.getSmsSendReserveSave(smsSendVOs, sessionInfoVO);
        if (result == -1) {
            HashMap<String, Object> blocked = new HashMap<String, Object>();
            blocked.put("blocked", true);
            if ("URL".equals(smsSendVOs[0].getBlockType())) {
                blocked.put("urlBlocked", true);
            } else {
                blocked.put("keyword", smsSendVOs[0].getKeyword());
            }
            return returnJson(Status.OK, blocked);
        }
        return returnJson(Status.OK, result);
    }

    /**
     * 전송,예약 1000건 저장
     *
     * @param smsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 10. 08.
     */
    @RequestMapping(value = "/smsSend/getSmsSendReserve1000Save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsSendReserve1000Save(@RequestBody SmsSendVO smsSendVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = smsSendService.getSmsSendReserve1000Save(smsSendVO, sessionInfoVO);

        if (result == -1) {
            HashMap<String, Object> blocked = new HashMap<String, Object>();
            blocked.put("blocked", true);
            // URL이 있으면 URL부터 확인
            if ("URL".equals(smsSendVO.getBlockType())) {
                blocked.put("urlBlocked", true);
            } else {
                blocked.put("keyword", smsSendVO.getKeyword());
            }
            return returnJson(Status.OK, blocked);
        }
        return returnJson(Status.OK, result);
    }

    /**
     * 데스크톱 마케팅 SMS 1000건 이상 전송/예약 저장 - C21 추가인증 토큰 필수
     * 화면 저장 요청 -> C21 토큰과 전송구분 검증·소비
     * -> 기존 1000건 이상 SMS 저장 서비스 호출 순서
     */
    @RequestMapping(value = "/smsSend/getMarketingSmsSendReserve1000Save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMarketingSmsSendReserve1000Save(@RequestBody SmsSendVO smsSendVO,
                                                     HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 사용자·전송구분·3분 유효시간 확인 및 1회용 C21 토큰 즉시 소비
        if (smsSendVO == null || !"marketingSmsSend".equals(smsSendVO.getPageGubun())
                || !consumeSmsSendVfcToken(smsSendVO.getSmsVfcToken(), smsSendVO.getReserveYn(),
                sessionInfoVO, request)) {
            return returnJson(Status.FAIL, "추가인증이 만료되었습니다. 다시 인증해주세요.");
        }

        int result = smsSendService.getSmsSendReserve1000Save(smsSendVO, sessionInfoVO);
        if (result == -1) {
            HashMap<String, Object> blocked = new HashMap<String, Object>();
            blocked.put("blocked", true);
            if ("URL".equals(smsSendVO.getBlockType())) {
                blocked.put("urlBlocked", true);
            } else {
                blocked.put("keyword", smsSendVO.getKeyword());
            }
            return returnJson(Status.OK, blocked);
        }
        return returnJson(Status.OK, result);
    }

    /**
     * 첨부파일 저장
     *
     * @return  Object
     * @author  김설아
     * @since   2021. 06. 14.
     */
    @RequestMapping(value = "/smsSend/getSmsSendFileSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsSendFileSave(MultipartHttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = smsSendService.getSmsSendFileSave(request, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 수신자추가 팝업 - 조회
     *
     * @param smsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 06. 14.
     */
    @RequestMapping(value = "/smsSend/getAddresseeAddList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAddresseeAddList(SmsSendVO smsSendVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = smsSendService.getAddresseeAddList(smsSendVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, result, smsSendVO);
    }

    /**
     * 일반번호 인증요청 팝업 - 저장
     *
     * @param smsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 01. 26.
     */
    @RequestMapping(value = "/smsGeneralNoRegister/getSmsGeneralNoRegisterSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsGeneralNoRegisterSave(@RequestBody SmsSendVO smsSendVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = smsSendService.getSmsGeneralNoRegisterSave(smsSendVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 일반번호 인증요청 팝업 - 첨부파일 저장
     *
     * @return  Object
     * @author  김설아
     * @since   2022. 01. 26.
     */
    @RequestMapping(value = "/smsGeneralNoRegister/getSmsGeneralNoFileSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsGeneralNoFileSave(MultipartHttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = smsSendService.getSmsGeneralNoFileSave(request, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 발신번호추가2 팝업 - 본인인증 여부 조회
     *
     * @param smsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 11. 12.
     */
    @RequestMapping(value = "/smsTelNoRegister2/getVerifyChk2.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVerifyChk2(SmsSendVO smsSendVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = smsSendService.getVerifyChk2(smsSendVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 발신번호추가2 팝업 - 저장 시 SMS사용등록 여부 + DI 재확인
     * (본인인증 성공 시 세션에 담아둔 DI와, 저장 시점의 SMS사용등록 DI가 여전히 일치하는지)
     *
     * @param   request
     * @return  Object
     * @author  김유승
     * @since   2026. 08. 03.
     */
    @RequestMapping(value = "/smsTelNoRegister2/getUserRegistDiChk.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getUserRegistDiChk(SmsSendVO smsSendVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        System.out.println("DI체크 진입 - certId : " + smsSendVO.getCertId());

        DefaultMap<Object> registInfo = smsUserRegistService.getUserRegistInfo(sessionInfoVO);

        // NOT_REGIST : SMS 사용등록 안됨, DI_MISMATCH : 본인인증 DI와 등록된 DI 불일치, OK : 통과
        String result;

        if (registInfo == null || registInfo.getStr("userId") == null || registInfo.getStr("userId").isEmpty()) {
            result = "NOT_REGIST";
        } else {
            String verifiedDi = smsSendService.getAddSmsNoDi(smsSendVO, sessionInfoVO);
            if (verifiedDi == null || !verifiedDi.equals(registInfo.getStr("di"))) {
                result = "DI_MISMATCH";
            } else {
                result = "OK";
            }
        }

        return returnJson(Status.OK, result);
    }

    /**
     * 발신번호추가2 팝업 - 저장
     *
     * @param smsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 11. 12.
     */
    @RequestMapping(value = "/smsTelNoRegister2/getSmsTelNoRegister2Save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsTelNoRegister2Save(@RequestBody SmsSendVO smsSendVO, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = smsSendService.getSmsTelNoRegister2Save(smsSendVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 발신번호추가2 팝업 - 첨부파일 저장
     *
     * @return  Object
     * @author  김설아
     * @since   2024. 11. 12.
     */
    @RequestMapping(value = "/smsTelNoRegister2/getSmsTelNoRegister2FileSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsTelNoRegister2FileSave(MultipartHttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = smsSendService.getSmsTelNoRegister2FileSave(request, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * SMS전송 - 전송, 예약 권한 확인
     *
     * @param   smsSendVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2026. 07. 27.
     */
    @RequestMapping(value = "/smsSend/getChkRegUserInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getChkRegUserInfo(SmsSendVO smsSendVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = smsSendService.getChkRegUserInfo(smsSendVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * SMS전송 - 서류인증신청 번호 수량 확인
     *
     * @param   smsSendVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2026. 07. 27.
     */
    @RequestMapping(value = "/smsTelNoRegister2/getChkRegInfoCnt.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getChkRegInfoCnt(SmsSendVO smsSendVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = smsSendService.getChkRegInfoCnt(smsSendVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /** SMS 인증 함수 결과를 추가인증 팝업 응답 데이터로 변환 */
    private HashMap<String, Object> smsVfcResponseData(SmsVfcResultVO result) {
        HashMap<String, Object> responseData = new HashMap<String, Object>();
        responseData.put("code", result.getCode());
        responseData.put("message", result.getMessage());
        responseData.put("sent", result.isSent());
        return responseData;
    }

    /** 현재 세션에 유효한 C20 발송 이력이 있으면 화면 타이머의 남은 시간을 반환 */
    private void appendSmsVfcRemainTime(HashMap<String, Object> responseData, String userId,
                                        String reserveYn, HttpServletRequest request) {
        Object sentUserId = request.getSession().getAttribute(SMS_SEND_VFC_USER_ID);
        Object sentAtValue = request.getSession().getAttribute(SMS_SEND_VFC_SENT_AT);
        Object requestedReserveYn = request.getSession().getAttribute(SMS_SEND_VFC_REQUEST_RESERVE_YN);
        if (!userId.equals(sentUserId) || !(sentAtValue instanceof Number)) {
            responseData.put("requested", false);
            return;
        }

        long elapsedMillis = System.currentTimeMillis() - ((Number) sentAtValue).longValue();
        if (elapsedMillis < 0L || elapsedMillis >= SMS_SEND_VFC_VALID_MILLIS) {
            // 애플리케이션 유효시간이 지난 C20 발송정보 세션 제거
            clearSmsSendVfcRequest(request);
            responseData.put("requested", false);
            return;
        }

        long expireMillis = SMS_SEND_VFC_VALID_MILLIS - elapsedMillis;
        long resendMillis = Math.max(0L, SMS_SEND_VFC_RESEND_MILLIS - elapsedMillis);
        responseData.put("resendSeconds", (resendMillis + 999L) / 1000L);
        if (!reserveYn.equals(requestedReserveYn)) {
            // 다른 용도로 발송한 번호는 검증할 수 없지만 DB의 재전송 대기시간은 화면에 유지한다.
            responseData.put("requested", false);
            return;
        }

        responseData.put("requested", true);
        responseData.put("expireSeconds", (expireMillis + 999L) / 1000L);
    }

    /** C21 호출 전 발송 사용자, 전송구분과 인증번호 유효시간 3분을 서버에서 확인 */
    private SmsVfcResultVO validateSmsVfcRequest(String userId, String reserveYn,
                                                 HttpServletRequest request) {
        Object sentUserId = request.getSession().getAttribute(SMS_SEND_VFC_USER_ID);
        Object sentAtValue = request.getSession().getAttribute(SMS_SEND_VFC_SENT_AT);
        Object requestedReserveYn = request.getSession().getAttribute(SMS_SEND_VFC_REQUEST_RESERVE_YN);

        if (!userId.equals(sentUserId) || !(sentAtValue instanceof Number)
                || (!"0".equals(requestedReserveYn) && !"1".equals(requestedReserveYn))) {
            return new SmsVfcResultVO("04", "추가 인증번호를 먼저 요청해주세요.");
        }
        if ((!("0".equals(reserveYn) || "1".equals(reserveYn)))
                || !reserveYn.equals(requestedReserveYn)) {
            // DB 결과코드와 구분되는 서버 검증 코드로 기존 발송 이력은 재전송 대기시간 동안 유지한다.
            return new SmsVfcResultVO("RESERVE_MISMATCH",
                    "전송 구분이 변경되었습니다.\n추가 인증번호를 다시 요청해주세요.");
        }

        long elapsedMillis = System.currentTimeMillis() - ((Number) sentAtValue).longValue();
        if (elapsedMillis < 0L || elapsedMillis >= SMS_SEND_VFC_VALID_MILLIS) {
            // 만료된 C20 발송정보 세션 제거 및 재검증 방지
            clearSmsSendVfcRequest(request);
            return new SmsVfcResultVO("04", "추가 인증번호가 유효하지 않습니다.\n인증번호를 다시 요청해주세요.");
        }

        return null;
    }

    /** 최종 전송/예약 저장 시 C21 성공 토큰을 검증하고 즉시 소비 */
    private boolean consumeSmsSendVfcToken(String requestToken, String reserveYn,
                                           SessionInfoVO sessionInfoVO, HttpServletRequest request) {
        Object sessionToken = request.getSession().getAttribute(SMS_SEND_VFC_TOKEN);
        Object verifiedUserId = request.getSession().getAttribute(SMS_SEND_VFC_VERIFIED_USER_ID);
        Object verifiedAtValue = request.getSession().getAttribute(SMS_SEND_VFC_VERIFIED_AT);
        Object verifiedReserveYn = request.getSession().getAttribute(SMS_SEND_VFC_VERIFIED_RESERVE_YN);

        if (sessionInfoVO == null || sessionInfoVO.getUserId() == null
                || requestToken == null || !requestToken.equals(sessionToken)
                || !sessionInfoVO.getUserId().equals(verifiedUserId)
                || (!"0".equals(reserveYn) && !"1".equals(reserveYn))
                || !reserveYn.equals(verifiedReserveYn)
                || !(verifiedAtValue instanceof Number)) {
            return false;
        }

        long elapsedMillis = System.currentTimeMillis() - ((Number) verifiedAtValue).longValue();
        if (elapsedMillis < 0L || elapsedMillis >= SMS_SEND_VFC_VALID_MILLIS) {
            // 만료된 C21 성공 토큰·검증정보 세션 제거
            clearSmsSendVfcToken(request);
            return false;
        }

        // 검증을 통과한 C21 성공 토큰을 최종 저장 전 1회 소비
        clearSmsSendVfcToken(request);
        return true;
    }

    /** 다건 전송 배열의 추가인증 토큰과 예약구분이 모두 같은지 비교 */
    private boolean sameValue(String first, String second) {
        return first == null ? second == null : first.equals(second);
    }

    /** C20 발송 사용자와 발송시각 세션값 제거 */
    private void clearSmsSendVfcRequest(HttpServletRequest request) {
        request.getSession().removeAttribute(SMS_SEND_VFC_USER_ID);
        request.getSession().removeAttribute(SMS_SEND_VFC_SENT_AT);
        request.getSession().removeAttribute(SMS_SEND_VFC_REQUEST_RESERVE_YN);
    }

    /** C21 성공 후 발급한 1회용 토큰과 인증 대상 세션값 제거 */
    private void clearSmsSendVfcToken(HttpServletRequest request) {
        request.getSession().removeAttribute(SMS_SEND_VFC_TOKEN);
        request.getSession().removeAttribute(SMS_SEND_VFC_VERIFIED_USER_ID);
        request.getSession().removeAttribute(SMS_SEND_VFC_VERIFIED_AT);
        request.getSession().removeAttribute(SMS_SEND_VFC_VERIFIED_RESERVE_YN);
    }
}
