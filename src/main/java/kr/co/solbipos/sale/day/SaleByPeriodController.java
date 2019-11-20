package kr.co.solbipos.sale.day;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/sale/day/saleByPeriod")
public class SaleByPeriodController {

    @Autowired
    public SaleByPeriodController() {
    }

    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String salePeriodView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/day/saleByPeriod";
    }
}
