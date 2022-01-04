package kr.co.solbipos.mobile.sale.status.todaySale.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.todaySale.service.MobileTodaySaleService;
import kr.co.solbipos.mobile.sale.status.todaySale.service.MobileTodaySaleVO;
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
 * @Class Name : MobileTodaySaleController.java
 * @Description : (모바일) 매출현황 > 당일매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.04.02  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.04.02
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/mobile/sale/status/todaySale")
public class MobileTodaySaleController {

    private final SessionService sessionService;
    private final MobileTodaySaleService mobileTodaySaleService;
    private final MobileProdSaleService mobileProdSaleService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileTodaySaleController(SessionService sessionService, MobileTodaySaleService mobileTodaySaleService, MobileProdSaleService mobileProdSaleService) {
        this.sessionService = sessionService;
        this.mobileTodaySaleService = mobileTodaySaleService;
        this.mobileProdSaleService = mobileProdSaleService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/mobileTodaySale/list.sb", method = RequestMethod.GET)
    public String mobileTodaySaleView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        MobileProdSaleVO mobileProdSaleVO = new MobileProdSaleVO();

        // 다중매장조회
        List<DefaultMap<String>> list = mobileProdSaleService.getMultiStoreList(mobileProdSaleVO, sessionInfoVO);

        model.addAttribute("multiStoreFg", list.size());

        return "mobile/sale/status/todaySale/mobileTodaySale";
    }

    /**
     * 당일매출종합 - 조회
     *
     * @param mobileTodaySaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 04. 02.
     */
    @RequestMapping(value = "/todaySale/getMobileTodaySaleTotalList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileTodaySaleTotalList(MobileTodaySaleVO mobileTodaySaleVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = mobileTodaySaleService.getMobileTodaySaleTotalList(mobileTodaySaleVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 결제수단 - 조회
     *
     * @param mobileTodaySaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 04. 02.
     */
    @RequestMapping(value = "/todaySale/getMobileTodaySalePayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileTodaySalePayList(MobileTodaySaleVO mobileTodaySaleVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileTodaySaleService.getMobileTodaySalePayList(mobileTodaySaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileTodaySaleVO);
    }

    /**
     * 할인내역 - 조회
     *
     * @param mobileTodaySaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 04. 02.
     */
    @RequestMapping(value = "/todaySale/getMobileTodaySaleDcList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileTodaySaleDcList(MobileTodaySaleVO mobileTodaySaleVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileTodaySaleService.getMobileTodaySaleDcList(mobileTodaySaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileTodaySaleVO);
    }

    /**
     * 내점/배달/포장 - 조회
     *
     * @param mobileTodaySaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 04. 02.
     */
    @RequestMapping(value = "/todaySale/getMobileTodaySaleDlvrList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileTodaySaleDlvrList(MobileTodaySaleVO mobileTodaySaleVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileTodaySaleService.getMobileTodaySaleDlvrList(mobileTodaySaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileTodaySaleVO);
    }

    /**
     * 시간대별 - 조회
     *
     * @param mobileTodaySaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 04. 02.
     */
    @RequestMapping(value = "/todaySale/getMobileTodaySaleTimeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileTodaySaleTimeList(MobileTodaySaleVO mobileTodaySaleVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileTodaySaleService.getMobileTodaySaleTimeList(mobileTodaySaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileTodaySaleVO);
    }
}