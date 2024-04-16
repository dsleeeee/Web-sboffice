package kr.co.solbipos.base.price.hqSplyPriceHistory.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.price.hqSplyPriceHistory.service.HqSplyPriceHistoryService;
import kr.co.solbipos.base.price.hqSplyPriceHistory.service.HqSplyPriceHistoryVO;
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
 * @Class Name : HqSplyPriceHistoryController.java
 * @Description : 기초관리 - 가격관리 - 본사공급가History
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.12  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.04.12
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/price/hqSplyPriceHistory")
public class HqSplyPriceHistoryController {

    private final SessionService sessionService;
    private final HqSplyPriceHistoryService hqSplyPriceHistoryService;
    private final CmmEnvUtil cmmEnvUtil;
    private final DayProdService dayProdService;

    /** Constructor Injection */
    @Autowired
    public HqSplyPriceHistoryController(SessionService sessionService, HqSplyPriceHistoryService hqSplyPriceHistoryService, CmmEnvUtil cmmEnvUtil, DayProdService dayProdService){
        this.sessionService = sessionService;
        this.hqSplyPriceHistoryService = hqSplyPriceHistoryService;
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

        return "base/price/hqSplyPriceHistory/hqSplyPriceHistory";
    }

    /**
     * 본사 공급가 History 조회
     *
     * @param hqSplyPriceHistoryVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/getHqSplyPriceHistoryList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqSplyPriceHistoryList(HqSplyPriceHistoryVO hqSplyPriceHistoryVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = hqSplyPriceHistoryService.getHqSplyPriceHistoryList(hqSplyPriceHistoryVO, sessionInfoVO);

        return returnListJson(Status.OK, result, hqSplyPriceHistoryVO);
    }

    /**
     * 본사 공급가 History 엑셀다운로드 조회
     *
     * @param hqSplyPriceHistoryVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/getHqSplyPriceHistoryExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqSplyPriceHistoryExcelList(HqSplyPriceHistoryVO hqSplyPriceHistoryVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = hqSplyPriceHistoryService.getHqSplyPriceHistoryExcelList(hqSplyPriceHistoryVO, sessionInfoVO);

        return returnListJson(Status.OK, result, hqSplyPriceHistoryVO);
    }
}
