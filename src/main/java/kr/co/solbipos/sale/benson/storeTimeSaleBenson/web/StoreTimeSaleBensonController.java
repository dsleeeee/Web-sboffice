package kr.co.solbipos.sale.benson.storeTimeSaleBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.benson.storeTimeSaleBenson.service.StoreTimeSaleBensonService;
import kr.co.solbipos.sale.benson.storeTimeSaleBenson.service.StoreTimeSaleBensonVO;
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
 * @Class Name : StoreTimeSaleBensonController.java
 * @Description : 벤슨 > 매출조회 > 지정가맹점_시간대별
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/benson/storeTimeSaleBenson")
public class StoreTimeSaleBensonController {

    private final SessionService sessionService;
    private final StoreTimeSaleBensonService storeTimeSaleBensonService;

    /**
     * Constructor Injection
     */
    @Autowired
    public StoreTimeSaleBensonController(SessionService sessionService, StoreTimeSaleBensonService storeTimeSaleBensonService) {
        this.sessionService = sessionService;
        this.storeTimeSaleBensonService = storeTimeSaleBensonService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/storeTimeSaleBenson/list.sb", method = RequestMethod.GET)
    public String storeTimeSaleBensonView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "sale/benson/storeTimeSaleBenson/storeTimeSaleBenson";
    }

    /**
     * 지정가맹점_시간대별 - 조회
     *
     * @param storeTimeSaleBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2026. 09. 10.
     */
    @RequestMapping(value = "/storeTimeSaleBenson/getStoreTimeSaleBensonList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreTimeSaleBensonList(StoreTimeSaleBensonVO storeTimeSaleBensonVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeTimeSaleBensonService.getStoreTimeSaleBensonList(storeTimeSaleBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeTimeSaleBensonVO);
    }
}
