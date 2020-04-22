package kr.co.solbipos.stock.status.storeperiod.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import kr.co.solbipos.stock.status.storeperiod.service.StorePeriodService;
import kr.co.solbipos.stock.status.storeperiod.service.StorePeriodVO;

/**
 * @Class Name : StorePeriodController.java
 * @Description : 재고관리 > 매장재고현황 > 매장기간수불 
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.02.25      박정은      최초생성
 *
 * @author 솔비포스 
 * @since 2020.03.13
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/stock/status/storePeriod")
public class StorePeriodController {
	private final SessionService sessionService;
	private final StorePeriodService storePeriodService;
	
	public StorePeriodController(SessionService sessionService, StorePeriodService storePeriodService) {
		this.sessionService = sessionService;
		this.storePeriodService = storePeriodService;
	}
	
    /**
     * 매장기간수불 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  박정은
     * @since   2020. 03. 13.
     */
	@RequestMapping(value = "/storePeriod/list.sb", method = RequestMethod.GET)
	public String storePeriodView(HttpServletRequest request, HttpServletResponse response, Model model) {
		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
		return "stock/status/storeperiod/storePeriod";
	}
	
	/**
     * 매장기간수불 - 리스트 조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   storePeriodVO
     * @return  String
     * @author  박정은
     * @since   2020. 03. 13.
     */
	@RequestMapping(value = "/storePeriod/storeperiodList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result getStoreperiodList(HttpServletRequest request, HttpServletResponse response, Model model, StorePeriodVO storePeriodVO) {
		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
		List<DefaultMap<String>> list = storePeriodService.getStorePeriodList(storePeriodVO, sessionInfoVO);
		return ReturnUtil.returnListJson(Status.OK, list, storePeriodVO);
	}
	
	/**
     * 매장기간수불 - 상품코드 선택 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param	storePeriodVO
     * @return  String
     * @author  박정은
     * @since   2020. 03. 23.
     */
	@RequestMapping(value = "/storePeriod/storeperiodDtlList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result getStorePeriodDtlList(HttpServletRequest request, HttpServletResponse response, Model model, StorePeriodVO storePeriodVO) {
		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
		List<DefaultMap<String>> list = storePeriodService.getStorePeriodDtlList(storePeriodVO, sessionInfoVO);
		return ReturnUtil.returnListJson(Status.OK, list, storePeriodVO);
	}
	
	/**
     * 매장기간수불 - 수량 선택 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param	storePeriodVO
     * @return  String
     * @author  박정은
     * @since   2020. 03. 23.
     */
	@RequestMapping(value = "/storePeriod/storeperiodQtyDtlList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result getStorePeriodQtyDtlList(HttpServletRequest request, HttpServletResponse response, Model model, StorePeriodVO storePeriodVO) {
		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
		List<DefaultMap<String>> list = storePeriodService.getStorePeriodQtyDtlList(storePeriodVO, sessionInfoVO);
		return ReturnUtil.returnListJson(Status.OK, list, storePeriodVO);
	}
	
	/**
     * 매장기간수불 - 엑셀 다운로드 전체 리스트 조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   storePeriodVO
     * @return  String
     * @author  박정은
     * @since   2020. 04. 20.
     */
	@RequestMapping(value = "/storePeriod/storeperiodExcelList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result getStoreperiodExcelList(HttpServletRequest request, HttpServletResponse response, Model model, StorePeriodVO storePeriodVO) {
		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
		List<DefaultMap<String>> list = storePeriodService.getStoreperiodExcelList(storePeriodVO, sessionInfoVO);
		return ReturnUtil.returnListJson(Status.OK, list, storePeriodVO);
	}
}
