package kr.co.solbipos.sale.day.dayMoms.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.dayMoms.service.DayMomsService;
import kr.co.solbipos.sale.day.dayMoms.service.DayMomsVO;
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
 * @Class Name : DayMomsController.java
 * @Description : 맘스터치 > 매출분석 > 일별 매출 리스트
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.06  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/day/dayMoms")
public class DayMomsController {

    private final SessionService sessionService;
    private final DayMomsService dayMomsService;

    /**
     * Constructor Injection
     */
    @Autowired
    public DayMomsController(SessionService sessionService, DayMomsService dayMomsService) {
        this.sessionService = sessionService;
        this.dayMomsService = dayMomsService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/dayMoms/list.sb", method = RequestMethod.GET)
    public String dayMomsView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/day/dayMoms/dayMoms";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayMomsVO
     * @return  String
     * @author  권지현
     * @since   2022.10.06
     */
    @RequestMapping(value = "/dayMoms/getDayMomsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayMomsList(HttpServletRequest request, HttpServletResponse response, Model model, DayMomsVO dayMomsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dayMomsService.getDayMomsList(dayMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayMomsVO);
    }

    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayMomsVO
     * @return  String
     * @author  권지현
     * @since   2022.10.06
     */
    @RequestMapping(value = "/dayMoms/getDayMomsExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayMomsExcelList(HttpServletRequest request, HttpServletResponse response, Model model, DayMomsVO dayMomsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dayMomsService.getDayMomsExcelList(dayMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayMomsVO);
    }

}