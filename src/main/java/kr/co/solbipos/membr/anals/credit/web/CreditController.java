package kr.co.solbipos.membr.anals.credit.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.anals.credit.service.CreditService;
import kr.co.solbipos.membr.anals.credit.service.CreditStoreVO;
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
 * @Class Name : CreditController.java
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
@RequestMapping(value = "/membr/anals/credit/")
public class CreditController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final CreditService service;
    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public CreditController(CreditService service, SessionService sessionService) {
        this.service = service;
        this.sessionService = sessionService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * */
    @RequestMapping(value = "credit/creditView.sb", method = RequestMethod.GET)
    public String registList(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "membr/anals/credit/creditView";
    }

    /**
     * 후불 회원 외상, 입금 내역
     *
     * @param creditStoreVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "credit/getCreditMemberList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCreditMemberList( CreditStoreVO creditStoreVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = service.getCreditMemberList(creditStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, creditStoreVO);
    }

    /**
     * 후불 대상 회원 조회
     *
     * @param creditStoreVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "deposit/getDepositMemberList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDepositMemberList(CreditStoreVO creditStoreVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = service.getDepositMemberList(creditStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, creditStoreVO);
    }

    /**
     * 외상입금
     * @param creditStoreVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "deposit/saveDeposit.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveDeposit(@RequestBody CreditStoreVO creditStoreVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = 0;

        try{
            result = service.saveDeposit(creditStoreVO, sessionInfoVO);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return returnJson(Status.OK, result);
    }

}
