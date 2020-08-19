package kr.co.solbipos.sys.stats.webLogin.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.stats.webLogin.service.WebLoginService;
import kr.co.solbipos.sys.stats.webLogin.service.WebLoginVO;
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
@RequestMapping("/sys/stats/webLogin")
public class WebLoginController {

    private final SessionService sessionService;
    private final WebLoginService webLoginService;

    /**
     * Constructor Injection
     */
    @Autowired
    public WebLoginController(SessionService sessionService, WebLoginService webLoginService) {
        this.sessionService = sessionService;
        this.webLoginService = webLoginService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/webLogin/list.sb", method = RequestMethod.GET)
    public String webLoginView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sys/stats/webLogin/webLogin";
    }

    /**
     * 웹로그인 현황 조회
     *
     * @param webLoginVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 06. 01.
     */
    @RequestMapping(value = "/webLogin/getWebLoginList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getWebLoginList(WebLoginVO webLoginVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = webLoginService.getWebLoginList(webLoginVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, webLoginVO);
    }

    /**
     * 일자별 현황 조회
     *
     * @param webLoginVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 06. 02.
     */
    @RequestMapping(value = "/webLogin/getWebLoginDayDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getWebLoginDayDetailList(WebLoginVO webLoginVO, HttpServletRequest request,
                                  HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = webLoginService.getWebLoginDayDetailList(webLoginVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, webLoginVO);
    }

    /**c
     * 로그인 현황 조회
     *
     * @param webLoginVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 06. 09.
     */
    @RequestMapping(value = "/webLogin/getWebLoginLoginDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getWebLoginLoginDetailList(WebLoginVO webLoginVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = webLoginService.getWebLoginLoginDetailList(webLoginVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, webLoginVO);
    }
}