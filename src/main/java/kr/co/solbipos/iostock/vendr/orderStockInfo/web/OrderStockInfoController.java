package kr.co.solbipos.iostock.vendr.orderStockInfo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.vendr.orderStockInfo.service.OrderStockInfoService;
import kr.co.solbipos.iostock.vendr.orderStockInfo.service.OrderStockInfoVO;
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
 * @Class Name : OrderStockInfoController.java
 * @Description : 수불관리 > 거래처(매입)입출고관리 > 발주대비 입고현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.04  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 12.04
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/vendr/orderStockInfo")
public class OrderStockInfoController {
    private final SessionService sessionService;
    private final OrderStockInfoService orderStockInfoService;

    @Autowired
    public OrderStockInfoController(SessionService sessionService, OrderStockInfoService orderStockInfoService) {
        this.sessionService = sessionService;
        this.orderStockInfoService = orderStockInfoService;
    }

    /**
     * 거래처 발주대비 입고현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  안동관
     * @since   2018. 12. 04.
     */
    @RequestMapping(value = "/orderStockInfo/view.sb", method = RequestMethod.GET)
    public String orderStockInfoView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "iostock/vendr/orderStockInfo/orderStockInfo";
    }


    /**
     * 거래처 발주대비 입고현황 - 발주대비 입고현황 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   orderStockInfoVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 04.
     */
    @RequestMapping(value = "/orderStockInfo/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderStockInfoList(HttpServletRequest request, HttpServletResponse response,
        Model model, OrderStockInfoVO orderStockInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderStockInfoService.getOrderStockInfoList(orderStockInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, orderStockInfoVO);
    }


    /**
     * 거래처 발주대비 입고현황 - 발주대비 입고현황 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   orderStockInfoVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 04.
     */
    @RequestMapping(value = "/orderStockInfoDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderStockInfoDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, OrderStockInfoVO orderStockInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>>
            list = orderStockInfoService.getOrderStockInfoDtlList(orderStockInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, orderStockInfoVO);
    }


    /**
     * 거래처 발주대비 입고현황 - 상품 입고현황 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   orderStockInfoVO
     * @return  String
     * @author  안동관
     * @since   2018. 12. 05.
     */
    @RequestMapping(value = "/prodInstockInfo/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdInstockInfoList(HttpServletRequest request, HttpServletResponse response,
        Model model, OrderStockInfoVO orderStockInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderStockInfoService.getProdInstockInfoList(orderStockInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, orderStockInfoVO);
    }
}
