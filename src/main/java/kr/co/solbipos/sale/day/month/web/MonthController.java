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
    public String monthView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/day/month/month";
    }

    /**
     * 월별종합탭 - 월별종합조회
     *
     * @param monthVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2019. 12. 09.
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
     * 할인구별별탭 - 할인구분별 매출조회
     *
     * @param monthVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2019. 12. 10.
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
     * 과면세별탭 - 과면세별 매출조회
     *
     * @param monthVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2019. 12. 10.
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
     * 시간대별탭 - 시간대별 매출조회
     *
     * @param monthVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2019. 12. 12.
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
     * 상품분류별탭 - 상품분류별 매출조회
     *
     * @param monthVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 03. 20.
     */
    @RequestMapping(value = "/month/getMonthProdClassList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthProdClassList(MonthVO monthVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = monthService.getMonthProdClassList(monthVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, monthVO);
    }

    /**
     * 코너별탭 - 코너별 매출조회
     *
     * @param monthVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 01. 20.
     */
    @RequestMapping(value = "/month/getMonthCornerList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthCornerList(MonthVO monthVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = monthService.getMonthCornerList(monthVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, monthVO);
    }

    /**
     * 외식테이블탭 - 외식테이블별 매출조회
     *
     * @param monthVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 01. 08.
     */
    @RequestMapping(value = "/month/getMonthTableList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthTableList(MonthVO monthVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = monthService.getMonthTableList(monthVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, monthVO);
    }

    /**
     * 포스별탭 - 포스별 매출조회
     *
     * @param monthVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2019. 12. 17.
     */
    @RequestMapping(value = "/month/getMonthPosList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthPosList(MonthVO monthVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = monthService.getMonthPosList(monthVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, monthVO);
    }

    /**
     * 사원카드별 - 사원카드별 매출조회
     *
     * @param monthVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2021.09.23
     */
    @RequestMapping(value = "/month/getMonthEmpCardList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthEmpCardList(MonthVO monthVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = monthService.getMonthEmpCardList(monthVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, monthVO);
    }
}