package kr.co.solbipos.sale.benson.orderEmpBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.benson.orderEmpBenson.service.OrderEmpBensonService;
import kr.co.solbipos.sale.benson.orderEmpBenson.service.OrderEmpBensonVO;
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
 * @Class Name : OrderEmpBensonController.java
 * @Description : 벤슨 > 매출현황2 > 주문자현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.10
 * @version 1.0
 * @see
 *
 * @Copyright (C) by LYNK CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/benson/orderEmpBenson")
public class OrderEmpBensonController {
    private final SessionService sessionService;
    private final OrderEmpBensonService orderEmpBensonService;

    @Autowired
    public OrderEmpBensonController(SessionService sessionService, OrderEmpBensonService orderEmpBensonService) {
        this.sessionService = sessionService;
        this.orderEmpBensonService = orderEmpBensonService;
    }


    /**
     * 주문자현황탭 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  김유승
     * @since   2026.09.10
     */
    @RequestMapping(value = "/orderEmpBenson/view.sb", method = RequestMethod.GET)
    public String OrderEmpBensonView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "sale/benson/orderEmpBenson/orderEmpBensonTab";
    }

    /**
     * 기간별탭 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   orderEmpBensonVO
     * @return  String
     * @author  김유승
     * @since   2026.09.10
     */
    @RequestMapping(value = "/orderEmpBenson/getOrderEmpPeriodList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderEmpPeriodList(HttpServletRequest request, HttpServletResponse response,
                                        Model model, OrderEmpBensonVO orderEmpBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderEmpBensonService.getOrderEmpPeriodList(orderEmpBensonVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list,  orderEmpBensonVO);
    }

    /**
     * 기간별탭 - 엑셀다운로드
     * @param   request
     * @param   response
     * @param   model
     * @param   orderEmpBensonVO
     * @return  String
     * @author  김유승
     * @since   2026.09.10
     */
    @RequestMapping(value = "/orderEmpBenson/getOrderEmpPeriodExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderEmpPeriodExcelList(HttpServletRequest request, HttpServletResponse response,
                                Model model, OrderEmpBensonVO orderEmpBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderEmpBensonService.getOrderEmpPeriodExcelList(orderEmpBensonVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list,  orderEmpBensonVO);
    }

    /**
     * 기간별탭 - 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   orderEmpBensonVO
     * @return  String
     * @author  김유승
     * @since   2026.09.10
     */
    @RequestMapping(value = "/orderEmpBenson/getOrderEmpPeriodDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderEmpPeriodDtlList(HttpServletRequest request, HttpServletResponse response,
                                        Model model, OrderEmpBensonVO orderEmpBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderEmpBensonService.getOrderEmpPeriodDtlList(orderEmpBensonVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list,  orderEmpBensonVO);
    }

    /**
     * 기간별탭 - 상세 엑셀다운로드
     * @param   request
     * @param   response
     * @param   model
     * @param   orderEmpBensonVO
     * @return  String
     * @author  김유승
     * @since   2026.09.10
     */
    @RequestMapping(value = "/orderEmpBenson/getOrderEmpPeriodDtlExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderEmpPeriodDtlExcelList(HttpServletRequest request, HttpServletResponse response,
                                Model model, OrderEmpBensonVO orderEmpBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderEmpBensonService.getOrderEmpPeriodDtlExcelList(orderEmpBensonVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list,  orderEmpBensonVO);
    }

    /**
     * 일자별 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   orderEmpBensonVO
     * @return  String
     * @author  김유승
     * @since   2026.09.10
     */
    @RequestMapping(value = "/orderEmpBenson/getOrderEmpDayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderEmpDayList(HttpServletRequest request, HttpServletResponse response,
                                Model model, OrderEmpBensonVO orderEmpBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderEmpBensonService.getOrderEmpDayList(orderEmpBensonVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list,  orderEmpBensonVO);
    }

    /**
     * 일자별 - 엑셀다운로드
     * @param   request
     * @param   response
     * @param   model
     * @param   orderEmpBensonVO
     * @return  String
     * @author  김유승
     * @since   2026.09.10
     */
    @RequestMapping(value = "/orderEmpBenson/getOrderEmpDayExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderEmpDayExcelList(HttpServletRequest request, HttpServletResponse response,
                                Model model, OrderEmpBensonVO orderEmpBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderEmpBensonService.getOrderEmpDayExcelList(orderEmpBensonVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list,  orderEmpBensonVO);
    }
}
