package kr.co.solbipos.sale.prod.monthProdMrpizza.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.sale.prod.monthProdMrpizza.service.MonthProdMrpizzaService;
import kr.co.solbipos.sale.prod.monthProdMrpizza.service.MonthProdMrpizzaVO;
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
 * @Class Name : MonthProdMrpizzaController.java
 * @Description : 미스터피자 > 상품매출분석 > 월별상품매출현황
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
@RequestMapping("/sale/prod/monthProdMrpizza")
public class MonthProdMrpizzaController {

    private final SessionService sessionService;
    private final MonthProdMrpizzaService monthProdMrpizzaService;
    private final CmmCodeUtil cmmCodeUtil;
    private final DayProdService dayProdService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MonthProdMrpizzaController(SessionService sessionService, MonthProdMrpizzaService monthProdMrpizzaService, CmmCodeUtil cmmCodeUtil, DayProdService dayProdService) {
        this.sessionService = sessionService;
        this.monthProdMrpizzaService = monthProdMrpizzaService;
        this.cmmCodeUtil = cmmCodeUtil;
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
    @RequestMapping(value = "/monthProdMrpizza/list.sb", method = RequestMethod.GET)
        public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);
        System.out.println("momsHqBrandCdComboList : " + momsHqBrandCdComboList);

        return "sale/prod/monthProdMrpizza/monthProdMrpizza";
    }

    /**
     * 월별상품매출현황 리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @param monthProdMrpizzaVO
     * @return
     * @author  이다솜
     * @since   2025.06.16
     */
    @RequestMapping(value = "/monthProdMrpizza/getMonthProdMrpizzaList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthProdMrpizzaList(HttpServletRequest request, HttpServletResponse response, Model model, MonthProdMrpizzaVO monthProdMrpizzaVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = monthProdMrpizzaService.getMonthProdMrpizzaList(monthProdMrpizzaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, monthProdMrpizzaVO);
    }

    /**
     * 월별상품매출현황 엑셀다운로드 조회
     *
     * @param request
     * @param response
     * @param model
     * @param monthProdMrpizzaVO
     * @return
     * @author  이다솜
     * @since   2025.06.16
     */
    @RequestMapping(value = "/monthProdMrpizza/getMonthProdMrpizzaExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMonthProdMrpizzaExcelList(HttpServletRequest request, HttpServletResponse response, Model model, MonthProdMrpizzaVO monthProdMrpizzaVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = monthProdMrpizzaService.getMonthProdMrpizzaExcelList(monthProdMrpizzaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, monthProdMrpizzaVO);
    }

}
