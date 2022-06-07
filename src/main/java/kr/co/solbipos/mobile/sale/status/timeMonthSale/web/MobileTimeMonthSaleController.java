package kr.co.solbipos.mobile.sale.status.timeMonthSale.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.timeMonthSale.service.MobileTimeMonthSaleService;
import kr.co.solbipos.mobile.sale.status.timeMonthSale.service.MobileTimeMonthSaleVO;
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
 * @Class Name : MobileTimeMonthSaleController.java
 * @Description : (모바일) 매출현황 > 시간대별(월별)매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.07  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.06.07
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/mobile/sale/status/timeMonthSale")
public class MobileTimeMonthSaleController {

    private final SessionService sessionService;
    private final MobileTimeMonthSaleService mobileTimeMonthSaleService;
    private final MobileProdSaleService mobileProdSaleService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileTimeMonthSaleController(SessionService sessionService, MobileTimeMonthSaleService mobileTimeMonthSaleService, MobileProdSaleService mobileProdSaleService) {
        this.sessionService = sessionService;
        this.mobileTimeMonthSaleService = mobileTimeMonthSaleService;
        this.mobileProdSaleService = mobileProdSaleService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/mobileTimeMonthSale/list.sb", method = RequestMethod.GET)
    public String mobileTimeMonthSaleView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        MobileProdSaleVO mobileProdSaleVO = new MobileProdSaleVO();

        // 다중매장조회
        List<DefaultMap<String>> list = mobileProdSaleService.getMultiStoreList(mobileProdSaleVO, sessionInfoVO);
        model.addAttribute("multiStoreFg", list.size());

        // 시간대 조회
        List<DefaultMap<String>> timeSlotColList = mobileProdSaleService.getTimeSlotList(sessionInfoVO);
        // 시간대를 , 로 연결하는 문자열 생성
        String timeSlotCol = "";
        for(int i=0; i < timeSlotColList.size(); i++) {
            timeSlotCol += (timeSlotCol.equals("") ? "" : ",") + timeSlotColList.get(i).getStr("value");
        }

        model.addAttribute("timeSlotColList", timeSlotColList);
        model.addAttribute("timeSlotCol", timeSlotCol);

        return "mobile/sale/status/timeMonthSale/mobileTimeMonthSale";
    }

    /**
     * 일자-시간대별 - 조회
     *
     * @param mobileTimeMonthSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 06. 07.
     */
    @RequestMapping(value = "/timeMonthSale/getMobileTimeMonthSaleDateTimeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileTimeMonthSaleDateTimeList(MobileTimeMonthSaleVO mobileTimeMonthSaleVO, HttpServletRequest request,
                                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileTimeMonthSaleService.getMobileTimeMonthSaleDateTimeList(mobileTimeMonthSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileTimeMonthSaleVO);
    }

    /**
     * 시간대별 - 조회
     *
     * @param mobileTimeMonthSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 06. 07.
     */
    @RequestMapping(value = "/timeMonthSale/getMobileTimeMonthSaleTimeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileTimeMonthSaleTimeList(MobileTimeMonthSaleVO mobileTimeMonthSaleVO, HttpServletRequest request,
                                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileTimeMonthSaleService.getMobileTimeMonthSaleTimeList(mobileTimeMonthSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileTimeMonthSaleVO);
    }

    /**
     * 시간대별 - 차트 조회
     *
     * @param mobileTimeMonthSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 06. 07.
     */
    @RequestMapping(value = "/timeMonthSale/getMobileTimeMonthSaleTimeChartList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileTimeMonthSaleTimeChartList(MobileTimeMonthSaleVO mobileTimeMonthSaleVO, HttpServletRequest request,
                                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileTimeMonthSaleService.getMobileTimeMonthSaleTimeChartList(mobileTimeMonthSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileTimeMonthSaleVO);
    }
}