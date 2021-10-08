package kr.co.solbipos.sale.orderStatus.orderStatus.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.orderStatus.orderStatus.service.OrderStatusService;
import kr.co.solbipos.sale.orderStatus.orderStatus.service.OrderStatusVO;
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

    @Autowired
    public OrderStatusController(SessionService sessionService, OrderStatusService orderStatusService) {
        this.sessionService = sessionService;
        this.orderStatusService = orderStatusService;
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
        return "sale/status/orderStatus/orderStatus";
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
}