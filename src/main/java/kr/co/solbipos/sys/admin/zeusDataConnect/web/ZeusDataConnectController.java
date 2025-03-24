package kr.co.solbipos.sys.admin.zeusDataConnect.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.admin.zeusDataConnect.service.ZeusDataConnectService;
import kr.co.solbipos.sys.admin.zeusDataConnect.service.ZeusDataConnectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : ZeusDataConnectController.java
 * @Description : 시스템관리 > 관리자기능 > 제우스데이터연동
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.03.19  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.03.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sys/admin/zeusDataConnect")
public class ZeusDataConnectController {

    private final SessionService sessionService;
    private final ZeusDataConnectService zeusDataConnectService;

    /**
     * Constructor Injection
     */
    @Autowired
    public ZeusDataConnectController(SessionService sessionService, ZeusDataConnectService zeusDataConnectService) {
        this.sessionService = sessionService;
        this.zeusDataConnectService = zeusDataConnectService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/zeusDataConnect/list.sb", method = RequestMethod.GET)
    public String zeusDataConnectView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sys/admin/zeusDataConnect/zeusDataConnect";
    }

    /**
     * 제우스데이터연동 - 제우스 PKG 호출 01
     *
     * @param zeusDataConnectVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2025. 03. 19.
     */
    @RequestMapping(value = "/zeusDataConnect/getZeusPkg01.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getZeusPkg01(@RequestBody ZeusDataConnectVO zeusDataConnectVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = zeusDataConnectService.getZeusPkg01(zeusDataConnectVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 제우스데이터연동 - 제우스 PKG 호출 02
     *
     * @param zeusDataConnectVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2025. 03. 19.
     */
    @RequestMapping(value = "/zeusDataConnect/getZeusPkg02.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getZeusPkg02(@RequestBody ZeusDataConnectVO zeusDataConnectVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = zeusDataConnectService.getZeusPkg02(zeusDataConnectVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 제우스데이터연동 - 제우스->링크 데이터연동
     *
     * @param zeusDataConnectVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2025. 03. 19.
     */
    @RequestMapping(value = "/zeusDataConnect/getZeusPkg01Call.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getZeusPkg01Call(@RequestBody ZeusDataConnectVO zeusDataConnectVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = zeusDataConnectService.getZeusPkg01Call(zeusDataConnectVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 제우스데이터연동 - 연동신청처리
     *
     * @param zeusDataConnectVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2025. 03. 19.
     */
    @RequestMapping(value = "/zeusDataConnect/getZeusPkg02Call.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getZeusPkg02Call(@RequestBody ZeusDataConnectVO zeusDataConnectVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = zeusDataConnectService.getZeusPkg02Call(zeusDataConnectVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 제우스데이터연동 - 조회
     *
     * @param zeusDataConnectVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2025. 03. 19.
     */
    @RequestMapping(value = "/zeusDataConnect/getZeusDataConnectList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBoardMasterList(ZeusDataConnectVO zeusDataConnectVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = zeusDataConnectService.getZeusDataConnectList(zeusDataConnectVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, zeusDataConnectVO);
    }
}