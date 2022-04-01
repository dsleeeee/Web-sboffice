package kr.co.solbipos.adi.alimtalk.alimtalkSendHist.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.alimtalk.alimtalkSendHist.service.AlimtalkSendHistService;
import kr.co.solbipos.adi.alimtalk.alimtalkSendHist.service.AlimtalkSendHistVO;
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
 * @Class Name : AlimtalkSendHistController.java
 * @Description : 부가서비스 > 알림톡관리 > 알림톡 전송이력
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.30  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.03.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/alimtalk/alimtalkSendHist")
public class AlimtalkSendHistController {

    private final SessionService sessionService;
    private final AlimtalkSendHistService alimtalkSendHistService;

    /**
     * Constructor Injection
     */
    @Autowired
    public AlimtalkSendHistController(SessionService sessionService, AlimtalkSendHistService alimtalkSendHistService) {
        this.sessionService = sessionService;
        this.alimtalkSendHistService = alimtalkSendHistService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
//    @RequestMapping(value = "/alimtalkSendHist/list.sb", method = RequestMethod.GET)
//    public String alimtalkSendHistView(HttpServletRequest request, HttpServletResponse response, Model model) {
//
//        return "adi/alimtalk/alimtalkSendHist/alimtalkSendHist";
//    }

    /**
     * 알림톡 전송이력 - 조회
     *
     * @param alimtalkSendHistVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 30.
     */
    @RequestMapping(value = "/alimtalkSendHist/getAlimtalkSendHistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkSendHistList(AlimtalkSendHistVO alimtalkSendHistVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = alimtalkSendHistService.getAlimtalkSendHistList(alimtalkSendHistVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, alimtalkSendHistVO);
    }

    /**
     * 알림톡 수신자정보 팝업 - 조회
     *
     * @param alimtalkSendHistVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 30.
     */
    @RequestMapping(value = "/alimtalkAddresseeDtl/getAlimtalkAddresseeDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkAddresseeDtlList(AlimtalkSendHistVO alimtalkSendHistVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = alimtalkSendHistService.getAlimtalkAddresseeDtlList(alimtalkSendHistVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, alimtalkSendHistVO);
    }
}