package kr.co.solbipos.sale.store.storeAvg.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.store.storeAvg.service.StoreAvgService;
import kr.co.solbipos.sale.store.storeAvg.service.StoreAvgVO;
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
 * @Class Name : StoreAvgController.java
 * @Description : 맘스터치 > 점포매출 > 점포별 매출 평균 현황
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
@RequestMapping("/sale/store/storeAvg")
public class StoreAvgController {

    private final SessionService sessionService;
    private final StoreAvgService storeAvgService;

    /**
     * Constructor Injection
     */
    @Autowired
    public StoreAvgController(SessionService sessionService, StoreAvgService storeAvgService) {
        this.sessionService = sessionService;
        this.storeAvgService = storeAvgService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/storeAvg/list.sb", method = RequestMethod.GET)
    public String storeAvgView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/store/storeAvg/storeAvg";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeAvgVO
     * @return  String
     * @author  권지현
     * @since   2022.10.14
     */
    @RequestMapping(value = "/storeAvg/getStoreAvgList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreAvgList(HttpServletRequest request, HttpServletResponse response, Model model, StoreAvgVO storeAvgVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = storeAvgService.getStoreAvgList(storeAvgVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeAvgVO);
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeAvgVO
     * @return  String
     * @author  권지현
     * @since   2022.10.14
     */
    @RequestMapping(value = "/storeAvg/getStoreAvgExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreAvgExcelList(HttpServletRequest request, HttpServletResponse response, Model model, StoreAvgVO storeAvgVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = storeAvgService.getStoreAvgExcelList(storeAvgVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeAvgVO);
    }

}