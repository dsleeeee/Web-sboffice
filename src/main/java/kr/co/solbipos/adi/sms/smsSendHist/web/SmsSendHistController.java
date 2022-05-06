package kr.co.solbipos.adi.sms.smsSendHist.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.smsSendHist.service.SmsSendHistService;
import kr.co.solbipos.adi.sms.smsSendHist.service.SmsSendHistVO;
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
 * @Class Name : SmsSendHistController.java
 * @Description : 부가서비스 > SMS관리 > SMS전송이력
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.08.05  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.08.05
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/sms/smsSendHist")
public class SmsSendHistController {

    private final SessionService sessionService;
    private final SmsSendHistService smsSendHistService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SmsSendHistController(SessionService sessionService, SmsSendHistService smsSendHistService) {
        this.sessionService = sessionService;
        this.smsSendHistService = smsSendHistService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/smsSendHist/list.sb", method = RequestMethod.GET)
    public String smsSendHistView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "adi/sms/smsSendHist/smsSendHist";
    }

    /**
     * SMS전송이력 - 조회
     *
     * @param smsSendHistVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 08. 05.
     */
    @RequestMapping(value = "/smsSendHist/getSmsSendHistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsSendHistList(SmsSendHistVO smsSendHistVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = smsSendHistService.getSmsSendHistList(smsSendHistVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, smsSendHistVO);
    }

    /**
     * SMS전송이력 - 엑셀 조회
     *
     * @param smsSendHistVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 05. 04.
     */
    @RequestMapping(value = "/smsSendHist/getSmsSendHistExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsSendHistExcelList(SmsSendHistVO smsSendHistVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = smsSendHistService.getSmsSendHistExcelList(smsSendHistVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, smsSendHistVO);
    }

    /**
     * 수신자정보 팝업 - 조회
     *
     * @param smsSendHistVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 10. 05.
     */
    @RequestMapping(value = "/addresseeDtl/getAddresseeDtlList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAddresseeDtlList(SmsSendHistVO smsSendHistVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = smsSendHistService.getAddresseeDtlList(smsSendHistVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, smsSendHistVO);
    }
}