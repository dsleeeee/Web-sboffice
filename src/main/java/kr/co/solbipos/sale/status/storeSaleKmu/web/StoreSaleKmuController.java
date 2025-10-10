package kr.co.solbipos.sale.status.storeSaleKmu.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.status.storeSaleKmu.service.StoreSaleKmuService;
import kr.co.solbipos.sale.status.storeSaleKmu.service.StoreSaleKmuVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : StoreSaleKmuController.java
 * @Description : 국민대 > 매출관리 > 점소별매출일보 (국민대)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.01  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.10.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/status/storeSaleKmu")
public class StoreSaleKmuController {

    private final SessionService sessionService;
    private final StoreSaleKmuService storeSaleKmuService;

    /**
     * Constructor Injection
     */
    @Autowired
    public StoreSaleKmuController(SessionService sessionService, StoreSaleKmuService storeSaleKmuService) {
        this.sessionService = sessionService;
        this.storeSaleKmuService = storeSaleKmuService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/storeSaleKmu/list.sb", method = RequestMethod.GET)
    public String storeSaleKmuView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/status/storeSaleKmu/storeSaleKmu";
    }

    /**
     * 점소별매출일보 - 조회
     *
     * @param storeSaleKmuVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2025. 10. 01.
     */
    @RequestMapping(value = "/storeSaleKmu/getStoreSaleKmuList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreSaleKmuList(StoreSaleKmuVO storeSaleKmuVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = storeSaleKmuService.getStoreSaleKmuList(storeSaleKmuVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, storeSaleKmuVO);
    }
}