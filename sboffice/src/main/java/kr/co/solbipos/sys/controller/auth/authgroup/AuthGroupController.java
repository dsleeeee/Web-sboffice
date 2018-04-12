package kr.co.solbipos.sys.controller.auth.authgroup;

import static kr.co.solbipos.utils.grid.ReturnUtil.returnJson;
import static kr.co.solbipos.utils.grid.ReturnUtil.returnListJson;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.enums.Status;
import kr.co.solbipos.service.session.SessionService;
import kr.co.solbipos.structure.DefaultMap;
import kr.co.solbipos.structure.Result;
import kr.co.solbipos.sys.domain.auth.authgroup.AuthGroup;
import kr.co.solbipos.sys.service.auth.authgroup.AuthGroupService;

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
    public String list(HttpServletRequest request, HttpServletResponse response,
            Model model) {
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
    public Result list(AuthGroup authGroup, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        
        //권한 그룹 정보 조회 - 시스템일 경우 전체, 대리점은 대상 조직이 자신인 데이터만 조회
        List<DefaultMap<String>> list = service.list(authGroup);

        //TODO 리소스 정보 조회 - 자신이 가진 권한
        return returnListJson(Status.OK, list, authGroup);
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
    public Result save(AuthGroup[] authGroups, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfo sessionInfo = sessionService.getSessionInfo(request);

        int result = service.save(authGroups, sessionInfo);
        
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
    public Result listResrce(AuthGroup authGroup, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        
        //TODO 리소스 정보 조회 - 자신이 가진 권한
        List<DefaultMap<String>> list = service.list(authGroup);

        return returnListJson(Status.OK, list, authGroup);
    }

    /**
     * 저장 - 리소스 정보
     * 
     * @param authGroup
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "saveResrc.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveResrc(AuthGroup authGroups, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        SessionInfo sessionInfo = sessionService.getSessionInfo(request);
        
        //TODO 리소스 정보 저장
        
        return returnJson(Status.OK, null);
    }

}