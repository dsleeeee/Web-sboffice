package kr.co.solbipos.pos.license.instlManage.web;

import kr.co.common.service.session.SessionService;
import kr.co.solbipos.pos.license.instlManage.service.InstlManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/pos/license/instlManage")
public class InstlManageController {

    private final SessionService sessionService;
    private final InstlManageService instlManageService;

    /** Constructor Injection */
    @Autowired
    public InstlManageController(SessionService sessionService, InstlManageService instlManageService){
        this.sessionService = sessionService;
        this.instlManageService = instlManageService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String instlManageView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "pos/license/instlManage/instlManage";
    }
}
