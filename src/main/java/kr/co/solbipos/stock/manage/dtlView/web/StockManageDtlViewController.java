package kr.co.solbipos.stock.manage.dtlView.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.stock.manage.dtlView.service.StockManageDtlViewService;
import kr.co.solbipos.stock.manage.dtlView.service.StockManageDtlViewVO;
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
 * @Class Name : StockManageDtlViewController.java
 * @Description : 재고관리 > 실사/조정/폐기 > 실사/조정/폐기 상세
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.07.12      권지현      최초생성
 *
 * @author 솔비포스
 * @since 2022.07.12
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/stock/manage/dtlView")
public class StockManageDtlViewController {
	private final SessionService sessionService;
	private final StockManageDtlViewService stockManageDtlViewService;

	@Autowired
	public StockManageDtlViewController(SessionService sessionService, StockManageDtlViewService stockManageDtlViewService) {
		this.sessionService = sessionService;
		this.stockManageDtlViewService = stockManageDtlViewService;
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
	@RequestMapping(value = "/dtlView/list.sb", method = RequestMethod.GET)
	public String stockView(HttpServletRequest request, HttpServletResponse response, Model model) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		return "stock/manage/dtlView/dtlView";
	}

	/**
     * 실사/조정/폐기 조회 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   stockManageDtlViewVO
     * @return  String
     * @author  박정은
     * @since   2020. 03. 17.
     */
	@RequestMapping(value = "/dtlView/stockManageDtlViewList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result getStockManageDtlViewList(HttpServletRequest request, HttpServletResponse response, Model model, StockManageDtlViewVO stockManageDtlViewVO) {
		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
		List<DefaultMap<String>> list = stockManageDtlViewService.getStockManageDtlViewList(stockManageDtlViewVO, sessionInfoVO);
		return ReturnUtil.returnListJson(Status.OK, list, stockManageDtlViewVO);
	}


	/**
     * 실사/조정/폐기 조회 - 사유 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   stockManageDtlViewVO
     * @return  String
     * @author  권지현
     * @since   2022.07.12
     */
	@RequestMapping(value = "/dtlView/getReason.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result getReason(HttpServletRequest request, HttpServletResponse response, Model model, StockManageDtlViewVO stockManageDtlViewVO) {
		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
		List<DefaultMap<String>> list = stockManageDtlViewService.getReason(stockManageDtlViewVO, sessionInfoVO);
		return ReturnUtil.returnListJson(Status.OK, list, stockManageDtlViewVO);
	}
}
