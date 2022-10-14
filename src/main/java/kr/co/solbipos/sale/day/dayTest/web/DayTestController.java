package kr.co.solbipos.sale.day.dayTest.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.dayTest.service.DayTestService;
import kr.co.solbipos.sale.day.dayTest.service.DayTestVO;
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
 * @Class Name : DayTestController.java
 * @Description : 맘스터치 > 매출분석 > 일별 매출 현황(테스트)
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
@RequestMapping("/sale/day/dayTest")
public class DayTestController {

    private final SessionService sessionService;
    private final DayTestService dayTestService;

    /**
     * Constructor Injection
     */
    @Autowired
    public DayTestController(SessionService sessionService, DayTestService dayTestService) {
        this.sessionService = sessionService;
        this.dayTestService = dayTestService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/dayTest/list.sb", method = RequestMethod.GET)
    public String dayTestView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/day/dayTest/dayTest";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayTestVO
     * @return  String
     * @author  권지현
     * @since   2022.10.12
     */
    @RequestMapping(value = "/dayTest/getDayTestList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayTestList(HttpServletRequest request, HttpServletResponse response, Model model, DayTestVO dayTestVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dayTestService.getDayTestList(dayTestVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayTestVO);
    }

    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayTestVO
     * @return  String
     * @author  권지현
     * @since   2022.10.12
     */
    @RequestMapping(value = "/dayTest/getDayTestExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayTestExcelList(HttpServletRequest request, HttpServletResponse response, Model model, DayTestVO dayTestVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dayTestService.getDayTestExcelList(dayTestVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayTestVO);
    }

}