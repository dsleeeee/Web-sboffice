package kr.co.solbipos.membr.anals.postpaid.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.anals.postpaid.service.PostpaidService;
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

    /** Constructor Injection */
    @Autowired
    public PostpaidController(PostpaidService service, SessionService sessionService) {
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
    @RequestMapping(value = "postpaid/postpaidView.sb", method = RequestMethod.GET)
    public String registList(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

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
    public Result getPostpaidMemberList( kr.co.solbipos.membr.anals.postpaid.service.PostpaidStoreVO postpaidStoreVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = service.getPostpaidMemberList(postpaidStoreVO, sessionInfoVO);

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
    public Result getDepositMemberList(
        kr.co.solbipos.membr.anals.postpaid.service.PostpaidStoreVO postpaidStoreVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = service.getDepositMemberList(postpaidStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, postpaidStoreVO);
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
    public Result saveDeposit(@RequestBody
        kr.co.solbipos.membr.anals.postpaid.service.PostpaidStoreVO postpaidStoreVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = 0;

        try{
            result = service.saveDeposit(postpaidStoreVO, sessionInfoVO);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return returnJson(Status.OK, result);
    }

}
