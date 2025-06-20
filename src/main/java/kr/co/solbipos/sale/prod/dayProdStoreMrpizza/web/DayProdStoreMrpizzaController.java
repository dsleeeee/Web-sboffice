package kr.co.solbipos.sale.prod.dayProdStoreMrpizza.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.sale.prod.dayProdStore.service.DayProdStoreVO;
import kr.co.solbipos.sale.prod.dayProdStoreMrpizza.service.DayProdStoreMrpizzaService;
import kr.co.solbipos.sale.prod.dayProdStoreMrpizza.service.DayProdStoreMrpizzaVO;
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
 * @Class Name : DayProdStoreMrpizzaController.java
 * @Description : 미스터피자 > 상품매출분석 > 일별상품매출현황(매장별)
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
@RequestMapping("/sale/prod/dayProdStoreMrpizza")
public class DayProdStoreMrpizzaController {

    private final SessionService sessionService;
    private final DayProdStoreMrpizzaService dayProdStoreMrpizzaService;
    private final CmmCodeUtil cmmCodeUtil;
    private final DayProdService dayProdService;

    /**
     * Constructor Injection
     */
    @Autowired
    public DayProdStoreMrpizzaController(SessionService sessionService, DayProdStoreMrpizzaService dayProdStoreMrpizzaService, CmmCodeUtil cmmCodeUtil, DayProdService dayProdService) {
        this.sessionService = sessionService;
        this.dayProdStoreMrpizzaService = dayProdStoreMrpizzaService;
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
    @RequestMapping(value = "/dayProdStoreMrpizza/list.sb", method = RequestMethod.GET)
        public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);
        System.out.println("momsHqBrandCdComboList : " + momsHqBrandCdComboList);

        return "sale/prod/dayProdStoreMrpizza/dayProdStoreMrpizza";
    };

    /**
     * 일별상품매출현황(매장별) 리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @param dayProdStoreMrpizzaVO
     * @return
     * @author  이다솜
     * @since   2025.06.16
     */
    @RequestMapping(value = "/dayProdStoreMrpizza/getDayProdStoreMrpizzaList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayProdStoreMrpizzaList(HttpServletRequest request, HttpServletResponse response, Model model, DayProdStoreMrpizzaVO dayProdStoreMrpizzaVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dayProdStoreMrpizzaService.getDayProdStoreMrpizzaList(dayProdStoreMrpizzaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayProdStoreMrpizzaVO);
    }

    /**
     * 일별상품매출현황(매장별) 엑셀다운로드 조회
     * @param request
     * @param response
     * @param model
     * @param dayProdStoreMrpizzaVO
     * @return
     * @author  이다솜
     * @since   2025.06.16
     */
    @RequestMapping(value = "/dayProdStoreMrpizza/getDayProdStoreMrpizzaExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayProdStoreMrpizzaExcelList(HttpServletRequest request, HttpServletResponse response, Model model, DayProdStoreMrpizzaVO dayProdStoreMrpizzaVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dayProdStoreMrpizzaService.getDayProdStoreMrpizzaExcelList(dayProdStoreMrpizzaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayProdStoreMrpizzaVO);
    }
}
