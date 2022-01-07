package kr.co.solbipos.mobile.adi.sms.marketingSmsSend.web;

import kr.co.common.data.enums.UseYn;
import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.adi.sms.marketingSmsSend.service.MobileMarketingSmsSendService;
import kr.co.solbipos.mobile.adi.sms.marketingSmsSend.service.MobileMarketingSmsSendVO;
import kr.co.solbipos.adi.sms.marketingSmsSend.service.MarketingSmsSendService;
import kr.co.solbipos.adi.sms.marketingSmsSend.service.MarketingSmsSendVO;
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
 * @Class Name : MobileMarketingSmsSendController.java
 * @Description : (모바일) 부가서비스 > 마케팅용 SMS전송
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.01.06  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.01.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/mobile/adi/sms/marketingSmsSend")
public class MobileMarketingSmsSendController {

    private final SessionService sessionService;
    private final MobileMarketingSmsSendService mobileMarketingSmsSendService;
    private final MarketingSmsSendService marketingSmsSendService; // 마케팅용 SMS전송
    private final CmmCodeUtil cmmCodeUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileMarketingSmsSendController(SessionService sessionService, MobileMarketingSmsSendService mobileMarketingSmsSendService, MarketingSmsSendService marketingSmsSendService, CmmCodeUtil cmmCodeUtil) {
        this.sessionService = sessionService;
        this.mobileMarketingSmsSendService = mobileMarketingSmsSendService;
        this.marketingSmsSendService = marketingSmsSendService; // 마케팅용 SMS전송
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/mobileMarketingSmsSend/list.sb", method = RequestMethod.GET)
    public String mobileMarketingSmsSendView(HttpServletRequest request, HttpServletResponse response, Model model) {

        MarketingSmsSendVO marketingSmsSendVO = new MarketingSmsSendVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // 마케팅용 SMS전송 - 메세지그룹 조회
        List<DefaultMap<String>> msgGrpAddColList = marketingSmsSendService.getMsgGrpColList(marketingSmsSendVO, sessionInfoVO);
        model.addAttribute("msgGrpAddColList", msgGrpAddColList);

        // 마케팅용 SMS전송 - 회원등급 리스트 조회
        List membrClassList = marketingSmsSendService.getMembrClassList(marketingSmsSendVO, sessionInfoVO);
        String membrClassListAll = cmmCodeUtil.assmblObj(membrClassList, "name", "value", UseYn.ALL);
        model.addAttribute("memberClassList", membrClassListAll);

        return "mobile/adi/sms/marketingSmsSend/mobileMarketingSmsSend";
    }
}