package kr.co.solbipos.adi.sms.smsChargeHist.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.smsChargeHist.service.SmsChargeHistService;
import kr.co.solbipos.adi.sms.smsChargeHist.service.SmsChargeHistVO;
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
 * @Class Name : SmsChargeHistController.java
 * @Description : 부가서비스 > SMS관리 > SMS충전내역
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.08.19  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.08.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/sms/smsChargeHist")
public class SmsChargeHistController {

    private final SessionService sessionService;
    private final SmsChargeHistService smsChargeHistService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SmsChargeHistController(SessionService sessionService, SmsChargeHistService smsChargeHistService) {
        this.sessionService = sessionService;
        this.smsChargeHistService = smsChargeHistService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/smsChargeHist/list.sb", method = RequestMethod.GET)
    public String smsChargeHistView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "adi/sms/smsChargeHist/smsChargeHist";
    }

    /**
     * SMS충전내역 - 조회
     *
     * @param smsChargeHistVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 08. 19.
     */
    @RequestMapping(value = "/smsChargeHist/getSmsChargeHistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsChargeHistList(SmsChargeHistVO smsChargeHistVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = smsChargeHistService.getSmsChargeHistList(smsChargeHistVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, smsChargeHistVO);
    }

    /**
     * SMS충전내역 - 엑셀 조회
     *
     * @param smsChargeHistVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 05. 03.
     */
    @RequestMapping(value = "/smsChargeHist/getSmsChargeHistExcelList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsChargeHistExcelList(SmsChargeHistVO smsChargeHistVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = smsChargeHistService.getSmsChargeHistExcelList(smsChargeHistVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, smsChargeHistVO);
    }

    /**
     * SMS임의충전 팝업 - 조회
     *
     * @param smsChargeHistVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 08. 19.
     */
    @RequestMapping(value = "/smsChargeHist/getSmsChargeRegistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsChargeRegistList(SmsChargeHistVO smsChargeHistVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = smsChargeHistService.getSmsChargeRegistList(smsChargeHistVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, smsChargeHistVO);
    }

    /**
     * SMS임의충전 팝업 - 저장
     *
     * @param smsChargeHistVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 08. 19.
     */
    @RequestMapping(value = "/smsChargeHist/getSmsChargeRegistSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsChargeRegistSave(@RequestBody SmsChargeHistVO smsChargeHistVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = smsChargeHistService.getSmsChargeRegistSave(smsChargeHistVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}