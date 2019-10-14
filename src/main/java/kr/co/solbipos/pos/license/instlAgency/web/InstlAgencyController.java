package kr.co.solbipos.pos.license.instlAgency.web;

import kr.co.common.service.session.SessionService;
import kr.co.solbipos.pos.license.instlAgency.service.InstlAgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/pos/license/instlAgency")
public class InstlAgencyController {

    private final SessionService sessionService;
    private final InstlAgencyService instlAgencyService;

    /** Constructor Injection */
    @Autowired
    public InstlAgencyController(SessionService sessionService, InstlAgencyService instlAgencyService){
        this.sessionService = sessionService;
        this.instlAgencyService = instlAgencyService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String instlAgencyView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "pos/license/instlAgency/instlAgency";
    }
}
