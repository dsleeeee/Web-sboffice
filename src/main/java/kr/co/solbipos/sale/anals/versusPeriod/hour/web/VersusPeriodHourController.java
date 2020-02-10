package kr.co.solbipos.sale.anals.versusPeriod.hour.web;

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
import kr.co.solbipos.sale.anals.versusPeriod.hour.service.VersusPeriodHourService;
import kr.co.solbipos.sale.anals.versusPeriod.hour.service.VersusPeriodHourVO;

/**
 * @Class Name : ProdHourController.java
 * @Description : 매출관리 > 매출현황 > 시간대별별매출상세현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.08  김진      최초생성
 *
 * @author 솔비포스 
 * @since 2020.01.08
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/anals/versusPeriod")
public class VersusPeriodHourController {
    private final SessionService sessionService;
    private final VersusPeriodHourService versusPeriodHourService;

    @Autowired
    public VersusPeriodHourController(SessionService sessionService, VersusPeriodHourService versusPeriodHourService) {
        this.sessionService = sessionService;
        this.versusPeriodHourService = versusPeriodHourService;
    }


/*    @RequestMapping(value = "/hour/view.sb", method = RequestMethod.GET)
    public String prodHourView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/status/versusperiod/hour/versusPeriodHour";
    }
*/
    @RequestMapping(value = "/hour/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdHourList(HttpServletRequest request, HttpServletResponse response, Model model, VersusPeriodHourVO versusPeriodHourVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = versusPeriodHourService.getVersusPeriodHourList(versusPeriodHourVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, versusPeriodHourVO);
    }
}
