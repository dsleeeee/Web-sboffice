package kr.co.solbipos.adi.sms.smsSendTab.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.marketingSmsSend.service.MarketingSmsSendService;
import kr.co.solbipos.adi.sms.marketingSmsSend.service.MarketingSmsSendVO;
import kr.co.solbipos.adi.sms.sendStatus.service.SendStatusService;
import kr.co.solbipos.adi.sms.sendStatus.service.SendStatusVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    public static String SITE_CD      = "AGSVU";
    public static String WEB_SITEID   = "J21101407426";
    public static String ENC_KEY      = "beba66643a50ad06b9bd92b6bcf6239d8199071bc8ffd361a81441f651f8efd2";
    public static String RET_URL      = "https://neo.solbipos.com/adi/sms/smsTelNoManage/smsTelNoManage/getSmsTelNoRegisterRequest.sb";
    public static String GW_URL       = "https://cert.kcp.co.kr/kcp_cert/cert_view.jsp";

    /**
     * Constructor Injection
     */
    @Autowired
    public SmsSendTabController(SessionService sessionService, MarketingSmsSendService marketingSmsSendService, CmmCodeUtil cmmCodeUtil, SendStatusService sendStatusService) {
        this.sessionService = sessionService;
        this.marketingSmsSendService = marketingSmsSendService; // 마케팅용 SMS전송
        this.cmmCodeUtil = cmmCodeUtil;
        this.sendStatusService = sendStatusService; // 문자전송현황
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

        // 로컬이나 개발은 테스트버전으로 연결되도록
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        String url = builder.build().toUri().toString();

        if (StringUtils.substring(url,4,10).equals("://192") || StringUtils.substring(url,4,10).equals("://loc")) {
            SITE_CD = "S6186";
            WEB_SITEID = "";
            ENC_KEY = "E66DCEB95BFBD45DF9DFAEEBCB092B5DC2EB3BF0";
            RET_URL = "https://192.168.0.72:2020/adi/sms/smsTelNoManage/smsTelNoManage/getSmsTelNoRegisterRequest.sb";
            GW_URL = "https://testcert.kcp.co.kr/kcp_cert/cert_view.jsp";
        }

        model.addAttribute("siteCd", SITE_CD);
        model.addAttribute("webSiteid", WEB_SITEID);
        model.addAttribute("encKey", ENC_KEY);
        model.addAttribute("retUrl", RET_URL);
        model.addAttribute("gwUrl", GW_URL);

        return "adi/sms/smsSendTab/smsSendTab";
    }

}