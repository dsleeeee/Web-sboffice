package kr.co.solbipos.sale.benson.daySaleStoreBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.benson.daySaleStoreBenson.service.DaySaleStoreBensonService;
import kr.co.solbipos.sale.benson.daySaleStoreBenson.service.DaySaleStoreBensonVO;
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

import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : DaySaleStoreBensonController.java
 * @Description : 벤슨 > 간소화화면 > 일별매출(매장)
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
@RequestMapping("/sale/benson/daySaleStoreBenson")
public class DaySaleStoreBensonController {

    private final SessionService sessionService;
    private final DaySaleStoreBensonService daySaleStoreBensonService;
    private final DayProdService dayProdService;

    /**
     * Constructor Injection
     */
    @Autowired
    public DaySaleStoreBensonController(SessionService sessionService, DaySaleStoreBensonService daySaleStoreBensonService, DayProdService dayProdService) {
        this.sessionService = sessionService;
        this.daySaleStoreBensonService = daySaleStoreBensonService;
        this.dayProdService = dayProdService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/daySaleStoreBenson/list.sb", method = RequestMethod.GET)
    public String daySaleStoreBensonView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);
        System.out.println("momsHqBrandCdComboList : " + momsHqBrandCdComboList);

        return "sale/benson/daySaleStoreBenson/daySaleStoreBenson";
    }

    /**
     * 일별매출(매장) - 조회
     *
     * @param daySaleStoreBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     */
    @RequestMapping(value = "/daySaleStoreBenson/getDaySaleStoreBensonList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDaySaleStoreBensonList(DaySaleStoreBensonVO daySaleStoreBensonVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = daySaleStoreBensonService.getDaySaleStoreBensonList(daySaleStoreBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, daySaleStoreBensonVO);
    }

    /**
     * 일별매출(매장) - 엑셀다운로드 조회
     *
     * @param daySaleStoreBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     */
    @RequestMapping(value = "/daySaleStoreBenson/getDaySaleStoreBensonExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDaySaleStoreBensonExcelList(DaySaleStoreBensonVO daySaleStoreBensonVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = daySaleStoreBensonService.getDaySaleStoreBensonExcelList(daySaleStoreBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, daySaleStoreBensonVO);
    }

    /**
     * 일별매출(매장) - 분할 엑셀다운로드 조회
     *
     * @param daySaleStoreBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     */
    @RequestMapping(value = "/daySaleStoreBenson/getDaySaleStoreBensonExcelDivisionList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDaySaleStoreBensonExcelDivisionList(DaySaleStoreBensonVO daySaleStoreBensonVO, HttpServletRequest request,
                                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = daySaleStoreBensonService.getDaySaleStoreBensonExcelDivisionList(daySaleStoreBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, daySaleStoreBensonVO);
    }
}
