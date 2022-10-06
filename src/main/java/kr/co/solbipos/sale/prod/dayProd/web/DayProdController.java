package kr.co.solbipos.sale.prod.dayProd.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
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
 * @Class Name : DayProdController.java
 * @Description : 맘스터치 > 상품매출분석 > 일별 상품 매출 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.04  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/prod/dayProd")
public class DayProdController {

    private final SessionService sessionService;
    private final DayProdService dayProdService;

    /**
     * Constructor Injection
     */
    @Autowired
    public DayProdController(SessionService sessionService, DayProdService dayProdService) {
        this.sessionService = sessionService;
        this.dayProdService = dayProdService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/dayProd/list.sb", method = RequestMethod.GET)
    public String prodRankMomsView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/prod/dayProd/dayProd";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayProdVO
     * @return  String
     * @author  권지현
     * @since   2022.10.04
     */
    @RequestMapping(value = "/dayProd/getDayProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayProdList(HttpServletRequest request, HttpServletResponse response, Model model, DayProdVO dayProdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dayProdService.getDayProdList(dayProdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayProdVO);
    }

    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   dayProdVO
     * @return  String
     * @author  권지현
     * @since   2022.10.04
     */
    @RequestMapping(value = "/dayProd/getDayProdExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayProdExcelList(HttpServletRequest request, HttpServletResponse response, Model model, DayProdVO dayProdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dayProdService.getDayProdExcelList(dayProdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayProdVO);
    }

}