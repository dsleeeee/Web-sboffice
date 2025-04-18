package kr.co.solbipos.application.pos.exceptForward.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.exception.AuthenticationException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.validate.Login;
import kr.co.solbipos.application.pos.exceptForward.service.ExceptForwardService;
import kr.co.solbipos.application.pos.exceptForward.service.ExcpForwardProductVO;
import kr.co.solbipos.application.session.auth.service.AuthService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.info.service.ProductClassVO;
import kr.co.solbipos.base.prod.prod.service.ProdVO;
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
import java.util.List;

import static kr.co.common.utils.HttpUtils.getClientIp;
import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * @Class Name : ExecptForwardController.java
 * @Description : POS 화면에서 예외출고 등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.14  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 09.14
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserAved.
 */
@Controller
@RequestMapping(value = "/application/pos/excpForward/")
public class ExceptForwardController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ExceptForwardService exceptForwardService;
    private final SessionService sessionService;
    private final AuthService authService;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public ExceptForwardController(ExceptForwardService exceptForwardService,
        SessionService sessionService, AuthService authService, MessageService messageService) {
        this.exceptForwardService = exceptForwardService;
        this.sessionService = sessionService;
        this.authService = authService;
        this.messageService = messageService;

    }

    /**
     * 상품 목록 조회 화면
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "forwardTest.sb", method = RequestMethod.GET)
    public String forwardTest(HttpServletRequest request, HttpServletResponse response,
        Model model) {

        return "application/pos/excpForward/forwardTest";
    }

    /**
     * 상품 목록 조회 화면
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "excpForwardView.sb", method = RequestMethod.GET)
    public String excpForwardView(HttpServletRequest request, HttpServletResponse response,
                       Model model) {
        return "application/pos/excpForward/excpForwardView";

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

        if(!isEmpty(request.getParameter("hqOfficeCd")) && !isEmpty(request.getParameter("storeCd")) && !isEmpty(request.getParameter("hwAuthKey")) && !isEmpty(request.getParameter("url"))) {
            LOGGER.info("posLogin hqOfficeCd : {} , store : {} , hwAuthKey : {} , url : {}", request.getParameter("hqOfficeCd"), request.getParameter("storeCd"), request.getParameter("hwAuthKey"), request.getParameter("url"));

            sessionInfoVO.setLoginIp(getClientIp(request));
            sessionInfoVO.setBrwsrInfo(request.getHeader("User-Agent"));

            // 로그인 시도
            SessionInfoVO posSi = authService.posLogin(sessionInfoVO);
            // 세션 생성
            sessionService.setSessionInfo(request, response, posSi);

            returnUrl = "application/pos/excpForward/"+request.getParameter("url");
        }
        else {
            throw new AuthenticationException(messageService.get("login.pos.error"), "/error/application/pos/403.sb");
        }

        return returnUrl;
    }

    /**
     * 예외출고용 상품 분류 트리 조회
     *
     * @param prodVO ProdVO
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param model Model
     * @return
     * @author 노현수
     * @since 2018.11.12
     */
    @RequestMapping(value = "excpForward/getProdClassTree.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdClassTree(ProdVO prodVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<ProductClassVO> result = exceptForwardService.getProdClassTree(prodVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 예외출고 목록 조회
     * @param   productVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 08. 14.
     */
    @RequestMapping(value = "excpForward/getExcpForwardProduct.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getExcpForwardProduct(ExcpForwardProductVO productVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = exceptForwardService.getExcpForwardProduct(productVO, sessionInfoVO);

        return returnListJson(Status.OK, list, productVO);
    }

    /**
     * 예외출고 저장
     * @param   request
     * @param   response
     * @param   model
     * @param   productVO
     * @return  String
     * @author  김지은
     * @since   2018. 12. 17.
     */
    @RequestMapping(value = "excpForward/saveExcpForwardProduct.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveExcpForwardProduct(@RequestBody ExcpForwardProductVO productVO, HttpServletRequest request,
        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = exceptForwardService.saveExcpForwardProduct(productVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
