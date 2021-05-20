package kr.co.solbipos.mobile.sale.status.multiStoreSale.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.multiStoreSale.service.MobileMultiStoreSaleService;
import kr.co.solbipos.mobile.sale.status.multiStoreSale.service.MobileMultiStoreSaleVO;
import kr.co.solbipos.mobile.sale.status.prod.service.MobileProdSaleService;
import kr.co.solbipos.mobile.sale.status.prod.service.MobileProdSaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : MobileMultiStoreSaleController.java
 * @Description : (모바일) 매출현황 > 다중매장매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.20  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2021.05.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/mobile/sale/status/multiStoreSale")
public class MobileMultiStoreSaleController {

    private final SessionService sessionService;
    private final MobileMultiStoreSaleService mobileMultiStoreSaleService;
    private final MobileProdSaleService mobileProdSaleService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileMultiStoreSaleController(SessionService sessionService, MobileMultiStoreSaleService mobileMultiStoreSaleService, MobileProdSaleService mobileProdSaleService) {
        this.sessionService = sessionService;
        this.mobileMultiStoreSaleService = mobileMultiStoreSaleService;
        this.mobileProdSaleService = mobileProdSaleService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/mobileMultiStoreSale/list.sb", method = RequestMethod.GET)
    public String multiStoreSaleView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        MobileProdSaleVO mobileProdSaleVO = new MobileProdSaleVO();

        // 다중매장조회
        List<DefaultMap<String>> list = mobileProdSaleService.getMultiStoreList(mobileProdSaleVO, sessionInfoVO);

        model.addAttribute("multiStoreFg", list.size());

        return "mobile/sale/status/multiStoreSale/mobileMultiStoreSale";
    }

    /**
     * 다중매장매출현황 - 조회
     *
     * @param mobileMultiStoreSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 05. 20.
     */
    @RequestMapping(value = "/multiStoreSale/getMobileMultiStoreSaleList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileMultiStoreSaleList(MobileMultiStoreSaleVO mobileMultiStoreSaleVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileMultiStoreSaleService.getMobileMultiStoreSaleList(mobileMultiStoreSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileMultiStoreSaleVO);
    }

    /**
     * 다중매장매출현황 - 차트 조회
     *
     * @param mobileMultiStoreSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 05. 20.
     */
    @RequestMapping(value = "/multiStoreSale/getMobileMultiStoreSaleChartList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileMultiStoreSaleChartList(MobileMultiStoreSaleVO mobileMultiStoreSaleVO, HttpServletRequest request,
                                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileMultiStoreSaleService.getMobileMultiStoreSaleChartList(mobileMultiStoreSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileMultiStoreSaleVO);
    }

    /**
     * 일자-매장별 매출현황 - 조회
     *
     * @param mobileMultiStoreSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 05. 20.
     */
    @RequestMapping(value = "/multiStoreSale/getMobileMultiStoreSaleDayStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileMultiStoreSaleDayStoreList(MobileMultiStoreSaleVO mobileMultiStoreSaleVO, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileMultiStoreSaleService.getMobileMultiStoreSaleDayStoreList(mobileMultiStoreSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileMultiStoreSaleVO);
    }
}