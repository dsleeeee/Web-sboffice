package kr.co.solbipos.sale.prod.prodClassSaleRate.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.prod.prodClassSaleRate.service.ProdClassSaleRateService;
import kr.co.solbipos.sale.prod.prodClassSaleRate.service.ProdClassSaleRateVO;
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
 * @Class Name : ProdClassSaleRateController.java
 * @Description : 맘스터치 > 상품매출분석 > 상품군 판매비율
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
@RequestMapping("/sale/prod/prodClassSaleRate")
public class ProdClassSaleRateController {

    private final SessionService sessionService;
    private final ProdClassSaleRateService prodClassSaleRateService;

    /**
     * Constructor Injection
     */
    @Autowired
    public ProdClassSaleRateController(SessionService sessionService, ProdClassSaleRateService prodClassSaleRateService) {
        this.sessionService = sessionService;
        this.prodClassSaleRateService = prodClassSaleRateService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/prodClassSaleRate/list.sb", method = RequestMethod.GET)
    public String prodRankMomsView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/prod/prodClassSaleRate/prodClassSaleRate";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodClassSaleRateVO
     * @return  String
     * @author  권지현
     * @since   2022.10.06
     */
    @RequestMapping(value = "/prodClassSaleRate/getProdClassSaleRateList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdClassSaleRateList(HttpServletRequest request, HttpServletResponse response, Model model, ProdClassSaleRateVO prodClassSaleRateVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = prodClassSaleRateService.getProdClassSaleRateList(prodClassSaleRateVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodClassSaleRateVO);
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   prodClassSaleRateVO
     * @return  String
     * @author  권지현
     * @since   2022.10.06
     */
    @RequestMapping(value = "/prodClassSaleRate/getProdClassSaleRateExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdClassSaleRateExcelList(HttpServletRequest request, HttpServletResponse response, Model model, ProdClassSaleRateVO prodClassSaleRateVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = prodClassSaleRateService.getProdClassSaleRateExcelList(prodClassSaleRateVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, prodClassSaleRateVO);
    }

}