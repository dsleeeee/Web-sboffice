package kr.co.solbipos.mobile.stock.status.storeMonth.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.stock.status.storeMonth.service.MobileStoreMonthService;
import kr.co.solbipos.mobile.stock.status.storeMonth.service.MobileStoreMonthVO;
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
 * @Class Name : MobileStoreMonthController.java
 * @Description : (모바일)재고현황 > 매장월수불
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.23  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.07.23
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/mobile/stock/status/storeMonth")
public class MobileStoreMonthController {

    private final SessionService sessionService;
    private final MobileStoreMonthService mobileStoreMonthService;

    @Autowired
    public MobileStoreMonthController(SessionService sessionService, MobileStoreMonthService mobileStoreMonthService) {
        this.sessionService = sessionService;
        this.mobileStoreMonthService = mobileStoreMonthService;
    }

    /**
     * 매장월수불 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024. 07. 23.
     */
    @RequestMapping(value = "/mobileStoreMonth/list.sb", method = RequestMethod.GET)
    public String StockStoreMonthView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "mobile/stock/status/storeMonth/mobileStoreMonth";
    }

    /**
     * 매장월수불 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024. 07. 23.
     */
    @RequestMapping(value = "/mobileStoreMonth/viewList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result StockStoreMonthList(HttpServletRequest request, HttpServletResponse response, Model model, MobileStoreMonthVO mobileStoreMonthVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileStoreMonthService.stockStoreMonthList(mobileStoreMonthVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, mobileStoreMonthVO);
    }

    /**
     * 매장월수불 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2024. 07. 23.
     */
    @RequestMapping(value = "/mobileStoreMonth/viewExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result StockStoreMonthExcelList(HttpServletRequest request, HttpServletResponse response, Model model, MobileStoreMonthVO mobileStoreMonthVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileStoreMonthService.stockStoreMonthExcelList(mobileStoreMonthVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, mobileStoreMonthVO);
    }
}
