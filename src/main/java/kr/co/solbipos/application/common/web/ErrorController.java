package kr.co.solbipos.application.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import kr.co.common.service.session.SessionService;

/**
 *
 * @author 정용길
 */

@Controller
@RequestMapping(value = "/error")
public class ErrorController {

    @Autowired
    SessionService sessionService;

    @RequestMapping(value = "/403.sb")
    public String denied(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "error/403";
    }

    @RequestMapping(value = "/404.sb")
    public String notFound(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "error/404";
    }

    @RequestMapping(value = "/500.sb")
    public String serverError(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "error/500";
    }


}


