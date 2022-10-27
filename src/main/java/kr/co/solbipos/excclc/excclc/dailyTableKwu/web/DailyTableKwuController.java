package kr.co.solbipos.excclc.excclc.dailyTableKwu.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.excclc.excclc.dailyTableKwu.service.DailyTableKwuService;
import kr.co.solbipos.excclc.excclc.dailyTableKwu.service.DailyTableKwuVO;
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
 * @Class Name : DailyTableKwuController.java
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
@RequestMapping("/excclc/excclc/dailyTableKwu")
public class DailyTableKwuController {

    private final SessionService sessionService;
    private final DailyTableKwuService dailyTableKwuService;

    /**
     * Constructor Injection
     */
    public DailyTableKwuController(SessionService sessionService, DailyTableKwuService dailyTableKwuService) {
        this.sessionService = sessionService;
        this.dailyTableKwuService = dailyTableKwuService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/dailyTableKwu/view.sb", method = RequestMethod.GET)
    public String dailyTableKwuView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "excclc/excclc/dailyTableKwu/dailyTableKwu";
    }

    /**
     * 일일일계표2 - 조회
     *
     * @param dailyTableKwuVO
     * @param request
     * @return  Object
     * @author  김설아
     * @since   2022. 10. 05.
     */
    @RequestMapping(value="/dailyTableKwu/getDailyTableKwuList.sb", method=RequestMethod.POST)
    @ResponseBody
    public Result getDailyTableKwuList(DailyTableKwuVO dailyTableKwuVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        Map<String, Object> rtnMap = dailyTableKwuService.getDailyTableKwuList(dailyTableKwuVO, sessionInfoVO);

        return returnJson(Status.OK, rtnMap);
    }

    /**
     * 일일일계표2 - 조회
     *
     * @param dailyTableKwuVO
     * @param request
     * @return  Object
     * @author  김설아
     * @since   2022. 10. 05.
     */
    @RequestMapping(value="/dailyTableKwu/getDailyTableKwuList1.sb", method=RequestMethod.POST)
    @ResponseBody
    public Result getDailyTableKwuList1(DailyTableKwuVO dailyTableKwuVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        Map<String, Object> rtnMap = dailyTableKwuService.getDailyTableKwuList1(dailyTableKwuVO, sessionInfoVO);

        return returnJson(Status.OK, rtnMap);
    }

    /**
     * 일일일계표2 - 조회
     *
     * @param dailyTableKwuVO
     * @param request
     * @return  Object
     * @author  김설아
     * @since   2022. 10. 05.
     */
    @RequestMapping(value="/dailyTableKwu/getDailyTableKwuList2.sb", method=RequestMethod.POST)
    @ResponseBody
    public Result getDailyTableKwuList2(DailyTableKwuVO dailyTableKwuVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        Map<String, Object> rtnMap = dailyTableKwuService.getDailyTableKwuList2(dailyTableKwuVO, sessionInfoVO);

        return returnJson(Status.OK, rtnMap);
    }
}