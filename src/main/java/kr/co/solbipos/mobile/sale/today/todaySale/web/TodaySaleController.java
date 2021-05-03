package kr.co.solbipos.mobile.sale.today.todaySale.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.today.todaySale.service.TodaySaleService;
import kr.co.solbipos.mobile.sale.today.todaySale.service.TodaySaleVO;
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
 * @Class Name : TodaySaleController.java
 * @Description : (모바일) 매출현황 > 당일매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.04.02  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2021.04.02
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/mobile/sale/today/todaySale")
public class TodaySaleController {

    private final SessionService sessionService;
    private final TodaySaleService todaySaleService;
    private final MobileProdSaleService mobileProdSaleService;

    /**
     * Constructor Injection
     */
    @Autowired
    public TodaySaleController(SessionService sessionService, TodaySaleService todaySaleService, MobileProdSaleService mobileProdSaleService) {
        this.sessionService = sessionService;
        this.todaySaleService = todaySaleService;
        this.mobileProdSaleService = mobileProdSaleService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/todaySale/list.sb", method = RequestMethod.GET)
    public String todaySaleView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        MobileProdSaleVO mobileProdSaleVO = new MobileProdSaleVO();

        // 다중매장조회
        List<DefaultMap<String>> list = mobileProdSaleService.getMultiStoreList(mobileProdSaleVO, sessionInfoVO);

        model.addAttribute("multiStoreFg", list.size());

        return "mobile/sale/today/todaySale/todaySale";
    }

    /**
     * 당일매출종합 - 조회
     *
     * @param todaySaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 04. 02.
     */
    @RequestMapping(value = "/todaySale/getTodaySaleList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTodaySaleList(TodaySaleVO todaySaleVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = todaySaleService.getTodaySaleList(todaySaleVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 결제수단 - 조회
     *
     * @param todaySaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 04. 02.
     */
    @RequestMapping(value = "/todaySale/getTodaySalePayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTodaySalePayList(TodaySaleVO todaySaleVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = todaySaleService.getTodaySalePayList(todaySaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, todaySaleVO);
    }

    /**
     * 할인내역 - 조회
     *
     * @param todaySaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 04. 02.
     */
    @RequestMapping(value = "/todaySale/getTodaySaleDcList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTodaySaleDcList(TodaySaleVO todaySaleVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = todaySaleService.getTodaySaleDcList(todaySaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, todaySaleVO);
    }

    /**
     * 매장/배달/포장 - 조회
     *
     * @param todaySaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 04. 02.
     */
    @RequestMapping(value = "/todaySale/getTodaySaleDlvrList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTodaySaleDlvrList(TodaySaleVO todaySaleVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = todaySaleService.getTodaySaleDlvrList(todaySaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, todaySaleVO);
    }

    /**
     * 시간대별 - 조회
     *
     * @param todaySaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 04. 02.
     */
    @RequestMapping(value = "/todaySale/getTodaySaleTimeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTodaySaleTimeList(TodaySaleVO todaySaleVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = todaySaleService.getTodaySaleTimeList(todaySaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, todaySaleVO);
    }
}