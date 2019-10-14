package kr.co.solbipos.pos.license.instalAgency.web;

import kr.co.common.service.session.SessionService;
import kr.co.solbipos.pos.license.instalAgency.service.InstalAgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/pos/license/instalAgency")
public class InstalAgencyController {

    private final SessionService sessionService;
    private final InstalAgencyService instalAgencyService;

    /** Constructor Injection */
    @Autowired
    public InstalAgencyController(SessionService sessionService, InstalAgencyService instalAgencyService){
        this.sessionService = sessionService;
        this.instalAgencyService = instalAgencyService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String instalAgencyView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "pos/license/instalAgency/instalAgency";
    }
}
