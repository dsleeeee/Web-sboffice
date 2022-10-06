package kr.co.solbipos.sale.prod.prodSaleRate.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.prod.prodSaleRate.service.ProdSaleRateService;
import kr.co.solbipos.sale.prod.prodSaleRate.service.ProdSaleRateVO;
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
 * @Class Name : ProdSaleRateController.java
 * @Description : 맘스터치 > 상품매출분석 > 상품 판매 비율
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.05  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.05
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/prod/prodSaleRate")
public class ProdSaleRateController {

    private final SessionService sessionService;
    private final ProdSaleRateService prodSaleRateService;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdSaleRateController(SessionService sessionService, ProdSaleRateService prodSaleRateService) {
        this.sessionService = sessionService;
        this.prodSaleRateService = prodSaleRateService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/prodSaleRate/list.sb", method = RequestMethod.GET)
    public String prodRankMomsView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/prod/prodSaleRate/prodSaleRate";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodSaleRateVO
     * @return  String
     * @author  권지현
     * @since   2022.10.05
     */
    @RequestMapping(value = "/prodSaleRate/getProdSaleRateList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleRateList(HttpServletRequest request, HttpServletResponse response, Model model, ProdSaleRateVO prodSaleRateVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = prodSaleRateService.getProdSaleRateList(prodSaleRateVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodSaleRateVO);
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodSaleRateVO
     * @return  String
     * @author  권지현
     * @since   2022.10.05
     */
    @RequestMapping(value = "/prodSaleRate/getProdSaleRateExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSaleRateExcelList(HttpServletRequest request, HttpServletResponse response, Model model, ProdSaleRateVO prodSaleRateVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = prodSaleRateService.getProdSaleRateExcelList(prodSaleRateVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodSaleRateVO);
    }

}