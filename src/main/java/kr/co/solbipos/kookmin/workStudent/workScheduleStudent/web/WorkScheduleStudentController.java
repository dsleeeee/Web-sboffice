package kr.co.solbipos.kookmin.workStudent.workScheduleStudent.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.workStudent.workScheduleStudent.service.WorkScheduleStudentService;
import kr.co.solbipos.kookmin.workStudent.workScheduleStudent.service.WorkScheduleStudentVO;
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
/**
 * @Class Name  : WorkScheduleStudentController.java
 * @Description : 국민대 > 근로학생관리 > 근로학생 배치
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.11  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.09.11
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/kookmin/workStudent/workScheduleStudent")
public class WorkScheduleStudentController {

    private final SessionService sessionService;
    private final WorkScheduleStudentService workScheduleStudentService;

    /**
     * Constructor Injection
     */
    @Autowired
    public WorkScheduleStudentController(SessionService sessionService, WorkScheduleStudentService workScheduleStudentService) {
        this.sessionService = sessionService;
        this.workScheduleStudentService = workScheduleStudentService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/workScheduleStudent/view.sb", method = RequestMethod.GET)
    public String termInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "kookmin/workStudent/workScheduleStudent/workScheduleStudent";
    }

    /**
     * 근로학생 배치 조회
     *
     * @param   workScheduleStudentVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 09. 11.
     */
    @RequestMapping(value = "/workScheduleStudent/getWorkScheduleStudentList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getWorkScheduleStudentList(WorkScheduleStudentVO workScheduleStudentVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = workScheduleStudentService.getWorkScheduleStudentList(workScheduleStudentVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, workScheduleStudentVO);
    }

    /**
     * 근로학생 등록 팝업 - 근로학생 조회
     *
     * @param   workScheduleStudentVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 09. 12.
     */
    @RequestMapping(value = "/workScheduleStudent/getWorkStudentList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getWorkStudentList(WorkScheduleStudentVO workScheduleStudentVO, HttpServletRequest request,
                                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = workScheduleStudentService.getWorkStudentList(workScheduleStudentVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, workScheduleStudentVO);
    }

    /**
     * 근로학생 근무배치 저장
     *
     * @param   workScheduleStudentVOs
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 09. 12.
     */
    @RequestMapping(value = "/workScheduleStudent/saveWorkScheduleStudent.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveWorkScheduleStudent(@RequestBody WorkScheduleStudentVO[] workScheduleStudentVOs, HttpServletRequest request,
                                        HttpServletResponse response , Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = workScheduleStudentService.saveWorkScheduleStudent(workScheduleStudentVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
