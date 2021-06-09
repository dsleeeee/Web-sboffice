package kr.co.solbipos.application.pos.posKitchenPrint.web;

import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.base.prod.touchkey.service.TouchKeyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Class Name : PosKitchenPrint.java
 * @Description : POS 화면에서 주방프린터상품연결
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.07  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.06.07
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/application/pos/posKitchenPrint/")
public class PosKitchenPrintController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * 조회 화면 전,
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "posKitchenPrintTest.sb", method = RequestMethod.GET)
    public String posTouchKeyTestView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "application/pos/posKitchenPrint/posKitchenPrintTest";
    }

    /**
     * 판매터치키 화면
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "posKitchenPrint.sb", method = RequestMethod.GET)
    public String posTouchKeydView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "application/pos/posKitchenPrint/posKitchenPrint";
    }

}