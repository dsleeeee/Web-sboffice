package kr.co.solbipos.pos.license.instalManage.web;

import kr.co.common.service.session.SessionService;
import kr.co.solbipos.pos.license.instalManage.service.InstalManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/pos/license/instalManage")
public class InstalManageController {

    private final SessionService sessionService;
    private final InstalManageService instalManageService;

    /** Constructor Injection */
    @Autowired
    public InstalManageController(SessionService sessionService, InstalManageService instalManageService){
        this.sessionService = sessionService;
        this.instalManageService = instalManageService;
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
        return "pos/license/instalManage/instalManage";
    }
}
