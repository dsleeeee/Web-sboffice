package kr.co.solbipos.adi.sms.resveCountStatus.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.adi.sms.resveCountStatus.service.ResveCountStatusService;
import kr.co.solbipos.adi.sms.resveCountStatus.service.ResveCountStatusVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
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
 * @Class Name : ResveCountStatusController.java
 * @Description : 부가서비스 > SMS분석 > 보나비문자전송현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.05.02  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.05.02
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/sms/resveCountStatus")
public class ResveCountStatusController {

    private final SessionService sessionService;
    private final ResveCountStatusService resveCountStatusService;

    /**
     * Constructor Injection
     */
    @Autowired
    public ResveCountStatusController(SessionService sessionService, ResveCountStatusService resveCountStatusService) {
        this.sessionService = sessionService;
        this.resveCountStatusService = resveCountStatusService;
    }


    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/resveCountStatus/list.sb", method = RequestMethod.GET)
    public String resveCountStatus(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "adi/sms/resveCountStatus/resveCountStatus";
    }

    /**
     * 보나비예약건수현황 - 조회
     * @param   request
     * @param   response
     * @param   resveCountStatusVO
     * @param   model
     * @return  Result
     * @author  김유승
     * @since   2025. 05. 02.
     */

    @RequestMapping(value = "/resveCountStatus/getResveCountStatusList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getResveCountStatusList(HttpServletRequest request, HttpServletResponse response,
                                          ResveCountStatusVO resveCountStatusVO, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo();

        List<DefaultMap<String>> list = resveCountStatusService.getResveCountStatusList(resveCountStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, resveCountStatusVO);

    }
}
