package kr.co.solbipos.sale.status.orderEmp.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.orderEmp.service.OrderEmpService;
import kr.co.solbipos.sale.status.orderEmp.service.OrderEmpVO;
import kr.co.solbipos.sale.status.side.service.SideService;
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
 * @Class Name : OrderEmpController.java
 * @Description : 매출관리 > 매출현황2 > 주문자현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.06.10  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.06.10
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/status/orderEmp")
public class OrderEmpController {
    private final SessionService sessionService;
    private final OrderEmpService orderEmpService;

    @Autowired
    public OrderEmpController(SessionService sessionService, OrderEmpService orderEmpService) {
        this.sessionService = sessionService;
        this.orderEmpService = orderEmpService;
    }


    /**
     * 주문자현황탭 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  권지현
     * @since   2022.06.10
     */
    @RequestMapping(value = "/orderEmp/view.sb", method = RequestMethod.GET)
    public String OrderEmpView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "sale/status/orderEmp/orderEmpTab";
    }

    /**
     * 기간별탭 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   orderEmpVO
     * @return  String
     * @author  권지현
     * @since   2022.06.10
     */
    @RequestMapping(value = "/orderEmp/getOrderEmpPeriodList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderEmpPeriodList(HttpServletRequest request, HttpServletResponse response,
                                        Model model, OrderEmpVO orderEmpVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderEmpService.getOrderEmpPeriodList(orderEmpVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list,  orderEmpVO);
    }

    /**
     * 기간별탭 - 엑셀다운로드
     * @param   request
     * @param   response
     * @param   model
     * @param   orderEmpVO
     * @return  String
     * @author  권지현
     * @since   2022.06.10
     */
    @RequestMapping(value = "/orderEmp/getOrderEmpPeriodExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderEmpPeriodExcelList(HttpServletRequest request, HttpServletResponse response,
                                Model model, OrderEmpVO orderEmpVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderEmpService.getOrderEmpPeriodExcelList(orderEmpVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list,  orderEmpVO);
    }

    /**
     * 기간별탭 - 상세 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   orderEmpVO
     * @return  String
     * @author  권지현
     * @since   2022.06.10
     */
    @RequestMapping(value = "/orderEmp/getOrderEmpPeriodDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderEmpPeriodDtlList(HttpServletRequest request, HttpServletResponse response,
                                        Model model, OrderEmpVO orderEmpVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderEmpService.getOrderEmpPeriodDtlList(orderEmpVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list,  orderEmpVO);
    }

    /**
     * 기간별탭 - 상세 엑셀다운로드
     * @param   request
     * @param   response
     * @param   model
     * @param   orderEmpVO
     * @return  String
     * @author  권지현
     * @since   2022.06.10
     */
    @RequestMapping(value = "/orderEmp/getOrderEmpPeriodDtlExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderEmpPeriodDtlExcelList(HttpServletRequest request, HttpServletResponse response,
                                Model model, OrderEmpVO orderEmpVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderEmpService.getOrderEmpPeriodDtlExcelList(orderEmpVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list,  orderEmpVO);
    }

    /**
     * 일자별 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   orderEmpVO
     * @return  String
     * @author  권지현
     * @since   2022.06.10
     */
    @RequestMapping(value = "/orderEmp/getOrderEmpDayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderEmpDayList(HttpServletRequest request, HttpServletResponse response,
                                Model model, OrderEmpVO orderEmpVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderEmpService.getOrderEmpDayList(orderEmpVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list,  orderEmpVO);
    }

    /**
     * 일자별 - 엑셀다운로드
     * @param   request
     * @param   response
     * @param   model
     * @param   orderEmpVO
     * @return  String
     * @author  권지현
     * @since   2022.06.10
     */
    @RequestMapping(value = "/orderEmp/getOrderEmpDayExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderEmpDayExcelList(HttpServletRequest request, HttpServletResponse response,
                                Model model, OrderEmpVO orderEmpVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = orderEmpService.getOrderEmpDayExcelList(orderEmpVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list,  orderEmpVO);
    }
}
