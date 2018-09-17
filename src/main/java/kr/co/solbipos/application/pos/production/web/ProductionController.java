package kr.co.solbipos.application.pos.production.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.exception.AuthenticationException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.validate.Login;
import kr.co.solbipos.application.pos.production.service.ProductionService;
import kr.co.solbipos.application.pos.production.service.ProductionVO;
import kr.co.solbipos.application.session.auth.service.AuthService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.HttpUtils.getClientIp;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * @Class Name : ProductionController.java
 * @Description : POS 화면에서 생산량 등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.16  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 09.16
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/application/pos/production/")
public class ProductionController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProductionService service;
    @Autowired
    SessionService sessionService;
    @Autowired
    AuthService authService;
    @Autowired
    MessageService messageService;


    /**
     * 조회 화면 전,
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "productionTest.sb", method = RequestMethod.GET)
    public String prodcutionTest(HttpServletRequest request, HttpServletResponse response,
        Model model) {

        return "application/pos/production/productionTest";
    }

    /**
     * 상품 목록 조회 화면
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "productionView.sb", method = RequestMethod.GET)
    public String productionView(HttpServletRequest request, HttpServletResponse response,
                       Model model) {

        LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>> productionView");

        return "application/pos/production/productionView";
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

        String returnUrl = "";

        if(!isEmpty(request.getParameter("storeCd")) && !isEmpty(request.getParameter("hwAuthKey")) && !isEmpty(request.getParameter("url"))) {
            LOGGER.info("posLogin store : {} , hwAuthKey : {} , url : {}", request.getParameter("storeCd"), request.getParameter("hwAuthKey"), request.getParameter("url"));

            sessionInfoVO.setLoginIp(getClientIp(request));
            sessionInfoVO.setBrwsrInfo(request.getHeader("User-Agent"));

            // 로그인 시도
            SessionInfoVO posSi = authService.posLogin(sessionInfoVO);
            // 세션 생성
            sessionService.setSessionInfo(request, response, posSi);

            returnUrl = "application/pos/production/"+request.getParameter("url");
        }
        else {
            throw new AuthenticationException(messageService.get("login.pos.error"), "/error/application/pos/403.sb");
        }

        return returnUrl;
    }

    /**
     * 생산량 대상 상품 목록 조회
     * @param   productionVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Result
     * @author  김지은
     * @since   2018. 08. 14.
     */
    @RequestMapping(value = "production/getProductList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProductList(ProductionVO productionVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        LOGGER.info(">>>>>>>>>>>>> getProductList");

        List<DefaultMap<String>> list = service.getProductList(productionVO, sessionInfoVO);

        LOGGER.info(">>>>>>> list.size() : " + list.size());

        return returnListJson(Status.OK, list);
    }

}
