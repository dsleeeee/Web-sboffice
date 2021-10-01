package kr.co.solbipos.base.price.salePriceHistory.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.price.salePriceHistory.service.SalePriceHistoryVO;
import kr.co.solbipos.base.price.salePriceHistory.service.SalePriceHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : SalePriceHistoryController.java
 * @Description : 기초관리 - 가격관리 - 판매가변경이력(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.28  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2021.09.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/price/salePriceHistory")
public class SalePriceHistoryController {

    private final SessionService sessionService;
    private final SalePriceHistoryService salePriceHistoryService;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public SalePriceHistoryController(SessionService sessionService, SalePriceHistoryService salePriceHistoryService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.salePriceHistoryService = salePriceHistoryService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 판매가변경이력(매장)
     *
     * @param request
     * @param response
     * @param model
     * @return
     * @author  권지현
     * @since   2021.09.28
     */
    @RequestMapping(value = "/salePriceHistory/list.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        // 내점/배달/포장 가격관리 사용여부
        model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "0044") , "0"));

        return "base/price/salePriceHistory/salePriceHistory";
    }

    /***
     * 판매가변경이력 - 조회
     * @param salePriceHistoryVO
     * @param request
     * @return
     * @author  권지현
     * @since   2021.09.28
     */
    @RequestMapping(value = "/salePriceHistory/getSalePriceHistoryList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreSalePriceHistoryList(SalePriceHistoryVO salePriceHistoryVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = salePriceHistoryService.getSalePriceHistoryList(salePriceHistoryVO, sessionInfoVO);

        return returnListJson(Status.OK, result, salePriceHistoryVO);
    }

    /***
     * 판매가변경이력 - 엑셀다운로드
     * @param salePriceHistoryVO
     * @param request
     * @return
     * @author  권지현
     * @since   2021.09.13
     */
    @RequestMapping(value = "/salePriceHistory/getSalePriceHistoryExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreSalePriceHistoryExcelList(SalePriceHistoryVO salePriceHistoryVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = salePriceHistoryService.getSalePriceHistoryExcelList(salePriceHistoryVO, sessionInfoVO);

        return returnListJson(Status.OK, result, salePriceHistoryVO);
    }


}
