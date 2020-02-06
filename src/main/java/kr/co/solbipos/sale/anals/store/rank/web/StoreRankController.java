package kr.co.solbipos.sale.anals.store.rank.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.store.rank.service.StoreRankService;
import kr.co.solbipos.sale.anals.store.rank.service.StoreRankVO;
import kr.co.solbipos.sale.status.prod.payFg.service.ProdPayFgVO;

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
 * @Class Name : StoreRankController.java
 * @Description : 매출관리 > 매출분석 > 매장별매출분석 > 매장순위
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
public class StoreRankController {
    private final SessionService sessionService;
    private final StoreRankService storeRankService;

    @Autowired
    public StoreRankController(SessionService sessionService, StoreRankService storeRankService) {
        this.sessionService = sessionService;
        this.storeRankService = storeRankService;
    }


    /**
     * 매장순위 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김진
     * @since   2020. 01. 29.
     */
    @RequestMapping(value = "/rank/view.sb", method = RequestMethod.GET)
    public String empMonthView(HttpServletRequest request, HttpServletResponse response, Model model) {

    	SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
   	
        return "sale/anals/store/store";
    }


    /**
     * 매장순위 - 매장순위 리스트 조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   storeRankVO
     * @return  String
     * @author  김진
     * @since   2020. 01. 29.
     */
    @RequestMapping(value = "/rank/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreRankList(HttpServletRequest request, HttpServletResponse response, Model model, StoreRankVO storeRankVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeRankService.getStoreRankList(storeRankVO, sessionInfoVO);
        
        return ReturnUtil.returnListJson(Status.OK, list, storeRankVO);
    }
    
    /**
     * 매장순위 - 결제수단별 리스트 조회 
     * @param   request
     * @param   response
     * @param   model
     * @param   storeRankVO
     * @return  String
     * @author  김진
     * @since   2020. 01. 29.
     */
    @RequestMapping(value = "/payFg/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdPayFgList(HttpServletRequest request, HttpServletResponse response, Model model, StoreRankVO storeRankVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeRankService.getPayColList(storeRankVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeRankVO);
    }
}
