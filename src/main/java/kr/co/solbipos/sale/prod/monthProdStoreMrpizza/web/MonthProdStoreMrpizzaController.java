package kr.co.solbipos.sale.prod.monthProdStoreMrpizza.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.sale.prod.monthProdStore.service.MonthProdStoreVO;
import kr.co.solbipos.sale.prod.monthProdStoreMrpizza.service.MonthProdStoreMrpizzaService;
import kr.co.solbipos.sale.prod.monthProdStoreMrpizza.service.MonthProdStoreMrpizzaVO;
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
 * @Class Name : MonthProdStoreMrpizzaController.java
 * @Description : 미스터피자 > 상품매출분석 > 월별상품매출현황(매장별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.16  이다솜      최초생성
 *
 * @author 솔비포스 개발실 개발1팀 이다솜
 * @since 2025.06.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/prod/monthProdStoreMrpizza")
public class MonthProdStoreMrpizzaController {

    private final SessionService sessionService;
    private final MonthProdStoreMrpizzaService monthProdStoreMrpizzaService;
    private final DayProdService dayProdService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MonthProdStoreMrpizzaController(SessionService sessionService, MonthProdStoreMrpizzaService monthProdStoreMrpizzaService, DayProdService dayProdService) {
        this.sessionService = sessionService;
        this.monthProdStoreMrpizzaService = monthProdStoreMrpizzaService;
        this.dayProdService = dayProdService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/monthProdStoreMrpizza/list.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);
        System.out.println("momsHqBrandCdComboList : " + momsHqBrandCdComboList);

        return "sale/prod/monthProdStoreMrpizza/monthProdStoreMrpizza";
    }

    /**
     * 월별상품매출현황(매장별) 리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @param monthProdStoreMrpizzaVO
     * @return
     * @author  이다솜
     * @since   2025.06.16
     */
    @RequestMapping(value = "/monthProdStoreMrpizza/getMonthProdStoreMrpizzaList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthProdStoreMrpizzaList(HttpServletRequest request, HttpServletResponse response, Model model, MonthProdStoreMrpizzaVO monthProdStoreMrpizzaVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = monthProdStoreMrpizzaService.getMonthProdStoreMrpizzaList(monthProdStoreMrpizzaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, monthProdStoreMrpizzaVO);
    }

    /**
     * 월별상품매출현황(매장별) 엑셀다운로드 조회
     *
     * @param request
     * @param response
     * @param model
     * @param monthProdStoreMrpizzaVO
     * @return
     * @author  이다솜
     * @since   2025.06.16
     */
    @RequestMapping(value = "/monthProdStoreMrpizza/getMonthProdStoreMrpizzaExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthProdStoreMrpizzaExcelList(HttpServletRequest request, HttpServletResponse response, Model model, MonthProdStoreMrpizzaVO monthProdStoreMrpizzaVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = monthProdStoreMrpizzaService.getMonthProdStoreMrpizzaExcelList(monthProdStoreMrpizzaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, monthProdStoreMrpizzaVO);
    }

}
