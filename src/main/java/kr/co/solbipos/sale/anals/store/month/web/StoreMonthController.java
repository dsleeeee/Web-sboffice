package kr.co.solbipos.sale.anals.store.month.web;

import kr.co.common.data.domain.CustomComboVO;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.store.month.service.StoreMonthService;
import kr.co.solbipos.sale.anals.store.month.service.StoreMonthVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class Name : StoreRankController.java
 * @Description : 매출관리 > 매출분석 > 매장별매출분석 > 매장월별순위
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.29  김진      최초생성
 *
 * @author 솔비포스 
 * @since 2020.01.29
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/anals/store")
public class StoreMonthController {
    private final SessionService sessionService;
    private final StoreMonthService storeMonthService;

    @Autowired
    public StoreMonthController(SessionService sessionService, StoreMonthService storeRankService) {
        this.sessionService = sessionService;
        this.storeMonthService = storeRankService;
    }


    /**
     * 매장월별순위 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김진
     * @since   2020. 01. 29.
     */
    @RequestMapping(value = "/month/view.sb", method = RequestMethod.GET)
    public String empMonthView(HttpServletRequest request, HttpServletResponse response, Model model) {

    	SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
   	
        return "sale/anals/store/month/storeMonth";
    }


    /**
     * 매장월별순위 - 매장월별순위 리스트 조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   storeMonthVO
     * @return  String
     * @author  김진
     * @since   2020. 01. 29.
     */
    @RequestMapping(value = "/month/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreMonthList(HttpServletRequest request, HttpServletResponse response, Model model, StoreMonthVO storeMonthVO) {

    	SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

      	List<DefaultMap<String>> list = storeMonthService.getStoreMonthList(storeMonthVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeMonthVO);
    }
    
    /**
     * 매장월별순위 - 월 리스트 조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   storeMonthVO
     * @return  String
     * @author  김진
     * @since   2020. 01. 29.
     */
    @RequestMapping(value = "/month/monthlist.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthColList(HttpServletRequest request, HttpServletResponse response, Model model, StoreMonthVO storeMonthVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
              
        List<DefaultMap<String>> list = storeMonthService.getMonthColList(storeMonthVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeMonthVO);
    }
}
