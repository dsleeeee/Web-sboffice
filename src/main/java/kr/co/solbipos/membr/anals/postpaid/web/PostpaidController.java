package kr.co.solbipos.membr.anals.postpaid.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.anals.postpaid.service.PostpaidService;
import kr.co.solbipos.membr.anals.taxBill.service.TaxBillVO;
import kr.co.solbipos.membr.anals.postpaid.service.PostpaidStoreVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : PostpaidController.java
 * @Description : 회원관리 > 회원분석 > 후불회원
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.20  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.09.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/membr/anals/postpaid/")
public class PostpaidController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final PostpaidService service;
    private final SessionService sessionService;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public PostpaidController(PostpaidService service, SessionService sessionService, CmmEnvUtil cmmEnvUtil) {
        this.service = service;
        this.sessionService = sessionService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * */
    @RequestMapping(value = "postpaid/postpaidView.sb", method = RequestMethod.GET)
    public String registList(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 회원등급 관리구분
        model.addAttribute("membrClassManageFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1237"), "1"));

        return "membr/anals/postpaid/postpaidView";
    }

    /**
     * 후불 회원 외상, 입금 내역
     *
     * @param postpaidStoreVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "postpaid/getPostpaidMemberList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPostpaidMemberList( PostpaidStoreVO postpaidStoreVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = service.getPostpaidMemberList(postpaidStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, postpaidStoreVO);
    }

    /**
     * 후불 회원 외상, 입금 내역
     *
     * @param postpaidStoreVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "postpaid/getPostpaidMemberListExcel.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPostpaidMemberListExcel( PostpaidStoreVO postpaidStoreVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = service.getPostpaidMemberListExcel(postpaidStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, postpaidStoreVO);
    }

    /**
     * 후불 대상 회원 조회
     *
     * @param postpaidStoreVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "deposit/getDepositMemberList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDepositMemberList(PostpaidStoreVO postpaidStoreVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = service.getDepositMemberList(postpaidStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, postpaidStoreVO);
    }

    /**
     * 외상입금
     * @param postpaidStoreVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "postpaid/saveDeposit.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDeposit(@RequestBody PostpaidStoreVO[] postpaidStoreVOs, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.saveDeposit(postpaidStoreVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
    /**
     * 외상입금
     * @param postpaidStoreVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "deposit/saveDeposit.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDeposit(@RequestBody PostpaidStoreVO postpaidStoreVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.saveDeposit(postpaidStoreVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 세금계산서 요청목록 조회
     *
     * @param taxBillVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "deposit/getTaxBillList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTaxBillList(TaxBillVO taxBillVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = service.getTaxBillList(taxBillVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, taxBillVO);
    }

    /**
     * 세금계산서 발행 입금
     * @param taxBillVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "deposit/saveTaxBillComplete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveTaxBillComplete(@RequestBody TaxBillVO taxBillVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.saveTaxBillComplete(taxBillVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}
