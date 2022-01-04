package kr.co.solbipos.mobile.sale.status.weekSale.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.weekSale.service.MobileWeekSaleService;
import kr.co.solbipos.mobile.sale.status.weekSale.service.MobileWeekSaleVO;
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
 * @Class Name : MobileWeekSaleController.java
 * @Description : (모바일) 매출현황 > 주간매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.14  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.05.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/mobile/sale/status/weekSale")
public class MobileWeekSaleController {

    private final SessionService sessionService;
    private final MobileWeekSaleService mobileWeekSaleService;
    private final MobileProdSaleService mobileProdSaleService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileWeekSaleController(SessionService sessionService, MobileWeekSaleService mobileWeekSaleService, MobileProdSaleService mobileProdSaleService) {
        this.sessionService = sessionService;
        this.mobileWeekSaleService = mobileWeekSaleService;
        this.mobileProdSaleService = mobileProdSaleService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/mobileWeekSale/list.sb", method = RequestMethod.GET)
    public String mobileWeekSaleView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        MobileProdSaleVO mobileProdSaleVO = new MobileProdSaleVO();

        // 다중매장조회
        List<DefaultMap<String>> list = mobileProdSaleService.getMultiStoreList(mobileProdSaleVO, sessionInfoVO);

        model.addAttribute("multiStoreFg", list.size());

        return "mobile/sale/status/weekSale/mobileWeekSale";
    }

    /**
     * 매출종합 - 조회
     *
     * @param mobileWeekSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 05. 14.
     */
    @RequestMapping(value = "/weekSale/getMobileWeekSaleTotalList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileWeekSaleTotalList(MobileWeekSaleVO mobileWeekSaleVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = mobileWeekSaleService.getMobileWeekSaleTotalList(mobileWeekSaleVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 결제수단 - 조회
     *
     * @param mobileWeekSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 05. 14.
     */
    @RequestMapping(value = "/weekSale/getMobileWeekSalePayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileWeekSalePayList(MobileWeekSaleVO mobileWeekSaleVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileWeekSaleService.getMobileWeekSalePayList(mobileWeekSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileWeekSaleVO);
    }

    /**
     * 할인내역 - 조회
     *
     * @param mobileWeekSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 05. 14.
     */
    @RequestMapping(value = "/weekSale/getMobileWeekSaleDcList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileWeekSaleDcList(MobileWeekSaleVO mobileWeekSaleVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileWeekSaleService.getMobileWeekSaleDcList(mobileWeekSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileWeekSaleVO);
    }

    /**
     * 내점현황 - 조회
     *
     * @param mobileWeekSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 05. 14.
     */
    @RequestMapping(value = "/weekSale/getMobileWeekSaleShopList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileWeekSaleShopList(MobileWeekSaleVO mobileWeekSaleVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = mobileWeekSaleService.getMobileWeekSaleShopList(mobileWeekSaleVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 내점/배달/포장 - 조회
     *
     * @param mobileWeekSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 05. 14.
     */
    @RequestMapping(value = "/weekSale/getMobileWeekSaleDlvrList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileWeekSaleDlvrList(MobileWeekSaleVO mobileWeekSaleVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileWeekSaleService.getMobileWeekSaleDlvrList(mobileWeekSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileWeekSaleVO);
    }

    /**
     * 내점/배달/포장 - 차트 조회
     *
     * @param mobileWeekSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 05. 14.
     */
    @RequestMapping(value = "/weekSale/getMobileWeekSaleDlvrChartList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileWeekSaleDlvrChartList(MobileWeekSaleVO mobileWeekSaleVO, HttpServletRequest request,
                                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileWeekSaleService.getMobileWeekSaleDlvrChartList(mobileWeekSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileWeekSaleVO);
    }

    /**
     * 일자별 매출현황 - 조회
     *
     * @param mobileWeekSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 05. 14.
     */
    @RequestMapping(value = "/weekSale/getMobileWeekSaleDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileWeekSaleDtlList(MobileWeekSaleVO mobileWeekSaleVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileWeekSaleService.getMobileWeekSaleDtlList(mobileWeekSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileWeekSaleVO);
    }
}