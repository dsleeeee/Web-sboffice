package kr.co.solbipos.kookmin.saleAnalysis.saleAnalysisByProduct.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.saleAnalysis.saleAnalysisByProduct.service.SaleAnalysisByProductService;
import kr.co.solbipos.kookmin.saleAnalysis.saleAnalysisByProduct.service.SaleAnalysisByProductVO;
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
 * @Class Name  : SaleAnalysisByProductController.java
 * @Description : 국민대 > 매출분석 > 상품별 매출분석
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.30  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.09.30
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/kookmin/saleAnalysis/saleAnalysisByProduct")
public class SaleAnalysisByProductController {

    private final SessionService sessionService;
    private final SaleAnalysisByProductService saleByAnalysisByProductService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SaleAnalysisByProductController(SessionService sessionService, SaleAnalysisByProductService saleByAnalysisByProductService) {
        this.sessionService = sessionService;
        this.saleByAnalysisByProductService = saleByAnalysisByProductService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/saleAnalysisByProduct/view.sb", method = RequestMethod.GET)
    public String saleAnalysisByProduct(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "kookmin/saleAnalysis/saleAnalysisByProduct/saleAnalysisByProduct";
    }

    /**
     * 상품별 매출분석 조회
     *
     * @param   saleAnalysisByProductVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 09. 30.
     */
    @RequestMapping(value = "/saleAnalysisByProduct/getSaleAnalysisByProductList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleAnalysisByProductList(SaleAnalysisByProductVO saleAnalysisByProductVO, HttpServletRequest request,
                                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = saleByAnalysisByProductService.getSaleAnalysisByProductList(saleAnalysisByProductVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleAnalysisByProductVO);
    }
}
