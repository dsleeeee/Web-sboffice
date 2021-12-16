package kr.co.solbipos.sale.status.saleAnalysisReport.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.saleAnalysisReport.service.SaleAnalysisReportService;
import kr.co.solbipos.sale.status.saleAnalysisReport.service.SaleAnalysisReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : SaleAnalysisReportController.java
 * @Description : 매출관리 > 매출현황2 > 중분류(매출분석) 다운로드(정직유부)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.12.14  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.12.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/status/saleAnalysisReport")
public class SaleAnalysisReportController {

    private final SessionService sessionService;
    private final SaleAnalysisReportService saleAnalysisReportService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SaleAnalysisReportController(SessionService sessionService, SaleAnalysisReportService saleAnalysisReportService) {
        this.sessionService = sessionService;
        this.saleAnalysisReportService = saleAnalysisReportService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/saleAnalysisReport/list.sb", method = RequestMethod.GET)
    public String saleAnalysisReportView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        SaleAnalysisReportVO saleAnalysisReportVO = new SaleAnalysisReportVO();

        // 매장 조회
        List<DefaultMap<String>> storeColList = saleAnalysisReportService.getStorerColList(saleAnalysisReportVO, sessionInfoVO);

        // 매장을 , 로 연결하는 문자열 생성
        String storeCol = "";
        for(int i=0; i < storeColList.size(); i++) {
            storeCol += (storeCol.equals("") ? "" : ",") + storeColList.get(i).getStr("storeCd");
        }
        model.addAttribute("storeColList", storeColList);
        model.addAttribute("storeCol", storeCol);
//        System.out.println("storeColList : "+storeColList);
//        System.out.println("storeCol : "+storeCol);

        return "sale/status/saleAnalysisReport/saleAnalysisReport";
    }

    /**
     * 중분류(매출분석) 다운로드 - 조회
     *
     * @param saleAnalysisReportVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 12. 14.
     */
    @RequestMapping(value = "/saleAnalysisReport/getSaleAnalysisReportList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleAnalysisReportList(SaleAnalysisReportVO saleAnalysisReportVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = saleAnalysisReportService.getSaleAnalysisReportList(saleAnalysisReportVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, saleAnalysisReportVO);
    }

    /**
     * 중분류(매출분석) 다운로드 - 조회된 매장 리스트
     *
     * @param saleAnalysisReportVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 12. 14.
     */
    @RequestMapping(value = "/saleAnalysisReport/getSaleAnalysisReportStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleAnalysisReportStoreList(SaleAnalysisReportVO saleAnalysisReportVO, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = saleAnalysisReportService.getSaleAnalysisReportStoreList(saleAnalysisReportVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }
}