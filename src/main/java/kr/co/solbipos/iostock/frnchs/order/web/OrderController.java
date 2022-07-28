package kr.co.solbipos.iostock.frnchs.order.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.common.service.code.CmmEnvService;
import kr.co.common.utils.CmmUtil;
import kr.co.solbipos.iostock.order.dstbReq.service.DstbReqService;
import kr.co.solbipos.iostock.order.dstbReq.service.DstbReqVO;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderService;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderVO;
import kr.co.solbipos.store.hq.brand.service.HqEnvstVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.iostock.frnchs.order.service.OrderService;
import kr.co.solbipos.iostock.frnchs.order.service.OrderVO;

import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : OrderController.java
 * @Description : 수불관리 > 본사 매장간 입출고관리 > 주문대비 입고현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.03.11  조동훤      최초생성
 *
 * @author 엠투엠글로벌 조동훤
 * @since 2020.03.11
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/iostock/frnchs/order")
public class OrderController {
    private final SessionService sessionService;
    private final OrderService orderService;
    private final StoreOrderService storeOrderService;
    private final CmmEnvService cmmEnvService;
    private final DstbReqService dstbReqService;

    @Autowired
    public OrderController(SessionService sessionService, OrderService orderService, StoreOrderService storeOrderService, CmmEnvService cmmEnvService, DstbReqService dstbReqService) {
        this.sessionService = sessionService;
        this.orderService = orderService;
        this.storeOrderService = storeOrderService;
        this.cmmEnvService = cmmEnvService;
        this.dstbReqService = dstbReqService;
    }

    /**
     * 주문대비 입고현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  조동훤
     * @since   2020.03.11.
     */
    @RequestMapping(value = "/ioStock/view.sb", method = RequestMethod.GET)
    public String orderView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 본사 환경설정 1242(거래처출고사용여부) 조회
        HqEnvstVO hqEnvstVO = new HqEnvstVO();
        hqEnvstVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        hqEnvstVO.setEnvstCd("1242");
        model.addAttribute("envst1242", CmmUtil.nvl(cmmEnvService.getHqEnvst(hqEnvstVO), "0"));

        // 본사 거래처 콤보박스
        StoreOrderVO storeOrderVO = new StoreOrderVO();
        model.addAttribute("vendrList", convertToJson(storeOrderService.getHqVendrCombo(storeOrderVO, sessionInfoVO)));

        // 현재 로그인 사원에 맵핑된 거래처코드 조회
        DstbReqVO dstbReqVO = new DstbReqVO();
        model.addAttribute("empVendrCd", dstbReqService.getEmployeeVendr(dstbReqVO, sessionInfoVO));

        return "iostock/frnchs/order/order";
    }


    /**
     * 주문대비 입고현황 - 주문대비 입고현황 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   orderStockInfoVO
     * @return  String
     * @author  조동훤
     * @since   2020.03.11.
     */
    @RequestMapping(value = "/ioStock/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderList(HttpServletRequest request, HttpServletResponse response,
        Model model, OrderVO orderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderService.getOrderList(orderVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, orderVO);
    }


    /**
     * 주문대비 입고현황 - 주문대비 입고현황 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   orderStockInfoVO
     * @return  String
     * @author  조동훤
     * @since   2020.03.11.
     */
    @RequestMapping(value = "/ioStockDtl/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderDtlList(HttpServletRequest request, HttpServletResponse response,
        Model model, OrderVO orderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>>
            list = orderService.getOrderDtlList(orderVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, orderVO);
    }
    
    /**
     * 전표별 입출고내역 - 조회조건 진행상태 콤보 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   orderVO
     * @return  String
     * @author  조동훤
     * @since   2020.03.12
     */
    @RequestMapping(value = "/srchOrderProcFg/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSrchOrderProcFgList(HttpServletRequest request, HttpServletResponse response, Model model, OrderVO orderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderService.getSrchOrderProcFgList(orderVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, orderVO);
    }
    
    /**
     * 주문대비 입고현황 - 주문대비 입고현황 엑셀리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   orderStockInfoVO
     * @return  String
     * @author  조동훤
     * @since   2020.04.21.
     */
    @RequestMapping(value = "/ioStock/excelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderExcelList(HttpServletRequest request, HttpServletResponse response,
        Model model, OrderVO orderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderService.getOrderExcelList(orderVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, orderVO);
    }


    /**
     * 주문대비 입고현황 - 주문대비 입고현황 상세 엑셀리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   orderStockInfoVO
     * @return  String
     * @author  조동훤
     * @since   2020.04.21.
     */
    @RequestMapping(value = "/ioStockDtl/excelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderDtlExcelList(HttpServletRequest request, HttpServletResponse response,
        Model model, OrderVO orderVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>>
            list = orderService.getOrderDtlExcelList(orderVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, orderVO);
    }
}
