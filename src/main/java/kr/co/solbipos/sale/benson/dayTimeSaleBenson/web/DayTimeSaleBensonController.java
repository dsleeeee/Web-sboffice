package kr.co.solbipos.sale.benson.dayTimeSaleBenson.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.benson.dayTimeSaleBenson.service.DayTimeSaleBensonService;
import kr.co.solbipos.sale.benson.dayTimeSaleBenson.service.DayTimeSaleBensonVO;
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
 * @Class Name : DayTimeSaleBensonController.java
 * @Description : 벤슨 > 매출분석 > 일별시간대별매출조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/benson/dayTimeSaleBenson")
public class DayTimeSaleBensonController {

    private final SessionService sessionService;
    private final DayTimeSaleBensonService dayTimeSaleBensonService;

    /**
     * Constructor Injection
     */
    @Autowired
    public DayTimeSaleBensonController(SessionService sessionService, DayTimeSaleBensonService dayTimeSaleBensonService) {
        this.sessionService = sessionService;
        this.dayTimeSaleBensonService = dayTimeSaleBensonService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/dayTimeSaleBenson/list.sb", method = RequestMethod.GET)
    public String dayTimeSaleBensonView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/benson/dayTimeSaleBenson/dayTimeSaleBenson";
    }

    /**
     * 일별시간대별매출조회 - 조회
     *
     * @param dayTimeSaleBensonVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김유승
     * @since   2026. 09. 10.
     */
    @RequestMapping(value = "/dayTimeSaleBenson/getDayTimeSaleBensonList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDayTimeSaleBensonList(DayTimeSaleBensonVO dayTimeSaleBensonVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = dayTimeSaleBensonService.getDayTimeSaleBensonList(dayTimeSaleBensonVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, dayTimeSaleBensonVO);
    }
}
