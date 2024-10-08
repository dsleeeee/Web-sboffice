package kr.co.solbipos.adi.sms.smsSendTab.web;

import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.marketingSmsSend.service.MarketingSmsSendService;
import kr.co.solbipos.adi.sms.marketingSmsSend.service.MarketingSmsSendVO;
import kr.co.solbipos.adi.sms.sendStatus.service.SendStatusService;
import kr.co.solbipos.adi.sms.sendStatus.service.SendStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : SmsSendTabController.java
 * @Description : 부가서비스 > SMS관리 > SMS전송(탭)
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
@RequestMapping("/adi/sms/smsSendTab")
public class SmsSendTabController {

    private final SessionService sessionService;
    private final MarketingSmsSendService marketingSmsSendService; // 마케팅용 SMS전송
    private final CmmCodeUtil cmmCodeUtil;
    private final SendStatusService sendStatusService; // 문자전송현황
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public SmsSendTabController(SessionService sessionService, MarketingSmsSendService marketingSmsSendService, CmmCodeUtil cmmCodeUtil, SendStatusService sendStatusService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.marketingSmsSendService = marketingSmsSendService; // 마케팅용 SMS전송
        this.cmmCodeUtil = cmmCodeUtil;
        this.sendStatusService = sendStatusService; // 문자전송현황
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/smsSendTab/list.sb", method = RequestMethod.GET)
    public String smsSendTabView(HttpServletRequest request, HttpServletResponse response, Model model) {

        MarketingSmsSendVO marketingSmsSendVO = new MarketingSmsSendVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 마케팅용 SMS전송 - 메세지그룹 조회
        List<DefaultMap<String>> msgGrpAddColList = marketingSmsSendService.getMsgGrpColList(marketingSmsSendVO, sessionInfoVO);
        model.addAttribute("msgGrpAddColList", msgGrpAddColList);

        // 마케팅용 SMS전송 - 회원등급 리스트 조회
        List membrClassList = marketingSmsSendService.getMembrClassList(marketingSmsSendVO, sessionInfoVO);
        String membrClassListAll = cmmCodeUtil.assmblObj(membrClassList, "name", "value", UseYn.ALL);
        model.addAttribute("memberClassList", membrClassListAll);

        SendStatusVO sendStatusVO = new SendStatusVO();

        // SMS전송 - 메세지그룹 조회
        List<DefaultMap<String>> msgGrpColList = sendStatusService.getMsgGrpColList(sendStatusVO, sessionInfoVO);
        model.addAttribute("msgGrpColList", msgGrpColList);

        // 비매출회원SMS전송여부
        if ( "H".equals(sessionInfoVO.getOrgnFg().getCode()) ) {
            model.addAttribute("envst1273", CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1273"), "0"));
        } else if ( "S".equals(sessionInfoVO.getOrgnFg().getCode()) ) {
            model.addAttribute("envst1273", CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1273"), "0"));
        }

        return "adi/sms/smsSendTab/smsSendTab";
    }

}