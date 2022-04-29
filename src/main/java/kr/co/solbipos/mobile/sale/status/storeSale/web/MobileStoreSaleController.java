package kr.co.solbipos.mobile.sale.status.storeSale.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.storeSale.service.MobileStoreSaleService;
import kr.co.solbipos.mobile.sale.status.storeSale.service.MobileStoreSaleVO;
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
@RequestMapping("/mobile/sale/status/storeSale")
public class MobileStoreSaleController {

    private final SessionService sessionService;
    private final MobileStoreSaleService mobileStoreSaleService;

    public MobileStoreSaleController(SessionService sessionService, MobileStoreSaleService mobileStoreSaleService) {
        this.sessionService = sessionService;
        this.mobileStoreSaleService = mobileStoreSaleService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/mobileStoreSale/list.sb", method = RequestMethod.GET)
    public String mobileStoreSaleView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "mobile/sale/status/storeSale/mobileStoreSale";
    }

    /**
     * 매장종합 - 조회
     *
     * @param mobileStoreSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.04.27
     */
    @RequestMapping(value = "/storeSale/getMobileStoreSaleList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileStoreSaleList(MobileStoreSaleVO mobileStoreSaleVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileStoreSaleService.getMobileStoreSaleList(mobileStoreSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileStoreSaleVO);
    }

    /**
     * 상세 - 조회
     *
     * @param mobileStoreSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.04.27
     */
    @RequestMapping(value = "/storeSale/getMobileStoreSaleDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileStoreSaleDtlList(MobileStoreSaleVO mobileStoreSaleVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileStoreSaleService.getMobileStoreSaleDtlList(mobileStoreSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileStoreSaleVO);
    }

}