package kr.co.solbipos.sys.stats.userBase.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.stats.userBase.service.UserBaseService;
import kr.co.solbipos.sys.stats.userBase.service.UserBaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/sys/stats/userBase")
public class UserBaseController {

    private final SessionService sessionService;
    private final UserBaseService userBaseService;

    /**
     * Constructor Injection
     */
    @Autowired
    public UserBaseController(SessionService sessionService, UserBaseService userBaseService) {
        this.sessionService = sessionService;
        this.userBaseService = userBaseService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/userBase/list.sb", method = RequestMethod.GET)
    public String userBaseView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sys/stats/userBase/userBase";
    }

    /**
     * 사용자기준 사용현황 조회
     *
     * @param userBaseVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 05. 27.
     */
    @RequestMapping(value = "/userBase/getUserBaseList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getUserBaseList(UserBaseVO userBaseVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = userBaseService.getUserBaseList(userBaseVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, userBaseVO);
    }

    /**
     * 사용자기준 사용현황 상세조회(사용자 탭)
     *
     * @param userBaseVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 05. 28.
     */
    @RequestMapping(value = "/user/getUserList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getUserList(UserBaseVO userBaseVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = userBaseService.getUserList(userBaseVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, userBaseVO);
    }

    /**
     * 사용자기준 사용현황 상세조회(사용메뉴 탭)
     *
     * @param userBaseVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 05. 28.
     */
    @RequestMapping(value = "/useMenu/getUseMenuList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getUseMenuList(UserBaseVO userBaseVO, HttpServletRequest request,
                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = userBaseService.getUseMenuList(userBaseVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, userBaseVO);
    }
}