package kr.co.solbipos.stock.com.popup.web;

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
import kr.co.solbipos.stock.com.popup.service.StockComPopupService;
import kr.co.solbipos.stock.com.popup.service.StockComPopupVO;
import kr.co.solbipos.stock.curr.hqCurr.service.HqCurrVO;
import kr.co.solbipos.stock.manage.view.service.StockManageViewVO;
import kr.co.solbipos.stock.status.dailyIoStock.service.DailyIoStockVO;
import kr.co.solbipos.stock.status.periodiostock.service.PeriodIostockVO;

/**
 * @Class Name : ProdPayFgController.java
 * @Description : 매출공통팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.03.18  김진      최초생성
 *
 * @author 솔비포스
 * @since 2020.03.18
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/stock/com/popup")
public class StockComPopupController {
    private final SessionService sessionService;
    private final StockComPopupService stockComPopupService;

    @Autowired
    public StockComPopupController(SessionService sessionService, StockComPopupService stockComPopupService) {
        this.sessionService = sessionService;
        this.stockComPopupService = stockComPopupService;
    }

    /**
     * 일자별수불현황 - 일자별수불현황 팝업 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  정유경
     * @since   2020.03.18
     */
	@RequestMapping(value = "/dailyIoStockInfo/getDailyIoStockInfoList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result getDailyIoStockInfoList(HttpServletRequest request, HttpServletResponse response, DailyIoStockVO dailyIoStockVO, Model model) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		List<DefaultMap<String>> list = stockComPopupService.getDailyIoStockInfoList(dailyIoStockVO, sessionInfoVO);

		return ReturnUtil.returnListJson(Status.OK, list, dailyIoStockVO);
	}

	/**
     * 현재고현황 - 재고현황 팝업 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  정유경
     * @since   2020.03.19
     */
	@RequestMapping(value = "/cmmStockStatus/getCmmStockStatusList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result getCmmStockStatusList(HttpServletRequest request, HttpServletResponse response, HqCurrVO hqCurrVO, Model model) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		List<DefaultMap<String>> list = stockComPopupService.getCmmStockStatusList(hqCurrVO, sessionInfoVO);

		return ReturnUtil.returnListJson(Status.OK, list, hqCurrVO);
	}
	
	/**
     * 현재고현황 - 매장 재고현황 팝업 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  조동훤
     * @since   2020.04.07
     */
	@RequestMapping(value = "/cmmStockStatus/getCmmStoreStockStatusList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result getCmmStoreStockStatusList(HttpServletRequest request, HttpServletResponse response, HqCurrVO hqCurrVO, Model model) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		List<DefaultMap<String>> list = stockComPopupService.getCmmStoreStockStatusList(hqCurrVO, sessionInfoVO);

		return ReturnUtil.returnListJson(Status.OK, list, hqCurrVO);
	}
	
	/**
     * 기간수불현황 - 상품코드 선택 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param	periodIostockVO
     * @return  String
     * @author  박정은
     * @since   2020. 03. 20.
     */
	@RequestMapping(value = "/cmmQtyDtl/getCmmProdCodeDtlList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result getCmmProdCodeDtlList(HttpServletRequest request, HttpServletResponse response, Model model, PeriodIostockVO periodIostockVO) {
		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
		List<DefaultMap<String>> list = stockComPopupService.getCmmProdCodeDtlList(periodIostockVO, sessionInfoVO);
		return ReturnUtil.returnListJson(Status.OK, list, periodIostockVO);
	}
	
	/**
     * 매장일수불 - 각 수량별 팝업 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  박정은
     * @since   2020.03.24
     */
	@RequestMapping(value = "/cmmQtyDtl/getCmmQtyDtlList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result getCmmQtyDtlList(HttpServletRequest request, HttpServletResponse response, PeriodIostockVO periodIostockVO, Model model) {
		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
		List<DefaultMap<String>> list = stockComPopupService.getCmmQtyDtlList(periodIostockVO, sessionInfoVO);
		return ReturnUtil.returnListJson(Status.OK, list, periodIostockVO);
	}
	
	/**
     * 실사/조정/폐기 조회 - 실사/조정/폐기 상세리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  박정은
     * @since   2020.03.30
     */
	@RequestMapping(value = "/cmmQtyDtl/getCmmViewDtlList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result getCmmViewDtlList(HttpServletRequest request, HttpServletResponse response, StockManageViewVO stockManageViewVO) {
		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
		List<DefaultMap<String>> list = stockComPopupService.getCmmViewDtlList(stockManageViewVO, sessionInfoVO);
		
		return ReturnUtil.returnListJson(Status.OK, list, stockManageViewVO);
	}
}
