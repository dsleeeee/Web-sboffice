package kr.co.solbipos.sale.store.storeMoms.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.store.storeMoms.service.StoreMomsService;
import kr.co.solbipos.sale.store.storeMoms.service.StoreMomsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @Class Name : StoreMomsController.java
 * @Description : 맘스터치 > 점포매출 > 점포별 매출 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.13  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/store/storeMoms")
public class StoreMomsController {

    private final SessionService sessionService;
    private final StoreMomsService storeMomsService;

    /**
     * Constructor Injection
     */
    @Autowired
    public StoreMomsController(SessionService sessionService, StoreMomsService storeMomsService) {
        this.sessionService = sessionService;
        this.storeMomsService = storeMomsService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/storeMoms/list.sb", method = RequestMethod.GET)
    public String storeMomsView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/store/storeMoms/storeMoms";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeMomsVO
     * @return  String
     * @author  권지현
     * @since   2022.10.13
     */
    @RequestMapping(value = "/storeMoms/getStoreMomsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreMomsList(HttpServletRequest request, HttpServletResponse response, Model model, StoreMomsVO storeMomsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = storeMomsService.getStoreMomsList(storeMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeMomsVO);
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeMomsVO
     * @return  String
     * @author  권지현
     * @since   2022.10.13
     */
    @RequestMapping(value = "/storeMoms/getStoreMomsExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreMomsExcelList(HttpServletRequest request, HttpServletResponse response, Model model, StoreMomsVO storeMomsVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = storeMomsService.getStoreMomsExcelList(storeMomsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeMomsVO);
    }

}