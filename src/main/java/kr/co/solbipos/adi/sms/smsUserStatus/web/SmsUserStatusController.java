package kr.co.solbipos.adi.sms.smsUserStatus.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.adi.sms.smsUserStatus.service.SmsUserStatusService;
import kr.co.solbipos.adi.sms.smsUserStatus.service.SmsUserStatusVO;
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
 * @Class Name : SmsUserStatusController.java
 * @Description : 부가서비스 > SMS관리 > SMS사용현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.07  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.08.07
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/sms/smsUserStatus")
public class SmsUserStatusController {

    private final SessionService sessionService;
    private final SmsUserStatusService smsUserStatusService;

    @Autowired
    public SmsUserStatusController(SessionService sessionService, SmsUserStatusService smsUserStatusService) {
        this.sessionService = sessionService;
        this.smsUserStatusService = smsUserStatusService;
    }

    /**
     * 페이지 이동
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "adi/sms/smsUserStatus/smsUserStatusTab";
    }

    /**
     * SMS사용자 - 목록 조회
     */
    @RequestMapping(value = "/getSmsUserList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsUserList(SmsUserStatusVO smsUserStatusVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        List<DefaultMap<Object>> result = smsUserStatusService.getSmsUserList(smsUserStatusVO);

        return ReturnUtil.returnListJson(Status.OK, result, smsUserStatusVO);
    }

    /**
     * 발신번호 - 목록 조회
     */
    @RequestMapping(value = "/getSendTelNoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSendTelNoList(SmsUserStatusVO smsUserStatusVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        List<DefaultMap<Object>> result = smsUserStatusService.getSendTelNoList(smsUserStatusVO);

        return ReturnUtil.returnListJson(Status.OK, result, smsUserStatusVO);
    }

    /**
     * 충전현황 - 목록 조회
     */
    @RequestMapping(value = "/getSmsChargeStatusList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsChargeStatusList(SmsUserStatusVO smsUserStatusVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        List<DefaultMap<Object>> result = smsUserStatusService.getSmsChargeStatusList(smsUserStatusVO);

        return ReturnUtil.returnListJson(Status.OK, result, smsUserStatusVO);
    }

    /**
     * 전송이력 - 목록 조회
     */
    @RequestMapping(value = "/getSmsSendHistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsSendHistList(SmsUserStatusVO smsUserStatusVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        List<DefaultMap<Object>> result = smsUserStatusService.getSmsSendHistList(smsUserStatusVO);

        return ReturnUtil.returnListJson(Status.OK, result, smsUserStatusVO);
    }
}
