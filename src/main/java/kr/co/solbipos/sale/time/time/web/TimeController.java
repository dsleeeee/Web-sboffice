package kr.co.solbipos.sale.time.time.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.time.time.service.TimeService;
import kr.co.solbipos.sale.time.time.service.TimeVO;
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
 * @Class Name : TimeController.java
 * @Description : 맘스터치 > 시간대별매출 > 시간대별 일 매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.12  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.12
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/time/time")
public class TimeController {

    private final SessionService sessionService;
    private final TimeService timeService;

    /**
     * Constructor Injection
     */
    @Autowired
    public TimeController(SessionService sessionService, TimeService timeService) {
        this.sessionService = sessionService;
        this.timeService = timeService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/time/list.sb", method = RequestMethod.GET)
    public String timeView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/time/time/time";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   timeVO
     * @return  String
     * @author  권지현
     * @since   2022.10.12
     */
    @RequestMapping(value = "/time/getTimeList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTimeList(HttpServletRequest request, HttpServletResponse response, Model model, TimeVO timeVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = timeService.getTimeList(timeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, timeVO);
    }

    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   timeVO
     * @return  String
     * @author  권지현
     * @since   2022.10.12
     */
    @RequestMapping(value = "/time/getTimeExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTimeExcelList(HttpServletRequest request, HttpServletResponse response, Model model, TimeVO timeVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = timeService.getTimeExcelList(timeVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, timeVO);
    }

}