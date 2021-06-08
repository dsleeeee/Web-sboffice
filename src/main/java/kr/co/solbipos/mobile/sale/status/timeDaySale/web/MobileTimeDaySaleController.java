package kr.co.solbipos.mobile.sale.status.timeDaySale.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.timeDaySale.service.MobileTimeDaySaleService;
import kr.co.solbipos.mobile.sale.status.timeDaySale.service.MobileTimeDaySaleVO;
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
 * @Class Name : MobileTimeDaySaleController.java
 * @Description : (모바일) 매출현황 > 시간대별(일자별)매출현황
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
@RequestMapping("/mobile/sale/status/timeDaySale")
public class MobileTimeDaySaleController {

    private final SessionService sessionService;
    private final MobileTimeDaySaleService mobileTimeDaySaleService;
    private final MobileProdSaleService mobileProdSaleService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileTimeDaySaleController(SessionService sessionService, MobileTimeDaySaleService mobileTimeDaySaleService, MobileProdSaleService mobileProdSaleService) {
        this.sessionService = sessionService;
        this.mobileTimeDaySaleService = mobileTimeDaySaleService;
        this.mobileProdSaleService = mobileProdSaleService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/mobileTimeDaySale/list.sb", method = RequestMethod.GET)
    public String mobileTimeDaySaleSaleView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        MobileProdSaleVO mobileProdSaleVO = new MobileProdSaleVO();

        // 다중매장조회
        List<DefaultMap<String>> list = mobileProdSaleService.getMultiStoreList(mobileProdSaleVO, sessionInfoVO);
        model.addAttribute("multiStoreFg", list.size());

        return "mobile/sale/status/timeDaySale/mobileTimeDaySale";
    }

    /**
     * 일자-시간대별 - 조회
     *
     * @param mobileTimeDaySaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 06. 07.
     */
    @RequestMapping(value = "/timeDaySale/getMobileTimeDaySaleDateTimeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileTimeDaySaleDateTimeList(MobileTimeDaySaleVO mobileTimeDaySaleVO, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileTimeDaySaleService.getMobileTimeDaySaleDateTimeList(mobileTimeDaySaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileTimeDaySaleVO);
    }

    /**
     * 시간대별 - 조회
     *
     * @param mobileTimeDaySaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 06. 07.
     */
    @RequestMapping(value = "/timeDaySale/getMobileTimeDaySaleTimeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileTimeDaySaleTimeList(MobileTimeDaySaleVO mobileTimeDaySaleVO, HttpServletRequest request,
                                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileTimeDaySaleService.getMobileTimeDaySaleTimeList(mobileTimeDaySaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileTimeDaySaleVO);
    }

    /**
     * 시간대별 - 차트 조회
     *
     * @param mobileTimeDaySaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 06. 07.
     */
    @RequestMapping(value = "/timeDaySale/getMobileTimeDaySaleTimeChartList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileTimeDaySaleTimeChartList(MobileTimeDaySaleVO mobileTimeDaySaleVO, HttpServletRequest request,
                                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileTimeDaySaleService.getMobileTimeDaySaleTimeChartList(mobileTimeDaySaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileTimeDaySaleVO);
    }
}