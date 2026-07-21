package kr.co.solbipos.sale.today.todayBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.day.day.service.DayVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.sale.today.todayDtl.service.TodayDtlService;
import kr.co.solbipos.sale.today.todayDtl.service.TodayDtlVO;
import kr.co.solbipos.sale.today.todayBenson.service.TodayBensonService;
import kr.co.solbipos.sale.today.todayBenson.service.TodayBensonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.spring.StringUtil.convertToJson;


/**
 * @Class Name : TodayBensonController.java
 * @Description : 벤슨 > 매출분석 > 당일매출현황(영수증)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.16  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.16
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/today/todayBenson")
public class TodayBensonController {

    private final SessionService sessionService;
    private final TodayBensonService todayBensonService;
    private final TodayDtlService todayDtlService;
    private final DayProdService dayProdService;
    private final DayService dayService;

    /**
     * Constructor Injection
     */
    @Autowired
    public TodayBensonController(SessionService sessionService, TodayBensonService todayBensonService, TodayDtlService todayDtlService, DayProdService dayProdService, DayService dayService) {
        this.sessionService = sessionService;
        this.todayBensonService = todayBensonService;
        this.todayDtlService = todayDtlService;
        this.dayProdService = dayProdService;
        this.dayService = dayService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/todayBenson/list.sb", method = RequestMethod.GET)
    public String todayBensonView(HttpServletRequest request, HttpServletResponse response, Model model) {

        TodayDtlVO todayDtlVO = new TodayDtlVO();
        DayVO dayVO = new DayVO();

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);
        System.out.println("momsHqBrandCdComboList : " + momsHqBrandCdComboList);

        // 결제수단 조회
        List<DefaultMap<String>> payColList = todayDtlService.getPayColList(todayDtlVO, sessionInfoVO);

        // 결제수단 코드를 , 로 연결하는 문자열 생성
        String payCol = "";
        for(int i=0; i < payColList.size(); i++) {
            payCol += (payCol.equals("") ? "" : ",") + payColList.get(i).getStr("payCd");
        }
        model.addAttribute("payColList", payColList);
        model.addAttribute("payCol", payCol);

        // 할인구분 조회
        List<DefaultMap<String>> dcColList = todayDtlService.getDcColList(todayDtlVO, sessionInfoVO);

        // 할인구분 코드를 , 로 연결하는 문자열 생성
        String dcCol = "";
        for(int i=0; i < dcColList.size(); i++) {
            dcCol += (dcCol.equals("") ? "" : ",") + dcColList.get(i).getStr("dcCd");
        }
        model.addAttribute("dcColList", dcColList);
        model.addAttribute("dcCol", dcCol);

        // 모바일페이상세 조회(현금영수증 포함)
        List<DefaultMap<String>> mpayColList = dayService.getMpayColList(dayVO, sessionInfoVO);

        // 모바일페이상세 코드를 , 로 연결하는 문자열 생성
        String mpayCol = "";
        for(int i=0; i < mpayColList.size(); i++) {
            mpayCol += (mpayCol.equals("") ? "" : ",") + mpayColList.get(i).getStr("mpayCd");
        }
        model.addAttribute("mpayColList", mpayColList);
        model.addAttribute("mpayCol", mpayCol);

        return "sale/today/todayBenson/todayBenson";
    }

    /**
     * 당일매출현황(영수증) 리스트 조회
     *
     * @param todayBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     */
    @RequestMapping(value = "/todayBenson/getTodayBensonList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTodayBensonList(TodayBensonVO todayBensonVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = todayBensonService.getTodayBensonList(todayBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, todayBensonVO);
    }

    /**
     * 당일매출현황(영수증) 엑셀 다운로드 조회
     *
     * @param todayBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     */
    @RequestMapping(value = "/todayBenson/getTodayBensonExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTodayBensonExcelList(TodayBensonVO todayBensonVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = todayBensonService.getTodayBensonExcelList(todayBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, todayBensonVO);
    }

    /**
     * 영수증조회팝업 - 영수증 출력데이터 조회
     *
     * @param todayBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     */
    @RequestMapping(value = "/todayBenson/getBillPrintData.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBillPrintData(TodayBensonVO todayBensonVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = todayBensonService.getBillPrintData(todayBensonVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }
}
