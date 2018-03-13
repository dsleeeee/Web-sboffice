package kr.co.solbipos.application.controller.error;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author 정용길
 */

@Slf4j
@Controller
@RequestMapping(value = "/error")
public class ErrorController {

    @RequestMapping(value = "/403.sb", method = RequestMethod.GET)
    public String denied(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "error/403";
    }
}


