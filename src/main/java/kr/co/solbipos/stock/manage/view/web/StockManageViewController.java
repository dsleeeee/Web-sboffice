package kr.co.solbipos.stock.manage.view.web;

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
import kr.co.solbipos.stock.manage.view.service.StockManageViewService;
import kr.co.solbipos.stock.manage.view.service.StockManageViewVO;

/**
 * @Class Name : StockManageViewController.java
 * @Description : 재고관리 > 실사/조정/폐기 > 실사/조정/폐기 조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.03.16      박정은      최초생성
 *
 * @author 솔비포스
 * @since 2020.03.16
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/stock/manage/view")
public class StockManageViewController {
	private final SessionService sessionService;
	private final StockManageViewService stockManageViewService;

	@Autowired
	public StockManageViewController(SessionService sessionService, StockManageViewService stockManageViewService) {
		this.sessionService = sessionService;
		this.stockManageViewService = stockManageViewService;
	}

	/**
     * 실사/조정/폐기 조회 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  박정은
     * @since   2020. 03. 16.
     */
	@RequestMapping(value = "/view/list.sb", method = RequestMethod.GET)
	public String stockView(HttpServletRequest request, HttpServletResponse response, Model model) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		return "stock/manage/view/view";
	}

	/**
     * 실사/조정/폐기 조회 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   StockManageViewVO
     * @return  String
     * @author  박정은
     * @since   2020. 03. 17.
     */
	@RequestMapping(value = "/view/stockManageViewList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result getStockManageViewList(HttpServletRequest request, HttpServletResponse response, Model model, StockManageViewVO stockManageViewVO) {
		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
		List<DefaultMap<String>> list = stockManageViewService.getStockManageViewList(stockManageViewVO, sessionInfoVO);
		return ReturnUtil.returnListJson(Status.OK, list, stockManageViewVO);
	}
}
