package kr.co.solbipos.adi.sms.smsCharge.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.smsCharge.service.SmsChargeService;
import kr.co.solbipos.adi.sms.smsCharge.service.SmsChargeVO;
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
 * @Class Name : SmsChargeController.java
 * @Description : 부가서비스 > SMS관리 > SMS충전/KCP PG
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.09  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.06.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/sms/smsCharge")
public class SmsChargeController {

    private final SessionService sessionService;
    private final SmsChargeService smsChargeService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SmsChargeController(SessionService sessionService, SmsChargeService smsChargeService) {
        this.sessionService = sessionService;
        this.smsChargeService = smsChargeService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/smsCharge/list.sb", method = RequestMethod.GET)
    public String smsChargeView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "adi/sms/smsCharge/smsCharge";
//        return "adi/sms/smsCharge/sample/order";
    }

    /**
     * 충전결제
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/smsCharge/charge.sb", method = RequestMethod.POST)
    public String charge(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "adi/sms/smsCharge/sample/pp_cli_hub";
    }
    @RequestMapping(value = "/smsCharge/chargeResult.sb", method = RequestMethod.POST)
    public String chargeResult(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "adi/sms/smsCharge/sample/result";
    }

    /**
     * 결제취소
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/smsCharge/cancel.sb", method = RequestMethod.POST)
    public String cancel(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "adi/sms/smsCharge/sample/cancel";
    }

    /**
     * 충전결제 저장
     *
     * @param smsChargeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 10. 13.
     */
    @RequestMapping(value = "/smsCharge/getSmsChargeSaveInsert.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsChargeSaveInsert(@RequestBody SmsChargeVO smsChargeVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        int result = smsChargeService.getSmsChargeSaveInsert(smsChargeVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 결제취소 저장
     *
     * @param smsChargeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 10. 13.
     */
    @RequestMapping(value = "/smsCharge/getSmsChargeSaveUpdate.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSmsChargeSaveUpdate(@RequestBody SmsChargeVO smsChargeVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        int result = smsChargeService.getSmsChargeSaveUpdate(smsChargeVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 메세지 건당 가격안내 팝업 - 조회
     *
     * @param smsChargeVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 05. 03.
     */
    @RequestMapping(value = "/msgOneAmtGuide/getMsgOneAmtGuideList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getMsgOneAmtGuideList(SmsChargeVO smsChargeVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = smsChargeService.getMsgOneAmtGuideList(smsChargeVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }
}
