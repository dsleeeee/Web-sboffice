package kr.co.solbipos.sale.store.storeDayTimeMrpizza.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.store.storeDayTimeMrpizza.service.StoreDayTimeMrpizzaService;
import kr.co.solbipos.sale.store.storeDayTimeMrpizza.service.StoreDayTimeMrpizzaVO;
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
 * @Class Name : StoreDayTimeMrpizzaController.java
 * @Description : 미스터피자 > 매장분석 > 매장-일별시간대
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
@RequestMapping("/sale/store/storeDayTimeMrpizza")
public class StoreDayTimeMrpizzaController {

    private final SessionService sessionService;
    private final StoreDayTimeMrpizzaService storeDayTimeMrpizzaService;
    private final DayService dayService;

    /**
     * Constructor Injection
     */
    @Autowired
    public StoreDayTimeMrpizzaController(SessionService sessionService, StoreDayTimeMrpizzaService storeDayTimeMrpizzaService, DayService dayService) {
        this.sessionService = sessionService;
        this.storeDayTimeMrpizzaService = storeDayTimeMrpizzaService;
        this.dayService = dayService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/storeDayTimeMrpizza/view.sb", method = RequestMethod.GET)
    public String View(HttpServletRequest request, HttpServletResponse response, Model model) {

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

        return "sale/store/storeDayTimeMrpizza/storeDayTimeMrpizza";
    }

    /**
     * 매장-일별시간대 리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @param storeDayTimeMrpizzaVO
     * @return
     * @author  이다솜
     * @since   2025.06.16
     */
    @RequestMapping(value = "/storeDayTimeMrpizza/getStoreDayTimeMrpizzaList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreDayTimeMrpizzaList(HttpServletRequest request, HttpServletResponse response, Model model, StoreDayTimeMrpizzaVO storeDayTimeMrpizzaVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = storeDayTimeMrpizzaService.getStoreDayTimeMrpizzaList(storeDayTimeMrpizzaVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, storeDayTimeMrpizzaVO);
    }
}
