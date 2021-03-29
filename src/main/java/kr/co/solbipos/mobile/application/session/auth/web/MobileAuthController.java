package kr.co.solbipos.mobile.application.session.auth.web;

import kr.co.common.exception.AuthenticationException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.spring.WebUtil;
import kr.co.common.validate.Login;
import kr.co.solbipos.application.session.auth.enums.LoginResult;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.application.session.auth.enums.LoginFg;
import kr.co.solbipos.mobile.application.session.auth.service.MobileAuthService;
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
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import static kr.co.common.utils.HttpUtils.getClientIp;
import static org.springframework.util.ObjectUtils.isEmpty;

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

    private final MobileAuthService mobileAuthService;
    private final SessionService sessionService;
    private final MessageService messageService;

    final String MAIN_PAGE_URL = "mobile/main.sb";

    /** Constructor Injection */
    @Autowired
    public MobileAuthController(MobileAuthService mobileAuthService, SessionService sessionService, MessageService messageService) {
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
                String failUrl = "/mobile/auth/login.sb?userId=" + result.getUserId();
                // 로그인 성공
                if (code == LoginResult.SUCCESS) {

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

        /**
         * TODO 로그인 시도 결과로 이동 경로<br>
         * 1. 성공 : 메인 페이지로 이동<br>
         * 2. 실패<br>
         * 2-1. 메세지와 함께 로그인 페이지로 이동<br>
         * 2-2. 패스워드 변경 페이지로 이동<br>
         */
        String returnUrl = MAIN_PAGE_URL;
        String failUrl = "/mobile/auth/login.sb?userId=" + result.getUserId();
        // 로그인 성공
        if (code == LoginResult.SUCCESS) {

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

        if(sessionInfoVO.getLoginFg() != null){
            if(sessionInfoVO.getLoginFg().equals(LoginFg.MOBILE.getCode())){
                rUrl = "redirect:/mobile/auth/login.sb";
            }
        }else{
            if(WebUtils.getCookie(request, BaseEnv.SB_LOGIN_FG) != null){
                if(WebUtils.getCookie(request, BaseEnv.SB_LOGIN_FG).getValue().equals(LoginFg.MOBILE.getCode())){
                    rUrl = "redirect:/mobile/auth/login.sb";
                }
            }
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
}
