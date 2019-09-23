package kr.co.solbipos.store.manage.status.web;

import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.store.manage.status.service.StoreStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/store/manage/status/store")
public class StoreStatusController {
    private final SessionService sessionService;
    private final StoreStatusService storeStatusService;

    @Autowired
    public StoreStatusController(SessionService sessionService, StoreStatusService storeStatusService) {
        this.sessionService = sessionService;
        this.storeStatusService = storeStatusService;
    }

    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String statusView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "store/manage/status/status";
    }

}
