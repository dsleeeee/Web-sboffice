package kr.co.solbipos.sale.anals.store.fg.web;

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
import kr.co.solbipos.sale.anals.store.fg.service.StoreFgService;
import kr.co.solbipos.sale.anals.store.fg.service.StoreFgVO;

/**
 * @Class Name : StoreFgController.java
 * @Description : 매출관리 > 매출분석 > 매장별매출분석 > 매장형태별
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.31  정유경      최초생성
 *
 * @author 솔비포스 
 * @since 2020.01.31
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/anals/store")
public class StoreFgController {
    private final SessionService sessionService;
    private final StoreFgService storeFgService;

    @Autowired
    public StoreFgController(SessionService sessionService, StoreFgService storeFgService) {
        this.sessionService = sessionService;
        this.storeFgService = storeFgService;
    }

    /**
     * 매장형태별 - 매장형태별 리스트 조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   storeFgVO
     * @return  String
     * @author  정유경
     * @since   2020.01.31
     */
    @RequestMapping(value = "/fg/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreFgList(HttpServletRequest request, HttpServletResponse response, Model model, StoreFgVO storeFgVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeFgService.getStoreFgList(storeFgVO, sessionInfoVO);
        
        return ReturnUtil.returnListJson(Status.OK, list, storeFgVO);
    }
    
    /**
     * 매장형태별 - 매장형태별 매장구분 콤보 리스트 조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   storeFgVO
     * @return  String
     * @author  정유경
     * @since   2020.01.31
     */
    @RequestMapping(value = "/fg/storeFgComboList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreFgComboList(HttpServletRequest request, HttpServletResponse response, Model model, StoreFgVO storeFgVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeFgService.getStoreFgComboList(storeFgVO, sessionInfoVO);
        
        return ReturnUtil.returnListJson(Status.OK, list, storeFgVO);
    }

}
