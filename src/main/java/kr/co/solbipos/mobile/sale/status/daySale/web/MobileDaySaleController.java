package kr.co.solbipos.mobile.sale.status.daySale.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.daySale.service.MobileDaySaleService;
import kr.co.solbipos.mobile.sale.status.daySale.service.MobileDaySaleVO;
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
 * @Class Name : MobileDaySaleController.java
 * @Description : (모바일) 매출현황 > 일별매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.04  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2021.05.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/mobile/sale/status/daySale")
public class MobileDaySaleController {

    private final SessionService sessionService;
    private final MobileDaySaleService mobileDaySaleService;
    private final MobileProdSaleService mobileProdSaleService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileDaySaleController(SessionService sessionService, MobileDaySaleService mobileDaySaleService, MobileProdSaleService mobileProdSaleService) {
        this.sessionService = sessionService;
        this.mobileDaySaleService = mobileDaySaleService;
        this.mobileProdSaleService = mobileProdSaleService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/mobileDaySale/list.sb", method = RequestMethod.GET)
    public String mobileDaySaleView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        MobileProdSaleVO mobileProdSaleVO = new MobileProdSaleVO();

        // 다중매장조회
        List<DefaultMap<String>> list = mobileProdSaleService.getMultiStoreList(mobileProdSaleVO, sessionInfoVO);

        model.addAttribute("multiStoreFg", list.size());

        return "mobile/sale/status/daySale/mobileDaySale";
    }

    /**
     * 매출종합 - 조회
     *
     * @param mobileDaySaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 05. 04.
     */
    @RequestMapping(value = "/daySale/getMobileDaySaleTotalList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileDaySaleTotalList(MobileDaySaleVO mobileDaySaleVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = mobileDaySaleService.getMobileDaySaleTotalList(mobileDaySaleVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 결제수단 - 조회
     *
     * @param mobileDaySaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 05. 04.
     */
    @RequestMapping(value = "/daySale/getMobileDaySalePayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileDaySalePayList(MobileDaySaleVO mobileDaySaleVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileDaySaleService.getMobileDaySalePayList(mobileDaySaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileDaySaleVO);
    }

    /**
     * 할인내역 - 조회
     *
     * @param mobileDaySaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 05. 04.
     */
    @RequestMapping(value = "/daySale/getMobileDaySaleDcList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileDaySaleDcList(MobileDaySaleVO mobileDaySaleVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileDaySaleService.getMobileDaySaleDcList(mobileDaySaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileDaySaleVO);
    }

    /**
     * 내점현황 - 조회
     *
     * @param mobileDaySaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 05. 04.
     */
    @RequestMapping(value = "/daySale/getMobileDaySaleShopList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileDaySaleShopList(MobileDaySaleVO mobileDaySaleVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = mobileDaySaleService.getMobileDaySaleShopList(mobileDaySaleVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 내점/배달/포장 - 조회
     *
     * @param mobileDaySaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 05. 04.
     */
    @RequestMapping(value = "/daySale/getMobileDaySaleDlvrList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileDaySaleDlvrList(MobileDaySaleVO mobileDaySaleVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileDaySaleService.getMobileDaySaleDlvrList(mobileDaySaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileDaySaleVO);
    }

    /**
     * 내점/배달/포장 - 차트 조회
     *
     * @param mobileDaySaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 05. 04.
     */
    @RequestMapping(value = "/daySale/getMobileDaySaleDlvrChartList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileDaySaleDlvrChartList(MobileDaySaleVO mobileDaySaleVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileDaySaleService.getMobileDaySaleDlvrChartList(mobileDaySaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileDaySaleVO);
    }

    /**
     * 일자별 매출현황 - 조회
     *
     * @param mobileDaySaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 05. 04.
     */
    @RequestMapping(value = "/daySale/getMobileDaySaleDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileDaySaleDtlList(MobileDaySaleVO mobileDaySaleVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileDaySaleService.getMobileDaySaleDtlList(mobileDaySaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileDaySaleVO);
    }
}