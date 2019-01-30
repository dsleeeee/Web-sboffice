package kr.co.solbipos.membr.anals.taxBill.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.anals.taxBill.service.TaxBillService;
import kr.co.solbipos.membr.anals.taxBill.service.TaxBillVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @Class Name : TaxBillController.java
 * @Description : 회원관리 > 회원분석 > 세금계산서 발행 목록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.13  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.12.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/membr/anals/taxBill/")
public class TaxBillController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final TaxBillService taxBillService;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public TaxBillController(TaxBillService taxBillService, SessionService sessionService) {
        this.taxBillService = taxBillService;
        this.sessionService = sessionService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * */
    @RequestMapping(value = "taxBill/taxBillView.sb", method = RequestMethod.GET)
    public String registList(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "membr/anals/taxBill/taxBillView";
    }

    /**
     * 세금계산서 발행 요청 목록
     *
     * @param taxBillVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "taxBill/getTaxBillRequestList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTaxBillRequestList( TaxBillVO taxBillVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = taxBillService.getTaxBillRequestList(taxBillVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, taxBillVO);
    }
}
