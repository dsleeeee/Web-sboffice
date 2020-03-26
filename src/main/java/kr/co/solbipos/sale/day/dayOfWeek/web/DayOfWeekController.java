package kr.co.solbipos.sale.day.dayOfWeek.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.dayOfWeek.service.DayOfWeekService;
import kr.co.solbipos.sale.day.dayOfWeek.service.DayOfWeekVO;
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
@RequestMapping("/sale/day/dayOfWeek")
public class DayOfWeekController {

    private final SessionService sessionService;
    private final DayOfWeekService dayOfWeekService;

    /** Constructor Injection */
    @Autowired
    public DayOfWeekController(SessionService sessionService, DayOfWeekService dayOfWeekService){
        this.sessionService = sessionService;
        this.dayOfWeekService = dayOfWeekService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String dayOfWeekView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/day/dayOfWeek/dayOfWeek";
    }

    /**
     * 주간종합탭 - 주간종합조회
     *
     * @param dayOfWeekVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2019. 11. 29.
     */
    @RequestMapping(value = "/dayOfWeek/getDayOfWeekTotalList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayOfWeekTotalList(DayOfWeekVO dayOfWeekVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
//        System.out.println("test11111");

        List<DefaultMap<Object>> result = dayOfWeekService.getDayOfWeekTotalList(dayOfWeekVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayOfWeekVO);
    }

    /**
     * 할인구별별탭 - 할인구분별 매출조회
     *
     * @param dayOfWeekVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2019. 12. 03.
     */
    @RequestMapping(value = "/dayOfWeek/getDayOfWeekDcList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayOfWeekDcList(DayOfWeekVO dayOfWeekVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dayOfWeekService.getDayOfWeekDcList(dayOfWeekVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayOfWeekVO);
    }

    /**
     * 과면세별탭 - 과면세별 매출조회
     *
     * @param dayOfWeekVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2019. 12. 04.
     */
    @RequestMapping(value = "/dayOfWeek/getDayOfWeekTaxList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayOfWeekTaxList(DayOfWeekVO dayOfWeekVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dayOfWeekService.getDayOfWeekTaxList(dayOfWeekVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayOfWeekVO);
    }

    /**
     * 시간대별탭 - 시간대별 매출조회
     *
     * @param dayOfWeekVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2019. 12. 04.
     */
    @RequestMapping(value = "/dayOfWeek/getDayOfWeekTimeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayOfWeekTimeList(DayOfWeekVO dayOfWeekVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dayOfWeekService.getDayOfWeekTimeList(dayOfWeekVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayOfWeekVO);
    }

    /**
     * 상품분류별탭 - 상품분류별 매출조회
     *
     * @param dayOfWeekVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 03. 20.
     */
    @RequestMapping(value = "/dayOfWeek/getDayOfWeekProdClassList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayOfWeekProdClassList(DayOfWeekVO dayOfWeekVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dayOfWeekService.getDayOfWeekProdClassList(dayOfWeekVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayOfWeekVO);
    }

    /**
     * 코너별탭 - 코너별 매출조회
     *
     * @param dayOfWeekVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2019. 12. 20.
     */
    @RequestMapping(value = "/dayOfWeek/getDayOfWeekCornerList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayOfWeekCornerList(DayOfWeekVO dayOfWeekVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dayOfWeekService.getDayOfWeekCornerList(dayOfWeekVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayOfWeekVO);
    }

    /**
     * 외식테이블탭 - 외식테이블별 매출조회
     *
     * @param dayOfWeekVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2019. 12. 26.
     */
    @RequestMapping(value = "/dayOfWeek/getDayOfWeekTableList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayOfWeekTableList(DayOfWeekVO dayOfWeekVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dayOfWeekService.getDayOfWeekTableList(dayOfWeekVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayOfWeekVO);
    }

    /**
     * 포스별탭 - 포스별 매출조회
     *
     * @param dayOfWeekVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2019. 12. 06.
     */
    @RequestMapping(value = "/dayOfWeek/getDayOfWeekPosList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayOfWeekPosList(DayOfWeekVO dayOfWeekVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dayOfWeekService.getDayOfWeekPosList(dayOfWeekVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayOfWeekVO);
    }
}