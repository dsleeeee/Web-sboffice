package kr.co.solbipos.sale.anals.timeSaleBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.timeSaleBenson.service.TimeSaleBensonService;
import kr.co.solbipos.sale.anals.timeSaleBenson.service.TimeSaleBensonVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : TimeSaleBensonController.java
 * @Description : 벤슨 > 매출분석 > 시간대매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.20  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.20
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/anals/timeSaleBenson")
public class TimeSaleBensonController {

    private final SessionService sessionService;
    private final TimeSaleBensonService timeSaleBensonService;

    /**
     *  Constructor Injection
     */
    public TimeSaleBensonController(SessionService sessionService, TimeSaleBensonService timeSaleBensonService) {
        this.sessionService = sessionService;
        this.timeSaleBensonService = timeSaleBensonService;
    }

    /**
     * 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2026.07.20
     */
    @RequestMapping(value = "/timeSaleBenson/view.sb", method = RequestMethod.GET)
    public String timeSaleBensonView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/anals/timeSaleBenson/timeSaleBenson";
    }

    /**
     * 일별 탭 - 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   timeSaleBensonVO
     * @return  String
     * @author  김유승
     * @since   2026.07.20
     */
    @RequestMapping(value = "/timeSaleBensonDay/getTimeSaleBensonDayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTimeSaleBensonDayList(HttpServletRequest request, HttpServletResponse response,
                                            Model model, TimeSaleBensonVO timeSaleBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<Object>> list = timeSaleBensonService.getTimeSaleBensonDayList(timeSaleBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, timeSaleBensonVO);
    }

    /**
     * 일별 탭 - 조회조건 엑셀다운로드
     * @param   request
     * @param   response
     * @param   model
     * @param   timeSaleBensonVO
     * @return  String
     * @author  김유승
     * @since   2026.07.20
     */
    @RequestMapping(value = "/timeSaleBensonDay/getTimeSaleBensonDayExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTimeSaleBensonDayExcelList(HttpServletRequest request, HttpServletResponse response,
                                                 Model model, TimeSaleBensonVO timeSaleBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<Object>> list = timeSaleBensonService.getTimeSaleBensonDayExcelList(timeSaleBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, timeSaleBensonVO);
    }

    /**
     * 월별 탭 - 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   timeSaleBensonVO
     * @return  String
     * @author  김유승
     * @since   2026.07.20
     */
    @RequestMapping(value = "/timeSaleBensonMonth/getTimeSaleBensonMonthList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTimeSaleBensonMonthList(HttpServletRequest request, HttpServletResponse response,
                                              Model model, TimeSaleBensonVO timeSaleBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<Object>> list = timeSaleBensonService.getTimeSaleBensonMonthList(timeSaleBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, timeSaleBensonVO);
    }

    /**
     * 월별 탭 - 조회조건 엑셀다운로드
     * @param   request
     * @param   response
     * @param   model
     * @param   timeSaleBensonVO
     * @return  String
     * @author  김유승
     * @since   2026.07.20
     */
    @RequestMapping(value = "/timeSaleBensonMonth/getTimeSaleBensonMonthExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTimeSaleBensonMonthExcelList(HttpServletRequest request, HttpServletResponse response,
                                                   Model model, TimeSaleBensonVO timeSaleBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<Object>> list = timeSaleBensonService.getTimeSaleBensonMonthExcelList(timeSaleBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, timeSaleBensonVO);
    }
}
