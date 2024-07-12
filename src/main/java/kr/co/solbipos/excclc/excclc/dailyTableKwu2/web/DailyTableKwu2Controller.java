package kr.co.solbipos.excclc.excclc.dailyTableKwu2.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.excclc.excclc.dailyTableKwu2.service.DailyTableKwu2Service;
import kr.co.solbipos.excclc.excclc.dailyTableKwu2.service.DailyTableKwu2VO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : DailyTableKwu2Controller.java
 * @Description : 광운대 > 광운대일마감 > 일일일계표3
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.07.11
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/excclc/excclc/dailyTableKwu2")
public class DailyTableKwu2Controller {

    private final SessionService sessionService;
    private final DailyTableKwu2Service dailyTableKwu2Service;

    /**
     * Constructor Injection
     */
    public DailyTableKwu2Controller(SessionService sessionService, DailyTableKwu2Service dailyTableKwu2Service) {
        this.sessionService = sessionService;
        this.dailyTableKwu2Service = dailyTableKwu2Service;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/dailyTableKwu2/view.sb", method = RequestMethod.GET)
    public String dailyTableKwu2View(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "excclc/excclc/dailyTableKwu2/dailyTableKwu2";
    }

    /**
     * 일일일계표3 - 조회
     *
     * @param dailyTableKwu2VO
     * @param request
     * @return  Object
     * @author  김설아
     * @since   2024. 07. 11.
     */
    @RequestMapping(value="/dailyTableKwu2/getDailyTableKwu2List.sb", method=RequestMethod.POST)
    @ResponseBody
    public Result getDailyTableKwu2List(DailyTableKwu2VO dailyTableKwu2VO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        Map<String, Object> rtnMap = dailyTableKwu2Service.getDailyTableKwu2List(dailyTableKwu2VO, sessionInfoVO);

        return returnJson(Status.OK, rtnMap);
    }

    /**
     * 일일일계표3 - 조회
     *
     * @param dailyTableKwu2VO
     * @param request
     * @return  Object
     * @author  김설아
     * @since   2024. 07. 11.
     */
    @RequestMapping(value="/dailyTableKwu2/getDailyTableKwu2List1.sb", method=RequestMethod.POST)
    @ResponseBody
    public Result getDailyTableKwu2List1(DailyTableKwu2VO dailyTableKwu2VO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        Map<String, Object> rtnMap = dailyTableKwu2Service.getDailyTableKwu2List1(dailyTableKwu2VO, sessionInfoVO);

        return returnJson(Status.OK, rtnMap);
    }

    /**
     * 일일일계표3 - 조회
     *
     * @param dailyTableKwu2VO
     * @param request
     * @return  Object
     * @author  김설아
     * @since   2024. 07. 11.
     */
    @RequestMapping(value="/dailyTableKwu2/getDailyTableKwu2List2.sb", method=RequestMethod.POST)
    @ResponseBody
    public Result getDailyTableKwu2List2(DailyTableKwu2VO dailyTableKwu2VO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        Map<String, Object> rtnMap = dailyTableKwu2Service.getDailyTableKwu2List2(dailyTableKwu2VO, sessionInfoVO);

        return returnJson(Status.OK, rtnMap);
    }
}