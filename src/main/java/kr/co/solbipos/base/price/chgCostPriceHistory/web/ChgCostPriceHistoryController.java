package kr.co.solbipos.base.price.chgCostPriceHistory.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.price.chgCostPriceHistory.service.ChgCostPriceHistoryService;
import kr.co.solbipos.base.price.chgCostPriceHistory.service.ChgCostPriceHistoryVO;
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
 * @Class Name : ChgCostPriceHistoryController.java
 * @Description : 기초관리 - 가격관리 - 원가변경History
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.05.14  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.05.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/price/chgCostPriceHistory")
public class ChgCostPriceHistoryController {

    private final SessionService sessionService;
    private final ChgCostPriceHistoryService chgCostPriceHistoryService;

    /** Constructor Injection */
    @Autowired
    public ChgCostPriceHistoryController(SessionService sessionService, ChgCostPriceHistoryService chgCostPriceHistoryService){
        this.sessionService = sessionService;
        this.chgCostPriceHistoryService = chgCostPriceHistoryService;
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

        return "base/price/chgCostPriceHistory/chgCostPriceHistory";
    }

    /**
     * 원가변경History 조회
     *
     * @param chgCostPriceHistoryVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2024.05.14
     */
    @RequestMapping(value = "/getChgCostPriceHistoryList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getChgCostPriceHistoryList(ChgCostPriceHistoryVO chgCostPriceHistoryVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = chgCostPriceHistoryService.getChgCostPriceHistoryList(chgCostPriceHistoryVO, sessionInfoVO);

        return returnListJson(Status.OK, result, chgCostPriceHistoryVO);
    }

    /**
     * 원가변경History 엑셀다운로드 조회
     *
     * @param chgCostPriceHistoryVO
     * @param request
     * @return
     * @author  이다솜
     * @since   2024.05.14
     */
    @RequestMapping(value = "/getChgCostPriceHistoryExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getChgCostPriceHistoryExcelList(ChgCostPriceHistoryVO chgCostPriceHistoryVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = chgCostPriceHistoryService.getChgCostPriceHistoryExcelList(chgCostPriceHistoryVO, sessionInfoVO);

        return returnListJson(Status.OK, result, chgCostPriceHistoryVO);
    }

}
