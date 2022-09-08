package kr.co.solbipos.stock.product.stock.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.product.stock.service.StockService;
import kr.co.solbipos.stock.product.stock.service.StockVO;
import kr.co.solbipos.stock.product.weightStock.service.WeightStockVO;
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
 * @Class Name : StockController.java
 * @Description : 재고관리 > 생산관리 > 재고현황(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.07  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.09.07
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/stock/product/stock")
public class StockController {
    private final SessionService sessionService;
    private final StockService stockService;

    @Autowired
        public StockController(SessionService sessionService, StockService stockService){
        this.sessionService = sessionService;
        this.stockService = stockService;
    }

    /**
     * 재고현황(매장) 화면 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/stock/list.sb", method = RequestMethod.GET)
    public String weightStockView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "stock/product/stock/stock";
    }

    /**
     * 재고현황(매장) - 조회
     * @param stockVO
     * @param request
     * @param response
     * @param model
     * @return Object
     * @author  이다솜
     * @since   2022. 09. 07.
     */
    @RequestMapping(value = "/stock/getStockList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getWeightStockList(StockVO stockVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = stockService.getStockList(stockVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, stockVO);
    }


}
