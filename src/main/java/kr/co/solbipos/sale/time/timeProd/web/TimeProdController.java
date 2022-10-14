package kr.co.solbipos.sale.time.timeProd.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.time.timeProd.service.TimeProdVO;
import kr.co.solbipos.sale.time.timeProd.service.TimeProdService;
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
 * @Class Name : TimeProdController.java
 * @Description : 맘스터치 > 시간대별매출 > 상품별 시간대 매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.12  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.12
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sale/time/timeProd")
public class TimeProdController {

    private final SessionService sessionService;
    private final TimeProdService timeProdService;

    /**
     * Constructor Injection
     */
    @Autowired
    public TimeProdController(SessionService sessionService, TimeProdService timeProdService) {
        this.sessionService = sessionService;
        this.timeProdService = timeProdService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/timeProd/list.sb", method = RequestMethod.GET)
    public String timeProdView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "sale/time/timeProd/timeProd";
    }


    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   timeProdVO
     * @return  String
     * @author  권지현
     * @since   2022.10.12
     */
    @RequestMapping(value = "/timeProd/getTimeProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTimeProdList(HttpServletRequest request, HttpServletResponse response, Model model, TimeProdVO timeProdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = timeProdService.getTimeProdList(timeProdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, timeProdVO);
    }

    /**
     * 조회
     * @param   request
     * @param   response
     * @param   model
     * @param   timeProdVO
     * @return  String
     * @author  권지현
     * @since   2022.10.12
     */
    @RequestMapping(value = "/timeProd/getTimeProdExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getTimeProdExcelList(HttpServletRequest request, HttpServletResponse response, Model model, TimeProdVO timeProdVO) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> list = timeProdService.getTimeProdExcelList(timeProdVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, timeProdVO);
    }

}