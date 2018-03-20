package kr.co.solbipos.pos.controller.confg.loginstatus;

import static kr.co.solbipos.utils.grid.ReturnUtil.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.solbipos.enums.Status;
import kr.co.solbipos.pos.domain.confg.loginstatus.LoginStatus;
import kr.co.solbipos.pos.service.confg.loginstatus.LoginStatusService;
import kr.co.solbipos.structure.DefaultMap;
import kr.co.solbipos.structure.Result;

/**
 * 
 * @author 정용길
 */

@Controller
@RequestMapping(value = "/pos/confg/loginstatus/loginstatus/")
public class LoginStatusController {

    @Autowired
    LoginStatusService loginStatusService;

    /**
     * POS관리 > POS 설정관리 > POS 로그인 현황 화면 이동
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "list.sb", method = RequestMethod.GET)
    public String loginstatusList(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        return "pos/confg/loginstatus/loginStatus";
    }

    /**
     * POS관리 > POS 설정관리 > POS 로그인 조회
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result loginstatusListPost(LoginStatus loginStatus, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        List<DefaultMap<Object>> result = loginStatusService.selectLoginStatus(loginStatus);

        return returnListJson(Status.OK, result, loginStatus);
    }
}


