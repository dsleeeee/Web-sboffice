package kr.co.solbipos.adi.sms.smsCharge.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.smsCharge.service.SmsChargeService;
import kr.co.solbipos.adi.sms.smsCharge.service.SmsChargeVO;
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
 * @Class Name : SmsChargeController.java
 * @Description : 부가서비스 > SMS관리 > SMS충전
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.09  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.06.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/sms/smsCharge")
public class SmsChargeController {

    private final SessionService sessionService;
    private final SmsChargeService smsChargeService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SmsChargeController(SessionService sessionService, SmsChargeService smsChargeService) {
        this.sessionService = sessionService;
        this.smsChargeService = smsChargeService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/smsCharge/list.sb", method = RequestMethod.GET)
    public String smsChargeView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "adi/sms/smsCharge/smsCharge";
    }

}
