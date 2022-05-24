package kr.co.solbipos.adi.sms.smsChargeTab.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.smsCharge.service.SmsChargeService;
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
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * @Class Name : SmsChargeTabController.java
 * @Description : 부가서비스 > SMS관리 > SMS충전(탭)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.09.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/sms/smsChargeTab")
public class SmsChargeTabController {

    private final SessionService sessionService;
    private final SmsChargeService smsChargeService; // SMS충전

    /**
     * Constructor Injection
     */
    @Autowired
    public SmsChargeTabController(SessionService sessionService, SmsChargeService smsChargeService) {
        this.sessionService = sessionService;
        this.smsChargeService = smsChargeService; // SMS충전
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/smsChargeTab/list.sb", method = RequestMethod.GET)
    public String smsChargeTabView(HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // SMS충전결제 후 충전모듈을 닫기위해 페이지 이동
        String tabVal = "";
        System.out.println("tabVal : " + request.getParameter("tabVal"));
        if(!isEmpty(request.getParameter("tabVal"))) {
            tabVal = request.getParameter("tabVal");
        }
        model.addAttribute("tabVal", tabVal);

        // 전송요금설정 - 화면 권한 체크
        String msgOneAmtSettingAuth = smsChargeService.msgOneAmtSettingAuth(sessionInfoVO);
        model.addAttribute("msgOneAmtSettingAuth", msgOneAmtSettingAuth);
        System.out.println("msgOneAmtSettingAuth : " + msgOneAmtSettingAuth);

        return "adi/sms/smsChargeTab/smsChargeTab";
    }

}