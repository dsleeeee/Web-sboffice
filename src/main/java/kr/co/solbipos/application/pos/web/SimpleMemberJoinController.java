package kr.co.solbipos.application.pos.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.exception.AuthenticationException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.validate.Login;
import kr.co.solbipos.application.pos.service.MemberVO;
import kr.co.solbipos.application.pos.service.SimpleMemberJoinService;
import kr.co.solbipos.application.session.auth.service.AuthService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static kr.co.common.utils.HttpUtils.getClientIp;
import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * @Class Name : SimpleMemberJoinController.java
 * @Description : POS 화면에서 간편 회원가입
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.14  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 08.14
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/application/pos/")
public class SimpleMemberJoinController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SimpleMemberJoinService service;
    @Autowired
    SessionService sessionService;
    @Autowired
    CmmCodeUtil cmmCodeUtil;
    @Autowired
    AuthService authService;
    @Autowired
    MessageService messageService;

    /** 화면이동 테스트
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "smjtest.sb", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response,
                       Model model) {

        System.out.println("들어옴??");

        return "application/pos/smjTest";

    }

    /**
     * TODO
     * POS 화면에서 띄우는건 메뉴등이 필요없기때문에 tiles 에 예외등록해두고 우선은 해당 컨트롤러에서 STORE_CD 와 HW_AUTH_KEY 로 인증체크 후 세션을 맺도록 함.
     * POS 화면 웹 로그인
     * @param sessionInfoVO
     * @param bindingResult
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "posLogin.sb", method = RequestMethod.POST)
    public String posLoginProcess(@Validated(Login.class) SessionInfoVO sessionInfoVO,
                                  BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response,
                                  Model model) {

        LOGGER.info("posLogin start");

        String returnUrl = "";

        //if(!isEmpty(sessionInfoVO.getHwAuthKey())) {
        if(!isEmpty(request.getParameter("storeCd")) && !isEmpty(request.getParameter("hwAuthKey")) && !isEmpty(request.getParameter("url"))) {
            LOGGER.info("posLogin store : {} , hwAuthKey : {} , url : {}", request.getParameter("storeCd"), request.getParameter("hwAuthKey"), request.getParameter("url"));

            sessionInfoVO.setLoginIp(getClientIp(request));
            sessionInfoVO.setBrwsrInfo(request.getHeader("User-Agent"));

            // 로그인 시도
            SessionInfoVO posSi = authService.posLogin(sessionInfoVO);
            // 세션 생성
            sessionService.setSessionInfo(request, response, posSi);

            returnUrl = "application/pos/"+request.getParameter("url");
        }
        else {
            throw new AuthenticationException(messageService.get("login.pos.error"), "/error/application/pos/403.sb");
        }

        return returnUrl;
    }

    /**
     * 회원 등록
     * @param   memberVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  안동관
     * @since   2018. 08. 14.
     */
    @RequestMapping(value = "simpleMemberJoin/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody MemberVO memberVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.save(memberVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }


}
