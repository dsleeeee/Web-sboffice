package kr.co.solbipos.stock.curr.storageHqCurr.web;

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
import kr.co.solbipos.stock.curr.storageHqCurr.service.StorageHqCurrService;
import kr.co.solbipos.stock.curr.storageHqCurr.service.StorageHqCurrVO;

/**
 * @Class Name : StorageHqCurrController.java
 * @Description : 재고관리 > 재고현황 > 창고별현재고현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.04.08  조동훤      최초생성
 *
 * @author 
 * @since 2020.04.08
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/stock/curr/storageHqCurr")
public class StorageHqCurrController {
    private final SessionService sessionService;
    private final StorageHqCurrService storageHqCurrService;

    @Autowired
    public StorageHqCurrController(SessionService sessionService, StorageHqCurrService storageHqCurrService) {
        this.sessionService = sessionService;
        this.storageHqCurrService = storageHqCurrService;
    }

    /**
     * 창고별현재고현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  조동훤
     * @since   2020. 04. 08.
     */
    @RequestMapping(value = "/storageHqCurr/view.sb", method = RequestMethod.GET)
    public String storageHqCurrView(HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "stock/curr/storageHqCurr/storageHqCurr";
    }

    /**
     * 창고별현재고현황 - 창고별현재고현황 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storageHqCurrVO
     * @return  String
     * @author  조동훤
     * @since   2020. 04. 08.
     */
    @RequestMapping(value = "/storageHqCurr/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStorageHqCurrList(HttpServletRequest request, HttpServletResponse response,
        Model model, StorageHqCurrVO storageHqCurrVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        storageHqCurrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        List<DefaultMap<String>> list = storageHqCurrService.getStorageHqCurrList(storageHqCurrVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storageHqCurrVO);
    }
    
    /**
     * 창고별현재고현황 - 창고별현재고현황 창고 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storageHqCurrVO
     * @return  String
     * @author  조동훤
     * @since   2020. 04. 09.
     */
    @RequestMapping(value = "/storageHqCurr/storageList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStorageList(HttpServletRequest request, HttpServletResponse response,
        Model model, StorageHqCurrVO storageHqCurrVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<String>> list = storageHqCurrService.getStorageList(storageHqCurrVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storageHqCurrVO);
    }
}
