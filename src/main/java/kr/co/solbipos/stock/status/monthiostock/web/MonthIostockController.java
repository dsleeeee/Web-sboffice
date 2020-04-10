package kr.co.solbipos.stock.status.monthiostock.web;

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
import kr.co.solbipos.stock.status.monthiostock.service.MonthIostockService;
import kr.co.solbipos.stock.status.monthiostock.service.MonthIostockVO;

/**
 * @Class Name : MonthIostockController.java
 * @Description : 재고관리 > 재고현황 > 일수불현황 
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.03.17      조동훤      최초생성
 *
 * @author 솔비포스 
 * @since 2020.03.18
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/stock/status/monthIoStock")
public class MonthIostockController {
	private final SessionService sessionService;
	private final MonthIostockService monthIostockService;
	
	@Autowired
	public MonthIostockController(SessionService sessionService, MonthIostockService monthIostockService) {
		this.sessionService = sessionService;
		this.monthIostockService = monthIostockService;
	}
	
	/**
     * 월수불현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  조동훤
     * @since   2020. 03. 18.
     */
    @RequestMapping(value = "/prod/list.sb", method = RequestMethod.GET)
    public String monthIostockView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "stock/status/monthiostock/prod";
    }
	
	/**
     * 월수불현황 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  조동훤
     * @since   2020. 03. 18.
     */
	@RequestMapping(value = "/prod/viewList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result monthIostockList(HttpServletRequest request, HttpServletResponse response, Model model, MonthIostockVO monthIostockVO) {
		
		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
		
		List<DefaultMap<String>> list = monthIostockService.monthIostockList(monthIostockVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, monthIostockVO);
	}
}
