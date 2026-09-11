package kr.co.solbipos.sale.benson.timeProdBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.benson.timeProdBenson.service.TimeProdBensonService;
import kr.co.solbipos.sale.benson.timeProdBenson.service.TimeProdBensonVO;
import kr.co.solbipos.sale.day.day.service.DayService;
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
 * @Class Name : TimeProdBensonController.java
 * @Description : (벤슨) 상품매출분석 > 상품별시간대매출
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
@RequestMapping("/sale/benson/timeProdBenson")
public class TimeProdBensonController {

    private final SessionService sessionService;
    private final TimeProdBensonService timeProdBensonService;
    private final DayProdService dayProdService;
    private final DayService dayService;

    /**
     * Constructor Injection
     */
    @Autowired
    public TimeProdBensonController(SessionService sessionService, TimeProdBensonService timeProdBensonService, DayProdService dayProdService, DayService dayService) {
        this.sessionService = sessionService;
        this.timeProdBensonService = timeProdBensonService;
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
    @RequestMapping(value = "/timeProdBenson/list.sb", method = RequestMethod.GET)
    public String timeProdBensonView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 사용자별 브랜드 조회(콤보박스용)
        DayProdVO dayProdVO = new DayProdVO();
        String momsHqBrandCdComboList = convertToJson(dayProdService.getUserBrandComboList(dayProdVO, sessionInfoVO));
        model.addAttribute("momsHqBrandCdComboList", momsHqBrandCdComboList);

        // 시간대 조회
        List<DefaultMap<String>> timeSlotColList = dayService.getTimeSlotList(sessionInfoVO);
        // 시간대를 , 로 연결하는 문자열 생성
        String timeSlotCol = "";
        for(int i=0; i < timeSlotColList.size(); i++) {
            timeSlotCol += (timeSlotCol.equals("") ? "" : ",") + timeSlotColList.get(i).getStr("value");
        }

        model.addAttribute("timeSlotColList", timeSlotColList);
        model.addAttribute("timeSlotCol", timeSlotCol);

        return "sale/benson/timeProdBenson/timeProdBenson";
    }

    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   timeProdBensonVO
     * @return  String
     * @author  김유승
     * @since   2026.09.09
     */
    @RequestMapping(value = "/timeProdBenson/getTimeProdBensonList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTimeProdBensonList(HttpServletRequest request, HttpServletResponse response, Model model, TimeProdBensonVO timeProdBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = timeProdBensonService.getTimeProdBensonList(timeProdBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, timeProdBensonVO);
    }

    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   timeProdBensonVO
     * @return  String
     * @author  김유승
     * @since   2026.09.09
     */
    @RequestMapping(value = "/timeProdBenson/getTimeProdBensonExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTimeProdBensonExcelList(HttpServletRequest request, HttpServletResponse response, Model model, TimeProdBensonVO timeProdBensonVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = timeProdBensonService.getTimeProdBensonExcelList(timeProdBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, timeProdBensonVO);
    }
}
