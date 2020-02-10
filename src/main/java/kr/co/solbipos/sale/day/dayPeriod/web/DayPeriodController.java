package kr.co.solbipos.sale.day.dayPeriod.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.dayPeriod.service.DayPeriodService;
import kr.co.solbipos.sale.day.dayPeriod.service.DayPeriodVO;
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
@RequestMapping("/sale/day/dayPeriod")
public class DayPeriodController {

    private final SessionService sessionService;
    private final DayPeriodService dayPeriodService;

    /**
     * Constructor Injection
     */
    @Autowired
    public DayPeriodController(SessionService sessionService, DayPeriodService dayPeriodService) {
        this.sessionService = sessionService;
        this.dayPeriodService = dayPeriodService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String dayPeriodView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/day/dayPeriod/dayPeriod";
    }

    /**
     * 시간대별탭 - 시간대별 매출조회
     *
     * @param dayPeriodVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 01. 30.
     */
    @RequestMapping(value = "/dayPeriod/getDayPeriodTimeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayPeriodTimeList(DayPeriodVO dayPeriodVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
//        System.out.println("test11111");

        List<DefaultMap<Object>> result = dayPeriodService.getDayPeriodTimeList(dayPeriodVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayPeriodVO);
    }

    /**
     * 시간대별탭 - 시간대별 매출상세조회
     *
     * @param dayPeriodVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 01. 30.
     */
    @RequestMapping(value = "/dayPeriod/getDayPeriodTimeDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayPeriodTimeDetailList(DayPeriodVO dayPeriodVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dayPeriodService.getDayPeriodTimeDetailList(dayPeriodVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayPeriodVO);
    }

    /**
     * 상품분류별탭 - 상품분류별 매출조회
     *
     * @param dayPeriodVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 02. 07.
     */
    @RequestMapping(value = "/dayPeriod/getDayPeriodProdClassList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayPeriodProdClassList(DayPeriodVO dayPeriodVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dayPeriodService.getDayPeriodProdClassList(dayPeriodVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayPeriodVO);
    }

    /**
     * 상품분류별탭 - 상품분류별 매출상세조회
     *
     * @param dayPeriodVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 02. 07.
     */
    @RequestMapping(value = "/dayPeriod/getDayPeriodProdClassDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayPeriodProdClassDetailList(DayPeriodVO dayPeriodVO, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dayPeriodService.getDayPeriodProdClassDetailList(dayPeriodVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayPeriodVO);
    }

    /**
     * 외식테이블별탭 - 외식테이블별 매출조회
     *
     * @param dayPeriodVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 02. 03.
     */
    @RequestMapping(value = "/dayPeriod/getDayPeriodTableList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayPeriodTableList(DayPeriodVO dayPeriodVO, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dayPeriodService.getDayPeriodTableList(dayPeriodVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayPeriodVO);
    }

    /**
     * 코너별탭 - 코너별 매출조회
     *
     * @param dayPeriodVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 02. 04.
     */
    @RequestMapping(value = "/dayPeriod/getDayPeriodCornerList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayPeriodCornerList(DayPeriodVO dayPeriodVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dayPeriodService.getDayPeriodCornerList(dayPeriodVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayPeriodVO);
    }

    /**
     * 코너별탭 - 코너별 매출상세조회
     *
     * @param dayPeriodVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 02. 04.
     */
    @RequestMapping(value = "/dayPeriod/getDayPeriodCornerDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayPeriodCornerDetailList(DayPeriodVO dayPeriodVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dayPeriodService.getDayPeriodCornerDetailList(dayPeriodVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayPeriodVO);
    }

    /**
     * 상품권별탭 - 상품권별 매출조회
     *
     * @param dayPeriodVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 02. 06.
     */
    @RequestMapping(value = "/dayPeriod/getDayPeriodGiftList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayPeriodGiftList(DayPeriodVO dayPeriodVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dayPeriodService.getDayPeriodGiftList(dayPeriodVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayPeriodVO);
    }

    /**
     * 상품권별탭 - 상품권별 매출상세조회
     *
     * @param dayPeriodVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 02. 06.
     */
    @RequestMapping(value = "/dayPeriod/getDayPeriodGiftDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayPeriodGiftDetailList(DayPeriodVO dayPeriodVO, HttpServletRequest request,
                                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dayPeriodService.getDayPeriodGiftDetailList(dayPeriodVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayPeriodVO);
    }
}