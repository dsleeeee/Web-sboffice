package kr.co.solbipos.mobile.sale.status.storeMonthSale.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.storeMonthSale.service.MobileStoreMonthSaleService;
import kr.co.solbipos.mobile.sale.status.storeMonthSale.service.MobileStoreMonthSaleVO;
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
 * @Class Name : MobileStoreMonthSaleController.java
 * @Description : 모바일 매장매출 > 월별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.07  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2021.09.07
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/mobile/sale/status/storeMonthSale")
public class MobileStoreMonthSaleController {
    private final SessionService sessionService;
    private final MobileStoreMonthSaleService mobileStoreMonthSaleService;

    @Autowired
    public MobileStoreMonthSaleController(SessionService sessionService, MobileStoreMonthSaleService mobileStoreMonthSaleService){
        this.sessionService = sessionService;
        this.mobileStoreMonthSaleService = mobileStoreMonthSaleService;
    }

    /**
     * 모바일 매장매출 - 월별현황 페이지 이동
     * @param request
     * @param response
     * @param model
     * @return  String
     * @author  권지현
     * @since   2021.09.01
     */
    @RequestMapping(value = "/mobileStoreMonthSale/list.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "mobile/sale/status/storeMonthSale/mobileStoreMonthSale";
    }

    /**
     * 월별 매출현황 - 조회
     *
     * @param mobileStoreMonthSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2021. 09.03
     */
    @RequestMapping(value = "/mobileStoreMonthSale/getMobileStoreMonthSaleDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileMonthSaleDtlList(MobileStoreMonthSaleVO  mobileStoreMonthSaleVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileStoreMonthSaleService.getMobileStoreMonthSaleDtlList(mobileStoreMonthSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileStoreMonthSaleVO);
    }
}
