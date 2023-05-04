package kr.co.solbipos.sale.status.nonSaleCard.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.nonSaleCard.service.NonSaleCardService;
import kr.co.solbipos.sale.status.nonSaleCard.service.NonSaleCardVO;
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
 * @Class Name : NonSaleCardController.java
 * @Description : 매출관리 > 승인현황 > 비매출카드상세
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.05.03  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.05.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/status/nonSaleCard")
public class NonSaleCardController {

    private final SessionService sessionService;
    private final NonSaleCardService nonSaleCardService;

    /**
     * Constructor Injection
     */
    @Autowired
    public NonSaleCardController(SessionService sessionService, NonSaleCardService nonSaleCardService) {
        this.sessionService = sessionService;
        this.nonSaleCardService = nonSaleCardService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/nonSaleCard/list.sb", method = RequestMethod.GET)
    public String nonSaleCardView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/status/nonSaleCard/nonSaleCard";
    }

    /**
     * 비매출카드상세 - 조회
     *
     * @param nonSaleCardVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2023. 05. 03.
     */
    @RequestMapping(value = "/nonSaleCard/getNonSaleCardList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNonSaleCardList(NonSaleCardVO nonSaleCardVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = nonSaleCardService.getNonSaleCardList(nonSaleCardVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, nonSaleCardVO);
    }
}