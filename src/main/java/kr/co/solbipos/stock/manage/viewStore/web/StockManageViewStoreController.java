package kr.co.solbipos.stock.manage.viewStore.web;

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
import kr.co.solbipos.stock.manage.viewStore.service.StockManageViewStoreService;
import kr.co.solbipos.stock.manage.viewStore.service.StockManageViewStoreVO;

/**
 * @Class Name : StockManageViewController.java
 * @Description : 재고관리 > 실사/조정/폐기 > 실사/조정/폐기 조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.03.16      정유경      최초생성
 *
 * @author 솔비포스
 * @since 2020.03.16
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/stock/manage/viewStore")
public class StockManageViewStoreController {
	private final SessionService sessionService;
	private final StockManageViewStoreService stockManageViewStoreService;

	@Autowired
	public StockManageViewStoreController(SessionService sessionService, StockManageViewStoreService stockManageViewStoreService) {
		this.sessionService = sessionService;
		this.stockManageViewStoreService = stockManageViewStoreService;
	}

	/**
     * 실사/조정/폐기 조회 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  정유경
     * @since   2020. 03. 18.
     */
	@RequestMapping(value = "/viewStore/list.sb", method = RequestMethod.GET)
	public String stockView(HttpServletRequest request, HttpServletResponse response, Model model) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		return "stock/manage/viewStore/viewStore";
	}

	@RequestMapping(value = "/viewStore/getStockManageViewStoreList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result getStockManageViewStoreList(HttpServletRequest request, HttpServletResponse response, Model model, StockManageViewStoreVO stockManageViewStoreVO) {
		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
		List<DefaultMap<String>> list = stockManageViewStoreService.getStockManageViewStoreList(stockManageViewStoreVO, sessionInfoVO);
		return ReturnUtil.returnListJson(Status.OK, list, stockManageViewStoreVO);
	}
}
