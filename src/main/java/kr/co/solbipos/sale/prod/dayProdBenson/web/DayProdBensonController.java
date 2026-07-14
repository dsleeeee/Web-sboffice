package kr.co.solbipos.sale.prod.dayProdBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdService;
import kr.co.solbipos.sale.prod.dayProd.service.DayProdVO;
import kr.co.solbipos.sale.prod.dayProdBenson.service.DayProdBensonService;
import kr.co.solbipos.sale.prod.dayProdBenson.service.DayProdBensonVO;
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
 * @Class Name : DayProdBensonController.java
 * @Description : 벤슨 > 상품매출분석 > 일별상품매출현황
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
@RequestMapping("/sale/prod/dayProdBenson")
public class DayProdBensonController {

    private final SessionService sessionService;
    private final DayProdBensonService dayProdBensonService;
    private final DayProdService dayProdService;

    @Autowired
    public DayProdBensonController(SessionService sessionService, DayProdBensonService dayProdBensonService, DayProdService dayProdService) {
        this.sessionService = sessionService;
        this.dayProdBensonService = dayProdBensonService;
        this.dayProdService = dayProdService;
    }

    /**
     * 페이지 이동
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/dayProdBenson/list.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);
        System.out.println("momsHqBrandCdComboList : " + momsHqBrandCdComboList);

        return "sale/prod/dayProdBenson/dayProdBenson";
    }

    /**
     * 일별상품매출현황 리스트 조회
     * @param request
     * @param response
     * @param model
     * @param dayProdBensonVO
     * @return
     */
    @RequestMapping(value = "/dayProdBenson/getDayProdBensonList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayProdBensonList(HttpServletRequest request, HttpServletResponse response, Model model, DayProdBensonVO dayProdBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dayProdBensonService.getDayProdBensonList(dayProdBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayProdBensonVO);
    }

    /**
     * 일별상품매출현황 엑셀 다운로드 조회
     * @param request
     * @param response
     * @param model
     * @param dayProdBensonVO
     * @return
     */
    @RequestMapping(value = "/dayProdBenson/getDayProdBensonExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayProdBensonExcelList(HttpServletRequest request, HttpServletResponse response, Model model, DayProdBensonVO dayProdBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dayProdBensonService.getDayProdBensonExcelList(dayProdBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayProdBensonVO);
    }
}
