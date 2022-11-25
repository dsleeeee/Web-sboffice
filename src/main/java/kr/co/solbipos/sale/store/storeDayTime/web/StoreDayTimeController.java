package kr.co.solbipos.sale.store.storeDayTime.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.store.storeDayTime.service.StoreDayTimeService;
import kr.co.solbipos.sale.store.storeDayTime.service.StoreDayTimeVO;
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
 * @Class Name : StoreDayTimeController.java
 * @Description : 맘스터치 > 매장분석 > 매장-일별시간대
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.11.25  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.11.25
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/store/storeDayTime")
public class StoreDayTimeController {

    private final SessionService sessionService;
    private final StoreDayTimeService storeDayTimeService;
    private final DayService dayService;

    /**
     * Constructor Injection
     */
    @Autowired
    public StoreDayTimeController(SessionService sessionService, StoreDayTimeService storeDayTimeService, DayService dayService) {
        this.sessionService = sessionService;
        this.storeDayTimeService = storeDayTimeService;
        this.dayService = dayService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/storeDayTime/list.sb", method = RequestMethod.GET)
    public String storeDayTimeView(HttpServletRequest request, HttpServletResponse response, Model model) {

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

        return "sale/store/storeDayTime/storeDayTime";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   storeDayTimeVO
     * @return  String
     * @author  권지현
     * @since   2022.11.25
     */
    @RequestMapping(value = "/storeDayTime/getStoreDayTimeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreDayTimeList(HttpServletRequest request, HttpServletResponse response, Model model, StoreDayTimeVO storeDayTimeVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = storeDayTimeService.getStoreDayTimeList(storeDayTimeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeDayTimeVO);
    }

}