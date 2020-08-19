package kr.co.solbipos.sys.stats.menuBase.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.stats.menuBase.service.MenuBaseService;
import kr.co.solbipos.sys.stats.menuBase.service.MenuBaseVO;
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
@RequestMapping("/sys/stats/menuBase")
public class MenuBaseController {

    private final SessionService sessionService;
    private final MenuBaseService menuBaseService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MenuBaseController(SessionService sessionService, MenuBaseService menuBaseService) {
        this.sessionService = sessionService;
        this.menuBaseService = menuBaseService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/menuBase/list.sb", method = RequestMethod.GET)
    public String menuBaseView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sys/stats/menuBase/menuBase";
    }

    /**
     * 메뉴기준 사용현황 조회
     *
     * @param menuBaseVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 05. 25.
     */
    @RequestMapping(value = "/menuBase/getMenuBaseList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMenuBaseList(MenuBaseVO menuBaseVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = menuBaseService.getMenuBaseList(menuBaseVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, menuBaseVO);
    }

    /**
     * 메뉴기준 사용현황 상세조회
     *
     * @param menuBaseVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 05. 26.
     */
    @RequestMapping(value = "/menuBase/getMenuBaseDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMenuBaseDetailList(MenuBaseVO menuBaseVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = menuBaseService.getMenuBaseDetailList(menuBaseVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, menuBaseVO);
    }
}