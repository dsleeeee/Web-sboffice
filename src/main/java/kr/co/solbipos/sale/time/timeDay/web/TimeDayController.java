package kr.co.solbipos.sale.time.timeDay.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.time.timeDay.service.TimeDayService;
import kr.co.solbipos.sale.time.timeDay.service.TimeDayVO;
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
 * @Class Name : TimeDayController.java
 * @Description : 맘스터치 > 시간대별매출 > 일자별 시간대 매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.13  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/time/timeDay")
public class TimeDayController {

    private final SessionService sessionService;
    private final TimeDayService timeDayService;

    /**
     * Constructor Injection
     */
    @Autowired
    public TimeDayController(SessionService sessionService, TimeDayService timeDayService) {
        this.sessionService = sessionService;
        this.timeDayService = timeDayService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/timeDay/list.sb", method = RequestMethod.GET)
    public String timeDayView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/time/timeDay/timeDay";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   timeDayVO
     * @return  String
     * @author  권지현
     * @since   2022.10.13
     */
    @RequestMapping(value = "/timeDay/getTimeDayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTimeDayList(HttpServletRequest request, HttpServletResponse response, Model model, TimeDayVO timeDayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = timeDayService.getTimeDayList(timeDayVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, timeDayVO);
    }

    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   timeDayVO
     * @return  String
     * @author  권지현
     * @since   2022.10.13
     */
    @RequestMapping(value = "/timeDay/getTimeDayExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTimeDayExcelList(HttpServletRequest request, HttpServletResponse response, Model model, TimeDayVO timeDayVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = timeDayService.getTimeDayExcelList(timeDayVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, timeDayVO);
    }

}