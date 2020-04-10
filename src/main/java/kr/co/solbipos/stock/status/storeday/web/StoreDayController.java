package kr.co.solbipos.stock.status.storeday.web;

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
import kr.co.solbipos.stock.status.storeday.service.StoreDayService;
import kr.co.solbipos.stock.status.storeday.service.StoreDayVO;

/**
 * @Class Name : StoreDayController.java
 * @Description : 재고관리 > 매장재고현황 > 매장일수불 
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
@RequestMapping("/stock/status/storeDay")
public class StoreDayController {
	private final SessionService sessionService;
	private final StoreDayService storeDayService;
	
	@Autowired
	public StoreDayController(SessionService sessionService, StoreDayService storeDayService) {
		this.sessionService = sessionService;
		this.storeDayService = storeDayService;
	}
	
	/**
     * 매장일수불 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  조동훤
     * @since   2020. 03. 18.
     */
    @RequestMapping(value = "/storeDay/list.sb", method = RequestMethod.GET)
    public String storeDayView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "stock/status/storeday/storeDay";
    }
	
	/**
     * 매장일수불 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  조동훤
     * @since   2020. 03. 18.
     */
	@RequestMapping(value = "/storeDay/viewList.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result storeDayList(HttpServletRequest request, HttpServletResponse response, Model model, StoreDayVO storeDayVO) {
		
		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
		
		List<DefaultMap<String>> list = storeDayService.storeDayList(storeDayVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, storeDayVO);
	}
}
