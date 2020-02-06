package kr.co.solbipos.sale.status.appr.cashBill.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import kr.co.solbipos.sale.status.appr.cashBill.service.ApprCashBillService;
import kr.co.solbipos.sale.status.appr.cashBill.service.ApprCashBillVO;

/**
 * @Class Name : ApprCashBillController.java
 * @Description : 매출관리 > 승인현황 > 신용카드 승인현황 탭
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.31  조동훤      최초생성
 *
 * @author 
 * @since 2020.01.31
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/status/appr")
public class ApprCashBillController {
    private final SessionService sessionService;
    private final ApprCashBillService apprCashBillService;

    @Autowired
    public ApprCashBillController(SessionService sessionService, ApprCashBillService apprCashBillService) {
        this.sessionService = sessionService;
        this.apprCashBillService = apprCashBillService;
    }


    /**
     * 신용카드 승인현황 - 페이지 이동
     * @param   request
     * @param   response
     * @param   model
     * @return  String
     * @author  조동훤
     * @since   2020. 01. 13.
     */
    @RequestMapping(value = "/cashBill/view.sb", method = RequestMethod.GET)
    public String apprCashBillView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sale/status/appr/apprSale";
    }


    /**
     * 신용카드 승인현황 - 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   apprCashBillVO
     * @return  String
     * @author  조동훤
     * @since   2020. 01. 13.
     */
    @RequestMapping(value = "/cashBill/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getApprCashBillList(HttpServletRequest request, HttpServletResponse response,
        Model model, ApprCashBillVO apprCashBillVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = apprCashBillService.getApprCashBillList(apprCashBillVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, list, apprCashBillVO);
    }
}
