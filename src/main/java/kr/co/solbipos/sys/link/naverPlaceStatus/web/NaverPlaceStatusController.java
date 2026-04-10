package kr.co.solbipos.sys.link.naverPlaceStatus.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.link.naverPlaceStatus.service.NaverPlaceStatusService;
import kr.co.solbipos.sys.link.naverPlaceStatus.service.NaverPlaceStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Class Name : NaverPlaceStatusController.java
 * @Description : 시스템관리 > 연동 > 네이버플레이스현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.03.11  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.03.11
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/sys/link/naverPlaceStatus")
public class NaverPlaceStatusController {

    private final SessionService sessionService;
    private final NaverPlaceStatusService naverPlaceStatusService;

    /**
     * Constructor Injection
     */
    @Autowired
    public NaverPlaceStatusController(SessionService sessionService, NaverPlaceStatusService naverPlaceStatusService) {
        this.sessionService = sessionService;
        this.naverPlaceStatusService = naverPlaceStatusService;
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
        return "sys/link/naverPlaceStatus/naverPlaceStatus";
    }

    /**
     * 사용자현황 조회
     * @param naverPlaceStatusVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getUserStatusList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getUserStatusList(NaverPlaceStatusVO naverPlaceStatusVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = naverPlaceStatusService.getUserStatusList(naverPlaceStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, naverPlaceStatusVO);
    }

    /**
     * 접속현황 조회
     * @param naverPlaceStatusVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/getConnectStatusList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getConnectStatusList(NaverPlaceStatusVO naverPlaceStatusVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = naverPlaceStatusService.getConnectStatusList(naverPlaceStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, naverPlaceStatusVO);
    }

    /**
     * 연동정보 초기화
     * @param naverPlaceStatusVO
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/naverPlaceStatusReset.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result naverPlaceStatusReset(@RequestBody NaverPlaceStatusVO naverPlaceStatusVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = naverPlaceStatusService.naverPlaceStatusReset(naverPlaceStatusVO, sessionInfoVO);

        return ReturnUtil.returnJson(Status.OK, result);
    }

}
