package kr.co.solbipos.sale.appr.cashBill.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.appr.cashBill.service.CashBillService;
import kr.co.solbipos.sale.appr.cashBill.service.CashBillVO;
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
 * @Class Name : CashBillController.java
 * @Description : 맘스터치 > 승인관리2 > 현금영수증 승인 조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.29  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.09.29
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/appr/cashBill")
public class CashBillController {

    private final SessionService sessionService;
    private final CashBillService cashBillService;

    /**
     * Constructor Injection
     */
    @Autowired
    public CashBillController(SessionService sessionService, CashBillService cashBillService) {
        this.sessionService = sessionService;
        this.cashBillService = cashBillService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/cashBill/list.sb", method = RequestMethod.GET)
    public String cashBillView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/appr/cashBill/cashBill";
    }

    /**
     * 현금영수증 승인 조회
     *
     * @param cashBillVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  권지현
     * @since   2022. 09. 29.
     */
    @RequestMapping(value = "/cashBill/getCashBillList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCashBillList(CashBillVO cashBillVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = cashBillService.getCashBillList(cashBillVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, cashBillVO);
    }


}