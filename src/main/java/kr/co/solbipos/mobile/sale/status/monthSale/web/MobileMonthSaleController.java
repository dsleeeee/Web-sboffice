package kr.co.solbipos.mobile.sale.status.monthSale.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.monthSale.service.MobileMonthSaleService;
import kr.co.solbipos.mobile.sale.status.monthSale.service.MobileMonthSaleVO;
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
 * @Class Name : MobileMonthSaleController.java
 * @Description : (모바일) 매출현황 > 월별매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2021.05.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/mobile/sale/status/monthSale")
public class MobileMonthSaleController {

    private final SessionService sessionService;
    private final MobileMonthSaleService mobileMonthSaleService;
    private final MobileProdSaleService mobileProdSaleService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileMonthSaleController(SessionService sessionService, MobileMonthSaleService mobileMonthSaleService, MobileProdSaleService mobileProdSaleService) {
        this.sessionService = sessionService;
        this.mobileMonthSaleService = mobileMonthSaleService;
        this.mobileProdSaleService = mobileProdSaleService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/mobileMonthSale/list.sb", method = RequestMethod.GET)
    public String mobileMonthSaleView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        MobileProdSaleVO mobileProdSaleVO = new MobileProdSaleVO();

        // 다중매장조회
        List<DefaultMap<String>> list = mobileProdSaleService.getMultiStoreList(mobileProdSaleVO, sessionInfoVO);

        model.addAttribute("multiStoreFg", list.size());

        return "mobile/sale/status/monthSale/mobileMonthSale";
    }

    /**
     * 매출종합 - 조회
     *
     * @param mobileMonthSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 05. 10.
     */
    @RequestMapping(value = "/monthSale/getMobileMonthSaleTotalList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileMonthSaleTotalList(MobileMonthSaleVO mobileMonthSaleVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = mobileMonthSaleService.getMobileMonthSaleTotalList(mobileMonthSaleVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 결제수단 - 조회
     *
     * @param mobileMonthSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 05. 10.
     */
    @RequestMapping(value = "/monthSale/getMobileMonthSalePayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileMonthSalePayList(MobileMonthSaleVO mobileMonthSaleVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileMonthSaleService.getMobileMonthSalePayList(mobileMonthSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileMonthSaleVO);
    }

    /**
     * 할인내역 - 조회
     *
     * @param mobileMonthSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 05. 10.
     */
    @RequestMapping(value = "/monthSale/getMobileMonthSaleDcList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileMonthSaleDcList(MobileMonthSaleVO mobileMonthSaleVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileMonthSaleService.getMobileMonthSaleDcList(mobileMonthSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileMonthSaleVO);
    }

    /**
     * 내점현황 - 조회
     *
     * @param mobileMonthSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 05. 10.
     */
    @RequestMapping(value = "/monthSale/getMobileMonthSaleShopList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileMonthSaleShopList(MobileMonthSaleVO mobileMonthSaleVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = mobileMonthSaleService.getMobileMonthSaleShopList(mobileMonthSaleVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 내점/배달/포장 - 조회
     *
     * @param mobileMonthSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 05. 10.
     */
    @RequestMapping(value = "/monthSale/getMobileMonthSaleDlvrList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileMonthSaleDlvrList(MobileMonthSaleVO mobileMonthSaleVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileMonthSaleService.getMobileMonthSaleDlvrList(mobileMonthSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileMonthSaleVO);
    }

    /**
     * 내점/배달/포장 - 차트 조회
     *
     * @param mobileMonthSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 05. 10.
     */
    @RequestMapping(value = "/monthSale/getMobileMonthSaleDlvrChartList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileMonthSaleDlvrChartList(MobileMonthSaleVO mobileMonthSaleVO, HttpServletRequest request,
                                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileMonthSaleService.getMobileMonthSaleDlvrChartList(mobileMonthSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileMonthSaleVO);
    }

    /**
     * 월자별 매출현황 - 조회
     *
     * @param mobileMonthSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 05. 10.
     */
    @RequestMapping(value = "/monthSale/getMobileMonthSaleDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileMonthSaleDtlList(MobileMonthSaleVO mobileMonthSaleVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileMonthSaleService.getMobileMonthSaleDtlList(mobileMonthSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileMonthSaleVO);
    }
}
