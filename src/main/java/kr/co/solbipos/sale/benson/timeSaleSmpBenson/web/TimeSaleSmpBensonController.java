package kr.co.solbipos.sale.benson.timeSaleSmpBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.benson.timeSaleSmpBenson.service.TimeSaleSmpBensonService;
import kr.co.solbipos.sale.benson.timeSaleSmpBenson.service.TimeSaleSmpBensonVO;
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
 * @Class Name : TimeSaleSmpBensonController.java
 * @Description : 벤슨 > 간소화화면 > 시간대매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/benson/timeSaleSmpBenson")
public class TimeSaleSmpBensonController {

    private final SessionService sessionService;
    private final TimeSaleSmpBensonService timeSaleSmpBensonService;
    private final DayProdService dayProdService;

    /**
     * Constructor Injection
     */
    @Autowired
    public TimeSaleSmpBensonController(SessionService sessionService, TimeSaleSmpBensonService timeSaleSmpBensonService, DayProdService dayProdService) {
        this.sessionService = sessionService;
        this.timeSaleSmpBensonService = timeSaleSmpBensonService;
        this.dayProdService = dayProdService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/timeSaleSmpBenson/list.sb", method = RequestMethod.GET)
    public String timeSaleSmpBensonView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);

        return "sale/benson/timeSaleSmpBenson/timeSaleSmpBenson";
    }

    /**
     * 시간대매출 - 조회
     *
     * @param timeSaleSmpBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2026. 09. 09.
     */
    @RequestMapping(value = "/timeSaleSmpBenson/getTimeSaleSmpBensonList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTimeSaleSmpBensonList(TimeSaleSmpBensonVO timeSaleSmpBensonVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = timeSaleSmpBensonService.getTimeSaleSmpBensonList(timeSaleSmpBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, timeSaleSmpBensonVO);
    }

    /**
     * 시간대매출 - 엑셀다운로드 조회
     *
     * @param timeSaleSmpBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2026. 09. 09.
     */
    @RequestMapping(value = "/timeSaleSmpBenson/getTimeSaleSmpBensonExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTimeSaleSmpBensonExcelList(TimeSaleSmpBensonVO timeSaleSmpBensonVO, HttpServletRequest request,
                                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = timeSaleSmpBensonService.getTimeSaleSmpBensonExcelList(timeSaleSmpBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, timeSaleSmpBensonVO);
    }

    /**
     * 시간대매출 - 분할 엑셀다운로드 조회
     *
     * @param timeSaleSmpBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2026. 09. 09.
     */
    @RequestMapping(value = "/timeSaleSmpBenson/getTimeSaleSmpBensonExcelDivisionList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTimeSaleSmpBensonExcelDivisionList(TimeSaleSmpBensonVO timeSaleSmpBensonVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = timeSaleSmpBensonService.getTimeSaleSmpBensonExcelDivisionList(timeSaleSmpBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, timeSaleSmpBensonVO);
    }
}
