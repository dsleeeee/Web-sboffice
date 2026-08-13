package kr.co.solbipos.sys.auth.smsMenuAuth.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.auth.smsMenuAuth.service.SmsMenuAuthService;
import kr.co.solbipos.sys.auth.smsMenuAuth.service.SmsMenuAuthVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : SmsMenuAuthController.java
 * @Description : 시스템관리 > 권한관리 > SMS화면관리(관리자)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.08.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/sys/auth/smsMenuAuth")
public class SmsMenuAuthController {

    private final SessionService sessionService;
    private final SmsMenuAuthService smsMenuAuthService;

    @Autowired
    public SmsMenuAuthController(SessionService sessionService,
                                 SmsMenuAuthService smsMenuAuthService) {
        this.sessionService = sessionService;
        this.smsMenuAuthService = smsMenuAuthService;
    }

    /** SMS화면관리(관리자) 페이지 이동 */
    @RequestMapping(value = "/smsMenuAuth/view.sb", method = RequestMethod.GET)
    public String view() {
        return "sys/auth/smsMenuAuth/smsMenuAuth";
    }

    /**
     * SMS화면관리(관리자) - SMS 메뉴 조회
     * @param   request
     * @param   smsMenuAuthVO
     * @return  String
     * @author  김유승
     * @since   2026. 08. 10.
     */
    @RequestMapping(value = "/smsMenuAuth/getSmsMenuList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsMenuList(SmsMenuAuthVO smsMenuAuthVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<String>> list = smsMenuAuthService.getSmsMenuList(sessionInfoVO, smsMenuAuthVO);

        return returnListJson(Status.OK, list, smsMenuAuthVO);
    }

    /**
     * SMS화면관리(관리자) - SMS 메뉴에 등록된 사용자 조회
     * @param   request
     * @param   smsMenuAuthVO
     * @return  String
     * @author  김유승
     * @since   2026. 08. 10.
     */
    @RequestMapping(value = "/smsMenuAuth/getRegUserList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getRegUserList(SmsMenuAuthVO smsMenuAuthVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<String>> list = smsMenuAuthService.getRegUserList(sessionInfoVO, smsMenuAuthVO);

        return returnListJson(Status.OK, list, smsMenuAuthVO);
    }

    /**
     * SMS화면관리(관리자) - SMS 메뉴에 미등록된 사용자 조회
     * @param   request
     * @param   smsMenuAuthVO
     * @return  String
     * @author  김유승
     * @since   2026. 08. 10.
     */
    @RequestMapping(value = "/smsMenuAuth/getNoRegUserList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getNoRegUserList(SmsMenuAuthVO smsMenuAuthVO, HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        List<DefaultMap<String>> list = smsMenuAuthService.getNoRegUserList(sessionInfoVO, smsMenuAuthVO);

        return returnListJson(Status.OK, list, smsMenuAuthVO);
    }

    /**
     * SMS화면관리(관리자) - 사용자 SMS 메뉴 권한 등록
     * @param   request
     * @param   smsMenuAuthVOS
     * @return  String
     * @author  김유승
     * @since   2026. 08. 10.
     */
    @RequestMapping(value = "/smsMenuAuth/insertSmsMenuAuth.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result insertSmsMenuAuth(@RequestBody SmsMenuAuthVO[] smsMenuAuthVOS,
                                    HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = smsMenuAuthService.insertSmsMenuAuth(smsMenuAuthVOS, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * SMS화면관리(관리자) - 사용자 SMS 메뉴 권한 삭제
     * @param   request
     * @param   smsMenuAuthVOS
     * @return  String
     * @author  김유승
     * @since   2026. 08. 10.
     */
    @RequestMapping(value = "/smsMenuAuth/deleteSmsMenuAuth.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteSmsMenuAuth(@RequestBody SmsMenuAuthVO[] smsMenuAuthVOS,
                                    HttpServletRequest request) {
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);
        int result = smsMenuAuthService.deleteSmsMenuAuth(smsMenuAuthVOS, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
