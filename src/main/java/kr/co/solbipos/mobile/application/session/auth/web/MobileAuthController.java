package kr.co.solbipos.mobile.application.session.auth.web;

import kr.co.common.exception.AuthenticationException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.DateUtil;
import kr.co.common.utils.spring.WebUtil;
import kr.co.common.validate.Login;
import kr.co.solbipos.application.session.auth.enums.LoginResult;
import kr.co.solbipos.application.session.auth.service.AuthService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.application.session.auth.enums.LoginFg;
import kr.co.solbipos.mobile.application.session.auth.service.MobileAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.HttpUtils.getClientIp;
import static org.springframework.util.ObjectUtils.isEmpty;

import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * @Class Name : MobileAuthController.java
 * @Description : 어플리케이션 > 세션 > 인증
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.03.10  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2021.03.10
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/mobile/auth")
public class MobileAuthController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final RedisConnectionFactory redisConnectionFactory;

    @Autowired
    AuthService authService;

    private final MobileAuthService mobileAuthService;
    private final SessionService sessionService;
    private final MessageService messageService;

    final String MAIN_PAGE_URL = "mobile/main.sb";
    // 차단할 클라우드 IP 대역 (CIDR 형식)
    // 예: 3.0.0.0/8 -> 3.x.x.x 전체 범위
    private static final String[] cloudCidrs = {
            // AWS
            "3.0.0.0/8",
            "13.0.0.0/8",
            "15.0.0.0/7",
            "18.0.0.0/7",
            "52.0.0.0/8",
            "54.0.0.0/8",
            // GCP
            "34.0.0.0/8",
            "35.0.0.0/8",
            // Azure
            "20.0.0.0/8",
            "40.0.0.0/8",
            // 개별 차단 IP
            "136.107.0.0/16",
            "44.192.0.0/11",
            "162.220.234.0/24",
            "8.228.0.0/14"

    };

    /** Constructor Injection */
    @Autowired
    public MobileAuthController(RedisConnectionFactory redisConnectionFactory, MobileAuthService mobileAuthService, SessionService sessionService, MessageService messageService) {
        this.redisConnectionFactory = redisConnectionFactory;
        this.mobileAuthService = mobileAuthService;
        this.sessionService = sessionService;
        this.messageService = messageService;
    }

    /**
     * <pre>
     * 모바일 로그인 페이지로 이동
     * </pre>
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "login.sb", method = RequestMethod.GET)
    public String login(String userId, String type, HttpServletRequest request, HttpServletResponse response, Model model) {

        // 세션이 있는경우, 메인 화면으로 redirect
        if (sessionService.isValidSession(request)) {
            return "redirect:/" + MAIN_PAGE_URL;
        }

        // 토큰 값 생성 및 셋팅
        String token = UUID.randomUUID().toString();
        request.getSession().setAttribute("LOGIN_CHK_TOKEN", token);

        // 자동로그인 시
        if(WebUtils.getCookie(request, BaseEnv.LOGIN_CHECK_ID_SAVE) != null && !WebUtils.getCookie(request,  BaseEnv.LOGIN_CHECK_ID_SAVE).getValue().equals("")) {
            if (WebUtils.getCookie(request, BaseEnv.SB_LOGIN_AUTO_SERIAL) != null && !WebUtils.getCookie(request, BaseEnv.SB_LOGIN_AUTO_SERIAL).getValue().equals("")) {

                StopWatch sw = new StopWatch();
                sw.start();

                SessionInfoVO params = new SessionInfoVO();
                params.setUserId(WebUtils.getCookie(request, BaseEnv.LOGIN_CHECK_ID_SAVE).getValue());
                params.setLoginAutoSerial(WebUtils.getCookie(request, BaseEnv.SB_LOGIN_AUTO_SERIAL).getValue());
                params.setLoginIp(getClientIp(request));
                params.setBrwsrInfo(request.getHeader("User-Agent"));

                // 로그인 시 특정아이디 블락
                if (CmmUtil.isBlockedId(params.getUserId())) {
                    // 세션 삭제
                    sessionService.deleteSessionInfo(request);
                    throw new AuthenticationException(messageService.get("login.fail"), "/error/403.sb");
                }

                LOGGER.info("login auto start : {} ", params.getUserId());


                // 접속정보 가져오기
                String chkUserId = params.getUserId();
                String ip = request.getRemoteAddr();
                String failUrl = "/mobile/auth/login.sb?userId=" + params.getUserId();

                // 로그인 시도 횟수 확인 (userId, IP) 기준
                Long count = isLoginAllowed(chkUserId, ip);
                String currentDt = DateUtil.currentDateTimeString();
                token = (String) request.getSession().getAttribute("LOGIN_CHK_TOKEN");
                StringBuilder log = new StringBuilder();

                try {
                    // 로그인 시도 값 체크
                    if (count > 3) {
                        // 로그인 시도 제한 체크
                        log.append("\n_login_chk_log_----------").append(userId).append(" 로그인 시도 제한 (count > 3) START----------\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",사용자ID :").append(userId).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",접속IP:").append(params.getLoginIp()).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",본사코드:").append(params.getHqOfficeCd()).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",매장코드:").append(params.getStoreCd()).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",User-Agent:").append(request.getHeader("User-Agent")).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Sec-Fetch-Site:").append(request.getHeader("Sec-Fetch-Site")).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Accept:").append(request.getHeader("Accept")).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",referer:").append(request.getHeader("referer")).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",초당접속횟수:").append(count).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",토큰정보:").append(token).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",처리여부:제한\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Accept-Language:").append(request.getHeader("Accept-Language")).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Sec-Fetch-Mode:").append(request.getHeader("Sec-Fetch-Mode")).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Upgrade-Insecure-Requests:").append(request.getHeader("Upgrade-Insecure-Requests")).append("\n")
                                .append("_login_chk_log_----------").append(userId).append(" 로그인 시도 제한 (count > 3) END----------");

                        // 특정 아이디만 세션 삭제
                        if (chkUserId.equals("momse08053") || chkUserId.equals("momse10160") || chkUserId.equals("momse09686") || chkUserId.equals("kjsun11177") || chkUserId.equals("ds053") || chkUserId.equals("ds00501")) {
                            // 세션 삭제 여부 확인
                            sessionService.deleteSessionInfo(request);
                            // 제한 초과 처리
                            params.setLoginResult(LoginResult.MANY_ATTEMPTS);
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
                        log.append("\n_login_chk_log_----------").append(userId).append(" 브라우저 이상 START----------\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",사용자ID :").append(userId).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",접속IP:").append(params.getLoginIp()).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",본사코드:").append(params.getHqOfficeCd()).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",매장코드:").append(params.getStoreCd()).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",User-Agent:").append(request.getHeader("User-Agent")).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Sec-Fetch-Site:").append(request.getHeader("Sec-Fetch-Site")).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Accept:").append(request.getHeader("Accept")).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",referer:").append(request.getHeader("referer")).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",토큰정보:").append(token).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Accept-Language:").append(request.getHeader("Accept-Language")).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Sec-Fetch-Mode:").append(request.getHeader("Sec-Fetch-Mode")).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Upgrade-Insecure-Requests:").append(request.getHeader("Upgrade-Insecure-Requests")).append("\n")
                                .append("_login_chk_log_----------").append(userId).append(" 브라우저 이상 END----------");

                        // 특정 아이디만 세션 삭제
                        if (userId.equals("momse08053") || userId.equals("momse10160") || userId.equals("momse09686") || userId.equals("kjsun11177") || userId.equals("ds053") || userId.equals("ds00501")) {
                            params.setLoginResult(LoginResult.CHK_BROWSER);
                            authService.loginHist(params);
                            throw new AuthenticationException(messageService.get("login.fail"), "/error/403.sb");
                        }
                    } else if (isCloudIp(params.getLoginIp())) {
                        log.append("\n_login_chk_log_----------").append(userId).append(" 클라우드 IP 로그인 START----------\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",사용자ID :").append(userId).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",접속IP:").append(params.getLoginIp()).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",본사코드:").append(params.getHqOfficeCd()).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",매장코드:").append(params.getStoreCd()).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",User-Agent:").append(request.getHeader("User-Agent")).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Sec-Fetch-Site:").append(request.getHeader("Sec-Fetch-Site")).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Accept:").append(request.getHeader("Accept")).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",referer:").append(request.getHeader("referer")).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",초당접속횟수:").append(count).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",토큰정보:").append(token).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Accept-Language:").append(request.getHeader("Accept-Language")).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Sec-Fetch-Mode:").append(request.getHeader("Sec-Fetch-Mode")).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Upgrade-Insecure-Requests:").append(request.getHeader("Upgrade-Insecure-Requests")).append("\n")
                                .append("_login_chk_log_----------").append(userId).append(" 클라우드 IP 로그인 END----------");
                        // 특정 아이디만 세션 삭제
                        if (userId.equals("momse08053") || userId.equals("momse10160") || userId.equals("momse09686") || userId.equals("kjsun11177") || userId.equals("ds053") || userId.equals("ds00501")) {
                            params.setLoginResult(LoginResult.CLOUD_IP_LOGIN);
                            authService.loginHist(params);
                            throw new AuthenticationException(messageService.get("login.fail"), failUrl);
                        }
                    } else {
                        log.append("\n_login_chk_log_----------").append(userId).append(" 로그인 시도 성공 START----------\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",사용자ID :").append(userId).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",접속IP:").append(params.getLoginIp()).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",본사코드:").append(params.getHqOfficeCd()).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",매장코드:").append(params.getStoreCd()).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",User-Agent:").append(request.getHeader("User-Agent")).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Sec-Fetch-Site:").append(request.getHeader("Sec-Fetch-Site")).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Accept:").append(request.getHeader("Accept")).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",referer:").append(request.getHeader("referer")).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",초당접속횟수:").append(count).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",토큰정보:").append(token).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",처리여부:성공\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Accept-Language:").append(request.getHeader("Accept-Language")).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Sec-Fetch-Mode:").append(request.getHeader("Sec-Fetch-Mode")).append("\n")
                                .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Upgrade-Insecure-Requests:").append(request.getHeader("Upgrade-Insecure-Requests")).append("\n")
                                .append("_login_chk_log_----------").append(userId).append(" 로그인 시도 성공 END----------");
                    }
                }finally {
                    // 로그파일 생성
                    makeLoginLog(log);
                    LOGGER.info(log.toString());
                }

                // 자동 로그인 시도
                SessionInfoVO result = mobileAuthService.login(params);

                // 로그인 결과값
                LoginResult code = result.getLoginResult();

                // 로그인 결과값이 성공이 아니면, 자동로그인 Serial 쿠키, 세션 로그아웃 처리
                if (code != LoginResult.SUCCESS) {
                    // 로그아웃 처리
                    mobileAuthService.logout(request, response);

                    // 모바일 로그인 여부 쿠키 제거(모바일에서 자동로그인 실패했기 때문에)
                    WebUtil.removeCookie(WebUtils.getCookie( request, BaseEnv.SB_LOGIN_FG ));

                    // 자동로그인 쿠키 제거
                    WebUtil.removeCookie(WebUtils.getCookie( request, BaseEnv.SB_LOGIN_AUTO_SERIAL ));
                }

                /**
                 * TODO 로그인 시도 결과로 이동 경로<br>
                 * 1. 성공 : 메인 페이지로 이동<br>
                 * 2. 실패<br>
                 * 2-1. 메세지와 함께 로그인 페이지로 이동<br>
                 * 2-2. 패스워드 변경 페이지로 이동<br>
                 */
                String returnUrl = MAIN_PAGE_URL;
                // 로그인 성공
                if (code == LoginResult.SUCCESS) {

                    // VO객체 세션 값 세팅
                    result.setLoginChkToken(token);

                    // 모바일 로그인 쿠키, 세션 처리
                    WebUtil.setCookie(BaseEnv.SB_LOGIN_FG, LoginFg.MOBILE.getCode(), 7*24*60*60 );
                    result.setLoginFg(LoginFg.MOBILE.getCode());

                    // 메인 페이지로
                    // 세션 생성
                    sessionService.setSessionInfo(request, response, result);
                } else if (code == LoginResult.NOT_EXISTS_ID || code == LoginResult.PASSWORD_ERROR) {
                    // 다시 로그인 페이지로 이동
                    returnUrl = failUrl;
                    throw new AuthenticationException(messageService.get("login.idpw.fail"), returnUrl);
                }else if (code == LoginResult.LOGIN_AUTO_FAIL) {
                    // 자동로그인 실패 -> 다시 로그인 페이지로 이동
                    returnUrl = failUrl;
                    throw new AuthenticationException(messageService.get("login.auto.fail"), returnUrl);
                } else if (code == LoginResult.NOT_USE_ID) {
                    // 사용하지 않는 유저
                    returnUrl = failUrl;
                    throw new AuthenticationException(messageService.get("login.pw.find.not.use"), returnUrl);
                } else if (code == LoginResult.PASSWORD_TEMPORARY) {
                    // 패스워드 변경 레이어 팝업
                    // 초기 비밀번호 입니다. 비밀번호 변경이 필요합니다.
                    returnUrl = failUrl + "&type=pwChg";
                    throw new AuthenticationException(messageService.get("login.pwd.chg"), returnUrl);
                }else if(code == LoginResult.LOGIN_FAIL_CNT_OVER) {
                    // 로그인 오류 횟수 초과
                    returnUrl = failUrl;
                    throw new AuthenticationException(messageService.get("login.fail.cnt"), returnUrl);
                }else if(code == LoginResult.PASSWORD_EXPIRE) {
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
                // 로그인 실패
                else {
                    sw.stop();
                    LOGGER.error("자동로그인 실패 처리 시간 : {}", sw.getTotalTimeSeconds());
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
        }

        model.addAttribute("userId", userId);
        model.addAttribute("type", isEmpty(type) ? "" : type);
        return "login/mlogin:Login";
    }

    /**
     * <pre>
     * 사용자 모바일 로그인
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
                               BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response,
                               Model model) {

        StopWatch sw = new StopWatch();
        sw.start();

        LOGGER.info("login start : {} ", params.getUserId());

        if (bindingResult.hasErrors()) {
            return "login/mlogin:Login";
        }

        // 아이디 저장 쿠키 처리
        if(params.isChk() || params.isChkLoginAuto()){
            WebUtil.setCookie(BaseEnv.LOGIN_CHECK_ID_SAVE, params.getUserId(),30*24*60*60);
        }else{
            WebUtil.setCookie(BaseEnv.LOGIN_CHECK_ID_SAVE, params.getUserId(),0);
        }

        params.setLoginIp(getClientIp(request));
        params.setBrwsrInfo(request.getHeader("User-Agent"));

        // 로그인 시 특정아이디 블락
        if (CmmUtil.isBlockedId(params.getUserId())) {
            // 세션 삭제
            sessionService.deleteSessionInfo(request);
            throw new AuthenticationException(messageService.get("login.fail"), "/error/409.sb");
        }


        // 접속정보 가져오기
        String userId = params.getUserId();
        String ip = request.getRemoteAddr();
        String failUrl = "/mobile/auth/login.sb?userId=" + params.getUserId();

        // 로그인 시도 횟수 확인 (userId, IP) 기준
        Long count = isLoginAllowed(userId, ip);
        String currentDt = DateUtil.currentDateTimeString();
        String token = (String) request.getSession().getAttribute("LOGIN_CHK_TOKEN");
        StringBuilder log = new StringBuilder();

        try {
            // 로그인 시도 값 체크
            if (count > 3) {
                // 로그인 시도 제한 체크
                log.append("\n_login_chk_log_----------").append(userId).append(" 로그인 시도 제한 (count > 3) START----------\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",사용자ID :").append(userId).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",접속IP:").append(params.getLoginIp()).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",본사코드:").append(params.getHqOfficeCd()).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",매장코드:").append(params.getStoreCd()).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",User-Agent:").append(request.getHeader("User-Agent")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Sec-Fetch-Site:").append(request.getHeader("Sec-Fetch-Site")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Accept:").append(request.getHeader("Accept")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",referer:").append(request.getHeader("referer")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",초당접속횟수:").append(count).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",토큰정보:").append(token).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",처리여부:제한\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Accept-Language:").append(request.getHeader("Accept-Language")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Sec-Fetch-Mode:").append(request.getHeader("Sec-Fetch-Mode")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Upgrade-Insecure-Requests:").append(request.getHeader("Upgrade-Insecure-Requests")).append("\n")
                        .append("_login_chk_log_----------").append(userId).append(" 로그인 시도 제한 (count > 3) END----------");

                if (userId.equals("momse08053") || userId.equals("momse10160") || userId.equals("momse09686") || userId.equals("kjsun11177") || userId.equals("ds053") || userId.equals("ds00501") || userId.equals("test0013")) {
                    // 세션 삭제
                    sessionService.deleteSessionInfo(request);
                    // 제한 초과 처리
                    params.setLoginResult(LoginResult.MANY_ATTEMPTS);
                    authService.loginHist(params);
                    throw new AuthenticationException(messageService.get("login.fail"), "/error/403.sb");
                }
            } else if (token == null || token.isEmpty()) {

                // 토큰 보유 여부 확인
                log.append("\n_login_chk_log_----------").append(userId).append(" 세션 토큰 값 없음 START----------\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",사용자ID :").append(userId).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",접속IP:").append(params.getLoginIp()).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",본사코드:").append(params.getHqOfficeCd()).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",매장코드:").append(params.getStoreCd()).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",User-Agent:").append(request.getHeader("User-Agent")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Sec-Fetch-Site:").append(request.getHeader("Sec-Fetch-Site")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Accept:").append(request.getHeader("Accept")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",referer:").append(request.getHeader("referer")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",토큰정보:").append(token).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Accept-Language:").append(request.getHeader("Accept-Language")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Sec-Fetch-Mode:").append(request.getHeader("Sec-Fetch-Mode")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Upgrade-Insecure-Requests:").append(request.getHeader("Upgrade-Insecure-Requests")).append("\n")
                        .append("_login_chk_log_----------").append(userId).append(" 세션 토큰 값 없음 END----------");

                // 특정 아이디만 세션 삭제
                if (userId.equals("momse08053") || userId.equals("momse10160") || userId.equals("momse09686") || userId.equals("kjsun11177") || userId.equals("ds053") || userId.equals("ds00501")) {
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
                log.append("\n_login_chk_log_----------").append(userId).append(" 브라우저 이상 START----------\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",사용자ID :").append(userId).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",접속IP:").append(params.getLoginIp()).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",본사코드:").append(params.getHqOfficeCd()).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",매장코드:").append(params.getStoreCd()).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",User-Agent:").append(request.getHeader("User-Agent")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Sec-Fetch-Site:").append(request.getHeader("Sec-Fetch-Site")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Accept:").append(request.getHeader("Accept")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",referer:").append(request.getHeader("referer")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",토큰정보:").append(token).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Accept-Language:").append(request.getHeader("Accept-Language")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Sec-Fetch-Mode:").append(request.getHeader("Sec-Fetch-Mode")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Upgrade-Insecure-Requests:").append(request.getHeader("Upgrade-Insecure-Requests")).append("\n")
                        .append("_login_chk_log_----------").append(userId).append(" 브라우저 이상 END----------");

                // 특정 아이디만 세션 삭제
                if (userId.equals("momse08053") || userId.equals("momse10160") || userId.equals("momse09686") || userId.equals("kjsun11177") || userId.equals("ds053") || userId.equals("ds00501")) {
                    // 세션 삭제
                    sessionService.deleteSessionInfo(request);
                    params.setLoginResult(LoginResult.CHK_BROWSER);
                    authService.loginHist(params);
                    throw new AuthenticationException(messageService.get("login.fail"), "/error/403.sb");
                }
            } else if (isCloudIp(params.getLoginIp())) {
                log.append("\n_login_chk_log_----------").append(userId).append(" 클라우드 IP 로그인 START----------\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",사용자ID :").append(userId).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",접속IP:").append(params.getLoginIp()).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",본사코드:").append(params.getHqOfficeCd()).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",매장코드:").append(params.getStoreCd()).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",User-Agent:").append(request.getHeader("User-Agent")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Sec-Fetch-Site:").append(request.getHeader("Sec-Fetch-Site")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Accept:").append(request.getHeader("Accept")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",referer:").append(request.getHeader("referer")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",초당접속횟수:").append(count).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",토큰정보:").append(token).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Accept-Language:").append(request.getHeader("Accept-Language")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Sec-Fetch-Mode:").append(request.getHeader("Sec-Fetch-Mode")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Upgrade-Insecure-Requests:").append(request.getHeader("Upgrade-Insecure-Requests")).append("\n")
                        .append("_login_chk_log_----------").append(userId).append(" 클라우드 IP 로그인 END----------");
                // 특정 아이디만 세션 삭제
                if (userId.equals("momse08053") || userId.equals("momse10160") || userId.equals("momse09686") || userId.equals("kjsun11177") || userId.equals("ds053") || userId.equals("ds00501")) {
                    // 세션 삭제
                    sessionService.deleteSessionInfo(request);
                    params.setLoginResult(LoginResult.CLOUD_IP_LOGIN);
                    authService.loginHist(params);
                    throw new AuthenticationException(messageService.get("login.fail"), "/error/403.sb");
                }
            } else {
                log.append("\n_login_chk_log_----------").append(userId).append(" 로그인 시도 성공 START----------\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",사용자ID :").append(userId).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",접속IP:").append(params.getLoginIp()).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",본사코드:").append(params.getHqOfficeCd()).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",매장코드:").append(params.getStoreCd()).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",User-Agent:").append(request.getHeader("User-Agent")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Sec-Fetch-Site:").append(request.getHeader("Sec-Fetch-Site")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Accept:").append(request.getHeader("Accept")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",referer:").append(request.getHeader("referer")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",초당접속횟수:").append(count).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",토큰정보:").append(token).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",처리여부:성공\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Accept-Language:").append(request.getHeader("Accept-Language")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Sec-Fetch-Mode:").append(request.getHeader("Sec-Fetch-Mode")).append("\n")
                        .append("_login_chk_log_" + userId).append(",").append(currentDt).append(",Upgrade-Insecure-Requests:").append(request.getHeader("Upgrade-Insecure-Requests")).append("\n")
                        .append("_login_chk_log_----------").append(userId).append(" 로그인 시도 성공 END----------");
            }
        }finally {
            // 로그파일 생성
            makeLoginLog(log);
            LOGGER.info(log.toString());
        }

        // 로그인 시도
        SessionInfoVO result = mobileAuthService.login(params);
        // 로그인 결과값
        LoginResult code = result.getLoginResult();

        /**
         * TODO 로그인 시도 결과로 이동 경로<br>
         * 1. 성공 : 메인 페이지로 이동<br>
         * 2. 실패<br>
         * 2-1. 메세지와 함께 로그인 페이지로 이동<br>
         * 2-2. 패스워드 변경 페이지로 이동<br>
         */
        String returnUrl = MAIN_PAGE_URL;
        // 로그인 성공
        if (code == LoginResult.SUCCESS) {

            // VO객체 세션값 셋팅
            result.setLoginChkToken(token);

            // 모바일 로그인 쿠키, 세션 처리
            WebUtil.setCookie(BaseEnv.SB_LOGIN_FG, LoginFg.MOBILE.getCode(), 7*24*60*60 );
            result.setLoginFg(LoginFg.MOBILE.getCode());

            // 자동로그인 체크 관련
            if(params.isChkLoginAuto()){

                // Serial 생성
                String serial = getSerial();

                // 쿠키와 세션에 Set
                WebUtil.setCookie(BaseEnv.SB_LOGIN_AUTO_SERIAL, serial, 7*24*60*60);
                result.setLoginAutoSerial(serial);

                // Serial Update
                result.setModDt(currentDateTimeString());
                result.setModId(result.getUserId());
                mobileAuthService.updateLoginAutoSerial(result);

            }else{
                WebUtil.removeCookie(WebUtils.getCookie( request, BaseEnv.SB_LOGIN_AUTO_SERIAL ));
                result.setLoginAutoSerial(null);
            }

            // 메인 페이지로
            // 세션 생성
            sessionService.setSessionInfo(request, response, result);
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
        mobileAuthService.logout(request, response);

        // 모바일 로그인 여부 쿠키 제거
        WebUtil.removeCookie(WebUtils.getCookie( request, BaseEnv.SB_LOGIN_FG ));

        // 자동로그인 쿠키 제거
        WebUtil.removeCookie(WebUtils.getCookie( request, BaseEnv.SB_LOGIN_AUTO_SERIAL ));

        return rUrl;
    }

    @RequestMapping(value = "logdenied.sb", method = RequestMethod.GET)
    public String denied(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "denied";
    }

    /**
     * 자동로그인을 위한 난수생성
     * @return
     */
    private static SecureRandom random = new SecureRandom();
    public static String getSerial(){

        // 난수생성에 사용할 문자열
        String eng_lower = "abcdefghijklmnopqrstuvwxyz";
        String eng_upper = eng_lower.toUpperCase();
        String no = "0123456789";
        String data = eng_lower + eng_upper + no;

        // 난수생성에 사용할 오늘 날짜 (년월일시분초 14자리)
        Date date_now = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        // 문자열 난수 길이
        int length = 100;

        // 문자열 난수 생성
        StringBuilder sb = new StringBuilder(length);
        for(int i=0; i<length; i++){
            sb.append(data.charAt(random.nextInt(data.length())));
        }

        // 문자열 난수(100) + 오늘날짜(14)  = 114자리 난수 생성
        sb.append(simpleDateFormat.format(date_now));

        return sb.toString();
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
