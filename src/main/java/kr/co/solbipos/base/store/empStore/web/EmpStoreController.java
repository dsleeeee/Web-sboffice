package kr.co.solbipos.base.store.empStore.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.empStore.service.EmpStoreService;
import kr.co.solbipos.base.store.empStore.service.EmpStoreVO;
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
@RequestMapping("/base/store/empStore")
public class EmpStoreController {

    private final SessionService sessionService;
    private final EmpStoreService empStoreService;

    /**
     * Constructor Injection
     */
    @Autowired
    public EmpStoreController(SessionService sessionService, EmpStoreService empStoreService) {
        this.sessionService = sessionService;
        this.empStoreService = empStoreService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/empStore/list.sb", method = RequestMethod.GET)
    public String empStoreView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "base/store/empStore/empStore";
    }

    /**
     * 사원별탭 - 사원정보 조회
     *
     * @param empStoreVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 05. 12.
     */
    @RequestMapping(value = "/emp/getEmpStoreEmpList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpStoreEmpList(EmpStoreVO empStoreVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = empStoreService.getEmpStoreEmpList(empStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, empStoreVO);
    }

    /**
     * 사원별탭 - 관리매장 조회
     *
     * @param empStoreVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 05. 12.
     */
    @RequestMapping(value = "/emp/getEmpManageStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpManageStoreList(EmpStoreVO empStoreVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = empStoreService.getEmpManageStoreList(empStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, empStoreVO);
    }

    /**
     * 사원별탭 - 미관리매장 조회
     *
     * @param empStoreVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 05. 12.
     */
    @RequestMapping(value = "/emp/getEmpNoManageStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpNoManageStoreList(EmpStoreVO empStoreVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = empStoreService.getEmpNoManageStoreList(empStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, empStoreVO);
    }

    /**
     * 사원별탭 - 관리매장 추가
     *
     * @param empStoreVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 05. 12.
     */
    @RequestMapping(value = "/emp/getEmpManageStoreSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpManageStoreSave(@RequestBody EmpStoreVO[] empStoreVOs, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = empStoreService.getEmpManageStoreSave(empStoreVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 사원별탭 - 관리매장 삭제
     *
     * @param empStoreVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 05. 12.
     */
    @RequestMapping(value = "/emp/getEmpManageStoreDelete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpManageStoreDelete(@RequestBody EmpStoreVO[] empStoreVOs, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = empStoreService.getEmpManageStoreDelete(empStoreVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장별탭 - 매장정보 조회
     *
     * @param empStoreVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 05. 13.
     */
    @RequestMapping(value = "/store/getEmpStoreStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmpStoreStoreList(EmpStoreVO empStoreVO, HttpServletRequest request,
                             HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = empStoreService.getEmpStoreStoreList(empStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, empStoreVO);
    }

    /**
     * 매장별탭 - 관리사원 조회
     *
     * @param empStoreVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 05. 13.
     */
    @RequestMapping(value = "/store/getStoreManageEmpList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreManageEmpList(EmpStoreVO empStoreVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = empStoreService.getStoreManageEmpList(empStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, empStoreVO);
    }

    /**
     * 매장별탭 - 미관리사원 조회
     *
     * @param empStoreVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 05. 13.
     */
    @RequestMapping(value = "/store/getStoreNoManageEmpList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreNoManageEmpList(EmpStoreVO empStoreVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = empStoreService.getStoreNoManageEmpList(empStoreVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, empStoreVO);
    }

    /**
     * 매장별탭 - 관리사원 추가
     *
     * @param empStoreVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 05. 13.
     */
    @RequestMapping(value = "/store/getStoreManageEmpSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreManageEmpSave(@RequestBody EmpStoreVO[] empStoreVOs, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = empStoreService.getStoreManageEmpSave(empStoreVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 매장별탭 - 관리사원 삭제
     *
     * @param empStoreVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 05. 13.
     */
    @RequestMapping(value = "/store/getStoreManageEmpDelete.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreManageEmpDelete(@RequestBody EmpStoreVO[] empStoreVOs, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = empStoreService.getStoreManageEmpDelete(empStoreVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}