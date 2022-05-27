package kr.co.solbipos.base.store.timeSlot.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.timeSlot.service.TimeSlotService;
import kr.co.solbipos.base.store.timeSlot.service.TimeSlotVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : TimeSlotController.java
 * @Description : 기초관리 - 매장관리 - 시간대분류관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.05.20  권지현       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.05.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/base/store/timeSlot")
public class TimeSlotController {

    private final SessionService sessionService;
    private final TimeSlotService timeSlotService;

    /**
     * Constructor Injection
     */
    @Autowired
    public TimeSlotController(SessionService sessionService, TimeSlotService timeSlotService) {
        this.sessionService = sessionService;
        this.timeSlotService = timeSlotService;
    }

    private HttpServletRequest request;

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     * @author  권지현
     * @since   2022.05.20
     */
    @RequestMapping(value = "/timeSlot/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "base/store/timeSlot/timeSlot";
    }

    /**
     * 시간대분류관리 - 시간대분류조회
     * @param timeSlotVO
     * @param request
     * @param response
     * @param model
     * @return
     * @author  권지현
     * @since   2022.05.20
     */
    @RequestMapping(value = "/timeSlot/getTimeSlot.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTimeSlot(TimeSlotVO timeSlotVO, HttpServletRequest request,
                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = timeSlotService.getTimeSlot(timeSlotVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, result);
    }

    /**
     * 매장타입관리 - 매장타입저장
     * @param timeSlotVOs
     * @param request
     * @param response
     * @param model
     * @return
     * @author  권지현
     * @since   2022.05.20
     */
    @RequestMapping(value = "/timeSlot/saveTimeSlot.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveTimeSlot(@RequestBody TimeSlotVO[] timeSlotVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = timeSlotService.saveTimeSlot(timeSlotVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}
