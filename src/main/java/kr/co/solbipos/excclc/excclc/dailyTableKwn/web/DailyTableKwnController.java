package kr.co.solbipos.excclc.excclc.dailyTableKwn.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.excclc.excclc.dailyTableKwn.service.DailyTableKwnService;
import kr.co.solbipos.excclc.excclc.dailyTableKwn.service.DailyTableKwnVO;
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
 * @Class Name : DailyTableKwnController.java
 * @Description : 광운대 > 광운대일마감 > 일일일계표2
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.05  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.10.05
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/excclc/excclc/dailyTableKwn")
public class DailyTableKwnController {

    private final SessionService sessionService;
    private final DailyTableKwnService dailyTableKwnService;

    /**
     * Constructor Injection
     */
    public DailyTableKwnController(SessionService sessionService, DailyTableKwnService dailyTableKwnService) {
        this.sessionService = sessionService;
        this.dailyTableKwnService = dailyTableKwnService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/dailyTableKwn/view.sb", method = RequestMethod.GET)
    public String dailyTableKwnView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "excclc/excclc/dailyTableKwn/dailyTableKwn";
    }

    /**
     * 일일일계표2 - 조회
     *
     * @param dailyTableKwnVO
     * @param request
     * @return  Object
     * @author  김설아
     * @since   2022. 10. 05.
     */
    @RequestMapping(value="/dailyTableKwn/getDailyTableKwnList.sb", method=RequestMethod.POST)
    @ResponseBody
    public Result getDailyTableKwnList(DailyTableKwnVO dailyTableKwnVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        Map<String, Object> rtnMap = dailyTableKwnService.getDailyTableKwnList(dailyTableKwnVO, sessionInfoVO);

        return returnJson(Status.OK, rtnMap);
    }

    /**
     * 일일일계표2 - 조회
     *
     * @param dailyTableKwnVO
     * @param request
     * @return  Object
     * @author  김설아
     * @since   2022. 10. 05.
     */
    @RequestMapping(value="/dailyTableKwn/getDailyTableKwnList1.sb", method=RequestMethod.POST)
    @ResponseBody
    public Result getDailyTableKwnList1(DailyTableKwnVO dailyTableKwnVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        Map<String, Object> rtnMap = dailyTableKwnService.getDailyTableKwnList1(dailyTableKwnVO, sessionInfoVO);

        return returnJson(Status.OK, rtnMap);
    }

    /**
     * 일일일계표2 - 조회
     *
     * @param dailyTableKwnVO
     * @param request
     * @return  Object
     * @author  김설아
     * @since   2022. 10. 05.
     */
    @RequestMapping(value="/dailyTableKwn/getDailyTableKwnList2.sb", method=RequestMethod.POST)
    @ResponseBody
    public Result getDailyTableKwnList2(DailyTableKwnVO dailyTableKwnVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        Map<String, Object> rtnMap = dailyTableKwnService.getDailyTableKwnList2(dailyTableKwnVO, sessionInfoVO);

        return returnJson(Status.OK, rtnMap);
    }
}