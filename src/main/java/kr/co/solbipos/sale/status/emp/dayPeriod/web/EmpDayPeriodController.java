package kr.co.solbipos.sale.status.emp.dayPeriod.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.emp.dayPeriod.service.EmpDayPeriodService;
import kr.co.solbipos.sale.status.emp.dayPeriod.service.EmpDayPeriodVO;

@Controller
@RequestMapping("/sale/status/emp")
public class EmpDayPeriodController {
    private final SessionService sessionService;
    private final EmpDayPeriodService empDayPeriodService;

    @Autowired
    public EmpDayPeriodController(SessionService sessionService, EmpDayPeriodService empDayPeriodService) {
        this.sessionService = sessionService;
        this.empDayPeriodService = empDayPeriodService;
    }

    @RequestMapping(value = "/dayperiod/view.sb", method = RequestMethod.GET)
    public String empDayPeriodView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/sataus/emp/dayPeriod/dayPeriod";
    }
    
    @RequestMapping(value = "/dayperiod/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpDayPeriodList(HttpServletRequest request, HttpServletResponse response,
        Model model, EmpDayPeriodVO empDayPeriodVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = empDayPeriodService.getEmpDayPeriodList(empDayPeriodVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, empDayPeriodVO);
    }
    
    @RequestMapping(value = "/dayperiod/dtl.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpDayPeriodDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, EmpDayPeriodVO empDayPeriodVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = empDayPeriodService.getEmpDayPeriodDtlList(empDayPeriodVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, empDayPeriodVO);
    }
}
