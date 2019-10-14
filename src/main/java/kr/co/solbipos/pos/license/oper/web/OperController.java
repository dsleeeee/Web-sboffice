package kr.co.solbipos.pos.license.oper.web;

import kr.co.common.service.session.SessionService;
import kr.co.solbipos.pos.license.instlManage.service.InstlManageService;
import kr.co.solbipos.pos.license.oper.service.OperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/pos/license/oper")
public class OperController {

    private final SessionService sessionService;
    private final OperService operService;

    /** Constructor Injection */
    @Autowired
    public OperController(SessionService sessionService, OperService operService){
        this.sessionService = sessionService;
        this.operService = operService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String instalManageView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "pos/license/oper/oper";
    }
}
