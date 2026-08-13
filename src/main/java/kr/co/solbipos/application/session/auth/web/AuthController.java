package kr.co.solbipos.application.session.auth.web;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.exception.AuthenticationException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.SessionUtil;
import kr.co.common.utils.spring.WebUtil;
import kr.co.common.validate.Login;
import kr.co.solbipos.application.session.auth.enums.LoginResult;
import kr.co.solbipos.application.session.auth.service.AuthService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.auth.service.SmsVfcResultVO;
import kr.co.solbipos.mobile.application.session.auth.enums.LoginFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static kr.co.common.utils.HttpUtils.getClientIp;
import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static org.springframework.util.ObjectUtils.isEmpty;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
/**
 * @Class Name : AuthController.java
 * @Description : 어플리케이션 > 세션 > 인증
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  정용길      최초생성
 *
 * @author NHN한국사이버결제 KCP 정용길
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/auth")
public class AuthController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final RedisConnectionFactory redisConnectionFactory;

    @Autowired
    AuthService authService;
    @Autowired
    SessionService sessionService;
    @Autowired
    MessageService messageService;
    @Autowired
    kr.co.common.service.code.CmmCodeService cmmCodeService;

    /** 로그인 성공 후 기본 이동 URL */
    final String MAIN_PAGE_URL = "main.sb";
    /** 일반 로그인에서 인증번호를 발송한 USER_ID를 보관하는 세션 키 */
    private static final String LOGIN_SMS_VFC_USER_ID = "LOGIN_SMS_VFC_USER_ID";
    /** 일반 로그인에서 인증번호를 발송한 서버 시각을 보관하는 세션 키 */
    private static final String LOGIN_SMS_VFC_SENT_AT = "LOGIN_SMS_VFC_SENT_AT";
    /** POS 자동로그인에서 인증번호를 발송한 USER_ID를 보관하는 세션 키 */
    private static final String POS_SMS_VFC_USER_ID = "POS_SMS_VFC_USER_ID";
    /** POS 자동로그인에서 인증번호를 발송한 서버 시각을 보관하는 세션 키 */
    private static final String POS_SMS_VFC_SENT_AT = "POS_SMS_VFC_SENT_AT";
    /** 인증번호 발송 후 검증 가능한 시간: 3분 */
    private static final long SMS_VFC_VALID_MILLIS = 3 * 60 * 1000L;
    /** 인증번호 발송 후 재전송을 제한하는 시간: 30초 */
    private static final long SMS_VFC_RESEND_MILLIS = 30 * 1000L;
    /** POS 자동로그인 SMS 인증 대기화면 URL */
    private static final String POS_SMS_VFC_URL = "/auth/posSmsVfc.sb";

    // 차단할 클라우드 IP 대역 (CIDR 형식)
    // 예: 3.0.0.0/8 -> 3.x.x.x 전체 범위
    private static final String[] cloudCidrs = {
            // AWS
            "3.0.0.0/8",
            "13.0.0.0/8",
            "18.0.0.0/8",
            "52.0.0.0/8",
            "54.0.0.0/8",
            // GCP
            "34.0.0.0/8",
            "35.0.0.0/8",
            // Azure - 실제접속없음 모두 제거
            // 개별 차단 IP
            "136.107.0.0/16",
            "44.196.52.83/32",
            "162.220.234.0/24",
            "79.127.159.104/32",
            "8.228.0.0/16"
    };

    @Autowired
    public AuthController(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    /**
     * <pre>
     * 로그인 페이지로 이동
     * </pre>
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "login.sb", method = RequestMethod.GET)
    public String login(String userId, String type, String smsAuth, HttpServletRequest request, HttpServletResponse response, Model model) {

        if (sessionService.isValidSession(request)) {

            // 기존 세션이 있는 POS 요청을 처리한 후 이동할 URL이다.
            String returnUrl = MAIN_PAGE_URL;

            // POS에서 WEB화면 접근 시 WEB 로그인 세션이 이미 있는 경우
            if (isPosAccessCd(request.getParameter("accessCd"))) {
                // 현재 Redis/WAS 세션에 저장된 사용자 정보에 POS 인증 플래그를 반영한다.
                SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
                // resrceCd가 있으면 특정 메뉴 직접이동이므로 POS SMS 인증 대상에서 제외한다.
                boolean hasResrceCd = hasRequestValue(request.getParameter("resrceCd"));

                sessionInfoVO.setAccessCdYn("Y");
                sessionInfoVO.setResrceCdYn(hasResrceCd ? "Y" : "N");

                if (hasResrceCd) {
                    sessionInfoVO.setResrceCd(request.getParameter("resrceCd"));
                    if (sessionInfoVO.getSmsVfcYn() == null) {
                        // 메뉴 직접이동은 인증 대상이 아니지만, SMS 인증 완료로 처리하지 않는다.
                        sessionInfoVO.setSmsVfcYn("N");
                    }

                    // resrceCd에 대응하는 실제 메뉴 이동 URL이다.
                    String posLoginReturnUrl = authService.getPosLoginReturnUrl(sessionInfoVO);

                    if(!isEmpty(posLoginReturnUrl)){

                        LOGGER.info("posLoginReturnUrl 값 : " + posLoginReturnUrl);

                        if("/".equals(posLoginReturnUrl.substring(0, 1))){
                            returnUrl = posLoginReturnUrl.substring(1, posLoginReturnUrl.length());
                        }else{
                            returnUrl = posLoginReturnUrl;
                        }

                        // view화면 처리시 사용
                        returnUrl += "?posLoginReconnect=Y";
                    }
                } else if (!"Y".equals(sessionInfoVO.getSmsVfcYn())) {
                    setPosSmsVfcRequirement(sessionInfoVO);
                }

                // 새 POS 로그인의 인증 대기 중 SMS 인증이 불필요한 경로로 바뀌면 성공 반영을 완료한다.
                if (isPosLoginCompletePending(sessionInfoVO) && !isPosSmsVfcPending(sessionInfoVO)) {
                    authService.completeLogin(sessionInfoVO);
                    sessionInfoVO.setPosLoginCompletePendingYn("N");
                }

                updateSessionInfo(request, sessionInfoVO);
                if (isPosSmsVfcPending(sessionInfoVO)) {
                    return "redirect:" + POS_SMS_VFC_URL;
                }
            }

            return "redirect:/" + returnUrl;
        }

        model.addAttribute("userId", userId);
        model.addAttribute("type", isEmpty(type) ? "" : type);
        model.addAttribute("smsAuth", isEmpty(smsAuth) ? "" : smsAuth);
        setSmsVfcTimerModel(userId, request, model, LOGIN_SMS_VFC_USER_ID, LOGIN_SMS_VFC_SENT_AT);
        // 토큰 생성, 셋팅
        String token = UUID.randomUUID().toString();
        request.getSession().setAttribute("LOGIN_CHK_TOKEN", token);
        return "login/login:Login";
    }

    /** USER_ID 기준 SMS 사용 등록 여부 조회 */
    @RequestMapping(value = "loginSmsUserRegistYn.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsUserRegistYn(String userId) {

        if (userId == null || userId.trim().isEmpty()) {
            return returnJson(Status.OK, "smsUserRegistYn", "N");
        }

        // CHK 함수의 SMS 사용 등록 여부 코드와 사용자 안내 메시지이다.
        SmsVfcResultVO result = authService.checkSmsUser(userId.trim());
        if (result.isSuccess()) {
            return returnJson(Status.OK, "smsUserRegistYn", "Y");
        }
        if ("01".equals(result.getCode())) {
            return returnJson(Status.OK, "smsUserRegistYn", "N");
        }

        // SMS 사용 여부를 확인할 수 없으면 로그인을 진행하지 않는다.
        return returnJson(Status.FAIL, result);
    }

    /** 로그인 SMS 인증번호 요청 */
    @RequestMapping(value = "loginSmsVfcCodeSend.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result requestLoginSmsVfcCode(String userId, HttpServletRequest request) {

        if (userId == null || userId.trim().isEmpty()) {
            return returnJson(Status.OK, new SmsVfcResultVO("01", "아이디를 입력하여 주십시오."));
        }

        // 공백을 제거한 실제 인증번호 발송 대상 USER_ID이다.
        String targetUserId = userId.trim();
        // C10 함수의 인증번호 발송 결과이다.
        SmsVfcResultVO result = authService.requestLoginSmsVfcCode(targetUserId);
        if (result.isSent()) {
            request.getSession().setAttribute(LOGIN_SMS_VFC_USER_ID, targetUserId);
            request.getSession().setAttribute(LOGIN_SMS_VFC_SENT_AT, System.currentTimeMillis());
        }

        return returnJson(Status.OK, result);
    }

    /** POS 자동로그인 SMS 인증 대기화면 */
    @RequestMapping(value = "posSmsVfc.sb", method = RequestMethod.GET)
    public String posSmsVfc(HttpServletRequest request, Model model) {

        // POS 자동로그인 완료 후 생성된 현재 사용자 세션 정보이다.
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        if (!isPosSmsVfcPending(sessionInfoVO)) {
            return "redirect:/main.sb";
        }

        setSmsVfcTimerModel(sessionInfoVO.getUserId(), request, model,
                POS_SMS_VFC_USER_ID, POS_SMS_VFC_SENT_AT);
        return "application/pos/autoLoginSmsVfc";
    }

    /** POS 자동로그인 인증번호 요청 */
    @RequestMapping(value = "posSmsVfcCodeSend.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result requestPosSmsVfcCode(HttpServletRequest request, HttpServletResponse response) {

        // 요청 파라미터 대신 로그인 세션의 USER_ID를 발송 대상으로 사용한다.
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        if (!isPosSmsVfcPending(sessionInfoVO)) {
            return returnJson(Status.FAIL, "SMS 인증 대기 상태가 아닙니다.");
        }

        // C10 함수의 POS 인증번호 발송 결과이다.
        SmsVfcResultVO result = authService.requestLoginSmsVfcCode(sessionInfoVO.getUserId());
        if (result.isSent()) {
            request.getSession().setAttribute(POS_SMS_VFC_USER_ID, sessionInfoVO.getUserId());
            request.getSession().setAttribute(POS_SMS_VFC_SENT_AT, System.currentTimeMillis());
        }

        // 인증 대기화면에 반환할 발송 결과 데이터이다.
        Map<String, Object> responseData = smsVfcResponseData(result);
        // 코드 03은 요청/실패 횟수 초과 잠금이므로 세션을 즉시 종료한다.
        boolean forceLogout = "03".equals(result.getCode());
        responseData.put("forceLogout", forceLogout);
        if (forceLogout) {
            logoutPosSmsVfc(request, response);
            responseData.put("url", "/auth/login.sb");
        }
        return returnJson(Status.OK, responseData);
    }

    /** POS 자동로그인 인증번호 검증 */
    @RequestMapping(value = "posSmsVfcCodeVerify.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result verifyPosSmsVfcCode(String smsVfcNo, HttpServletRequest request, HttpServletResponse response) {

        // 인증 성공 여부를 반영할 현재 POS 자동로그인 사용자 세션이다.
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        if (!isPosSmsVfcPending(sessionInfoVO)) {
            return returnJson(Status.FAIL, "SMS 인증 대기 상태가 아닙니다.");
        }

        // DB 검증 전에 발송 계정 일치 여부와 애플리케이션 기준 3분을 확인한 결과이다.
        // 유효하면 null이며, 유효하지 않으면 화면에 반환할 실패 결과가 들어간다.
        SmsVfcResultVO result = validateSmsVfcRequest(
                sessionInfoVO.getUserId(), request, POS_SMS_VFC_USER_ID, POS_SMS_VFC_SENT_AT);
        if (result == null) {
            // C11 함수에 전달할 사용자의 6자리 인증번호이다.
            String verificationNo = smsVfcNo == null ? "" : smsVfcNo.trim();
            result = authService.verifyLoginSmsVfcCode(sessionInfoVO.getUserId(), verificationNo);
        }

        // 인증 대기화면에서 성공 이동 또는 실패 메시지 처리에 사용할 응답 데이터이다.
        Map<String, Object> responseData = smsVfcResponseData(result);
        responseData.put("verified", result.isSuccess());
        responseData.put("forceLogout", false);

        if (result.isSuccess()) {
            // 새 POS 자동로그인은 C11 성공 시점에만 마지막 로그인 일시와 성공 이력을 반영한다.
            if (isPosLoginCompletePending(sessionInfoVO)) {
                authService.completeLogin(sessionInfoVO);
                sessionInfoVO.setPosLoginCompletePendingYn("N");
            }
            sessionInfoVO.setSmsVfcYn("Y");
            updateSessionInfo(request, sessionInfoVO);
            clearPosSmsVfc(request);
            responseData.put("url", "/main.sb");
        } else {
            // 인증 실패 이력만 남긴 뒤 정상 로그인 세션 상태를 복원하기 위한 원본 결과값이다.
            LoginResult loginResult = sessionInfoVO.getLoginResult();
            sessionInfoVO.setLoginResult(LoginResult.FAIL);
            authService.loginHist(sessionInfoVO);
            sessionInfoVO.setLoginResult(loginResult);

            if ("03".equals(result.getCode())) {
                responseData.put("forceLogout", true);
                logoutPosSmsVfc(request, response);
                responseData.put("url", "/auth/login.sb");
            }
        }

        return returnJson(Status.OK, responseData);
    }

    /**
     * <pre>
     * 사용자 웹 로그인
     * </pre>
     * @param params
     * @param bindingResult
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "login.sb", method = RequestMethod.POST)
    public String loginProcess(@Validated(Login.class) SessionInfoVO params,
                               BindingResult bindingResult, String smsVfcNo,
                               HttpServletRequest request, HttpServletResponse response,
                               Model model) {

        StopWatch sw = new StopWatch();
        sw.start();

        LOGGER.info("login start : {} ", params.getUserId());

        if (bindingResult.hasErrors()) {
            return "login/login:Login";
        }

        // 로그인 시 특정아이디 블락
        if (CmmUtil.isBlockedId(params.getUserId())) {
            // 세션 삭제
            sessionService.deleteSessionInfo(request);
            throw new AuthenticationException(messageService.get("login.fail"), "/error/403.sb");
        }

        // 아이디 저장 쿠키 처리
        WebUtil.setCookie(BaseEnv.LOGIN_CHECK_ID_SAVE, params.getUserId(), params.isChk() ? 30*24*60*6 : 0);

        // 웹에서 로그인 시, 모바일 로그인 여부 쿠키 제거
        WebUtil.removeCookie(WebUtils.getCookie( request, BaseEnv.SB_LOGIN_FG ));

        // 웹에서 로그인 시, 자동로그인 쿠키 제거
        WebUtil.removeCookie(WebUtils.getCookie( request, BaseEnv.SB_LOGIN_AUTO_SERIAL ));

        params.setLoginIp(getClientIp(request));
        params.setBrwsrInfo(request.getHeader("User-Agent"));
        params.setServerInstance(System.getProperty("server.instance", "unknown"));

        // 접속정보 가져오기
        String userId = params.getUserId();
        String ip = request.getRemoteAddr();
        String failUrl = "/auth/login.sb?userId=" + userId;

        // 로그인 시도 횟수 확인 (userId, IP) 기준
        Long count = isLoginAllowed(userId, ip);
        // 로그인 시도 횟수 확인 (userId) 기준
        Long idCount = isLoginAllowed(userId);
        String token = (String) request.getSession().getAttribute("LOGIN_CHK_TOKEN");
        StringBuilder log = new StringBuilder();

        try {
            // 로그인 시도 값 체크
            if (count > 3) {
                // 로그인 시도 제한 체크
                appendLoginLog(log, "err2", "로그인 시도 제한(userId+ip) (count > 3)", request, params, token, count, "제한");

                // 특정 아이디만 세션 삭제
                if (userId.equals("momse08053") || userId.equals("momse10160") || userId.equals("momse09686") || userId.equals("kjsun11177") || userId.equals("ds053") || userId.equals("ds00501") || userId.startsWith("h0268")) {
                    // 세션 삭제
                    sessionService.deleteSessionInfo(request);
                    // 제한 초과 처리
                    params.setLoginResult(LoginResult.MANY_ATTEMPTS);
                    authService.loginHist(params);
                    throw new AuthenticationException(messageService.get("login.fail"), "/error/403.sb");
                }
            } else if (idCount > 3) {
                // 로그인 시도 제한 체크
                appendLoginLog(log, "err5", "로그인 시도 제한(userId) (count > 3)", request, params, token, count, "제한");

                // 특정 아이디만 세션 삭제
                if (userId.equals("momse08053") || userId.equals("momse10160") || userId.equals("momse09686") || userId.equals("kjsun11177") || userId.equals("ds053") || userId.equals("ds00501") || userId.startsWith("h0268")) {
                    // 세션 삭제
                    sessionService.deleteSessionInfo(request);
                    // 제한 초과 처리
                    params.setLoginResult(LoginResult.MANY_ATTEMPTS_USERID);
                    authService.loginHist(params);
                    throw new AuthenticationException(messageService.get("login.fail"), "/error/403.sb");
                }
            } else if (token == null || token.isEmpty()) {
                // 토큰 보유 여부 확인
                appendLoginLog(log, "err1", "세션 토큰 값 없음", request, params, token, null, "제한");

                // 특정 아이디만 세션 삭제
                if (userId.equals("momse08053") || userId.equals("momse10160") || userId.equals("momse09686") || userId.equals("kjsun11177") || userId.equals("ds053") || userId.equals("ds00501") || userId.startsWith("h0268")) {
                    // 세션 삭제
                    sessionService.deleteSessionInfo(request);
                    params.setLoginResult(LoginResult.TOKEN_ERROR);
                    authService.loginHist(params);
                    throw new AuthenticationException(messageService.get("login.fail"), "/error/403.sb");
                }
            } else if ("".equals(request.getHeader("User-Agent")) || request.getHeader("User-Agent") == null || request.getHeader("User-Agent").contains("python")
                    || request.getHeader("User-Agent").contains("curl") || request.getHeader("User-Agent").contains("wget")
                    || request.getHeader("User-Agent").contains("bot") || request.getHeader("User-Agent").contains("node")
                    || "".equals(request.getHeader("Accept")) || request.getHeader("Accept") == null
                    || !request.getHeader("Accept").contains("text/html") || "".equals(request.getHeader("referer"))
                    || request.getHeader("referer") == null || !request.getHeader("referer").contains("/auth/login.sb")) {

                // 브라우저 확인
                appendLoginLog(log, "err3", "브라우저 이상", request, params, token, null, "제한");

                // 특정 아이디만 세션 삭제
                if (userId.equals("momse08053") || userId.equals("momse10160") || userId.equals("momse09686") || userId.equals("kjsun11177") || userId.equals("ds053") || userId.equals("ds00501") || userId.startsWith("h0268")) {
                    // 세션 삭제
                    sessionService.deleteSessionInfo(request);
                    params.setLoginResult(LoginResult.CHK_BROWSER);
                    authService.loginHist(params);
                    throw new AuthenticationException(messageService.get("login.fail"), "/error/403.sb");
                }
            } else if (isCloudIp(params.getLoginIp())) {
                appendLoginLog(log, "err4", "클라우드 IP 로그인", request, params, token, count, "제한");

                // 특정 아이디만 세션 삭제
                if (userId.equals("momse08053") || userId.equals("momse10160") || userId.equals("momse09686") || userId.equals("kjsun11177") || userId.equals("ds053") || userId.equals("ds00501") || userId.startsWith("h0268")) {
                    // 세션 삭제
                    sessionService.deleteSessionInfo(request);
                    params.setLoginResult(LoginResult.CLOUD_IP_LOGIN);
                    authService.loginHist(params);
                    throw new AuthenticationException(messageService.get("login.fail"), "/error/403.sb");
                }
            } else {
                appendLoginLog(log, "succ", "로그인 시도 성공", request, params, token, count, "성공");
            }
        }
        finally {
            // 로그파일 생성
            makeLoginLog(log);
            LOGGER.info(log.toString());
        }

        // TB_WB_LOGIN_HIST에 세션ID 저장
        params.setSessionId(request.getSession().getId());
        // 로그인 화면이 accessCd를 userPwd에 담아 전송하므로 장문 여부로 POS 자동로그인을 구분한다.
        boolean posAutoLogin = params.getUserPwd() != null && params.getUserPwd().length() > 30;
        // 일반 로그인은 비밀번호, POS 자동로그인은 DB의 accessCd를 검증한 사용자 정보이다.
        SessionInfoVO result = authService.authenticate(params);
        // 계정 존재, 비밀번호/accessCd 일치, 계정 상태를 포함한 로그인 결과 코드이다.
        LoginResult code = result.getLoginResult();

        /**
         * 1. 성공 : 메인 페이지로 이동<br>
         * 2. 실패<br>
         * 2-1. 메세지와 함께 로그인 페이지로 이동<br>
         * 2-2. 패스워드 변경 페이지로 이동<br>
         */
        String returnUrl = MAIN_PAGE_URL;
        // 로그인 성공
        if (code == LoginResult.SUCCESS) {

            // POS 자동로그인은 이번 웹 로그인 SMS 인증 대상에서 제외한다.
            if (!posAutoLogin) {
                // CHK 함수로 일반 로그인 사용자의 SMS 인증 대상 여부를 확인한다.
                SmsVfcResultVO smsUserResult = authService.checkSmsUser(result.getUserId());
                if (!smsUserResult.isSuccess() && !"01".equals(smsUserResult.getCode())) {
                    result.setLoginResult(LoginResult.FAIL);
                    authService.loginHist(result);
                    throw new AuthenticationException(escapeJavaScriptMessage(smsUserResult.getMessage()), failUrl);
                }

                if (smsUserResult.isSuccess()) {
                    // 인증번호를 요청한 계정인지와 발송 후 3분 이내인지 확인한 결과이다.
                    SmsVfcResultVO requestResult = validateSmsVfcRequest(
                            result.getUserId(), request, LOGIN_SMS_VFC_USER_ID, LOGIN_SMS_VFC_SENT_AT);
                    if (requestResult != null) {
                        result.setLoginResult(LoginResult.FAIL);
                        authService.loginHist(result);
                        throw new AuthenticationException(escapeJavaScriptMessage(requestResult.getMessage()), failUrl + "&smsAuth=Y");
                    }

                    // C11 함수에 전달할 로그인 화면의 6자리 인증번호이다.
                    String verificationNo = smsVfcNo == null ? "" : smsVfcNo.trim();
                    // C11 함수의 인증번호 일치 여부와 실패 횟수 결과이다.
                    SmsVfcResultVO smsResult = authService.verifyLoginSmsVfcCode(result.getUserId(), verificationNo);
                    if (!smsResult.isSuccess()) {
                        result.setLoginResult(LoginResult.FAIL);
                        authService.loginHist(result);
                        throw new AuthenticationException(escapeJavaScriptMessage(smsResult.getMessage()), failUrl + "&smsAuth=Y");
                    }
                }
            }

            // 로그인 방식에 따라 POS 자동로그인 SMS 인증 상태를 세션에 기록한다.
            setLoginSmsVfcSessionState(result, posAutoLogin, params.getResrceCd(), failUrl);

            // POS SMS 인증 대기 상태가 아니면 현재 요청에서 로그인 성공 정보를 즉시 반영한다.
            // 인증 대기 상태는 C11 성공 시점까지 마지막 로그인 일시와 성공 이력 반영을 미룬다.
            if (!isPosLoginCompletePending(result)) {
                authService.completeLogin(result);
            }
            clearLoginSmsVfc(request);

            // VO객체 세션값 셋팅
            result.setLoginChkToken(token);

            // 공지 폴링 활성화 여부 조회 (NMCODE_GRP_CD='184', NMCODE_CD='0000')
            DefaultMap<String> noticePollingConfig = cmmCodeService.getNoticePollingEnabled();
            result.setNoticePollingEnabled(noticePollingConfig != null ? noticePollingConfig.get("nmcodeNm") : "0");

            // 메인 페이지로
            // 세션 생성
            sessionService.setSessionInfo(request, response, result);

            // POS 자동 로그인 return URL 조회
            if(params.getUserPwd().length() > 30){

                LOGGER.info("resrceCd 값 : " + params.getResrceCd());

                // resrceCd가 있는 POS 요청의 실제 메뉴 이동 URL이다.
                String posLoginReturnUrl = authService.getPosLoginReturnUrl(params);
                if(!isEmpty(posLoginReturnUrl)){

                    LOGGER.info("posLoginReturnUrl 값 : " + posLoginReturnUrl);

                    if("/".equals(posLoginReturnUrl.substring(0, 1))){
                        returnUrl = posLoginReturnUrl.substring(1, posLoginReturnUrl.length());
                    }else{
                        returnUrl = posLoginReturnUrl;
                    }
                }
            }

            // SMS 인증이 필요한 POS 자동로그인은 메인화면보다 인증 대기화면을 먼저 연다.
            if (isPosSmsVfcPending(result)) {
                returnUrl = POS_SMS_VFC_URL.substring(1);
            }

            LOGGER.info("returnUrl 값 : " + returnUrl);

        } else if (code == LoginResult.NOT_EXISTS_ID || code == LoginResult.PASSWORD_ERROR) {
            // 다시 로그인 페이지로 이동
            returnUrl = failUrl;
            throw new AuthenticationException(messageService.get("login.idpw.fail"), returnUrl);
        } else if (code == LoginResult.NOT_USE_ID) {
            // 사용하지 않는 유저
            returnUrl = failUrl;
            throw new AuthenticationException(messageService.get("login.pw.find.not.use"), returnUrl);
        } else if (code == LoginResult.PASSWORD_TEMPORARY) {
            // 패스워드 변경 레이어 팝업
            // 초기 비밀번호 입니다. 비밀번호 변경이 필요합니다.
            returnUrl = failUrl + "&type=pwChg";
            throw new AuthenticationException(messageService.get("login.pwd.chg"), returnUrl);
        }
        else if(code == LoginResult.LOGIN_FAIL_CNT_OVER) {
            // 로그인 오류 횟수 초과
            returnUrl = failUrl;
            throw new AuthenticationException(messageService.get("login.fail.cnt"), returnUrl);
        }
        else if(code == LoginResult.PASSWORD_EXPIRE) {
            // 비밀번호 변경 및 연장이 필요합니다.
            returnUrl = failUrl + "&type=pwExpire";
            throw new AuthenticationException(messageService.get("login.pwd.expire"), returnUrl);
        }
        else if(code == LoginResult.DORMANT_ACCOUNT) {
            // 비밀번호 변경 및 연장이 필요합니다.
            returnUrl = failUrl + "&type=pwDormant";
            throw new AuthenticationException(messageService.get("login.pwd.dormant"), returnUrl);
        }
        else if (code == LoginResult.NOT_WEB_USE_ID) {
            // 계정 사용 중지
            // 계정사용이 중지되었습니다. 고객센터에 문의 해주세요.
            returnUrl = failUrl;
            throw new AuthenticationException(messageService.get("login.pw.find.not.web.use"), returnUrl);
        }
        else if (code == LoginResult.SUSPEND_ACCOUNT){
            returnUrl = failUrl;
            throw new AuthenticationException(messageService.get("login.pw.find.suspend"), returnUrl);
        }
        // 로그인 실패
        else {
            sw.stop();
            LOGGER.error("로그인 실패 처리 시간 : {}", sw.getTotalTimeSeconds());
            returnUrl = failUrl;
            // 실패 처리
            throw new AuthenticationException(messageService.get("login.fail"), returnUrl);
        }

        /*
         * try { Thread.sleep(3000); } catch (InterruptedException e) { // TODO Auto-generated catch
         * block e.printStackTrace(); }
         */
        sw.stop();
        LOGGER.error("로그인 성공 처리 시간 : {}", sw.getTotalTimeSeconds());

        return "redirect:/" + returnUrl;
    }

    /** 예외 메시지를 로그인 화면 JavaScript 문자열에서 안전하게 출력할 수 있도록 변환 */
    private String escapeJavaScriptMessage(String message) {
        if (message == null || message.isEmpty()) {
            return "SMS 인증 처리 중 오류가 발생했습니다.";
        }

        return message.replace("\\", "\\\\")
                .replace("'", "\\'")
                .replace("\r\n", "\\n")
                .replace("\r", "\\n")
                .replace("\n", "\\n");
    }

    /**
     * 인증번호 검증 전 발송 대상 계정과 애플리케이션 기준 유효시간을 확인한다.
     *
     * @return 검증 가능하면 null, 요청 이력 없음 또는 만료이면 실패 결과
     */
    private SmsVfcResultVO validateSmsVfcRequest(String userId, HttpServletRequest request,
                                                 String userIdSessionKey, String sentAtSessionKey) {
        // 인증번호를 발송한 USER_ID로, 다른 계정의 인증번호 사용을 차단할 때 비교한다.
        Object requestedUserId = request.getSession().getAttribute(userIdSessionKey);
        // 인증번호 발송 성공 시 저장한 서버 시각으로, 3분 유효시간을 계산할 때 사용한다.
        Object sentAt = request.getSession().getAttribute(sentAtSessionKey);
        if (!userId.equals(requestedUserId) || !(sentAt instanceof Long)) {
            return new SmsVfcResultVO("04", "인증번호 요청 이력이 없습니다.\n인증번호를 먼저 요청하여 주십시오.");
        }

        // 인증번호 발송 후 현재까지 경과한 시간이다.
        long elapsedMillis = System.currentTimeMillis() - (Long) sentAt;
        if (elapsedMillis < 0 || elapsedMillis >= SMS_VFC_VALID_MILLIS) {
            return new SmsVfcResultVO("04", "인증번호가 유효하지 않습니다.\n인증번호를 다시 요청하여 주십시오.");
        }

        return null;
    }

    /** 화면 새로고침 후에도 인증 유효시간과 재전송 제한시간을 이어서 표시하도록 모델값 설정 */
    private void setSmsVfcTimerModel(String userId, HttpServletRequest request, Model model,
                                     String userIdSessionKey, String sentAtSessionKey) {
        // 현재 화면의 USER_ID와 일치하는지 확인할 인증번호 발송 대상 계정이다.
        Object requestedUserId = request.getSession().getAttribute(userIdSessionKey);
        // 화면에 남은 시간을 복원하기 위한 인증번호 발송 성공 시각이다.
        Object sentAt = request.getSession().getAttribute(sentAtSessionKey);
        if (userId == null || !userId.equals(requestedUserId) || !(sentAt instanceof Long)) {
            model.addAttribute("smsVfcRequestedYn", "N");
            return;
        }

        // 서버 시각 역전 시 음수가 되지 않도록 보정한 발송 후 경과시간이다.
        long elapsedMillis = Math.max(0L, System.currentTimeMillis() - (Long) sentAt);
        model.addAttribute("smsVfcRequestedYn", "Y");
        model.addAttribute("smsVfcExpireSeconds", remainingSeconds(SMS_VFC_VALID_MILLIS, elapsedMillis));
        model.addAttribute("smsVfcResendSeconds", remainingSeconds(SMS_VFC_RESEND_MILLIS, elapsedMillis));
    }

    /** 제한시간과 경과시간의 차이를 화면 타이머에서 사용할 초 단위로 변환 */
    private long remainingSeconds(long limitMillis, long elapsedMillis) {
        // 제한시간을 이미 지난 경우 화면에 음수가 표시되지 않도록 0으로 보정한다.
        long remainingMillis = Math.max(0L, limitMillis - elapsedMillis);
        return (remainingMillis + 999L) / 1000L;
    }

    /** 일반 로그인 인증 성공 후 발송 대상 계정과 발송시각 세션값 제거 */
    private void clearLoginSmsVfc(HttpServletRequest request) {
        request.getSession().removeAttribute(LOGIN_SMS_VFC_USER_ID);
        request.getSession().removeAttribute(LOGIN_SMS_VFC_SENT_AT);
    }

    /** POS 자동로그인 인증 성공 또는 로그아웃 후 발송 계정과 발송시각 세션값 제거 */
    private void clearPosSmsVfc(HttpServletRequest request) {
        request.getSession().removeAttribute(POS_SMS_VFC_USER_ID);
        request.getSession().removeAttribute(POS_SMS_VFC_SENT_AT);
    }

    /**
     * 로그인 방식에 따른 POS SMS 인증 세션 상태 설정
     * 일반 로그인 N/N/Y, 메뉴 직접이동 Y/Y/N, POS 메인 SMS 미등록 Y/N/Y,
     * POS 메인 SMS 등록 Y/N/N 상태로 저장한다.
     */
    private void setLoginSmsVfcSessionState(SessionInfoVO result, boolean posAutoLogin,
                                            String resrceCd, String failUrl) {
        if (!posAutoLogin) {
            // 일반 로그인은 POS 인증 대상이 아니므로 SMS 인증이 불필요한 상태로 저장한다.
            result.setAccessCdYn("N");
            result.setResrceCdYn("N");
            result.setSmsVfcYn("Y");
            result.setPosLoginCompletePendingYn("N");
            return;
        }

        // 특정 메뉴 직접이동 여부를 나타내며 값이 없을 때만 POS 메인 SMS 인증을 확인한다.
        boolean hasResrceCd = hasRequestValue(resrceCd);
        result.setAccessCdYn("Y");
        result.setResrceCdYn(hasResrceCd ? "Y" : "N");
        // 메뉴 직접이동은 인증 대상이 아니지만, 추후 메인 진입에 대비해 미인증으로 저장한다.
        result.setSmsVfcYn("N");
        result.setPosLoginCompletePendingYn("N");

        if (!hasResrceCd) {
            // CHK 함수로 POS 메인 진입 사용자의 SMS 등록 여부를 확인한 결과이다.
            SmsVfcResultVO smsUserResult = authService.checkSmsUser(result.getUserId());
            if (smsUserResult.isSuccess()) {
                result.setSmsVfcYn("N");
                // 새 POS 로그인 성공 정보는 C11 인증 성공 후 반영한다.
                result.setPosLoginCompletePendingYn("Y");
            } else if ("01".equals(smsUserResult.getCode())) {
                result.setSmsVfcYn("Y");
            } else {
                result.setLoginResult(LoginResult.FAIL);
                authService.loginHist(result);
                throw new AuthenticationException(escapeJavaScriptMessage(smsUserResult.getMessage()), failUrl);
            }
        }
    }

    /** 기존 로그인 세션으로 POS 메인 진입 시 SMS 인증 필요 여부 설정 */
    private void setPosSmsVfcRequirement(SessionInfoVO sessionInfoVO) {
        // CHK 함수 결과에 따라 기존 세션을 인증 대기 또는 인증 불필요 상태로 변경한다.
        SmsVfcResultVO smsUserResult = authService.checkSmsUser(sessionInfoVO.getUserId());
        if (smsUserResult.isSuccess()) {
            sessionInfoVO.setSmsVfcYn("N");
        } else if ("01".equals(smsUserResult.getCode())) {
            sessionInfoVO.setSmsVfcYn("Y");
        } else {
            throw new AuthenticationException(
                    escapeJavaScriptMessage(smsUserResult.getMessage()), "/auth/logout.sb");
        }
    }

    /**
     * POS 자동로그인 SMS 인증 대기 상태인지 확인한다.
     * accessCd로 로그인했고, 특정 메뉴 직접이동이 아니며, SMS 인증이 완료되지 않은 경우이다.
     */
    private boolean isPosSmsVfcPending(SessionInfoVO sessionInfoVO) {
        return sessionInfoVO != null
                && "Y".equals(sessionInfoVO.getAccessCdYn())
                && "N".equals(sessionInfoVO.getResrceCdYn())
                && "N".equals(sessionInfoVO.getSmsVfcYn());
    }

    /** 새 POS 자동로그인의 성공 정보가 C11 인증 완료를 기다리는 상태인지 확인 */
    private boolean isPosLoginCompletePending(SessionInfoVO sessionInfoVO) {
        return sessionInfoVO != null
                && "Y".equals(sessionInfoVO.getPosLoginCompletePendingYn());
    }

    /** request 파라미터의 null 문자열까지 제외하여 실제 값 존재 여부 확인 */
    private boolean hasRequestValue(String value) {
        return value != null && !value.trim().isEmpty() && !"null".equalsIgnoreCase(value.trim());
    }

    /** 로그인 화면에서 userPwd로 전달되는 30자 초과 accessCd인지 확인하는 기존 POS 판별 기준 */
    private boolean isPosAccessCd(String accessCd) {
        return hasRequestValue(accessCd) && accessCd.length() > 30;
    }

    /** Redis 세션과 WAS 세션의 사용자 정보를 함께 갱신 */
    private void updateSessionInfo(HttpServletRequest request, SessionInfoVO sessionInfoVO) {
        sessionService.setSessionInfo(sessionInfoVO);
        SessionUtil.setEnv(request.getSession(), sessionInfoVO.getSessionId(), sessionInfoVO);
    }

    /** SMS 함수 결과를 인증 화면 AJAX 응답 형태로 변환 */
    private Map<String, Object> smsVfcResponseData(SmsVfcResultVO result) {
        // 화면에서 메시지, 발송 성공 여부, 결과 코드를 처리할 응답 데이터이다.
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("code", result.getCode());
        responseData.put("message", result.getMessage());
        responseData.put("sent", result.isSent());
        return responseData;
    }

    /** 인증 잠금 코드 발생 시 POS 인증 세션과 로그인 쿠키를 함께 제거 */
    private void logoutPosSmsVfc(HttpServletRequest request, HttpServletResponse response) {
        clearPosSmsVfc(request);
        authService.logout(request, response);
        WebUtil.removeCookie(WebUtils.getCookie(request, BaseEnv.SB_LOGIN_FG));
        WebUtil.removeCookie(WebUtils.getCookie(request, BaseEnv.SB_LOGIN_AUTO_SERIAL));
    }

    /**
     * <pre>
     * 사용자 로그아웃
     * </pre>
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "logout.sb", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        String rUrl = "redirect:/auth/login.sb";

        if(sessionInfoVO != null){
            if(sessionInfoVO.getLoginFg() != null && sessionInfoVO.getLoginFg().equals(LoginFg.MOBILE.getCode())){
                rUrl = "redirect:/mobile/auth/login.sb";
            }
        }else if(WebUtils.getCookie(request, BaseEnv.SB_LOGIN_FG) != null){
            if(WebUtils.getCookie(request, BaseEnv.SB_LOGIN_FG).getValue().equals(LoginFg.MOBILE.getCode())){
                rUrl = "redirect:/mobile/auth/login.sb";
            }
        }else if (request.getRequestURI().substring(0, 8).equals("/mobile/")){
            rUrl = "redirect:/mobile/auth/login.sb";
        }

        // 로그아웃 처리
        authService.logout(request, response);

        // 웹에서 로그아웃 시, 모바일 로그인 여부 쿠키 제거
        WebUtil.removeCookie(WebUtils.getCookie( request, BaseEnv.SB_LOGIN_FG ));

        // 웹에서 로그아웃 시, 자동로그인 쿠키 제거
        WebUtil.removeCookie(WebUtils.getCookie( request, BaseEnv.SB_LOGIN_AUTO_SERIAL ));

        return rUrl;
    }

    @RequestMapping(value = "logdenied.sb", method = RequestMethod.GET)
    public String denied(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "denied";
    }

    /**
     * 로그인 시도 허용 여부 체크 (아이디 + IP 기준)
     * 1초에 3회 초과 시 false 반환
     *
     * @param userId 사용자 아이디
     * @param ip     접속 IP
     * @return true: 로그인 허용, false: 제한 초과
     */
    public long isLoginAllowed(String userId, String ip) {

        String key = "login:limit:" + userId + ":" + ip;

        RedisConnection conn = null;

        try {

            // Redis 연결 가져오기
            conn = redisConnectionFactory.getConnection();

            // Redis key를 UTF-8 bytes로 변환
            byte[] redisKey = key.getBytes(StandardCharsets.UTF_8);

            // INCR 명령으로 로그인 시도 카운트 증가
            Long count = conn.incr(redisKey);

            // 첫 번째 시도라면 TTL 1초 설정
            if (count == 1) {
                conn.expire(redisKey, 1);
            }

            // 3회 초과 시 false 반환
            return count;

        } finally {
            // Redis 연결 닫기
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * 로그인 시도 허용 여부 체크 (아이디 )
     * 1초에 3회 초과 시 false 반환
     *
     * @param userId 사용자 아이디
     * @return true: 로그인 허용, false: 제한 초과
     */
    public long isLoginAllowed(String userId) {

        String key = "login:limit:" + userId;

        RedisConnection conn = null;

        try {

            // Redis 연결 가져오기
            conn = redisConnectionFactory.getConnection();

            // Redis key를 UTF-8 bytes로 변환
            byte[] redisKey = key.getBytes(StandardCharsets.UTF_8);

            // INCR 명령으로 로그인 시도 카운트 증가
            Long count = conn.incr(redisKey);

            // 첫 번째 시도라면 TTL 1초 설정
            if (count == 1) {
                conn.expire(redisKey, 1);
            }

            // 3회 초과 시 false 반환
            return count;

        } finally {
            // Redis 연결 닫기
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * 전달받은 IP가 클라우드 IP인지 확인
     *
     * @param ip     접속 IP
     */
    public boolean isCloudIp(String ip) {

        try {
            if (ip.contains(":")) {
                // IPv6 주소, 클라우드 IPv4 범위에는 없음
                return false;
            }
            // 모든 CIDR 대역을 하나씩 검사
            for (String cidr : cloudCidrs) {
                // 현재 CIDR 범위에 IP가 포함되는지 확인
                if (isInRange(ip, cidr)) {
                    return true;  // 하나라도 포함되면 클라우드 IP
                }
            }
            // 어떤 범위에도 포함되지 않으면 일반 IP
            return false;
        } catch (Exception e) {
            // 이상한 IP 들어오면 그냥 일반 IP 취급
            return false;
        }
    }

    /**
     * 특정 IP가 CIDR 범위 안에 있는지 확인하는 함수
     *
     * @param ip     접속 IP
     * @param cidr   차단할 IP 범위
     */
    private boolean isInRange(String ip, String cidr) {
        // CIDR 문자열을 "/" 기준으로 분리
        String[] parts = cidr.split("/");
        // 잘못된 CIDR 처리
        if (parts.length != 2) {
            return false;
        }
        // 네트워크 시작 주소
        String network = parts[0];
        // prefix 길이 (예: /8, /16, /24)
        int prefix;
        try {
            prefix = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            return false; // 잘못된 prefix 처리
        }
        // IP를 숫자(long) 형태로 변환
        long ipLong = ipToLong(ip);
        // 네트워크 주소도 숫자로 변환
        long networkLong = ipToLong(network);
        // 잘못된 IP 처리
        if (networkLong == -1 || ipLong == -1) return false;

        // subnet mask 생성
        long mask = (prefix == 0) ? 0 : (0xFFFFFFFFL << (32 - prefix)) & 0xFFFFFFFFL; // 32bit 마스크
        // IP와 네트워크에 mask 적용 후 동일하면 같은 네트워크
        return (ipLong & mask) == (networkLong & mask);
    }

    /**
     * IP 문자열을 long 숫자로 변환하는 함수
     *
     * @param ip     접속 IP
     */
    private long ipToLong(String ip) {

        // "." 기준으로 IP 분리
        String[] octets = ip.split("\\.");

        // 잘못된 IP 처리
        if (octets.length != 4) return -1;

        long result = 0;
        // 4개의 옥텟을 순서대로 처리
        for (int i = 0; i < 4; i++) {
            int value;
            // 잘못된 값 처리
            try {
                value = Integer.parseInt(octets[i]);
            } catch (NumberFormatException e) {
                return -1;
            }
            // 잘못된 값 처리
            if (value < 0 || value > 255) return -1;
            // 각 숫자를 8비트씩 왼쪽으로 이동해서 합침
            result |= (long) value << (24 - (8 * i));
        }
        return result & 0xFFFFFFFFL; // unsigned 처리
    }

    /**
     * 로그인 체크 로그 항목을 StringBuilder에 추가
     *
     * @param log           로그 버퍼
     * @param prefix        로그 접두사 (err1, err2, succ 등)
     * @param title         로그 제목
     * @param request       HTTP 요청
     * @param params        로그인 세션 VO
     * @param token         로그인 체크 토큰
     * @param count         초당 접속 횟수 (null이면 해당 줄 생략)
     * @param processResult 처리여부 문자열 ("제한" 또는 "성공")
     */
    private void appendLoginLog(StringBuilder log, String prefix, String title,
                                HttpServletRequest request, SessionInfoVO params,
                                String token, Long count, String processResult) {
        String userId = params.getUserId();
        String currentDt = DateUtil.currentDateTimeString();
        log.append("\n_login_chk_log_").append(prefix + "_").append("----------").append(userId).append(" ").append(title).append(" START----------\n")
           .append("_login_chk_log_").append(prefix + "_").append(userId).append(",").append(currentDt).append(",사용자ID :").append(userId).append("\n")
           .append("_login_chk_log_").append(prefix + "_").append(userId).append(",").append(currentDt).append(",접속IP:").append(params.getLoginIp()).append("\n")
           .append("_login_chk_log_").append(prefix + "_").append(userId).append(",").append(currentDt).append(",본사코드:").append(params.getHqOfficeCd()).append("\n")
           .append("_login_chk_log_").append(prefix + "_").append(userId).append(",").append(currentDt).append(",매장코드:").append(params.getStoreCd()).append("\n")
           .append("_login_chk_log_").append(prefix + "_").append(userId).append(",").append(currentDt).append(",User-Agent:").append(request.getHeader("User-Agent")).append("\n")
           .append("_login_chk_log_").append(prefix + "_").append(userId).append(",").append(currentDt).append(",Sec-Fetch-Site:").append(request.getHeader("Sec-Fetch-Site")).append("\n")
           .append("_login_chk_log_").append(prefix + "_").append(userId).append(",").append(currentDt).append(",Accept:").append(request.getHeader("Accept")).append("\n")
           .append("_login_chk_log_").append(prefix + "_").append(userId).append(",").append(currentDt).append(",referer:").append(request.getHeader("referer")).append("\n");
        if (count != null) {
            log.append("_login_chk_log_").append(prefix + "_").append(userId).append(",").append(currentDt).append(",초당접속횟수:").append(count).append("\n");
        }
        log.append("_login_chk_log_").append(prefix + "_").append(userId).append(",").append(currentDt).append(",토큰정보:").append(token).append("\n")
           .append("_login_chk_log_").append(prefix + "_").append(userId).append(",").append(currentDt).append(",처리여부:").append(processResult).append("\n")
           .append("_login_chk_log_").append(prefix + "_").append(userId).append(",").append(currentDt).append(",Accept-Language:").append(request.getHeader("Accept-Language")).append("\n")
           .append("_login_chk_log_").append(prefix + "_").append(userId).append(",").append(currentDt).append(",Sec-Fetch-Mode:").append(request.getHeader("Sec-Fetch-Mode")).append("\n")
           .append("_login_chk_log_").append(prefix + "_").append(userId).append(",").append(currentDt).append(",Upgrade-Insecure-Requests:").append(request.getHeader("Upgrade-Insecure-Requests")).append("\n")
           .append("_login_chk_log_").append(prefix + "_").append("----------").append(userId).append(" ").append(title).append(" END----------");
    }

    /**
     * 로그를 입력받아 로그 파일에 출력
     *
     * @param log     로그
     */
    public void makeLoginLog(StringBuilder log) {

        String catalinaBase = System.getProperty("catalina.base");
        // 오늘 날짜
        Date date = new Date();
        String nowDate = new SimpleDateFormat("yyyyMMdd").format(date);

        // 생성 파일 경로
//        String fileName = "D:\\log_test\\QUERYTIME_" + nowDate + ".OUT"; // TEST
        String fileName = catalinaBase + "/logs/LOGIN_CHK_LOG_" + nowDate + ".OUT";

        try {

            // 파일 객체 생성
            File file = new File(fileName);

            // true 지정시 파일의 기존 내용에 이어서 작성
            FileWriter fw = new FileWriter(file, true);

            // 파일안에 문자열 쓰기
            fw.write(log.toString());
            fw.flush();

            // 객체 닫기
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


