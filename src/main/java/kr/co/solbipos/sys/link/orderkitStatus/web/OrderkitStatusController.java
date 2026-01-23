package kr.co.solbipos.sys.link.orderkitStatus.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.link.orderkitStatus.service.OrderkitStatusService;
import kr.co.solbipos.sys.link.orderkitStatus.service.OrderkitStatusVO;
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
 * @Class Name : OrderkitStatusController.java
 * @Description : 시스템관리 > 연동 > 오더킷현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.01.21  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.01.21
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sys/link/orderkitStatus")
public class OrderkitStatusController {

    private final SessionService sessionService;
    private final OrderkitStatusService orderkitStatusService;

    /**
     * Constructor Injection
     */
    @Autowired
    public OrderkitStatusController(SessionService sessionService, OrderkitStatusService orderkitStatusService) {
        this.sessionService = sessionService;
        this.orderkitStatusService = orderkitStatusService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sys/link/orderkitStatus/orderkitStatus";
    }

    /**
     * 사용자현황 조회
     * @param orderkitStatusVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getUserStatusList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getUserStatusList(OrderkitStatusVO orderkitStatusVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = orderkitStatusService.getUserStatusList(orderkitStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, orderkitStatusVO);
    }

    /**
     * 접속현황 조회
     * @param orderkitStatusVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getConnectStatusList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getConnectStatusList(OrderkitStatusVO orderkitStatusVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = orderkitStatusService.getConnectStatusList(orderkitStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, orderkitStatusVO);
    }
}
