package kr.co.solbipos.sys.auth.authgroup.web;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.auth.authgroup.service.AuthGroupService;
import kr.co.solbipos.sys.auth.authgroup.service.AuthGroupVO;
import kr.co.solbipos.sys.auth.authgroup.service.AuthorExceptVO;
import kr.co.solbipos.sys.auth.authgroup.service.AuthorGrpResrceVO;

/**
 * 시스템관리 - 권한관리 - 권한 그룹 관리
 * @author 조병준
 */
@Controller
@RequestMapping(value = "/sys/auth/authgroup/authgroup/")
public class AuthGroupController {

    @Autowired
    AuthGroupService service;

    @Autowired
    SessionService sessionService;

    /**
     * 페이지
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "list.sb", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        List<DefaultMap<String>> list = service.listAvailGroup(sessionInfoVO);
        model.addAttribute("availAuthGrp", convertToJson(list));
        return "sys/auth/authgroup/authGroup";
    }

    /**
     * 그룹 조회
     *
     * @param authGroup
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result list(AuthGroupVO authGroupVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        //권한 그룹 정보 조회 - 시스템일 경우 전체, 대리점은 대상 조직이 자신인 데이터만 조회
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();
        List<DefaultMap<String>> list = service.list(authGroupVO, sessionInfoVO);

        //TODO 리소스 정보 조회 - 자신이 가진 권한
        return returnListJson(Status.OK, list);
    }

    /**
     * 저장
     *
     * @param authGroup
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody AuthGroupVO[] authGroupVOs, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = service.save(authGroupVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 리소스 정보 조회
     *
     * @param authGroup
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "listResrce.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result listResrce(AuthGroupVO authGroupVO, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        //리소스 정보 조회 - 자신이 가진 권한
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<AuthorGrpResrceVO> list = service.listResrce(authGroupVO, sessionInfoVO);

        return returnListJson(Status.OK, list);
    }

    /**
     * 리소스 정보 저장
     *
     * @param authGroup
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "saveResrce.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveResrce(@RequestBody AuthorGrpResrceVO[] authorGrpResrceVOs, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        //리소스 정보 저장
        int result = service.saveResrce(authorGrpResrceVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 아이디 기준 리소스 정보 조회 - for 임의 권한 부여 팝업
     *
     * @param userId
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "listResrceById.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result listResrceById(String userId, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        //리소스 정보 조회 - 자신이 가진 권한
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<AuthorGrpResrceVO> list = service.listResrceById(userId, sessionInfoVO);

        return returnListJson(Status.OK, list);
    }

    /**
     * 아이디 기준 리소스 정보 저장 - for 임의 권한 부여 팝업
     *
     * @param authGroup
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "saveResrceById.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveResrceById(@RequestBody AuthorExceptVO[] authorExceptVOs, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        //리소스 정보 저장
        int result = service.saveResrceById(authorExceptVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}