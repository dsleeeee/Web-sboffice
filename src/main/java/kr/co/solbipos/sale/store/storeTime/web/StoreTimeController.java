package kr.co.solbipos.sale.store.storeTime.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.store.storeTime.service.StoreTimeService;
import kr.co.solbipos.sale.store.storeTime.service.StoreTimeVO;
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
 * @Class Name : StoreTimeController.java
 * @Description : 맘스터치 > 점포매출 > 점포-시간대별 매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.14  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/store/storeTime")
public class StoreTimeController {

    private final SessionService sessionService;
    private final StoreTimeService storeTimeService;

    /**
     * Constructor Injection
     */
    @Autowired
    public StoreTimeController(SessionService sessionService, StoreTimeService storeTimeService) {
        this.sessionService = sessionService;
        this.storeTimeService = storeTimeService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/storeTime/list.sb", method = RequestMethod.GET)
    public String storeTimeView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/store/storeTime/storeTime";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeTimeVO
     * @return  String
     * @author  권지현
     * @since   2022.10.14
     */
    @RequestMapping(value = "/storeTime/getStoreTimeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreTimeList(HttpServletRequest request, HttpServletResponse response, Model model, StoreTimeVO storeTimeVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = storeTimeService.getStoreTimeList(storeTimeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeTimeVO);
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeTimeVO
     * @return  String
     * @author  권지현
     * @since   2022.10.14
     */
    @RequestMapping(value = "/storeTime/getStoreTimeExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreTimeExcelList(HttpServletRequest request, HttpServletResponse response, Model model, StoreTimeVO storeTimeVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = storeTimeService.getStoreTimeExcelList(storeTimeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeTimeVO);
    }

}