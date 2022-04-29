package kr.co.solbipos.mobile.sale.status.dayOfWeekSale.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.dayOfWeekSale.service.MobileDayOfWeekSaleService;
import kr.co.solbipos.mobile.sale.status.dayOfWeekSale.service.MobileDayOfWeekSaleVO;
import kr.co.solbipos.mobile.sale.status.prod.service.MobileProdSaleService;
import kr.co.solbipos.mobile.sale.status.prod.service.MobileProdSaleVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : MobileDayOfWeekSaleController.java
 * @Description : (모바일) 매출현황 > 요일별
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
@RequestMapping("/mobile/sale/status/dayOfWeekSale")
public class MobileDayOfWeekSaleController {

    private final SessionService sessionService;
    private final MobileDayOfWeekSaleService mobileDayOfWeekSaleService;
    private final MobileProdSaleService mobileProdSaleService;

    public MobileDayOfWeekSaleController(SessionService sessionService, MobileDayOfWeekSaleService mobileDayOfWeekSaleService, MobileProdSaleService mobileProdSaleService) {
        this.sessionService = sessionService;
        this.mobileDayOfWeekSaleService = mobileDayOfWeekSaleService;
        this.mobileProdSaleService = mobileProdSaleService;
    }


    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/mobileDayOfWeekSale/list.sb", method = RequestMethod.GET)
    public String mobileDayOfWeekSaleView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        MobileProdSaleVO mobileProdSaleVO = new MobileProdSaleVO();

        // 다중매장조회
        List<DefaultMap<String>> list = mobileProdSaleService.getMultiStoreList(mobileProdSaleVO, sessionInfoVO);

        model.addAttribute("multiStoreFg", list.size());

        return "mobile/sale/status/dayOfWeekSale/mobileDayOfWeekSale";
    }

    /**
     * 매출종합 - 조회
     *
     * @param mobileDayOfWeekSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.04.27
     */
    @RequestMapping(value = "/dayOfWeekSale/getMobileDayOfWeekSaleList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileDayOfWeekSaleList(MobileDayOfWeekSaleVO mobileDayOfWeekSaleVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileDayOfWeekSaleService.getMobileDayOfWeekSaleList(mobileDayOfWeekSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileDayOfWeekSaleVO);
    }

    /**
     * 일평균 - 조회
     *
     * @param mobileDayOfWeekSaleVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022.04.27
     */
    @RequestMapping(value = "/dayOfWeekSale/getMobileDayOfWeekSaleChartList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileDayOfWeekSaleChartList(MobileDayOfWeekSaleVO mobileDayOfWeekSaleVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = mobileDayOfWeekSaleService.getMobileDayOfWeekSaleChartList(mobileDayOfWeekSaleVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, mobileDayOfWeekSaleVO);
    }

}