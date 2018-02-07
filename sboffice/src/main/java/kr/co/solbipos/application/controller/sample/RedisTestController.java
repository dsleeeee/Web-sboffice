package kr.co.solbipos.application.controller.sample;

import static kr.co.solbipos.utils.grid.ReturnUtil.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.co.solbipos.application.domain.login.SessionInfo;
import kr.co.solbipos.service.session.SessionService;
import kr.co.solbipos.structure.JsonResult;
import kr.co.solbipos.structure.Result.Status;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 정용길
 */

@Slf4j
@Controller
public class RedisTestController {
// 브렌치 3 수정
    
    @Autowired 
    SessionService sessionService;

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
    public JsonResult exRedis(HttpServletRequest request, HttpServletResponse response,
            SessionInfo tempDomain, Model model) {

        String key = sessionService.setSessionInfo(request, response, tempDomain);

        return returnJson(Status.OK, key);
    }

    /**
     * 
     * @param sessionInfo
     * @param model
     * @return
     */
    @RequestMapping(value = "exRedisGet.sb", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult exRedisGet(SessionInfo sessionInfo, Model model) {

        SessionInfo result = sessionService.getSessionInfo(sessionInfo.getSessionId());

        return returnJson(Status.OK, result);
    }
}


