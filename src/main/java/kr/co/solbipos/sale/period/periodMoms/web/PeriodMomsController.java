package kr.co.solbipos.sale.period.periodMoms.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.period.periodMoms.service.PeriodMomsService;
import kr.co.solbipos.sale.period.periodMoms.service.PeriodMomsVO;
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
 * @Class Name : PeriodMomsController.java
 * @Description : 맘스터치 > 매출분석 > 대비기간별매출
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
@RequestMapping("/sale/period/periodMoms")
public class PeriodMomsController {

    private final SessionService sessionService;
    private final PeriodMomsService periodMomsService;

    /**
     * Constructor Injection
     */
    @Autowired
    public PeriodMomsController(SessionService sessionService, PeriodMomsService periodMomsService) {
        this.sessionService = sessionService;
        this.periodMomsService = periodMomsService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/periodMoms/list.sb", method = RequestMethod.GET)
    public String periodMomsView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/period/periodMoms/periodMoms";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   periodMomsVO
     * @return  String
     * @author  권지현
     * @since   2022.10.11
     */
    @RequestMapping(value = "/periodMoms/getPeriodMomsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPeriodMomsList(HttpServletRequest request, HttpServletResponse response, Model model, PeriodMomsVO periodMomsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = periodMomsService.getPeriodMomsList(periodMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, periodMomsVO);
    }

    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   periodMomsVO
     * @return  String
     * @author  권지현
     * @since   2022.10.11
     */
    @RequestMapping(value = "/periodMoms/getPeriodMomsExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPeriodMomsExcelList(HttpServletRequest request, HttpServletResponse response, Model model, PeriodMomsVO periodMomsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = periodMomsService.getPeriodMomsExcelList(periodMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, periodMomsVO);
    }

}