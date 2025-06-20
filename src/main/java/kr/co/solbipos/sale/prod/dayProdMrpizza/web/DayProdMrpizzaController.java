package kr.co.solbipos.sale.prod.dayProdMrpizza.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.sale.prod.dayProdMrpizza.service.DayProdMrpizzaService;
import kr.co.solbipos.sale.prod.dayProdMrpizza.service.DayProdMrpizzaVO;
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
 * @Class Name : DayProdMrpizzaController.java
 * @Description : 미스터피자 > 상품매출분석 > 일별상품매출현황
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
@RequestMapping("/sale/prod/dayProdMrpizza")
public class DayProdMrpizzaController {

    private final SessionService sessionService;
    private final DayProdMrpizzaService dayProdMrpizzaService;
    private final DayProdService dayProdService;
    private final CmmCodeUtil cmmCodeUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public DayProdMrpizzaController(SessionService sessionService, DayProdMrpizzaService dayProdMrpizzaService, DayProdService dayProdService, CmmCodeUtil cmmCodeUtil) {
        this.sessionService = sessionService;
        this.dayProdMrpizzaService = dayProdMrpizzaService;
        this.dayProdService = dayProdService;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/dayProdMrpizza/list.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);
        System.out.println("momsHqBrandCdComboList : " + momsHqBrandCdComboList);

        return "sale/prod/dayProdMrpizza/dayProdMrpizza";
    }

    /**
     * 일별상품매출현황 리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @param dayProdMrpizzaVO
     * @return
     * @author  이다솜
     * @since   2025.06.16
     */
    @RequestMapping(value = "/dayProdMrpizza/getDayProdMrpizzaList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayProdMrpizzaList(HttpServletRequest request, HttpServletResponse response, Model model, DayProdMrpizzaVO dayProdMrpizzaVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dayProdMrpizzaService.getDayProdMrpizzaList(dayProdMrpizzaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayProdMrpizzaVO);
    }

    /**
     * 일별상품매출현황 엑셀다운로드 조회
     *
     * @param request
     * @param response
     * @param model
     * @param dayProdMrpizzaVO
     * @return
     * @author  이다솜
     * @since   2025.06.16
     */
    @RequestMapping(value = "/dayProdMrpizza/getDayProdMrpizzaExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayProdMrpizzaExcelList(HttpServletRequest request, HttpServletResponse response, Model model, DayProdMrpizzaVO dayProdMrpizzaVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dayProdMrpizzaService.getDayProdMrpizzaExcelList(dayProdMrpizzaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayProdMrpizzaVO);
    }
}
