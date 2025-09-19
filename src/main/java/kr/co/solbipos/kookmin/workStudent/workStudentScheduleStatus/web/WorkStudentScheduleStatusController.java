package kr.co.solbipos.kookmin.workStudent.workStudentScheduleStatus.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.workStudent.workStudentScheduleStatus.service.WorkStudentScheduleStatusService;
import kr.co.solbipos.kookmin.workStudent.workStudentScheduleStatus.service.WorkStudentScheduleStatusVO;
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
 * @Class Name  : WorkStudentScheduleStatusController.java
 * @Description : 국민대 > 근로학생관리 > 근로학생 근무배치 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.19  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.09.19
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/kookmin/workStudent/workStudentScheduleStatus")
public class WorkStudentScheduleStatusController {

    private final SessionService sessionService;
    private final WorkStudentScheduleStatusService workStudentScheduleStatusService;

    /**
     * Constructor Injection
     */
    @Autowired
    public WorkStudentScheduleStatusController(SessionService sessionService, WorkStudentScheduleStatusService workStudentScheduleStatusService) {
        this.sessionService = sessionService;
        this.workStudentScheduleStatusService = workStudentScheduleStatusService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/workStudentScheduleStatus/view.sb", method = RequestMethod.GET)
    public String workStudentScheduleStatus(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "kookmin/workStudent/workStudentScheduleStatus/workStudentScheduleStatus";
    }

    /**
     * 근로학생 근무배치 현황 조회
     *
     * @param   workStudentScheduleStatusVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 09. 19.
     */
    @RequestMapping(value = "/workStudentScheduleStatus/getWorkStudentScheduleStatusList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getWorkStudentScheduleStatusList(WorkStudentScheduleStatusVO workStudentScheduleStatusVO, HttpServletRequest request,
                                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = workStudentScheduleStatusService.getWorkStudentScheduleStatusList(workStudentScheduleStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, workStudentScheduleStatusVO);
    }

}
