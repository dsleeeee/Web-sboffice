package kr.co.solbipos.sale.prod.periodProd.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.prod.periodProd.service.PeriodProdService;
import kr.co.solbipos.sale.prod.periodProd.service.PeriodProdVO;
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
 * @Class Name : PeriodProdController.java
 * @Description : 맘스터치 > 상품매출분석 > 대비기간별 상품 매출
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
@RequestMapping("/sale/prod/periodProd")
public class PeriodProdController {

    private final SessionService sessionService;
    private final PeriodProdService periodProdService;

    /**
     * Constructor Injection
     */
    @Autowired
    public PeriodProdController(SessionService sessionService, PeriodProdService periodProdService) {
        this.sessionService = sessionService;
        this.periodProdService = periodProdService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/periodProd/list.sb", method = RequestMethod.GET)
    public String prodRankMomsView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/prod/periodProd/periodProd";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   periodProdVO
     * @return  String
     * @author  권지현
     * @since   2022.10.04
     */
    @RequestMapping(value = "/periodProd/getPeriodProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPeriodProdList(HttpServletRequest request, HttpServletResponse response, Model model, PeriodProdVO periodProdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = periodProdService.getPeriodProdList(periodProdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, periodProdVO);
    }

    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   periodProdVO
     * @return  String
     * @author  권지현
     * @since   2022.10.04
     */
    @RequestMapping(value = "/periodProd/getPeriodProdExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPeriodProdExcelList(HttpServletRequest request, HttpServletResponse response, Model model, PeriodProdVO periodProdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = periodProdService.getPeriodProdExcelList(periodProdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, periodProdVO);
    }

}