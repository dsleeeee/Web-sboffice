package kr.co.solbipos.application.controller.login;

import static kr.co.solbipos.utils.HttpUtils.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.application.enums.login.LoginResult;
import kr.co.solbipos.application.service.login.LoginService;
import kr.co.solbipos.exception.AuthenticationException;
import kr.co.solbipos.service.session.SessionService;
import kr.co.solbipos.system.Prop;
import kr.co.solbipos.utils.spring.WebUtil;
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
    Prop prop;

    @RequestMapping(value = "login.sb", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        if (sessionService.isValidSession(request)) {
            return "redirect:/sample.sb";
        }
        return "login/login:Login";
    }


    @RequestMapping(value = "login.sb", method = RequestMethod.POST)
    public String loginProcess(
            // @Valid SessionInfo sessionInfo, BindingResult bindingResult,
            SessionInfo sessionInfo, HttpServletRequest request, HttpServletResponse response,
            Model model) {
        
        StopWatch sw = new StopWatch();
        sw.start();
        
        log.info("login start : {} ", sessionInfo.getUserId());
        
        /*
         * if (bindingResult.hasErrors()) { 
         *      return "Login"; 
         * }
         */
        
        // 아이디 저장 쿠키 처리 
        WebUtil.setCookie(prop.loginSaveId, sessionInfo.getUserId(), sessionInfo.isChk() ? -1 : 0);
        
        sessionInfo.setLoginIp(getClientIp(request));
        sessionInfo.setBrwsrInfo(request.getHeader("User-Agent"));

        // 로그인 시도
        SessionInfo si = loginService.login(sessionInfo);

        LoginResult code = si.getLoginResult();

        /**
         * TODO 로그인 시도 결과로 이동 경로 
         * 1. 성공 : 메인 페이지로 이동 
         * 2. 실패 
         * 2-1. 메세지와 함께 로그인 페이지로 이동 
         * 2-2. 패스워드 변경 페이지로
         * 이동
         */
        
        String returnUrl = "sample.sb";
        // 로그인 성공
        if (code == LoginResult.SUCCESS) {

            // 메인 페이지
            returnUrl = "sample.sb";

            // 세션 생성
            sessionService.setSessionInfo(request, response, si);
        } else if (code == LoginResult.NOT_EXISTS_ID || code == LoginResult.PASSWORD_ERROR
                || code == LoginResult.LOCK) {
            
            
            // 다시 로그인 페이지로 이동
            returnUrl = "login.sb";
            
            
        } else if (code == LoginResult.PASSWORD_CHANGE || code == LoginResult.PASSWORD_EXPIRE) {
            
            
            // 패스워드 변경 페이지로 이동
            returnUrl = "user/pwdChg.sb";
            
            
        }
        // 로그인 실패
        else {
            sw.stop();
            log.error("로그인 실패 처리 시간 : {}", sw.getTotalTimeSeconds());
            
            returnUrl = "auth/login.sb";
            
            // 실패 처리
            throw new AuthenticationException("로그인에 실패했습니다.", returnUrl);
        }

        /*
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        */
        
        sw.stop();
        log.error("로그인 성공 처리 시간 : {}", sw.getTotalTimeSeconds());
        
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


