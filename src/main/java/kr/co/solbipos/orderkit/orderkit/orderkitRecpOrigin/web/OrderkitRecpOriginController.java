package kr.co.solbipos.orderkit.orderkit.orderkitRecpOrigin.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.orderkit.orderkit.orderkit.web.OrderkitController;
import kr.co.solbipos.orderkit.orderkit.orderkitRecpOrigin.service.OrderkitRecpOriginService;
import kr.co.solbipos.orderkit.orderkit.orderkitRecpOrigin.service.OrderkitRecpOriginVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name  : OrderkitRecpOriginController.java
 * @Description : 오더킷 > 오더킷 > 원산지 정보
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.11.05  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.11.05
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/orderkit/orderkit/orderkitRecpOrigin")
public class OrderkitRecpOriginController {

    private final SessionService sessionService;
    private final OrderkitRecpOriginService orderkitRecpOriginService;
    private final OrderkitController orderkitController;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructor Injection
     */
    @Autowired
    public OrderkitRecpOriginController(SessionService sessionService, OrderkitRecpOriginService orderkitRecpOriginService, OrderkitController orderkitController) {
        this.sessionService = sessionService;
        this.orderkitRecpOriginService = orderkitRecpOriginService;
        this.orderkitController = orderkitController;
    }

    /**
     * 페이지 이동
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "orderkit/orderkit/orderkitRecpOrigin/orderkitRecpOrigin";
    }

    /**
     * 원산지 정보 조회
     * @param request
     * @param response
     * @param model
     * @param orderkitRecpOriginVO
     * @return
     */
    @RequestMapping(value = "/getOrderkitRecpOrigin.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getOrderkitRecpOrigin(HttpServletRequest request, HttpServletResponse response, Model model, OrderkitRecpOriginVO orderkitRecpOriginVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = orderkitRecpOriginService.getOrderkitRecpOrigin(orderkitRecpOriginVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, orderkitRecpOriginVO);
    }

    /**
     * 오더킷 바로가기
     * @param request
     * @param response
     * @param model
     * @param orderkitRecpOriginVO
     * @return
     */
    @RequestMapping(value = "/orderkitGoto.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result orderkitGoto(HttpServletRequest request, HttpServletResponse response, Model model, OrderkitRecpOriginVO orderkitRecpOriginVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        String token = "";

        if (orderkitRecpOriginVO.getRedirectUrl() != null && !orderkitRecpOriginVO.getRedirectUrl().equals("")) {

            // JWT 토큰 생성
            token = orderkitController.createJWT2(sessionInfoVO, orderkitRecpOriginVO.getRedirectUrl());
            LOGGER.info("createJWT2 JWT2 Token: " + token);

            // JWT 토큰 파싱(확인용)
            orderkitController.parseJWT2(token);

        } else {

            // JWT 토큰 생성
            token = orderkitController.createJWT(sessionInfoVO);
            LOGGER.info("Crate JWT Token: " + token);

            // JWT 토큰 파싱(확인용)
            orderkitController.parseJWT(token);
        }

        return ReturnUtil.returnJson(Status.OK, token);
    }

}
