package kr.co.solbipos.adi.sms.smsSend.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.adi.sms.smsSend.service.SmsSendService;
import kr.co.solbipos.adi.sms.smsSend.service.SmsSendVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.spring.StringUtil.convertToJson;

/**
 * @Class Name : SmsSendController.java
 * @Description : 부가서비스 > SMS관리 > SMS전송
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.06.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/sms/smsSend")
public class SmsSendController {

    private final SessionService sessionService;
    private final SmsSendService smsSendService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SmsSendController(SessionService sessionService, SmsSendService smsSendService) {
        this.sessionService = sessionService;
        this.smsSendService = smsSendService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
//    @RequestMapping(value = "/smsSend/list.sb", method = RequestMethod.GET)
//    public String smsSendView(HttpServletRequest request, HttpServletResponse response, Model model) {
//
//        return "adi/sms/smsSend/smsSend";
//    }

    /**
     * 발신번호 조회
     *
     * @param smsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 06. 14.
     */
    @RequestMapping(value = "/smsSend/getSmsTelNoComboList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsTelNoComboList(SmsSendVO smsSendVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = smsSendService.getSmsTelNoComboList(smsSendVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, smsSendVO);
    }

    /**
     * 관리자/총판/본사/매장 명칭 조회
     *
     * @param smsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 06. 14.
     */
    @RequestMapping(value = "/smsSend/getStoreNmList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreNmList(SmsSendVO smsSendVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<Object> result = smsSendService.getStoreNmList(smsSendVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 잔여금액 조회
     *
     * @param smsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 06. 14.
     */
    @RequestMapping(value = "/smsSend/getSmsAmtList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsAmtList(SmsSendVO smsSendVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<Object> result = smsSendService.getSmsAmtList(smsSendVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 전송,예약 저장
     *
     * @param smsSendVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 06. 14.
     */
    @RequestMapping(value = "/smsSend/getSmsSendReserveSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsSendReserveSave(@RequestBody SmsSendVO[] smsSendVOs, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = smsSendService.getSmsSendReserveSave(smsSendVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 전송,예약 1000건 저장
     *
     * @param smsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 10. 08.
     */
    @RequestMapping(value = "/smsSend/getSmsSendReserve1000Save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsSendReserve1000Save(@RequestBody SmsSendVO smsSendVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = smsSendService.getSmsSendReserve1000Save(smsSendVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 첨부파일 저장
     *
     * @return  Object
     * @author  김설아
     * @since   2021. 06. 14.
     */
    @RequestMapping(value = "/smsSend/getSmsSendFileSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsSendFileSave(MultipartHttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = smsSendService.getSmsSendFileSave(request, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 수신자추가 팝업 - 조회
     *
     * @param smsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 06. 14.
     */
    @RequestMapping(value = "/smsSend/getAddresseeAddList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAddresseeAddList(SmsSendVO smsSendVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = smsSendService.getAddresseeAddList(smsSendVO, sessionInfoVO);
        return ReturnUtil.returnListJson(Status.OK, result, smsSendVO);
    }

    /**
     * 일반번호 인증요청 팝업 - 저장
     *
     * @param smsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 01. 26.
     */
    @RequestMapping(value = "/smsGeneralNoRegister/getSmsGeneralNoRegisterSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsGeneralNoRegisterSave(@RequestBody SmsSendVO smsSendVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = smsSendService.getSmsGeneralNoRegisterSave(smsSendVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 일반번호 인증요청 팝업 - 첨부파일 저장
     *
     * @return  Object
     * @author  김설아
     * @since   2022. 01. 26.
     */
    @RequestMapping(value = "/smsGeneralNoRegister/getSmsGeneralNoFileSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsGeneralNoFileSave(MultipartHttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = smsSendService.getSmsGeneralNoFileSave(request, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 발신번호추가2 팝업 - 본인인증 여부 조회
     *
     * @param smsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 11. 12.
     */
    @RequestMapping(value = "/smsTelNoRegister2/getVerifyChk2.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getVerifyChk2(SmsSendVO smsSendVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = smsSendService.getVerifyChk2(smsSendVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result);
    }

    /**
     * 발신번호추가2 팝업 - 저장
     *
     * @param smsSendVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2024. 11. 12.
     */
    @RequestMapping(value = "/smsTelNoRegister2/getSmsTelNoRegister2Save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsTelNoRegister2Save(@RequestBody SmsSendVO smsSendVO, HttpServletRequest request,
                                              HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = smsSendService.getSmsTelNoRegister2Save(smsSendVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 발신번호추가2 팝업 - 첨부파일 저장
     *
     * @return  Object
     * @author  김설아
     * @since   2024. 11. 12.
     */
    @RequestMapping(value = "/smsTelNoRegister2/getSmsTelNoRegister2FileSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsTelNoRegister2FileSave(MultipartHttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = smsSendService.getSmsTelNoRegister2FileSave(request, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}
