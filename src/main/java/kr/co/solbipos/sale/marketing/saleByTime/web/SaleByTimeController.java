package kr.co.solbipos.sale.marketing.saleByTime.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.day.service.DayService;
import kr.co.solbipos.sale.marketing.saleByTime.service.SaleByTimeService;
import kr.co.solbipos.sale.marketing.saleByTime.service.SaleByTimeVO;
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
 * @Class Name  : SaleByTimeController.java
 * @Description : 미스터피자 > 마케팅조회 > 시간대별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.07  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.08.07
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/marketing/saleByTime")
public class SaleByTimeController {

    private final SessionService sessionService;
    private final DayService dayService;
    private final SaleByTimeService saleByTimeService;


    /**
     * Constructor Injection
     */
    @Autowired
    public SaleByTimeController(SessionService sessionService, DayService dayService, SaleByTimeService saleByTimeService) {
        this.sessionService = sessionService;
        this.dayService = dayService;
        this.saleByTimeService = saleByTimeService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/saleByTime/list.sb", method = RequestMethod.GET)
    public String saleByTimeView(HttpServletRequest request, HttpServletResponse response, Model model) {

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

        return "sale/marketing/saleByTime/saleByTime";
    }

    /**
     * 시간대별매출 - 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   saleByTimeVO
     * @return  String
     * @author  김유승
     * @since   2025.08.07
     */
    @RequestMapping(value = "/saleByTime/getSaleByTimeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSaleByTimeList(HttpServletRequest request, HttpServletResponse response, Model model, SaleByTimeVO saleByTimeVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = saleByTimeService.getSaleByTimeList(saleByTimeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, saleByTimeVO);
    }
}
