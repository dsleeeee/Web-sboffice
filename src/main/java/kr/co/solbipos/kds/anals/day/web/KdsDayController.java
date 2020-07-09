package kr.co.solbipos.kds.anals.day.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kds.anals.day.service.KdsDayService;
import kr.co.solbipos.kds.anals.day.service.KdsDayVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * KdsDayController.java
 */
@Controller
@RequestMapping(value = "/kds/anals/day/")
public class KdsDayController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final KdsDayService kdsDayService;
    private final SessionService sessionService;

    @Autowired
    public KdsDayController(KdsDayService kdsDayService, SessionService sessionService) {
        this.kdsDayService = kdsDayService;
        this.sessionService = sessionService;
    }
    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "view/list.sb", method = RequestMethod.GET)
    public String kdsDayList(HttpServletRequest request, HttpServletResponse response, Model model) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        return "kds/anals/view/kdsDay";
    }
    /**
     * 조회
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "view/getKdsDay.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getKdsDay(KdsDayVO kdsDayVO, HttpServletRequest request,
                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> result = kdsDayService.getKdsDay(kdsDayVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }
}
