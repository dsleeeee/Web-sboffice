package kr.co.solbipos.base.price.salePrice.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.price.salePrice.service.SalePriceService;
import kr.co.solbipos.base.price.salePrice.service.SalePriceVO;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
import kr.co.solbipos.base.prod.prod.service.enums.PriceEnvFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : SalePriceController.java
 * @Description : 기초관리 - 가격관리 - 판매가격관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.20  김지은       최초생성
 *
 * @author 솔비포스 김지은
 * @since 2018. 12.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/price/salePrice")
public class SalePriceController {

    private final SessionService sessionService;
    private final SalePriceService salePriceService;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public SalePriceController(SessionService sessionService, SalePriceService salePriceService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.salePriceService = salePriceService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 매장 판매가 관리 화면 조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/salePrice/salePriceView.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 판매가 본사 통제여부
        PriceEnvFg priceEnvstVal = PriceEnvFg.getEnum(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0022"));

        System.out.println(">>>>> priceEnvstVal : "+ priceEnvstVal);

        model.addAttribute("priceEnvstVal", priceEnvstVal);

        return "base/price/salePrice/salePriceView";
    }


    /***
     * 상품별 정보 조회
     * @param salePriceVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/prodSalePrice/getProdInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdInfo(SalePriceVO salePriceVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = salePriceService.getProdInfo(salePriceVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /***
     * 상품별 매장 판매가 조회
     * @param salePriceVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/prodSalePrice/getProdSalePriceList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdSalePriceList(SalePriceVO salePriceVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = salePriceService.getProdSalePriceList(salePriceVO, sessionInfoVO);

        return returnListJson(Status.OK, list, salePriceVO);
    }


}
