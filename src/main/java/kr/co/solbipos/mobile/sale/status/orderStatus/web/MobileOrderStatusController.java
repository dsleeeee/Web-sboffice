package kr.co.solbipos.mobile.sale.status.orderStatus.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.sale.status.orderStatus.service.MobileOrderStatusService;
import kr.co.solbipos.mobile.sale.status.orderStatus.service.MobileOrderStatusVO;
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
 * @Class Name : MobileOrderStatusController.java
 * @Description : 매출현황 > 주문현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.10.01  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2021.10.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/mobile/sale/status/orderStatus")
public class MobileOrderStatusController {
    private final SessionService sessionService;
    private final MobileOrderStatusService mobileOrderStatusService;

    @Autowired
    public MobileOrderStatusController(SessionService sessionService, MobileOrderStatusService mobileOrderStatusService) {
        this.sessionService = sessionService;
        this.mobileOrderStatusService = mobileOrderStatusService;
    }

    /**
     * 주문현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  권지현
     * @since   2021.10.01
     */
    @RequestMapping(value = "/mobileOrderStatus/list.sb", method = RequestMethod.GET)
    public String orderView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "mobile/sale/status/orderStatus/mobileOrderStatus";
    }

    /**
     * 주문현황 - 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   mobileOrderStatusVO
     * @return  String
     * @author  권지현
     * @since   2021.10.01
     */
    @RequestMapping(value = "/orderStatus/getMobileOrderStatusList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileOrderStatusList(HttpServletRequest request, HttpServletResponse response,
                               Model model, MobileOrderStatusVO mobileOrderStatusVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileOrderStatusService.getMobileOrderStatusList(mobileOrderStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, mobileOrderStatusVO);
    }

    /**
     * 주문현황 상세 팝업 - 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   mobileOrderStatusVO
     * @return  String
     * @author  권지현
     * @since   2021.10.01
     */
    @RequestMapping(value = "/orderStatus/getMobileOrderStatusDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMobileOrderStatusDtlList(HttpServletRequest request, HttpServletResponse response,
                                     Model model, MobileOrderStatusVO mobileOrderStatusVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = mobileOrderStatusService.getMobileOrderStatusDtlList(mobileOrderStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, mobileOrderStatusVO);
    }
}