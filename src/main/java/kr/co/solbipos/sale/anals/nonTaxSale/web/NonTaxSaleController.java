package kr.co.solbipos.sale.anals.nonTaxSale.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.nonTaxSale.service.NonTaxSaleService;
import kr.co.solbipos.sale.anals.nonTaxSale.service.NonTaxSaleVO;
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
 * @Class Name  : NonTaxSaleController.java
 * @Description : 미스터피자 > 매출분석 > 비과세매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.01.02  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.01.02
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/anals/nonTaxSale")
public class NonTaxSaleController {

    private final SessionService sessionService;
    private final NonTaxSaleService nonTaxSaleService;

    /**
     * Constructor Injection
     */
    @Autowired
    public NonTaxSaleController(SessionService sessionService, NonTaxSaleService nonTaxSaleService) {
        this.sessionService = sessionService;
        this.nonTaxSaleService = nonTaxSaleService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/nonTaxSale/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        NonTaxSaleVO nonTaxSaleVO = new NonTaxSaleVO();

        return "sale/anals/nonTaxSale/nonTaxSale";
    }

    /**
     * 비과세매출 - 조회
     *
     * @param   request
     * @param   response
     * @param   model
     * @param   nonTaxSaleVO
     * @return
     * @author  김유승
     * @since   2006.01.02
     */
    @RequestMapping(value = "/nonTaxSale/getNonTaxSaleList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNonTaxSaleList(HttpServletRequest request, HttpServletResponse response,
                                    Model model, NonTaxSaleVO nonTaxSaleVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = nonTaxSaleService.getNonTaxSaleList(nonTaxSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, nonTaxSaleVO);
    }
}
