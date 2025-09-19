package kr.co.solbipos.kookmin.workStudent.workHistoryByWorkStudent.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.workStudent.workHistoryByWorkStudent.service.WorkHistoryByWorkStudentService;
import kr.co.solbipos.kookmin.workStudent.workHistoryByWorkStudent.service.WorkHistoryByWorkStudentVO;
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
 * @Class Name  : WorkHistoryByWorkStudentController.java
 * @Description : 국민대 > 근로학생관리 > 근로학생별 근무내역
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
@RequestMapping(value = "/kookmin/workStudent/workHistoryByWorkStudent")
public class WorkHistoryByWorkStudentController {

    private final SessionService sessionService;
    private final WorkHistoryByWorkStudentService workStudentByWorkHistoryService;

    /**
     * Constructor Injection
     */
    @Autowired
    public WorkHistoryByWorkStudentController(SessionService sessionService, WorkHistoryByWorkStudentService workStudentByWorkHistoryService) {
        this.sessionService = sessionService;
        this.workStudentByWorkHistoryService = workStudentByWorkHistoryService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/workHistoryByWorkStudent/view.sb", method = RequestMethod.GET)
    public String workHistoryByWorkStudent(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "kookmin/workStudent/workHistoryByWorkStudent/workHistoryByWorkStudent";
    }

    /**
     * 근로학생별 근무내역 조회
     *
     * @param   workStudentByWorkHistoryVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 09. 19.
     */
    @RequestMapping(value = "/workHistoryByWorkStudent/getWorkHistoryByWorkStudentList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getWorkHistoryByWorkStudentList(WorkHistoryByWorkStudentVO workStudentByWorkHistoryVO, HttpServletRequest request,
                                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = workStudentByWorkHistoryService.getWorkHistoryByWorkStudentList(workStudentByWorkHistoryVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, workStudentByWorkHistoryVO);
    }

}
