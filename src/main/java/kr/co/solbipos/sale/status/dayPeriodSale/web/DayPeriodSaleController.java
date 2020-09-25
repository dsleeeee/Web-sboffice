package kr.co.solbipos.sale.status.dayPeriodSale.web;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.day.day.service.DayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/sale/status/dayPeriodSale")
public class DayPeriodSaleController {
    private final SessionService sessionService;
    private final DayService dayService;

    @Autowired
    public DayPeriodSaleController(SessionService sessionService, DayService dayService) {
        this.sessionService = sessionService;
        this.dayService = dayService;
    }

    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String dayPeriodSaleView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/status/dayPeriodSale/dayPeriodSale";
    }
}