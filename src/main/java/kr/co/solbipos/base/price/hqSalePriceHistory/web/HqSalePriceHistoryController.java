package kr.co.solbipos.base.price.hqSalePriceHistory.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.price.hqSalePriceHistory.service.HqSalePriceHistoryService;
import kr.co.solbipos.base.price.hqSalePriceHistory.service.HqSalePriceHistoryVO;
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
 * @Class Name : HqSalePriceHistoryController.java
 * @Description : 기초관리 - 가격관리 - 판매가변경이력(본사)
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
@RequestMapping(value = "/base/price/hqSalePriceHistory")
public class HqSalePriceHistoryController {

    private final SessionService sessionService;
    private final HqSalePriceHistoryService hqSalePriceHistoryService;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public HqSalePriceHistoryController(SessionService sessionService, HqSalePriceHistoryService hqSalePriceHistoryService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.hqSalePriceHistoryService = hqSalePriceHistoryService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 판매가변경이력(본사)
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
        model.addAttribute("subPriceFg", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "0044"), "0"));

        return "base/price/hqSalePriceHistory/salePriceHistory";
    }

    /***
     * 매장판매가변경이력 - 조회
     * @param hqSalePriceHistoryVO
     * @param request
     * @return
     * @author  권지현
     * @since   2021.09.28
     */
    @RequestMapping(value = "/storeSalePriceHistory/getStoreSalePriceHistoryList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreSalePriceHistoryList(HqSalePriceHistoryVO hqSalePriceHistoryVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = hqSalePriceHistoryService.getStoreSalePriceHistoryList(hqSalePriceHistoryVO, sessionInfoVO);

        return returnListJson(Status.OK, result, hqSalePriceHistoryVO);
    }

    /***
     * 매장판매가변경이력 - 엑셀다운로드
     * @param hqSalePriceHistoryVO
     * @param request
     * @return
     * @author  권지현
     * @since   2021.09.13
     */
    @RequestMapping(value = "/storeSalePriceHistory/getStoreSalePriceHistoryExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreSalePriceHistoryExcelList(HqSalePriceHistoryVO hqSalePriceHistoryVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = hqSalePriceHistoryService.getStoreSalePriceHistoryExcelList(hqSalePriceHistoryVO, sessionInfoVO);

        return returnListJson(Status.OK, result, hqSalePriceHistoryVO);
    }


    /***
     * 본사판매가변경이력 - 조회
     * @param hqSalePriceHistoryVO
     * @param request
     * @return
     * @author  권지현
     * @since   2021.09.28
     */
    @RequestMapping(value = "/hqSalePriceHistory/getHqSalePriceHistoryList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqSalePriceHistoryList(HqSalePriceHistoryVO hqSalePriceHistoryVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = hqSalePriceHistoryService.getHqSalePriceHistoryList(hqSalePriceHistoryVO, sessionInfoVO);

        return returnListJson(Status.OK, result, hqSalePriceHistoryVO);
    }

    /***
     * 본사판매가변경이력 - 엑셀다운로드
     * @param hqSalePriceHistoryVO
     * @param request
     * @return
     * @author  권지현
     * @since   2021.09.13
     */
    @RequestMapping(value = "/hqSalePriceHistory/getHqSalePriceHistoryExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getHqSalePriceHistoryExcelList(HqSalePriceHistoryVO hqSalePriceHistoryVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = hqSalePriceHistoryService.getHqSalePriceHistoryExcelList(hqSalePriceHistoryVO, sessionInfoVO);

        return returnListJson(Status.OK, result, hqSalePriceHistoryVO);
    }

}
