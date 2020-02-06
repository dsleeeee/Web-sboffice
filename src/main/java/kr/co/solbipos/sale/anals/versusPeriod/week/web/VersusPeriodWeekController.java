package kr.co.solbipos.sale.anals.versusPeriod.week.web;

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
import kr.co.solbipos.sale.anals.versusPeriod.week.service.VersusPeriodWeekService;
import kr.co.solbipos.sale.anals.versusPeriod.week.service.VersusPeriodWeekVO;

@Controller
@RequestMapping("/sale/anals/versusPeriod")
public class VersusPeriodWeekController {
    private final SessionService sessionService;
    private final VersusPeriodWeekService versusPeriodWeekService;

    @Autowired
    public VersusPeriodWeekController(SessionService sessionService, VersusPeriodWeekService versusPeriodWeekService) {
        this.sessionService = sessionService;
        this.versusPeriodWeekService = versusPeriodWeekService;
    }


/*    @RequestMapping(value = "/week/view.sb", method = RequestMethod.GET)
    public String prodCalssView(HttpServletRequest request, HttpServletResponse response, Model model) {
    	return "sale/anals/versusperiod/week/versusPeriodWeek";
    }
*/

    @RequestMapping(value = "/week/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVersusPeriodWeekList(HttpServletRequest request, HttpServletResponse response, Model model, VersusPeriodWeekVO versusPeriodWeekVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = versusPeriodWeekService.getVersusPeriodWeekList(versusPeriodWeekVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, versusPeriodWeekVO);
    }
}
