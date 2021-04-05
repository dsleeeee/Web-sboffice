package kr.co.solbipos.application.common.web;

import kr.co.common.data.enums.CodeType;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.SessionUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.application.session.auth.enums.LoginFg;
import kr.co.solbipos.sys.cd.envconfg.service.EnvstVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Class Name : ErrorController.java
 * @Description : 에러 처리용 컨트롤러
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.04  노현수       부분수정
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/error")
public class ErrorController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SessionService sessionService;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public ErrorController(SessionService sessionService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    String ROOT_PATH = "";

    /** 에러페이지는 인터셉터를 수행하지 않으므로, 세션처리를 별도로 하여 가상로그인 사용시 에러를 방지한다. */
    private void setSessionInfo(HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = new SessionInfoVO();
        // 가상로그인 사용시에는 파라미터로 세션ID 달고 다니기 때문에 별도 체크로직 추가 : 20180817 노현수
        if ( request.getParameter("sid") != null && request.getParameter("sid").length() > 0 ) {
            // 세션 가져오기
            sessionInfoVO = SessionUtil.getEnv(request.getSession(), request.getParameter("sid"));
        } else {
            // 세션 가져오기
            sessionInfoVO = sessionService.getSessionInfo(request);
        }
        if ( sessionInfoVO != null && sessionInfoVO.getUserId() != null ) {
            // jsp > sessionscope 로 쓸수 있게 httpsession 에 세팅
            HttpSession session = request.getSession();
            session.setAttribute("sessionInfo", sessionInfoVO);
        }

        //
        ROOT_PATH = "";
        if(sessionInfoVO != null){
            if(sessionInfoVO.getLoginFg() != null && sessionInfoVO.getLoginFg().equals(LoginFg.MOBILE.getCode())){
                ROOT_PATH = "mobile/";
            }
        }else if(WebUtils.getCookie(request, BaseEnv.SB_LOGIN_FG) != null){
            if(WebUtils.getCookie(request, BaseEnv.SB_LOGIN_FG).getValue().equals(LoginFg.MOBILE.getCode())){
                ROOT_PATH = "mobile/";
            }
        }else if (request.getRequestURI().substring(0, 8).equals("/mobile/")){
            ROOT_PATH = "mobile/";
        }

    }

    @RequestMapping(value = "/403.sb")
    public String denied(HttpServletRequest request, HttpServletResponse response, Model model) {
        // 세션처리 수행 : 가상로그인 사용시 오류 방지
        setSessionInfo(request);

        return ROOT_PATH + "error/403";
    }

    @RequestMapping(value = "/application/pos/403.sb")
    public String posDenied(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "application/pos/403";
    }

    @RequestMapping(value = "/404.sb")
    public String notFound(HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
        // 세션처리 수행 : 가상로그인 사용시 오류 방지
        setSessionInfo(request);

        return ROOT_PATH + "error/404";
    }

    @RequestMapping(value = "/500.sb")
    public String serverError(HttpServletRequest request, HttpServletResponse response, Model model) {
        // 세션처리 수행 : 가상로그인 사용시 오류 방지
        setSessionInfo(request);

        return ROOT_PATH + "error/500";
    }

    /**
     * 환경변수 설정이 안됐을 경우
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/envError.sb")
    public String envError(HttpServletRequest request, HttpServletResponse response, Model model) {

        CodeType codeType =  CodeType.valueOf(request.getParameter("codeType"));
        String codeCd = request.getParameter("codeCd");
        String codeNm = "";
        String codeStr = "";

        // 본사 환경변수가 없을 경우
        if(codeType == CodeType.HQ_ENV ) {

            EnvstVO envstVO = new EnvstVO();
            envstVO.setEnvstCd(codeCd);

            codeNm = cmmEnvUtil.getEnvNm(envstVO);
            codeStr = "'[" + codeCd + "]" + codeNm + "'";
        }
        // 매장 환경변수가 없을 경우
        else if(codeType == CodeType.STORE_ENV) {

            EnvstVO envstVO = new EnvstVO();
            envstVO.setEnvstCd(codeCd);

            codeNm = cmmEnvUtil.getEnvNm(envstVO);
            codeStr = "'[" + codeCd + "]  " + codeNm + "'";
        }

        model.addAttribute("codeType", codeType);
        model.addAttribute("codeCd", codeCd);
        model.addAttribute("codeNm", codeNm);
        model.addAttribute("codeStr", codeStr);

        return "error/envError";
    }


}


