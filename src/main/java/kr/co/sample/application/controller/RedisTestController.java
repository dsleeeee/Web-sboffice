package kr.co.sample.application.controller;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @author 정용길
 */

@Controller
public class RedisTestController {

    private final SessionService sessionService;

    /** Constructor Injection */
    @Autowired
    public RedisTestController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "exRedis.sb", method = RequestMethod.GET)
    public String exGridPage(Model model) {
        return "application/sample/exRedis";
    }

    /**
     *
     * @param request
     * @param tempDomain
     * @param model
     * @return
     */
    @RequestMapping(value = "exRedisSet.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result exRedis(HttpServletRequest request, HttpServletResponse response,
            SessionInfoVO tempDomain, Model model) {

        String key = sessionService.setSessionInfo(request, response, tempDomain);

        return returnJson(Status.OK, key);
    }

    /**
     *
     * @param sessionInfoVO
     * @param model
     * @return
     */
    @RequestMapping(value = "exRedisGet.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result exRedisGet(SessionInfoVO sessionInfoVO, Model model) {

        SessionInfoVO result = sessionService.getSessionInfo(sessionInfoVO.getSessionId());

        return returnJson(Status.OK, result);
    }
}


