package kr.co.solbipos.sale.day.month.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.month.service.MonthService;
import kr.co.solbipos.sale.day.month.service.MonthVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/sale/day/month")
public class MonthController {

    private final SessionService sessionService;
    private final MonthService monthService;

    /** Constructor Injection */
    @Autowired
    public MonthController(SessionService sessionService, MonthService monthService){
        this.sessionService = sessionService;
        this.monthService = monthService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String MonthView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/day/month/month";
    }

    /**
     * 월별종합탭 - 월별종합조회
     *
     * @param monthVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/month/getMonthTotalList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthTotalList(MonthVO monthVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
//        System.out.println("test11111");

        List<DefaultMap<Object>> result = monthService.getMonthTotalList(monthVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, monthVO);
    }

    /**
     * 할인구별별탭 - 할인구분별매출조회
     *
     * @param monthVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/month/getMonthDcList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthDcList(MonthVO monthVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = monthService.getMonthDcList(monthVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, monthVO);
    }

    /**
     * 과면세별탭 - 과면세별매출조회
     *
     * @param monthVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/month/getMonthTaxList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthTaxList(MonthVO monthVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = monthService.getMonthTaxList(monthVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, monthVO);
    }

    /**
     * 시간대별 - 시간대별매출조회
     *
     * @param monthVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/month/getMonthTimeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthTimeList(MonthVO monthVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = monthService.getMonthTimeList(monthVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, monthVO);
    }

    /**
     * 포스별 - 포스별매출조회
     *
     * @param monthVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/month/getMonthPosList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthPosList(MonthVO monthVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = monthService.getMonthPosList(monthVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, monthVO);
    }
}