package kr.co.solbipos.mobile.adi.sms.sendStatus.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.adi.sms.sendStatus.service.MobileSendStatusService;
import kr.co.solbipos.mobile.adi.sms.sendStatus.service.MobileSendStatusVO;
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
 * @Class Name : MobileSendStatusController.java
 * @Description : (모바일) 부가서비스 > 문자전송현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.01.04  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.01.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/mobile/adi/sms/sendStatus")
public class MobileSendStatusController {

    private final SessionService sessionService;
    private final MobileSendStatusService mobileSendStatusService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileSendStatusController(SessionService sessionService, MobileSendStatusService mobileSendStatusService) {
        this.sessionService = sessionService;
        this.mobileSendStatusService = mobileSendStatusService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/mobileSendStatus/list.sb", method = RequestMethod.GET)
    public String mobileSendStatusView(HttpServletRequest request, HttpServletResponse response, Model model) {

//        SendStatusVO sendStatusVO = new SendStatusVO();
//        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

//        // SMS전송 - 메세지그룹 조회
//        List<DefaultMap<String>> msgGrpColList = sendStatusService.getMsgGrpColList(sendStatusVO, sessionInfoVO);
//        model.addAttribute("msgGrpColList", msgGrpColList);
////        System.out.println("msgGrpColList : "+msgGrpColList);

        return "mobile/adi/sms/sendStatus/mobileSendStatus";
    }
}