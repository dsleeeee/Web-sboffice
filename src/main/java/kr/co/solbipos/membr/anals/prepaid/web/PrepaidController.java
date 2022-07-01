package kr.co.solbipos.membr.anals.prepaid.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.membr.anals.prepaid.service.PrepaidService;
import kr.co.solbipos.membr.anals.prepaid.service.PrepaidStoreVO;
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
 * @Class Name : PrepaidController.java
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
@RequestMapping(value = "/membr/anals/prepaid/")
public class PrepaidController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final PrepaidService service;
    private final SessionService sessionService;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public PrepaidController(PrepaidService service, SessionService sessionService, CmmEnvUtil cmmEnvUtil) {
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
    @RequestMapping(value = "prepaid/prepaidView.sb", method = RequestMethod.GET)
    public String registList(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 회원등급 관리구분
        model.addAttribute("membrClassManageFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1237"), "1"));

        return "membr/anals/prepaid/prepaidView";
    }

    /**
     * 선불 회원 충전, 사용 내역
     *
     * @param prepaidStoreVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "prepaid/getPrepaidMemberList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPrepaidMemberList( PrepaidStoreVO prepaidStoreVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = service.getPrepaidMemberList(prepaidStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prepaidStoreVO);
    }

    /**
     * 선불 회원 충전, 사용 내역(엑셀)
     *
     * @param prepaidStoreVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "prepaid/getPrepaidMemberListExcel.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPrepaidMemberListExcel( PrepaidStoreVO prepaidStoreVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = service.getPrepaidMemberListExcel(prepaidStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prepaidStoreVO);
    }


    /**
     * 선불충전
     * @param prepaidStoreVOs
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "prepaid/saveChargeAmt.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveChargeAmt(@RequestBody PrepaidStoreVO[] prepaidStoreVOs, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.saveChargeAmt(prepaidStoreVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 후불 대상 회원 조회
     *
     * @param prepaidStoreVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "charge/getChargeMemberList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getChargeMemberList(PrepaidStoreVO prepaidStoreVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = service.getChargeMemberList(prepaidStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, prepaidStoreVO);
    }

    /**
     * 선불충전
     * @param prepaidStoreVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "charge/saveChargeAmt.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveChargeAmt(@RequestBody PrepaidStoreVO prepaidStoreVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.saveChargeAmt(prepaidStoreVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}
