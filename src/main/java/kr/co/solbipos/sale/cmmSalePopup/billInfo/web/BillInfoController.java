package kr.co.solbipos.sale.cmmSalePopup.billInfo.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.cmmSalePopup.billInfo.service.BillInfoService;
import kr.co.solbipos.sale.cmmSalePopup.billInfo.service.BillInfoVO;
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
 * @Class Name : BillInfoController.java
 * @Description : 매출공통팝업 - 영수증상세 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.01.30  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2019.01.30
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Controller
@RequestMapping("/sale/cmmSalePopup/billInfo")
public class BillInfoController {
    private final SessionService sessionService;
    private final BillInfoService billInfoService;

    @Autowired
    public BillInfoController(SessionService sessionService, BillInfoService billInfoService) {
        this.sessionService = sessionService;
        this.billInfoService = billInfoService;
    }


    /**
     * 매출공통팝업 - 영수증상세 종합내역 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   billInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 01. 30.
     */
    @RequestMapping(value = "/billInfo/billInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBillInfo(HttpServletRequest request, HttpServletResponse response,
        Model model, BillInfoVO billInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = billInfoService.getBillInfo(billInfoVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 매출공통팝업 - 영수증상세 결제내역 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   billInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 01. 31.
     */
    @RequestMapping(value = "/billInfo/billPayInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBillPayInfo(HttpServletRequest request, HttpServletResponse response,
        Model model, BillInfoVO billInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = billInfoService.getBillPayInfo(billInfoVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 매출공통팝업 - 영수증상세 방문인원 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   billInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 01. 31.
     */
    @RequestMapping(value = "/billInfo/billGuestInfo.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBillGuestInfo(HttpServletRequest request, HttpServletResponse response,
        Model model, BillInfoVO billInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = billInfoService.getBillGuestInfo(billInfoVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }


    /**
     * 당일매출상세현황 - 매출종합 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   billInfoVO
     * @return  String
     * @author  안동관
     * @since   2019. 01. 31.
     */
    @RequestMapping(value = "/billInfo/billProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBillProdList(HttpServletRequest request, HttpServletResponse response,
        Model model, BillInfoVO billInfoVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = billInfoService.getBillProdList(billInfoVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, billInfoVO);
    }

}
