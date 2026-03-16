package kr.co.solbipos.mobile.application.session.auth.web;

import kr.co.common.exception.AuthenticationException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
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

                LOGGER.info("login auto start : {} ", params.getUserId());

                // 자동 로그인 시도
                SessionInfoVO result = mobileAuthService.login(params);

                // 로그인 결과값
                LoginResult code = result.getLoginResult();

                // 접속정보 가져오기
                String chkUserId = result.getUserId();
                String ip = request.getRemoteAddr();
                String failUrl = "/mobile/auth/login.sb?userId=" + result.getUserId();

                // 로그인 시도 횟수 확인 (userId, IP) 기준
                Long count = isLoginAllowed(chkUserId, ip);
                String currentDt = DateUtil.currentDateTimeString();
                token = (String) request.getSession().getAttribute("LOGIN_CHK_TOKEN");
                StringBuilder log = new StringBuilder();

                // 로그인 시도 제한 체크
                if (count > 3) {

                    log.append("\n----------").append(userId).append(" 로그인 시도 제한 (count > 3) START----------\n")
                            .append(userId).append(",").append(currentDt).append(",사용자ID :").append(userId).append("\n")
                            .append(userId).append(",").append(currentDt).append(",접속IP:").append(result.getLoginIp()).append("\n")
                            .append(userId).append(",").append(currentDt).append(",본사코드:").append(result.getHqOfficeCd()).append("\n")
                            .append(userId).append(",").append(currentDt).append(",매장코드:").append(result.getStoreCd()).append("\n")
                            .append(userId).append(",").append(currentDt).append(",User-Agent:").append(request.getHeader("User-Agent")).append("\n")
                            .append(userId).append(",").append(currentDt).append(",Sec-Fetch-Site:").append(request.getHeader("Sec-Fetch-Site")).append("\n")
                            .append(userId).append(",").append(currentDt).append(",Accept:").append(request.getHeader("Accept")).append("\n")
                            .append(userId).append(",").append(currentDt).append(",referer:").append(request.getHeader("referer")).append("\n")
                            .append(userId).append(",").append(currentDt).append(",초당접속횟수:").append(count).append("\n")
                            .append(userId).append(",").append(currentDt).append(",토큰정보:").append(token).append("\n")
                            .append(userId).append(",").append(currentDt).append(",처리여부:제한\n")
                            .append(userId).append(",").append(currentDt).append(",Accept-Language:").append(request.getHeader("Accept-Language")).append("\n")
                            .append(userId).append(",").append(currentDt).append(",Sec-Fetch-Mode:").append(request.getHeader("Sec-Fetch-Mode")).append("\n")
                            .append(userId).append(",").append(currentDt).append(",Upgrade-Insecure-Requests:").append(request.getHeader("Upgrade-Insecure-Requests")).append("\n")
                            .append("----------").append(userId).append(" 로그인 시도 제한 (count > 3) END----------");

                    // 특정 아이디만 세션 삭제
                    if(chkUserId.equals("momse08053") || chkUserId.equals("momse10160") || chkUserId.equals("momse09686") || chkUserId.equals("kjsun11177") || chkUserId.equals("ds053") || chkUserId.equals("ds00501")) {
                        // 세션 삭제 여부 확인
                        sessionService.deleteSessionInfo(request);
                        // 제한 초과 처리
                        result.setLoginResult(LoginResult.MANY_ATTEMPTS);
                        authService.loginHist(result);
                        throw new AuthenticationException(messageService.get("login.fail"), failUrl);
                    }
                }else{

                    log.append("\n----------").append(userId).append(" 로그인 시도 성공 START----------\n")
                            .append(userId).append(",").append(currentDt).append(",사용자ID :").append(userId).append("\n")
                            .append(userId).append(",").append(currentDt).append(",접속IP:").append(result.getLoginIp()).append("\n")
                            .append(userId).append(",").append(currentDt).append(",본사코드:").append(result.getHqOfficeCd()).append("\n")
                            .append(userId).append(",").append(currentDt).append(",매장코드:").append(result.getStoreCd()).append("\n")
                            .append(userId).append(",").append(currentDt).append(",User-Agent:").append(request.getHeader("User-Agent")).append("\n")
                            .append(userId).append(",").append(currentDt).append(",Sec-Fetch-Site:").append(request.getHeader("Sec-Fetch-Site")).append("\n")
                            .append(userId).append(",").append(currentDt).append(",Accept:").append(request.getHeader("Accept")).append("\n")
                            .append(userId).append(",").append(currentDt).append(",referer:").append(request.getHeader("referer")).append("\n")
                            .append(userId).append(",").append(currentDt).append(",초당접속횟수:").append(count).append("\n")
                            .append(userId).append(",").append(currentDt).append(",토큰정보:").append(token).append("\n")
                            .append(userId).append(",").append(currentDt).append(",처리여부:성공\n")
                            .append(userId).append(",").append(currentDt).append(",Accept-Language:").append(request.getHeader("Accept-Language")).append("\n")
                            .append(userId).append(",").append(currentDt).append(",Sec-Fetch-Mode:").append(request.getHeader("Sec-Fetch-Mode")).append("\n")
                            .append(userId).append(",").append(currentDt).append(",Upgrade-Insecure-Requests:").append(request.getHeader("Upgrade-Insecure-Requests")).append("\n")
                            .append("----------").append(userId).append(" 로그인 시도 성공 END----------");

                    LOGGER.info(log.toString());
                }

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

        // 로그인 시도
        SessionInfoVO result = mobileAuthService.login(params);
        // 로그인 결과값
        LoginResult code = result.getLoginResult();

        // 접속정보 가져오기
        String userId = result.getUserId();
        String ip = request.getRemoteAddr();
        String failUrl = "/mobile/auth/login.sb?userId=" + result.getUserId();

        // 로그인 시도 횟수 확인 (userId, IP) 기준
        Long count = isLoginAllowed(userId, ip);
        String currentDt = DateUtil.currentDateTimeString();
        String token = (String) request.getSession().getAttribute("LOGIN_CHK_TOKEN");
        StringBuilder log = new StringBuilder();

        // 로그인 시도 제한 체크
        if (count > 3) {
            log.append("\n----------").append(userId).append(" 로그인 시도 제한 (count > 3) START----------\n")
                    .append(userId).append(",").append(currentDt).append(",사용자ID :").append(userId).append("\n")
                    .append(userId).append(",").append(currentDt).append(",접속IP:").append(result.getLoginIp()).append("\n")
                    .append(userId).append(",").append(currentDt).append(",본사코드:").append(result.getHqOfficeCd()).append("\n")
                    .append(userId).append(",").append(currentDt).append(",매장코드:").append(result.getStoreCd()).append("\n")
                    .append(userId).append(",").append(currentDt).append(",User-Agent:").append(request.getHeader("User-Agent")).append("\n")
                    .append(userId).append(",").append(currentDt).append(",Sec-Fetch-Site:").append(request.getHeader("Sec-Fetch-Site")).append("\n")
                    .append(userId).append(",").append(currentDt).append(",Accept:").append(request.getHeader("Accept")).append("\n")
                    .append(userId).append(",").append(currentDt).append(",referer:").append(request.getHeader("referer")).append("\n")
                    .append(userId).append(",").append(currentDt).append(",초당접속횟수:").append(count).append("\n")
                    .append(userId).append(",").append(currentDt).append(",토큰정보:").append(token).append("\n")
                    .append(userId).append(",").append(currentDt).append(",처리여부:제한\n")
                    .append(userId).append(",").append(currentDt).append(",Accept-Language:").append(request.getHeader("Accept-Language")).append("\n")
                    .append(userId).append(",").append(currentDt).append(",Sec-Fetch-Mode:").append(request.getHeader("Sec-Fetch-Mode")).append("\n")
                    .append(userId).append(",").append(currentDt).append(",Upgrade-Insecure-Requests:").append(request.getHeader("Upgrade-Insecure-Requests")).append("\n")
                    .append("----------").append(userId).append(" 로그인 시도 제한 (count > 3) END----------");

            LOGGER.info(log.toString());

            if(userId.equals("momse08053") || userId.equals("momse10160") || userId.equals("momse09686") || userId.equals("kjsun11177") || userId.equals("ds053") || userId.equals("ds00501") || userId.equals("test0013")) {
                // 세션 삭제 여부 확인
                sessionService.deleteSessionInfo(request);
                // 제한 초과 처리
                result.setLoginResult(LoginResult.MANY_ATTEMPTS);
                authService.loginHist(result);
                throw new AuthenticationException(messageService.get("login.fail"), failUrl);
            }
        }else if(token == null || token.isEmpty()){

            // 토큰 보유 여부 확인
            log.append("\n----------").append(userId).append(" 세션 토큰 값 없음 START----------\n")
                    .append(userId).append(",").append(currentDt).append(",사용자ID :").append(userId).append("\n")
                    .append(userId).append(",").append(currentDt).append(",접속IP:").append(result.getLoginIp()).append("\n")
                    .append(userId).append(",").append(currentDt).append(",본사코드:").append(result.getHqOfficeCd()).append("\n")
                    .append(userId).append(",").append(currentDt).append(",매장코드:").append(result.getStoreCd()).append("\n")
                    .append(userId).append(",").append(currentDt).append(",User-Agent:").append(request.getHeader("User-Agent")).append("\n")
                    .append(userId).append(",").append(currentDt).append(",Sec-Fetch-Site:").append(request.getHeader("Sec-Fetch-Site")).append("\n")
                    .append(userId).append(",").append(currentDt).append(",Accept:").append(request.getHeader("Accept")).append("\n")
                    .append(userId).append(",").append(currentDt).append(",referer:").append(request.getHeader("referer")).append("\n")
                    .append(userId).append(",").append(currentDt).append(",토큰정보:").append(token).append("\n")
                    .append(userId).append(",").append(currentDt).append(",Accept-Language:").append(request.getHeader("Accept-Language")).append("\n")
                    .append(userId).append(",").append(currentDt).append(",Sec-Fetch-Mode:").append(request.getHeader("Sec-Fetch-Mode")).append("\n")
                    .append(userId).append(",").append(currentDt).append(",Upgrade-Insecure-Requests:").append(request.getHeader("Upgrade-Insecure-Requests")).append("\n")
                    .append("----------").append(userId).append(" 세션 토큰 값 없음 END----------");

            LOGGER.info(log.toString());

            // 특정 아이디만 세션 삭제
            if (userId.equals("momse08053") || userId.equals("momse10160") || userId.equals("momse09686") || userId.equals("kjsun11177") || userId.equals("ds053") || userId.equals("ds00501")) {
                result.setLoginResult(LoginResult.TOKEN_ERROR);
                authService.loginHist(result);
                throw new AuthenticationException(messageService.get("login.fail"), failUrl);
            }
        }else if("".equals(request.getHeader("User-Agent")) ||request.getHeader("User-Agent") == null || request.getHeader("User-Agent").contains("python")
                || request.getHeader("User-Agent").contains("curl") || request.getHeader("User-Agent").contains("wget")
                || request.getHeader("User-Agent").contains("bot") || request.getHeader("User-Agent").contains("node")
                ||  "".equals(request.getHeader("Accept")) || request.getHeader("Accept") == null
                ||  !request.getHeader("Accept").contains("text/html") || "".equals(request.getHeader("referer"))
                ||  request.getHeader("referer") == null || !request.getHeader("referer").contains("/auth/login.sb")){

            // 브라우저 확인
            log.append("\n----------").append(userId).append(" 브라우저 이상 START----------\n")
                    .append(userId).append(",").append(currentDt).append(",사용자ID :").append(userId).append("\n")
                    .append(userId).append(",").append(currentDt).append(",접속IP:").append(result.getLoginIp()).append("\n")
                    .append(userId).append(",").append(currentDt).append(",본사코드:").append(result.getHqOfficeCd()).append("\n")
                    .append(userId).append(",").append(currentDt).append(",매장코드:").append(result.getStoreCd()).append("\n")
                    .append(userId).append(",").append(currentDt).append(",User-Agent:").append(request.getHeader("User-Agent")).append("\n")
                    .append(userId).append(",").append(currentDt).append(",Sec-Fetch-Site:").append(request.getHeader("Sec-Fetch-Site")).append("\n")
                    .append(userId).append(",").append(currentDt).append(",Accept:").append(request.getHeader("Accept")).append("\n")
                    .append(userId).append(",").append(currentDt).append(",referer:").append(request.getHeader("referer")).append("\n")
                    .append(userId).append(",").append(currentDt).append(",토큰정보:").append(token).append("\n")
                    .append(userId).append(",").append(currentDt).append(",Accept-Language:").append(request.getHeader("Accept-Language")).append("\n")
                    .append(userId).append(",").append(currentDt).append(",Sec-Fetch-Mode:").append(request.getHeader("Sec-Fetch-Mode")).append("\n")
                    .append(userId).append(",").append(currentDt).append(",Upgrade-Insecure-Requests:").append(request.getHeader("Upgrade-Insecure-Requests")).append("\n")
                    .append("----------").append(userId).append(" 브라우저 이상 END----------");

            LOGGER.info(log.toString());
            // 특정 아이디만 세션 삭제
            if (userId.equals("momse08053") || userId.equals("momse10160") || userId.equals("momse09686") || userId.equals("kjsun11177") || userId.equals("ds053") || userId.equals("ds00501")) {
                result.setLoginResult(LoginResult.CHK_BROWSER);
                authService.loginHist(result);
                throw new AuthenticationException(messageService.get("login.fail"), failUrl);
            }
        }else{
            log.append("\n----------").append(userId).append(" 로그인 시도 성공 START----------\n")
                    .append(userId).append(",").append(currentDt).append(",사용자ID :").append(userId).append("\n")
                    .append(userId).append(",").append(currentDt).append(",접속IP:").append(result.getLoginIp()).append("\n")
                    .append(userId).append(",").append(currentDt).append(",본사코드:").append(result.getHqOfficeCd()).append("\n")
                    .append(userId).append(",").append(currentDt).append(",매장코드:").append(result.getStoreCd()).append("\n")
                    .append(userId).append(",").append(currentDt).append(",User-Agent:").append(request.getHeader("User-Agent")).append("\n")
                    .append(userId).append(",").append(currentDt).append(",Sec-Fetch-Site:").append(request.getHeader("Sec-Fetch-Site")).append("\n")
                    .append(userId).append(",").append(currentDt).append(",Accept:").append(request.getHeader("Accept")).append("\n")
                    .append(userId).append(",").append(currentDt).append(",referer:").append(request.getHeader("referer")).append("\n")
                    .append(userId).append(",").append(currentDt).append(",초당접속횟수:").append(count).append("\n")
                    .append(userId).append(",").append(currentDt).append(",토큰정보:").append(token).append("\n")
                    .append(userId).append(",").append(currentDt).append(",처리여부:성공\n")
                    .append(userId).append(",").append(currentDt).append(",Accept-Language:").append(request.getHeader("Accept-Language")).append("\n")
                    .append(userId).append(",").append(currentDt).append(",Sec-Fetch-Mode:").append(request.getHeader("Sec-Fetch-Mode")).append("\n")
                    .append(userId).append(",").append(currentDt).append(",Upgrade-Insecure-Requests:").append(request.getHeader("Upgrade-Insecure-Requests")).append("\n")
                    .append("----------").append(userId).append(" 로그인 시도 성공 END----------");

            LOGGER.info(log.toString());
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
     * 로그인 성공 시 rate-limit 초기화
     * Redis에 저장된 로그인 시도 카운트 삭제
     *
     * @param userId 사용자 아이디
     * @param ip     접속 IP
     */
    public void clearLoginLimit(String userId, String ip) {
        String key = "login:limit:" + userId + ":" + ip;
        RedisConnection conn = null;
        try {
            conn = redisConnectionFactory.getConnection();
            byte[] redisKey = key.getBytes(StandardCharsets.UTF_8);
            conn.del(redisKey);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) conn.close();
        }
    }
}
