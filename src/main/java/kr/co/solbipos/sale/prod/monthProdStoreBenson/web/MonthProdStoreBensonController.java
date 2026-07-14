package kr.co.solbipos.sale.prod.monthProdStoreBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.sale.prod.monthProdStoreBenson.service.MonthProdStoreBensonService;
import kr.co.solbipos.sale.prod.monthProdStoreBenson.service.MonthProdStoreBensonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : MonthProdStoreBensonController.java
 * @Description : 벤슨 > 상품매출분석 > 월별상품매출현황(매장별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.08  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.08
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/prod/monthProdStoreBenson")
public class MonthProdStoreBensonController {

    private final SessionService sessionService;
    private final MonthProdStoreBensonService monthProdStoreBensonService;
    private final DayProdService dayProdService;

    @Autowired
    public MonthProdStoreBensonController(SessionService sessionService, MonthProdStoreBensonService monthProdStoreBensonService, DayProdService dayProdService) {
        this.sessionService = sessionService;
        this.monthProdStoreBensonService = monthProdStoreBensonService;
        this.dayProdService = dayProdService;
    }

    @RequestMapping(value = "/monthProdStoreBenson/list.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);
        System.out.println("momsHqBrandCdComboList : " + momsHqBrandCdComboList);

        return "sale/prod/monthProdStoreBenson/monthProdStoreBenson";
    }

    /**
     * 월별상품매출현황(매장별) 리스트 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   monthProdStoreBensonVO
     * @return  String
     */
    @RequestMapping(value = "/monthProdStoreBenson/getMonthProdStoreBensonList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthProdStoreBensonList(HttpServletRequest request, HttpServletResponse response, Model model, MonthProdStoreBensonVO monthProdStoreBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = monthProdStoreBensonService.getMonthProdStoreBensonList(monthProdStoreBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, monthProdStoreBensonVO);
    }

    /**
     * 월별상품매출현황(매장별) 엑셀 다운로드 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   monthProdStoreBensonVO
     * @return  String
     */
    @RequestMapping(value = "/monthProdStoreBenson/getMonthProdStoreBensonExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthProdStoreBensonExcelList(HttpServletRequest request, HttpServletResponse response, Model model, MonthProdStoreBensonVO monthProdStoreBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = monthProdStoreBensonService.getMonthProdStoreBensonExcelList(monthProdStoreBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, monthProdStoreBensonVO);
    }
}
