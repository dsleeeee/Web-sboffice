package kr.co.solbipos.application.controller.login;

import static kr.co.solbipos.utils.HttpUtils.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.enums.login.LoginResult;
import kr.co.solbipos.application.service.login.LoginService;
import kr.co.solbipos.exception.AuthenticationException;
import kr.co.solbipos.service.session.SessionService;
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

    @RequestMapping(value = "login.sb", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        if (sessionService.isValidSession(request)) {
            return "redirect:/sample.sb";
        }
        return "Login";
    }


    @RequestMapping(value = "login.sb", method = RequestMethod.POST)
    public String loginProcess(HttpServletRequest request, HttpServletResponse response,
            SessionInfo sessionInfo, Model model) {

        log.info("login start : {} ", sessionInfo.getUserId());

        String returnUrl = "";

        sessionInfo.setLoginIp(getClientIp(request));
        sessionInfo.setBrwsrInfo(request.getHeader("User-Agent"));

        SessionInfo si = loginService.login(sessionInfo);

        LoginResult code = si.getLoginResult();
        
        // 로그인 성공
        if (code == LoginResult.SUCCESS) {

            // 메인 페이지
            returnUrl = "sample.sb";

            // 세션 생성
            sessionService.setSessionInfo(request, response, si);
        }
        // 로그인 실패
        else {
            // 실패 처리
            throw new AuthenticationException("로그인에 실패했습니다.", "/auth/login.sb");
        }

        return "redirect:/" + returnUrl;
    }

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


