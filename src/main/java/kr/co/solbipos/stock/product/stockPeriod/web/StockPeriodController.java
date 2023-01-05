package kr.co.solbipos.stock.product.stockPeriod.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.product.stockPeriod.service.StockPeriodService;
import kr.co.solbipos.stock.product.stockPeriod.service.StockPeriodVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : StockPeriodController.java
 * @Description : 재고관리 > 생산관리 > 재고현황(매장-기간별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.22  김설아      최초생성
 *
 * @author 솔비포스 WEB개발팀 김설아
 * @since 2022.12.22
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/stock/product/stockPeriod")
public class StockPeriodController {

    private final SessionService sessionService;
    private final StockPeriodService stockPeriodService;

    /**
     * Constructor Injection
     */
    @Autowired
    public StockPeriodController(SessionService sessionService, StockPeriodService stockPeriodService) {
        this.sessionService = sessionService;
        this.stockPeriodService = stockPeriodService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/stockPeriod/list.sb", method = RequestMethod.GET)
    public String stockPeriodView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "stock/product/stockPeriod/stockPeriod";
    }

    /**
     * 재고현황(매장-기간별) - 조회
     *
     * @param stockPeriodVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 12. 22.
     */
    @RequestMapping(value = "/stockPeriod/getStockPeriodList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStockPeriodList(StockPeriodVO stockPeriodVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = stockPeriodService.getStockPeriodList(stockPeriodVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, stockPeriodVO);
    }
}