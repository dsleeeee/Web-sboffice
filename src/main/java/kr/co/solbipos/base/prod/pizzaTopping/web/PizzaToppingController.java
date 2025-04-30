package kr.co.solbipos.base.prod.pizzaTopping.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.pizzaTopping.service.PizzaToppingService;
import kr.co.solbipos.base.prod.pizzaTopping.service.PizzaToppingVO;
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

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : PizzaToppingController.java
 * @Description : 미스터피자 > 상품관리 > 피자-토핑관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.04.28  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.04.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/prod/pizzaTopping")
public class PizzaToppingController {

    private final SessionService sessionService;
    private final PizzaToppingService pizzaToppingService;

    /**
     * Constructor Injection
     */
    @Autowired
    public PizzaToppingController(SessionService sessionService, PizzaToppingService pizzaToppingService) {
        this.sessionService = sessionService;
        this.pizzaToppingService = pizzaToppingService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/pizzaTopping/list.sb", method = RequestMethod.GET)
    public String pizzaToppingView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "base/prod/pizzaTopping/pizzaTopping";
    }

    /**
     * 피자-토핑관리 - 조회
     *
     * @param pizzaToppingVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2025. 04. 28.
     */
    @RequestMapping(value = "/pizzaTopping/getPizzaToppingList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPizzaToppingList(PizzaToppingVO pizzaToppingVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = pizzaToppingService.getPizzaToppingList(pizzaToppingVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, pizzaToppingVO);
    }

    /**
     * 피자-토핑관리 - 등록 상품 조회
     *
     * @param pizzaToppingVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2025. 04. 28.
     */
    @RequestMapping(value = "/pizzaTopping/getPizzaToppingProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPizzaToppingProdList(PizzaToppingVO pizzaToppingVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = pizzaToppingService.getPizzaToppingProdList(pizzaToppingVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, pizzaToppingVO);
    }

    /**
     * 피자-토핑관리 - 미등록 상품 조회
     *
     * @param pizzaToppingVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2025. 04. 28.
     */
    @RequestMapping(value = "/pizzaTopping/getPizzaToppingNoProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPizzaToppingNoProdList(PizzaToppingVO pizzaToppingVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = pizzaToppingService.getPizzaToppingNoProdList(pizzaToppingVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, pizzaToppingVO);
    }

    /**
     * 피자-토핑관리 - 상품 저장
     *
     * @param pizzaToppingVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2025. 04. 28.
     */
    @RequestMapping(value = "/pizzaTopping/getPizzaToppingProdSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPizzaToppingProdSave(@RequestBody PizzaToppingVO[] pizzaToppingVOs, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = pizzaToppingService.getPizzaToppingProdSave(pizzaToppingVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}