package kr.co.solbipos.base.price.storeChgCostPriceHistory.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.price.chgCostPriceHistory.service.ChgCostPriceHistoryVO;
import kr.co.solbipos.base.price.storeChgCostPriceHistory.service.StoreChgCostPriceHistoryService;
import kr.co.solbipos.base.price.storeChgCostPriceHistory.service.StoreChgCostPriceHistoryVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
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
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : StoreChgCostPriceHistoryController.java
 * @Description : 기초관리 - 가격관리 - 매장원가변경History
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.05.24  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.05.24
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/price/storeChgCostPriceHistory")
public class StoreChgCostPriceHistoryController {
    private final SessionService sessionService;
    private final StoreChgCostPriceHistoryService storeChgCostPriceHistoryService;
    private final CmmEnvUtil cmmEnvUtil;
    private final DayProdService dayProdService;

    /** Constructor Injection */
    @Autowired
    public StoreChgCostPriceHistoryController(SessionService sessionService, StoreChgCostPriceHistoryService storeChgCostPriceHistoryService, CmmEnvUtil cmmEnvUtil, DayProdService dayProdService) {
        this.sessionService = sessionService;
        this.storeChgCostPriceHistoryService = storeChgCostPriceHistoryService;
        this.cmmEnvUtil = cmmEnvUtil;
        this.dayProdService = dayProdService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 브랜드사용여부
        model.addAttribute("brandUseFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1114"), "0"));
        // 사용자별 브랜드 콤보박스 조회
        DayProdVO dayProdVO = new DayProdVO();
        model.addAttribute("userHqBrandCdComboList", convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO)));

        return "base/price/storeChgCostPriceHistory/storeChgCostPriceHistory";
    }

    /**
     * 매장원가변경History 조회
     *
     * @param storechgCostPriceHistoryVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2024.05.27
     */
    @RequestMapping(value = "/getStoreChgCostPriceHistoryList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreChgCostPriceHistoryList(StoreChgCostPriceHistoryVO storechgCostPriceHistoryVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = storeChgCostPriceHistoryService.getStoreChgCostPriceHistoryList(storechgCostPriceHistoryVO, sessionInfoVO);

        return returnListJson(Status.OK, result, storechgCostPriceHistoryVO);
    }

    /**
     * 매장원가변경History 엑셀다운로드 조회
     *
     * @param storechgCostPriceHistoryVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2024.05.27
     */
    @RequestMapping(value = "/getStoreChgCostPriceHistoryExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreChgCostPriceHistoryExcelList(StoreChgCostPriceHistoryVO storechgCostPriceHistoryVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = storeChgCostPriceHistoryService.getStoreChgCostPriceHistoryExcelList(storechgCostPriceHistoryVO, sessionInfoVO);

        return returnListJson(Status.OK, result, storechgCostPriceHistoryVO);
    }

}
