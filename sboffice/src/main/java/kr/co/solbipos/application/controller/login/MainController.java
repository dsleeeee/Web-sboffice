package kr.co.solbipos.application.controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.service.session.SessionService;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author 정용길
 */

@Slf4j
@Controller
public class MainController {

    @Autowired
    SessionService sessionService;

    /**
      * 로그인 후 메인 페이지로 이동
      * 
      * @param request
      * @param response
      * @param model
      * @return
      */
    @RequestMapping(value = "main.sb", method = RequestMethod.GET)
    public String main(HttpServletRequest request, HttpServletResponse response, Model model) {
        
        if (!sessionService.isValidSession(request)) {
            return "redirect:/auth/login.sb";
        }
        
        SessionInfo sessionInfo = sessionService.getSessionInfo(request);
        
        String userAuthType = sessionInfo.getUserAuthType();
        
        
        /** 
         * 
         * 유져 권한 타입<br> 
         * 시스템 : SYSTEM<br>
         * 본사 : HEDOFC(HEAD OFFICE)<br>
         * 대리점 : AGENCY<br>
         * 가맹점 : MRHST(MEMBER BRANCH STORE)
         * enum type 으로 변경 해야됨
         * 
         * */
        
        
        if(userAuthType.equals("HEDOFC")) {
            return "application/main/hedofcMain";
        }
        else if(userAuthType.equals("AGENCY")) {
            return "application/main/agencyMain";
        }
        else if(userAuthType.equals("SYSTEM")) {
            return "application/main/systemMain";
        }
        else if(userAuthType.equals("MRHST")) {
            return "application/main/mrhstMain";
        }
        
        return "application/main/mrhstMain";
    }
}


