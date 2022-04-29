package kr.co.solbipos.mobile.sale.status.storeDayOfWeekSale.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.storeDayOfWeekSale.service.MobileStoreDayOfWeekSaleService;
import kr.co.solbipos.mobile.sale.status.storeDayOfWeekSale.service.MobileStoreDayOfWeekSaleVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : MobileStoreSaleController.java
 * @Description : (모바일) 매장매출 > 매장종합
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.04.27  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.04.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/mobile/sale/status/storeDayOfWeekSale")
public class MobileStoreDayOfWeekSaleController {

    private final SessionService sessionService;
    private final MobileStoreDayOfWeekSaleService mobileStoreDayOfWeekSaleService;

    public MobileStoreDayOfWeekSaleController(SessionService sessionService, MobileStoreDayOfWeekSaleService mobileStoreDayOfWeekSaleService) {
        this.sessionService = sessionService;
        this.mobileStoreDayOfWeekSaleService = mobileStoreDayOfWeekSaleService;
    }


    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/mobileStoreDayOfWeekSale/list.sb", method = RequestMethod.GET)
    public String mobileStoreDayOfWeekSaleView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "mobile/sale/status/storeDayOfWeekSale/mobileStoreDayOfWeekSale";
    }

    /**
     * 요일별 - 조회
     *
     * @param mobileStoreDayOfWeekSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.04.27
     */
    @RequestMapping(value = "/storeDayOfWeekSale/getMobileStoreDayOfWeekSaleList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileStoreDayOfWeekSaleList(MobileStoreDayOfWeekSaleVO mobileStoreDayOfWeekSaleVO, HttpServletRequest request,
                                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileStoreDayOfWeekSaleService.getMobileStoreDayOfWeekSaleList(mobileStoreDayOfWeekSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileStoreDayOfWeekSaleVO);
    }


}