package kr.co.solbipos.membr.info.memberFg.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.membr.info.memberFg.service.MemberFgService;
import kr.co.solbipos.membr.info.memberFg.service.MemberFgVO;
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

/**
 * @Class Name : MemberFgController.java
 * @Description : 회원정보 - 회원정보 - 선불/후불 회원 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.12  권지현       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.05.12
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/membr/info/memberFg")
public class MemberFgController {

    private final SessionService sessionService;
    private final MemberFgService memberFgService;
    private final CmmCodeUtil cmmCodeUtil;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public MemberFgController(SessionService sessionService, MemberFgService memberFgService, CmmCodeUtil cmmCodeUtil, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.memberFgService = memberFgService;
        this.cmmCodeUtil = cmmCodeUtil;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 선불/후불 회원 관리
     *
     * @param request
     * @param response
     * @param model
     * @return
     * @author
     * @since
     */
    @RequestMapping(value = "/memberFg/list.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        // 회원등급 리스트 조회
        List membrClassList = memberFgService.getMembrClassList(sessionInfoVO);

        String membrClassListAll = cmmCodeUtil.assmblObj(membrClassList, "name", "value", UseYn.ALL);
        String membrClassListSelect = cmmCodeUtil.assmblObj(membrClassList, "name", "value", UseYn.N);

        // 본사일 경우 해당 본사의 기본매장(코드)을 조회 해야 함.
        // [보나비]의 경우 기본매장코드를 사용하여
        // 회원등록 매장이 기본매장일 경우 후불회원 적용매장을 등록한다.
        String defaultStoreCd = "";
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            defaultStoreCd = StringUtil.getOrBlank(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0025"));
            defaultStoreCd.replace("*", "");
        }

        model.addAttribute("memberClassList", membrClassListAll);
        model.addAttribute("memberClassSelect", membrClassListSelect);
        model.addAttribute("defaultStoreCd", defaultStoreCd);

        // 회원등급 관리구분
        model.addAttribute("membrClassManageFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1237"), "1"));

        return "membr/info/memberFg/memberFg";
    }

    /**
     * 선불 회원 조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     * @author
     * @since
     */
    @RequestMapping(value = "/memberFg/getMemberPrepaid.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMemberPrepaid(MemberFgVO memberFgVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> result = memberFgService.getMemberPrepaid(memberFgVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, memberFgVO);
    }

    /**
     * 미선불 회원 조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     * @author
     * @since
     */
    @RequestMapping(value = "/memberFg/getMemberNoPrepaid.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMemberNoPrepaid(MemberFgVO memberFgVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> result = memberFgService.getMemberNoPrepaid(memberFgVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, memberFgVO);
    }

    /**
     * 후불 회원 조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     * @author
     * @since
     */
    @RequestMapping(value = "/memberFg/getMemberPostpaid.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMemberPostpaid(MemberFgVO memberFgVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> result = memberFgService.getMemberPostpaid(memberFgVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, memberFgVO);
    }

    /**
     * 미후불 회원 조회
     *
     * @param request
     * @param response
     * @param model
     * @return
     * @author
     * @since
     */
    @RequestMapping(value = "/memberFg/getMemberNoPostpaid.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMemberNoPostpaid(MemberFgVO memberFgVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> result = memberFgService.getMemberNoPostpaid(memberFgVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, memberFgVO);
    }

    /***
     * 선불 회원 등록
     * @param memberFgVO[]
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/memberFg/regPrepaid.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result regPrepaid(@RequestBody MemberFgVO[] memberFgVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int result = memberFgService.regPrepaid(memberFgVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

    /***
     * 후불불 회원등록
     * @param memberFgVO[]
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/memberFg/regPostpaid.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result regPostpaid(@RequestBody MemberFgVO[] memberFgVOs, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        int result = memberFgService.regPostpaid(memberFgVOs, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}