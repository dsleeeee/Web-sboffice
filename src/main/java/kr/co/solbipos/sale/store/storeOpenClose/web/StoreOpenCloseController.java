package kr.co.solbipos.sale.store.storeOpenClose.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.store.storeOpenClose.service.StoreOpenCloseService;
import kr.co.solbipos.sale.store.storeOpenClose.service.StoreOpenCloseVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : StoreOpenCloseController.java
 * @Description : 맘스터치 > 점포매출 > 매장 오픈/마감 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.11.11  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.11.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/store/storeOpenClose")
public class StoreOpenCloseController {

    private final SessionService sessionService;
    private final StoreOpenCloseService storeOpenCloseService;

    public StoreOpenCloseController(SessionService sessionService, StoreOpenCloseService storeOpenCloseService){
        this.sessionService = sessionService;
        this.storeOpenCloseService = storeOpenCloseService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  권지현
     * @since   2022.11.11
     */
    @RequestMapping(value = "/storeOpenClose/list.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/store/storeOpenClose/storeOpenClose";
    }

    /**
     * 주문채널별현황 - 일별 탭 조회
     * @param storeOpenCloseVO
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  권지현
     * @since   2022.11.11
     */
    @RequestMapping(value = "/storeOpenClose/getStoreOpenCloseDayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreOpenCloseDayList(StoreOpenCloseVO storeOpenCloseVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeOpenCloseService.getStoreOpenCloseDayList(storeOpenCloseVO, sessionInfoVO);

        return returnListJson(Status.OK, list, list);
    }



    /**
     * 주문채널별현황 - 일별 탭 상세 조회
     * @param storeOpenCloseVO
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  권지현
     * @since   2022.11.11
     */
    @RequestMapping(value = "/storeOpenClose/getStoreOpenCloseDayDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreOpenCloseDayDtlList(StoreOpenCloseVO storeOpenCloseVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeOpenCloseService.getStoreOpenCloseDayDtlList(storeOpenCloseVO, sessionInfoVO);

        return returnListJson(Status.OK, list, list);
    }

    /**
     * 주문채널별현황 - 월별 탭 조회
     * @param storeOpenCloseVO
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  권지현
     * @since   2022.11.11
     */
    @RequestMapping(value = "/storeOpenClose/getStoreOpenCloseMonthList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreOpenCloseMonthList(StoreOpenCloseVO storeOpenCloseVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeOpenCloseService.getStoreOpenCloseMonthList(storeOpenCloseVO, sessionInfoVO);

        return returnListJson(Status.OK, list, list);
    }

    /**
     * 주문채널별현황 - 월별 탭 상세 조회
     * @param storeOpenCloseVO
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  권지현
     * @since   2022.11.11
     */
    @RequestMapping(value = "/storeOpenClose/getStoreOpenCloseMonthDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreOpenCloseMonthDtlList(StoreOpenCloseVO storeOpenCloseVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = storeOpenCloseService.getStoreOpenCloseMonthDtlList(storeOpenCloseVO, sessionInfoVO);

        return returnListJson(Status.OK, list, list);
    }

}
