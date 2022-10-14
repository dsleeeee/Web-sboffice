package kr.co.solbipos.sale.month.monthMoms.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.month.monthMoms.service.MonthMomsService;
import kr.co.solbipos.sale.month.monthMoms.service.MonthMomsVO;
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
 * @Class Name : MonthMomsController.java
 * @Description : 맘스터치 > 매출분석 > 일별 매출 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.11  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/month/monthMoms")
public class MonthMomsController {

    private final SessionService sessionService;
    private final MonthMomsService monthMomsService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MonthMomsController(SessionService sessionService, MonthMomsService monthMomsService) {
        this.sessionService = sessionService;
        this.monthMomsService = monthMomsService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/monthMoms/list.sb", method = RequestMethod.GET)
    public String monthMomsView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/month/monthMoms/monthMoms";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   monthMomsVO
     * @return  String
     * @author  권지현
     * @since   2022.10.11
     */
    @RequestMapping(value = "/monthMoms/getMonthMomsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthMomsList(HttpServletRequest request, HttpServletResponse response, Model model, MonthMomsVO monthMomsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = monthMomsService.getMonthMomsList(monthMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, monthMomsVO);
    }

    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   monthMomsVO
     * @return  String
     * @author  권지현
     * @since   2022.10.11
     */
    @RequestMapping(value = "/monthMoms/getMonthMomsExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthMomsExcelList(HttpServletRequest request, HttpServletResponse response, Model model, MonthMomsVO monthMomsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = monthMomsService.getMonthMomsExcelList(monthMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, monthMomsVO);
    }

}