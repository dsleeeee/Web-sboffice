package kr.co.solbipos.base.store.memberTerms.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.memberTerms.service.MemberTermsService;
import kr.co.solbipos.base.store.memberTerms.service.MemberTermsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import java.io.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : MemberTermsComtroller.java
 * @Description : 기초관리 > 매장관리 > 회원약관관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.07.06  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.07.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/store/memberTerms")
public class MemberTermsComtroller {

    private final SessionService sessionService;
    private final MemberTermsService memberTermsService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MemberTermsComtroller(SessionService sessionService, MemberTermsService memberTermsService) {
        this.sessionService = sessionService;
        this.memberTermsService = memberTermsService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/memberTerms/list.sb", method = RequestMethod.GET)
    public String memberTermsView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "base/store/memberTerms/memberTerms";
    }

    /**
     * 회원약관관리 - 조회
     *
     * @param memberTermsVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 07. 06.
     */
    @RequestMapping(value = "/memberTerms/getMemberTermsList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMemberTermsList(MemberTermsVO memberTermsVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = memberTermsService.getMemberTermsList(memberTermsVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, memberTermsVO);
    }
}