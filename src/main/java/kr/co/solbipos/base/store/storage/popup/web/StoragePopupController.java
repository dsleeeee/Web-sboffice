package kr.co.solbipos.base.store.storage.popup.web;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.storage.popup.service.StoragePopupService;
import kr.co.solbipos.base.store.storage.popup.service.StoragePopupVO;

/**
 * @Class Name : StoragePopupController.java
 * @Description : 창고관리 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.03.25  조동훤      최초생성
 *
 * @author 솔비포스
 * @since 2020.03.25
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/base/store/storage/popup")
public class StoragePopupController {
    private final SessionService sessionService;
    private final StoragePopupService storagePopupService;

    @Autowired
    public StoragePopupController(SessionService sessionService, StoragePopupService storagePopupService) {
        this.sessionService = sessionService;
        this.storagePopupService = storagePopupService;
    }
    
	/**
     * 창고관리 - 창고정보 저장
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  조동훤
     * @since   2020.03.25
     */
	@RequestMapping(value = "/storage/reg.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result RegStorageInfo(@RequestBody StoragePopupVO storagePopupVO, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
		
		int result = storagePopupService.RegStorageInfo(storagePopupVO, sessionInfoVO);

        return returnJson(Status.OK, result);
	}
	
	/**
     * 창고관리 - 창고정보 수정
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  박지선
     * @since   2020.03.25
     */
	@RequestMapping(value = "/storage/modInfo.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result ModStorageInfo(@RequestBody StoragePopupVO storagePopupVO, HttpServletRequest request, HttpServletResponse response, Model model) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		int result = storagePopupService.ModStorageInfo(storagePopupVO, sessionInfoVO);

        return returnJson(Status.OK, result);
		
	}
	
	
	/**
     * 창고관리 - 창고재고 확인
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  박지선
     * @since   2020.03.27
     */
	@RequestMapping(value = "/storage/stockCnt.sb", method = RequestMethod.POST)
	@ResponseBody
	public Result StockCnt(@RequestBody StoragePopupVO storagePopupVO, HttpServletRequest request, HttpServletResponse response, Model model) {

		SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

		int result = storagePopupService.StockCnt(storagePopupVO, sessionInfoVO);
		
        return returnJson(Status.OK, result);
		
	}
}
