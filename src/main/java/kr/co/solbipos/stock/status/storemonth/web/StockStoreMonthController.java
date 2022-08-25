package kr.co.solbipos.stock.status.storemonth.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.store.month.service.StoreMonthVO;
import kr.co.solbipos.stock.status.storemonth.service.StockStoreMonthService;
import kr.co.solbipos.stock.status.storemonth.service.StockStoreMonthVO;

/**
 * @Class Name : StoreMonthController.java
 * @Description : 재고관리 > 매장재고현황 > 매장월수불 
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.03.20      조동훤      최초생성
 *
 * @author 솔비포스 
 * @since 2020.03.20
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/stock/status/storeMonth")
public class StockStoreMonthController {
	private final SessionService sessionService;
	private final StockStoreMonthService stockStoreMonthService;
	
	@Autowired
	public StockStoreMonthController(SessionService sessionService, StockStoreMonthService stockStoreMonthService) {
		this.sessionService = sessionService;
		this.stockStoreMonthService = stockStoreMonthService;
	}
	
	/**
     * 매장월수불 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  조동훤
     * @since   2020. 03. 18.
     */
    @RequestMapping(value = "/storeMonth/list.sb", method = RequestMethod.GET)
    public String StockStoreMonthView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "stock/status/storemonth/storeMonth";
    }
	
	/**
     * 매장월수불 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  조동훤
     * @since   2020. 03. 18.
     */
	@RequestMapping(value = "/storeMonth/viewList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result StockStoreMonthList(HttpServletRequest request, HttpServletResponse response, Model model, StockStoreMonthVO stockStoreMonthVO) {
		
		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
		
		List<DefaultMap<String>> list = stockStoreMonthService.stockStoreMonthList(stockStoreMonthVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, stockStoreMonthVO);
	}

	/**
     * 매장월수불 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  조동훤
     * @since   2020. 03. 18.
     */
	@RequestMapping(value = "/storeMonth/viewExcelList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result StockStoreMonthExcelList(HttpServletRequest request, HttpServletResponse response, Model model, StockStoreMonthVO stockStoreMonthVO) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		List<DefaultMap<String>> list = stockStoreMonthService.stockStoreMonthExcelList(stockStoreMonthVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, stockStoreMonthVO);
	}
}
