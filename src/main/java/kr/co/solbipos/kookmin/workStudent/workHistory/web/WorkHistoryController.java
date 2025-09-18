package kr.co.solbipos.kookmin.workStudent.workHistory.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.base.termInfo.service.TermInfoVO;
import kr.co.solbipos.kookmin.workStudent.workHistory.service.WorkHistoryService;
import kr.co.solbipos.kookmin.workStudent.workHistory.service.WorkHistoryVO;
import kr.co.solbipos.kookmin.workStudent.workScheduleStore.service.WorkScheduleStoreVO;
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

@Controller
@RequestMapping(value = "/kookmin/workStudent/workHistory")
public class WorkHistoryController {

    private final WorkHistoryService workHistoryService;
    private final SessionService sessionService;

    /**
     * Constructor Injection
     */
    @Autowired
    public WorkHistoryController(WorkHistoryService workHistoryService, SessionService sessionService) {
        this.workHistoryService = workHistoryService;
        this.sessionService = sessionService;
    }


    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/workHistory/view.sb", method = RequestMethod.GET)
    public String workHistory(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "kookmin/workStudent/workHistory/workHistory";
    }

    /**
     * 근무내역 조회
     *
     * @param   workHistoryVO
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 09. 05.
     */
    @RequestMapping(value = "/workHistory/getWorkHistoryList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getWorkHistoryList(WorkHistoryVO workHistoryVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = workHistoryService.getWorkHistoryList(workHistoryVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, workHistoryVO);
    }

    /**
     * 근무내역 - 근무시간 저장
     *
     * @param   workHistoryVOS
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 09. 16.
     */
    @RequestMapping(value = "/workHistory/saveWorkHistory.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveWorkHistory(@RequestBody WorkHistoryVO[] workHistoryVOS, HttpServletRequest request,
                                        HttpServletResponse response , Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = workHistoryService.saveWorkHistory(workHistoryVOS, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 근무내역 - 근무시간 일괄등록
     *
     * @param   workHistoryVOS
     * @param   request
     * @param   response
     * @param   model
     * @return  Object
     * @author  김유승
     * @since   2025. 09. 17.
     */
    @RequestMapping(value = "/workHistory/saveRegCommuteAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveRegCommuteAll(@RequestBody WorkHistoryVO[] workHistoryVOS, HttpServletRequest request,
                                  HttpServletResponse response , Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = workHistoryService.saveRegCommuteAll(workHistoryVOS, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
