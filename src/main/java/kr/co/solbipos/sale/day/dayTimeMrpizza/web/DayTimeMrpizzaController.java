package kr.co.solbipos.sale.day.dayTimeMrpizza.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.day.dayTime.service.DayTimeVO;
import kr.co.solbipos.sale.day.dayTimeMrpizza.service.DayTimeMrpizzaService;
import kr.co.solbipos.sale.day.dayTimeMrpizza.service.DayTimeMrpizzaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : DayTimeMrpizzaController.java
 * @Description : 미스터피자 > 매출분석 > 일별시간대
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.10  이다솜      최초생성
 *
 * @author 솔비포스 개발실 개발1팀 이다솜
 * @since 2025.06.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/day/dayTimeMrpizza")
public class DayTimeMrpizzaController {

    private final SessionService sessionService;
    private final DayTimeMrpizzaService dayTimeMrpizzaService;
    private final DayService dayService;

    /**
     * Constructor Injection
     */
    @Autowired
    public DayTimeMrpizzaController(SessionService sessionService, DayTimeMrpizzaService dayTimeMrpizzaService, DayService dayService) {
        this.sessionService = sessionService;
        this.dayTimeMrpizzaService = dayTimeMrpizzaService;
        this.dayService = dayService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/dayTimeMrpizza/list.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 시간대 조회
        List<DefaultMap<String>> timeSlotColList = dayService.getTimeSlotList(sessionInfoVO);
        // 시간대를 , 로 연결하는 문자열 생성
        String timeSlotCol = "";
        for(int i=0; i < timeSlotColList.size(); i++) {
            timeSlotCol += (timeSlotCol.equals("") ? "" : ",") + timeSlotColList.get(i).getStr("value");
        }

        model.addAttribute("timeSlotColList", timeSlotColList);
        model.addAttribute("timeSlotCol", timeSlotCol);

        return "sale/day/dayTimeMrpizza/dayTimeMrpizza";
    }

    /**
     * 일별시간대 리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @param dayTimeMrpizzaVO
     * @return
     * @author  이다솜
     * @since   2025.06.10
     */
    @RequestMapping(value = "/dayTimeMrpizza/getDayTimeMrpizzaList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayTimeMrpizzaList(HttpServletRequest request, HttpServletResponse response, Model model, DayTimeMrpizzaVO dayTimeMrpizzaVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = dayTimeMrpizzaService.getDayTimeMrpizzaList(dayTimeMrpizzaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, dayTimeMrpizzaVO);
    }

}
