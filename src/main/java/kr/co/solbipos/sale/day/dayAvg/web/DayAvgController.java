package kr.co.solbipos.sale.day.dayAvg.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.dayAvg.service.DayAvgService;
import kr.co.solbipos.sale.day.dayAvg.service.DayAvgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @Class Name : DayAvgController.java
 * @Description : 맘스터치 > 매출분석 > 일별 매출 현황(평균합산)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.12  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.12
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/day/dayAvg")
public class DayAvgController {

    private final SessionService sessionService;
    private final DayAvgService dayAvgService;

    /**
     * Constructor Injection
     */
    @Autowired
    public DayAvgController(SessionService sessionService, DayAvgService dayAvgService) {
        this.sessionService = sessionService;
        this.dayAvgService = dayAvgService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/dayAvg/list.sb", method = RequestMethod.GET)
    public String dayAvgView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/day/dayAvg/dayAvg";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayAvgVO
     * @return  String
     * @author  권지현
     * @since   2022.10.12
     */
    @RequestMapping(value = "/dayAvg/getDayAvgList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayAvgList(HttpServletRequest request, HttpServletResponse response, Model model, DayAvgVO dayAvgVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dayAvgService.getDayAvgList(dayAvgVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayAvgVO);
    }

    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayAvgVO
     * @return  String
     * @author  권지현
     * @since   2022.10.12
     */
    @RequestMapping(value = "/dayAvg/getDayAvgExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayAvgExcelList(HttpServletRequest request, HttpServletResponse response, Model model, DayAvgVO dayAvgVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dayAvgService.getDayAvgExcelList(dayAvgVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayAvgVO);
    }

}