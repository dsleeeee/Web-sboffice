package kr.co.solbipos.stock.status.dailyIoStock.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.solbipos.application.session.user.enums.OrgnFg;
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
import kr.co.solbipos.stock.status.dailyIoStock.service.DailyIoStockService;
import kr.co.solbipos.stock.status.dailyIoStock.service.DailyIoStockVO;

/**
 * @Class Name : PeriodIostockController.java
 * @Description : 재고관리 > 재고현황 > 일수불현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.03.12      정유경      최초생성
 *
 * @author
 * @since 2020.03.12
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/stock/status/dailyIoStock")
public class DailyIoStockController {
	private final SessionService sessionService;
	private final DailyIoStockService dailyIoStockService;

	@Autowired
	public DailyIoStockController(SessionService sessionService, DailyIoStockService dailyIoStockService) {
		this.sessionService = sessionService;
		this.dailyIoStockService = dailyIoStockService;
	}

	/**
     * 일수불현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  정유경
     * @since   2020.03.17
     */
	@RequestMapping(value = "/dailyIoStock/list.sb", method = RequestMethod.GET)
	public String periodIostockView(HttpServletRequest request, HttpServletResponse response, Model model) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
		String url = "";
		if(sessionInfoVO.getOrgnFg().equals(OrgnFg.HQ)){
			url = "stock/status/dailyIoStock/hqDailyIoStock";
		} else if(sessionInfoVO.getOrgnFg().equals(OrgnFg.STORE)){
			url = "stock/status/dailyIoStock/storeDailyIoStock";
		}
		return url;
	}

	/**
     * 일수불현황 - 일수불현황 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  정유경
     * @since   2020.03.17
     */
	@RequestMapping(value = "/dailyIoStock/getDailyIoStockList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result getDailyIoStockList(HttpServletRequest request, HttpServletResponse response, DailyIoStockVO dailyIoStockVO, Model model) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		List<DefaultMap<String>> list = dailyIoStockService.getDailyIoStockList(dailyIoStockVO, sessionInfoVO);

		return ReturnUtil.returnListJson(Status.OK, list, dailyIoStockVO);
	}
	

	/**
     * 일수불현황 - 일수불현황 리스트(엑셀) 조회
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  박지선
     * @since   2020.04.21
     */
	@RequestMapping(value = "/dailyIoStock/getDailyIoStockExcelList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result getDailyIoStockExcelList(HttpServletRequest request, HttpServletResponse response, DailyIoStockVO dailyIoStockVO, Model model) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		List<DefaultMap<String>> list = dailyIoStockService.getDailyIoStockExcelList(dailyIoStockVO, sessionInfoVO);

		return ReturnUtil.returnListJson(Status.OK, list, dailyIoStockVO);
	}

}
