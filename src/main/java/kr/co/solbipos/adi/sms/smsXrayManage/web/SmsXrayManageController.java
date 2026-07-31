package kr.co.solbipos.adi.sms.smsXrayManage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.smsXrayManage.service.SmsXrayManageService;
import kr.co.solbipos.adi.sms.smsXrayManage.service.SmsXrayManageVO;
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

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : SmsXrayManageController.java
 * @Description : 부가서비스 > SMS관리 > SMS Xray관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.23  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.23
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/sms/smsXrayManage")
public class SmsXrayManageController {

    private final SessionService sessionService;
    private final SmsXrayManageService smsXrayManageService;

    @Autowired
    public SmsXrayManageController(SessionService sessionService, SmsXrayManageService smsXrayManageService) {
        this.sessionService = sessionService;
        this.smsXrayManageService = smsXrayManageService;
    }

    /**
     * 페이지 이동
     */
    @RequestMapping(value = "/smsXrayManage/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "adi/sms/smsXrayManage/smsXrayTab";
    }

    /**
     * SMS Xray관리 - 목록 조회
     */
    @RequestMapping(value = "/smsXrayManage/getSmsXrayManageList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsXrayManageList(SmsXrayManageVO smsXrayManageVO, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = smsXrayManageService.getSmsXrayManageList(smsXrayManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, smsXrayManageVO);
    }

    /**
     * SMS Xray관리 - 저장
     */
    @RequestMapping(value = "/smsXrayManage/saveSmsXrayManage.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveSmsXrayManage(@RequestBody SmsXrayManageVO[] smsXrayManageVOs, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = smsXrayManageService.saveSmsXrayManage(smsXrayManageVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 탐지/차단결과 로그 - 목록 조회
     */
    @RequestMapping(value = "/urlBlockLog/getUrlBlockLogList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getUrlBlockLogList(SmsXrayManageVO smsXrayManageVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = smsXrayManageService.getUrlBlockLogList(smsXrayManageVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, smsXrayManageVO);
    }
}
