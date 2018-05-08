package kr.co.solbipos.application.controller.login;

import static kr.co.common.utils.HttpUtils.*;
import static org.springframework.util.ObjectUtils.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import kr.co.common.exception.AuthenticationException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.Prop;
import kr.co.common.utils.spring.WebUtil;
import kr.co.solbipos.application.domain.login.SessionInfoVO;
import kr.co.solbipos.application.enums.login.LoginResult;
import kr.co.solbipos.application.service.login.LoginService;
import kr.co.solbipos.application.validate.login.Login;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author 정용길
 */

@Slf4j
@Controller
@RequestMapping(value = "/auth")
public class LoginController {
    @Autowired
    LoginService loginService;

    @Autowired
    SessionService sessionService;

    @Autowired
    MessageService messageService;

    @Autowired
    Prop prop;

    final String MAIN_PAGE_URL = "main.sb";

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
    public String login(String userId, String type, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (sessionService.isValidSession(request)) {
            return "redirect:/" + MAIN_PAGE_URL;
        }
        model.addAttribute("userId", userId);
        model.addAttribute("type", isEmpty(type) ? "" : type);
        return "login/login:Login";
    }

    /**
      * <pre>
      * 사용자 웹 로그인
      * </pre>
      * @param sessionInfoVO
      * @param bindingResult
      * @param request
      * @param response
      * @param model
      * @return
      */
    @RequestMapping(value = "login.sb", method = RequestMethod.POST)
    public String loginProcess(@Validated(Login.class) SessionInfoVO sessionInfoVO,
            BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response,
            Model model) {

        StopWatch sw = new StopWatch();
        sw.start();

        log.info("login start : {} ", sessionInfoVO.getUserId());

        if (bindingResult.hasErrors()) {
            return "login/login:Login";
        }

        // 아이디 저장 쿠키 처리
        WebUtil.setCookie(prop.loginSaveId, sessionInfoVO.getUserId(), sessionInfoVO.isChk() ? -1 : 0);

        sessionInfoVO.setLoginIp(getClientIp(request));
        sessionInfoVO.setBrwsrInfo(request.getHeader("User-Agent"));

        // 로그인 시도
        SessionInfoVO si = loginService.login(sessionInfoVO);

        LoginResult code = si.getLoginResult();

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
            // 메인 페이지
            returnUrl = MAIN_PAGE_URL;
            // 세션 생성
            sessionService.setSessionInfo(request, response, si);
        } else if (code == LoginResult.NOT_EXISTS_ID || code == LoginResult.PASSWORD_ERROR) {
            // 다시 로그인 페이지로 이동
            returnUrl = "/auth/login.sb?userId=" + si.getUserId();
            throw new AuthenticationException(messageService.get("login.idpw.fail"), returnUrl);
        } else if (code == LoginResult.LOCK) {
            // 잠금상태의 유져
            returnUrl = "/auth/login.sb?userId=" + si.getUserId();
            throw new AuthenticationException(messageService.get("login.pw.find.lock.user"), returnUrl);
        } else if (code == LoginResult.PASSWORD_CHANGE) {

            // 패스워드 변경 레이어 팝업
            // 초기 비밀번호 입니다. 비밀번호 변경이 필요합니다.
             returnUrl = "/auth/login.sb?userId=" + si.getUserId() + "&type=pwChg";
            throw new AuthenticationException(messageService.get("login.pwd.chg"), returnUrl);
        }
        else if(code == LoginResult.PASSWORD_EXPIRE) {
            // 비밀번호 변경 및 연장이 필요합니다.
            returnUrl = "/auth/login.sb?userId=" + si.getUserId() + "&type=pwExpire";
            throw new AuthenticationException(messageService.get("login.pwd.expire"), returnUrl);
        }
        // 로그인 실패
        else {
            sw.stop();
            log.error("로그인 실패 처리 시간 : {}", sw.getTotalTimeSeconds());
            returnUrl = "auth/login.sb?userId=" + si.getUserId();
            // 실패 처리
            throw new AuthenticationException(messageService.get("login.fail"), returnUrl);
        }

        /*
         * try { Thread.sleep(3000); } catch (InterruptedException e) { // TODO Auto-generated catch
         * block e.printStackTrace(); }
         */

        sw.stop();
        log.error("로그인 성공 처리 시간 : {}", sw.getTotalTimeSeconds());

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
        // 로그아웃 처리
        loginService.logout(request, response);
        return "redirect:/auth/login.sb";
    }

    @RequestMapping(value = "logdenied.sb", method = RequestMethod.GET)
    public String denied(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "denied";
    }
}


