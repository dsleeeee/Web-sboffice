package kr.co.solbipos.stock.status.dayiostock.web;

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
import kr.co.solbipos.stock.status.dayiostock.service.DayIostockService;
import kr.co.solbipos.stock.status.dayiostock.service.DayIostockVO;

/**
 * @Class Name : DayIostockController.java
 * @Description : 재고관리 > 재고현황 > 일수불현황 
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.03.17      조동훤      최초생성
 *
 * @author 솔비포스 
 * @since 2020.03.17
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/stock/status/dayIoStock")
public class DayIostockController {
	private final SessionService sessionService;
	private final DayIostockService dayIostockService;
	
	@Autowired
	public DayIostockController(SessionService sessionService, DayIostockService dayIostockService) {
		this.sessionService = sessionService;
		this.dayIostockService = dayIostockService;
	}
	
	/**
     * 일수불현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  조동훤
     * @since   2020. 03. 17.
     */
    @RequestMapping(value = "/prod/list.sb", method = RequestMethod.GET)
    public String dayIostockView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "stock/status/dayiostock/prod";
    }
	
	/**
     * 일수불현황 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  조동훤
     * @since   2020. 03. 17.
     */
	@RequestMapping(value = "/prod/viewList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result dayIostockList(HttpServletRequest request, HttpServletResponse response, Model model, DayIostockVO dayIostockVO) {
		
		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
		
		List<DefaultMap<String>> list = dayIostockService.dayIostockList(dayIostockVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, dayIostockVO);
	}
}
