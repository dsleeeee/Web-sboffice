package kr.co.solbipos.base.store.empCorner.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.empCorner.service.EmpCornerService;
import kr.co.solbipos.base.store.empCorner.service.EmpCornerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

@Controller
@RequestMapping("/base/store/empCorner")
public class EmpCornerController {

    private final SessionService sessionService;
    private final EmpCornerService empCornerService;

    /**
     * Constructor Injection
     */
    @Autowired
    public EmpCornerController(SessionService sessionService, EmpCornerService empCornerService) {
        this.sessionService = sessionService;
        this.empCornerService = empCornerService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/empCorner/list.sb", method = RequestMethod.GET)
    public String empCornerView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "base/store/empCorner/empCorner";
    }

    /**
     * 사원별탭 - 사원정보 조회
     *
     * @param empCornerVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 05. 14.
     */
    @RequestMapping(value = "/emp/getEmpCornerEmpList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpCornerEmpList(EmpCornerVO empCornerVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = empCornerService.getEmpCornerEmpList(empCornerVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, empCornerVO);
    }

    /**
     * 사원별탭 - 관리코너 조회
     *
     * @param empCornerVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 05. 14.
     */
    @RequestMapping(value = "/emp/getEmpManageCornerList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpManageCornerList(EmpCornerVO empCornerVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = empCornerService.getEmpManageCornerList(empCornerVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, empCornerVO);
    }

    /**
     * 사원별탭 - 미관리코너 조회
     *
     * @param empCornerVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 05. 14.
     */
    @RequestMapping(value = "/emp/getEmpNoManageCornerList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpNoManageCornerList(EmpCornerVO empCornerVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = empCornerService.getEmpNoManageCornerList(empCornerVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, empCornerVO);
    }

    /**
     * 사원별탭 - 관리코너 추가
     *
     * @param empCornerVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 05. 14.
     */
    @RequestMapping(value = "/emp/getEmpManageCornerSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpManageCornerSave(@RequestBody EmpCornerVO[] empCornerVOs, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = empCornerService.getEmpManageCornerSave(empCornerVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 사원별탭 - 관리코너 삭제
     *
     * @param empCornerVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 05. 14.
     */
    @RequestMapping(value = "/emp/getEmpManageCornerDelete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpManageCornerDelete(@RequestBody EmpCornerVO[] empCornerVOs, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = empCornerService.getEmpManageCornerDelete(empCornerVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 코너별탭 - 코너정보 조회
     *
     * @param empCornerVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 05. 15.
     */
    @RequestMapping(value = "/corner/getEmpCornerCornerList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpCornerCornerList(EmpCornerVO empCornerVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = empCornerService.getEmpCornerCornerList(empCornerVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, empCornerVO);
    }

    /**
     * 코너별탭 - 관리사원 조회
     *
     * @param empCornerVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 05. 15.
     */
    @RequestMapping(value = "/corner/getCornerManageEmpList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCornerManageEmpList(EmpCornerVO empCornerVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = empCornerService.getCornerManageEmpList(empCornerVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, empCornerVO);
    }

    /**
     * 코너별탭 - 비관리사원 조회
     *
     * @param empCornerVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 05. 15.
     */
    @RequestMapping(value = "/corner/getCornerNoManageEmpList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCornerNoManageEmpList(EmpCornerVO empCornerVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = empCornerService.getCornerNoManageEmpList(empCornerVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, empCornerVO);
    }

    /**
     * 코너별탭 - 관리사원 추가
     *
     * @param empCornerVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 05. 15.
     */
    @RequestMapping(value = "/corner/getCornerManageEmpSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCornerManageEmpSave(@RequestBody EmpCornerVO[] empCornerVOs, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = empCornerService.getCornerManageEmpSave(empCornerVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 코너별탭 - 관리사원 삭제
     *
     * @param empCornerVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 05. 15.
     */
    @RequestMapping(value = "/corner/getCornerManageEmpDelete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCornerManageEmpDelete(@RequestBody EmpCornerVO[] empCornerVOs, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = empCornerService.getCornerManageEmpDelete(empCornerVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}