package kr.co.solbipos.adi.sms.marketingSmsSend.web;

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
 * @Class Name : MarketingSmsSendController.java
 * @Description : 부가서비스 > SMS관리 > 마케팅용 SMS전송
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.08.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.08.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/sms/marketingSmsSend")
public class MarketingSmsSendController {

    private final SessionService sessionService;
    private final MarketingSmsSendService marketingSmsSendService;
    private final CmmCodeUtil cmmCodeUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public MarketingSmsSendController(SessionService sessionService, MarketingSmsSendService marketingSmsSendService, CmmCodeUtil cmmCodeUtil) {
        this.sessionService = sessionService;
        this.marketingSmsSendService = marketingSmsSendService;
        this.cmmCodeUtil = cmmCodeUtil;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/marketingSmsSend/list.sb", method = RequestMethod.GET)
    public String marketingSmsSendView(HttpServletRequest request, HttpServletResponse response, Model model) {

        MarketingSmsSendVO marketingSmsSendVO = new MarketingSmsSendVO();
        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        // SMS전송 - 메세지그룹 조회
        List<DefaultMap<String>> msgGrpColList = marketingSmsSendService.getMsgGrpColList(marketingSmsSendVO, sessionInfoVO);
        model.addAttribute("msgGrpColList", msgGrpColList);

        // 회원등급 리스트 조회
        List membrClassList = marketingSmsSendService.getMembrClassList(marketingSmsSendVO, sessionInfoVO);
        String membrClassListAll = cmmCodeUtil.assmblObj(membrClassList, "name", "value", UseYn.ALL);
        model.addAttribute("memberClassList", membrClassListAll);

        return "adi/sms/marketingSmsSend/marketingSmsSend";
    }

    /**
     * 메세지관리 - 메세지서식 조회
     *
     * @param marketingSmsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 08. 10.
     */
    @RequestMapping(value = "/marketingSmsSend/getMarketingSmsSendMsgManageDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMarketingSmsSendMsgManageDtlList(MarketingSmsSendVO marketingSmsSendVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = marketingSmsSendService.getMarketingSmsSendMsgManageDtlList(marketingSmsSendVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, marketingSmsSendVO);
    }

    /**
     * 마케팅용 SMS전송 - 회원 조회
     *
     * @param marketingSmsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 08. 10.
     */
    @RequestMapping(value = "/marketingSmsSend/getMarketingSmsSendList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMarketingSmsSendList(MarketingSmsSendVO marketingSmsSendVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = marketingSmsSendService.getMarketingSmsSendList(marketingSmsSendVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, marketingSmsSendVO);
    }
}