package kr.co.solbipos.base.prod.pizzaEdge.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.pizzaEdge.service.PizzaEdgeService;
import kr.co.solbipos.base.prod.pizzaEdge.service.PizzaEdgeVO;
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
 * @Class Name : PizzaEdgeController.java
 * @Description : 미스터피자 > 상품관리 > 피자-엣지관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.04.25  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.04.18
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/prod/pizzaEdge")
public class PizzaEdgeController {

    private final SessionService sessionService;
    private final PizzaEdgeService pizzaEdgeService;

    @Autowired
    public PizzaEdgeController(SessionService sessionService, PizzaEdgeService pizzaEdgeService) {
        this.sessionService = sessionService;
        this.pizzaEdgeService = pizzaEdgeService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/pizzaEdge/list.sb", method = RequestMethod.GET)
    public String cashBillView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "base/prod/pizzaEdge/pizzaEdge";
    }

    /**
     * 피자-엣지관리 - 피자 조회
     *
     * @param   pizzaEdgeVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 04. 24.
     */
    @RequestMapping(value = "/pizzaEdge/getSearchPizzaList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchPizzaList(PizzaEdgeVO pizzaEdgeVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = pizzaEdgeService.getSearchPizzaList(pizzaEdgeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, pizzaEdgeVO);
    }

    /**
     * 피자-엣지관리 - 등록상품 조회
     *
     * @param   pizzaEdgeVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 04. 24.
     */
    @RequestMapping(value = "/pizzaEdge/getSearchPizzaMappList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchPizzaMappList(PizzaEdgeVO pizzaEdgeVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = pizzaEdgeService.getSearchPizzaMappList(pizzaEdgeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, pizzaEdgeVO);
    }

    /**
     * 피자-엣지관리 - 미등록상품 조회
     *
     * @param   pizzaEdgeVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 04. 24.
     */
    @RequestMapping(value = "/pizzaEdge/getSearchNoRegProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSearchNoRegProdList(PizzaEdgeVO pizzaEdgeVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = pizzaEdgeService.getSearchNoRegProdList(pizzaEdgeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, pizzaEdgeVO);
    }

    /**
     * 피자-엣지관리 - 등록상품 삭제
     *
     * @param   pizzaEdgeVOS
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 04. 24.
     */
    @RequestMapping(value = "/pizzaEdge/getDeleteProd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDeleteProd(@RequestBody PizzaEdgeVO[] pizzaEdgeVOS, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = pizzaEdgeService.getDeleteProd(pizzaEdgeVOS, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 피자-엣지관리 - 상품 등록
     *
     * @param   pizzaEdgeVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 04. 24.
     */
    @RequestMapping(value = "/pizzaEdge/getRegProd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRegProd(@RequestBody PizzaEdgeVO[] pizzaEdgeVOs, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = pizzaEdgeService.getRegProd(pizzaEdgeVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
