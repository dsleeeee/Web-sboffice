package kr.co.solbipos.sale.status.table.dayPeriod.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import kr.co.solbipos.sale.status.table.dayPeriod.service.TableDayPeriodService;
import kr.co.solbipos.sale.status.table.dayPeriod.service.TableDayPeriodVO;

@Controller
@RequestMapping("sale/status/table")
public class TableDayPeriodController {

	private final SessionService sessionService;
    private final TableDayPeriodService tableDayPeriodService;

    public TableDayPeriodController(SessionService sessionService, TableDayPeriodService tableDayPeriodService) {
		super();
		this.sessionService = sessionService;
		this.tableDayPeriodService = tableDayPeriodService;
	}

    @RequestMapping(value = "/dayperiod/view.sb", method = RequestMethod.GET)
    public String todayDtlView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/status/table/dayperiod";
    }

    @RequestMapping(value = "/dayperiod/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTableDayPeriodList(HttpServletRequest request, HttpServletResponse response, Model model, TableDayPeriodVO tdpVO) {

            SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
            List<DefaultMap<String>> list = tableDayPeriodService.getTableDayPeriodList(tdpVO, sessionInfoVO);
            return ReturnUtil.returnListJson(Status.OK, list, tdpVO);
    }

    @RequestMapping(value = "/dayperiod/excelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTableDayPeriodExcelList(HttpServletRequest request, HttpServletResponse response, Model model, TableDayPeriodVO tdpVO) {

            SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
            List<DefaultMap<String>> list = tableDayPeriodService.getTableDayPeriodExcelList(tdpVO, sessionInfoVO);
            return ReturnUtil.returnListJson(Status.OK, list, tdpVO);
    }

}
