package kr.co.solbipos.stock.status.periodiostock.web;

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
import kr.co.solbipos.stock.status.periodiostock.service.PeriodIostockService;
import kr.co.solbipos.stock.status.periodiostock.service.PeriodIostockVO;

/**
 * @Class Name : PeriodIostockController.java
 * @Description : 재고관리 > 재고현황 > 기간수불현황 
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.03.12      박정은      최초생성
 *
 * @author 솔비포스 
 * @since 2020.03.12
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/stock/status/periodIoStock")
public class PeriodIostockController {
	private final SessionService sessionService;
	private final PeriodIostockService periodIostockService;
	
	@Autowired
	public PeriodIostockController(SessionService sessionService, PeriodIostockService periodIostockService) {
		this.sessionService = sessionService;
		this.periodIostockService = periodIostockService;
	}
	
	/**
     * 기간수불현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  박정은
     * @since   2020. 03. 12.
     */
	@RequestMapping(value = "/prod/list.sb", method = RequestMethod.GET)
	public String periodIostockView(HttpServletRequest request, HttpServletResponse response, Model model) {
		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
		return "stock/status/periodiostock/prod";
	}
	
	/**
     * 기간수불현황 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param	periodIostockVO
     * @return  String
     * @author  박정은
     * @since   2020. 03. 12.
     */
	@RequestMapping(value = "/prod/periodiostockList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result getPeriodIostockList(HttpServletRequest request, HttpServletResponse response, Model model, PeriodIostockVO periodIostockVO) {
		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
		List<DefaultMap<String>> list = periodIostockService.getPeriodIostockList(periodIostockVO, sessionInfoVO);
		return ReturnUtil.returnListJson(Status.OK, list, periodIostockVO);
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
	@RequestMapping(value = "/prod/periodiostockProdDtlList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result getPeriodiostockProdDtlList(HttpServletRequest request, HttpServletResponse response, Model model, PeriodIostockVO periodIostockVO) {
		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
		List<DefaultMap<String>> list = periodIostockService.getPeriodiostockProdDtlList(periodIostockVO, sessionInfoVO);
		return ReturnUtil.returnListJson(Status.OK, list, periodIostockVO);
	}
}
