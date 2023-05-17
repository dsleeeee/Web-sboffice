package kr.co.solbipos.sale.orderStatus.orderStatus.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.orderStatus.orderStatus.service.OrderStatusService;
import kr.co.solbipos.sale.orderStatus.orderStatus.service.OrderStatusVO;
import kr.co.solbipos.sale.today.todayDtl.service.TodayDtlService;
import kr.co.solbipos.sale.today.todayDtl.service.TodayDtlVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : OrderStatusController.java
 * @Description : 매출관리 > 매출현황 > 주문현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.30  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2021.09.30
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/orderStatus/orderStatus")
public class OrderStatusController {
    private final SessionService sessionService;
    private final OrderStatusService orderStatusService;
    private final TodayDtlService todayDtlService;

    @Autowired
    public OrderStatusController(SessionService sessionService, OrderStatusService orderStatusService, TodayDtlService todayDtlService) {
        this.sessionService = sessionService;
        this.orderStatusService = orderStatusService;
        this.todayDtlService = todayDtlService;
    }

    /**
     * 주문현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  권지현
     * @since   2021.09.30
     */
    @RequestMapping(value = "/orderStatus/view.sb", method = RequestMethod.GET)
    public String orderView(HttpServletRequest request, HttpServletResponse response, Model model) {

        TodayDtlVO todayDtlVO = new TodayDtlVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 결제수단 조회
        List<DefaultMap<String>> payColList = todayDtlService.getPayColList(todayDtlVO, sessionInfoVO);

        // 결제수단 코드를 , 로 연결하는 문자열 생성
        String payCol = "";
        for(int i=0; i < payColList.size(); i++) {
            payCol += (payCol.equals("") ? "" : ",") + payColList.get(i).getStr("payCd");
        }
        model.addAttribute("payColList", payColList);
        model.addAttribute("payCol", payCol);

        // 할인구분 조회
        List<DefaultMap<String>> dcColList = todayDtlService.getDcColList(todayDtlVO, sessionInfoVO);

        // 할인구분 코드를 , 로 연결하는 문자열 생성
        String dcCol = "";
        for(int i=0; i < dcColList.size(); i++) {
            dcCol += (dcCol.equals("") ? "" : ",") + dcColList.get(i).getStr("dcCd");
        }
        model.addAttribute("dcColList", dcColList);
        model.addAttribute("dcCol", dcCol);

        // 객수 조회
        List<DefaultMap<String>> guestColList = todayDtlService.getGuestColList(todayDtlVO, sessionInfoVO);

        // 할인구분 코드를 , 로 연결하는 문자열 생성
        String guestCol = "";
        for(int i=0; i < guestColList.size(); i++) {
            guestCol += (guestCol.equals("") ? "" : ",") + guestColList.get(i).getStr("guestCd");
        }
        model.addAttribute("guestColList", guestColList);
        model.addAttribute("guestCol", guestCol);

        return "sale/status/orderStatus/orderStatusTab";
    }

    /**
     * 주문현황 - 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   orderVO
     * @return  String
     * @author  권지현
     * @since   2021.09.30
     */
    @RequestMapping(value = "/orderStatus/getOrderStatusList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderStatusList(HttpServletRequest request, HttpServletResponse response,
                               Model model, OrderStatusVO orderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderStatusService.getOrderStatusList(orderVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, orderVO);
    }

    /**
     * 주문현황 상세 팝업 - 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   orderVO
     * @return  String
     * @author  권지현
     * @since   2021.10.01
     */
    @RequestMapping(value = "/orderStatus/getOrderStatusDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderStatusDtlList(HttpServletRequest request, HttpServletResponse response,
                                     Model model, OrderStatusVO orderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderStatusService.getOrderStatusDtlList(orderVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, orderVO);
    }

    /**
     * 주문취소 - 기간내 전체취소건수 조회
     * @param request
     * @param response
     * @param model
     * @param orderVO
     * @return
     * @author  이다솜
     * @since   2023.05.15
     */
    @RequestMapping(value = "/orderStatus/getOrderCancelPeriod.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderCancelPeriod(HttpServletRequest request, HttpServletResponse response,
                                     Model model, OrderStatusVO orderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderStatusService.getOrderCancelPeriod(orderVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, orderVO);
    }

    /**
     * 주문취소 - 일자별 취소건수 조회
     * @param request
     * @param response
     * @param model
     * @param orderVO
     * @return
     * @author  이다솜
     * @since   2023.05.15
     */
    @RequestMapping(value = "/orderStatus/getOrderCancelByDate.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderCancelByDate(HttpServletRequest request, HttpServletResponse response,
                                     Model model, OrderStatusVO orderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderStatusService.getOrderCancelByDate(orderVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, orderVO);
    }

    /**
     * 주문취소 - 캐셔별 취소건수 조회
     * @param request
     * @param response
     * @param model
     * @param orderVO
     * @return
     * @author  이다솜
     * @since   2023.05.15
     */
    @RequestMapping(value = "/orderStatus/getOrderCancelByCashier.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderCancelByCashier(HttpServletRequest request, HttpServletResponse response,
                                     Model model, OrderStatusVO orderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderStatusService.getOrderCancelByCashier(orderVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, orderVO);
    }

    /**
     * 주문취소 - 주문취소내역 조회
     * @param request
     * @param response
     * @param model
     * @param orderVO
     * @return
     * @author  이다솜
     * @since   2023.05.15
     */
    @RequestMapping(value = "/orderStatus/getOrderCancelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderCancelList(HttpServletRequest request, HttpServletResponse response,
                                     Model model, OrderStatusVO orderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderStatusService.getOrderCancelList(orderVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, orderVO);
    }
}