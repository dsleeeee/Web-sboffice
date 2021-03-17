package kr.co.solbipos.mobile.application.session.auth.web;

import kr.co.common.exception.AuthenticationException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.spring.WebUtil;
import kr.co.common.validate.Login;
import kr.co.solbipos.application.session.auth.enums.LoginResult;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        if (sessionService.isValidSession(request)) {
            return "redirect:/" + MAIN_PAGE_URL;
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
        if(params.isChk() || params.isChkAutoLogin()){
            WebUtil.setCookie(BaseEnv.LOGIN_CHECK_ID_SAVE, params.getUserId(),-1);
        }else{
            WebUtil.setCookie(BaseEnv.LOGIN_CHECK_ID_SAVE, params.getUserId(),0);
        }

        // 모바일 로그인 쿠키 처리
        WebUtil.setCookie("sb_login_fg", BaseEnv.SB_LOGIN_FG, -1 );

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

            // 모바일 로그인 세션 처리
            result.setSbLoginFg(BaseEnv.SB_LOGIN_FG);

            // 자동로그인 체크 관련
            if(params.isChkAutoLogin()){
                String randomNo = "1111";

                // insert DB

                WebUtil.setCookie("sb_login_auto_serial", randomNo, -1);
                result.setSbLoginAutoSerial(randomNo);

            }else{
                WebUtil.removeCookie(WebUtils.getCookie( request, "sb_login_auto_serial" ));
                result.setSbLoginAutoSerial(null);
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

        if(sessionInfoVO.getSbLoginFg() != null){
            if(sessionInfoVO.getSbLoginFg().equals(BaseEnv.SB_LOGIN_FG)){
                rUrl = "redirect:/mobile/auth/login.sb";
            }
        }else{
            if(WebUtils.getCookie(request, "sb_login_fg") != null){
                if(WebUtils.getCookie(request, "sb_login_fg").getValue().equals(BaseEnv.SB_LOGIN_FG)){
                    rUrl = "redirect:/mobile/auth/login.sb";
                }
            }
        }

        // 로그아웃 처리
        mobileAuthService.logout(request, response);

        // 모바일 로그인 쿠키 제거
        //WebUtil.removeCookie(WebUtils.getCookie( request, "sb_login_fg" ));

        return rUrl;
    }

    @RequestMapping(value = "logdenied.sb", method = RequestMethod.GET)
    public String denied(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "denied";
    }
}
