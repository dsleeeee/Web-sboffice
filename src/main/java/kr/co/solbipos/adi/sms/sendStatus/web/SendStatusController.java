package kr.co.solbipos.adi.sms.sendStatus.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.sendStatus.service.SendStatusService;
import kr.co.solbipos.adi.sms.sendStatus.service.SendStatusVO;
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
 * @Class Name : SendStatusController.java
 * @Description : 부가서비스 > SMS관리 > 문자전송현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.18  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.06.18
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/sms/sendStatus")
public class SendStatusController {

    private final SessionService sessionService;
    private final SendStatusService sendStatusService;

    /**
     * Constructor Injection
     */
    @Autowired
    public SendStatusController(SessionService sessionService, SendStatusService sendStatusService) {
        this.sessionService = sessionService;
        this.sendStatusService = sendStatusService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/sendStatus/list.sb", method = RequestMethod.GET)
    public String sendStatusView(HttpServletRequest request, HttpServletResponse response, Model model) {

//        SendStatusVO sendStatusVO = new SendStatusVO();
//        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

//        // SMS전송 - 메세지그룹 조회
//        List<DefaultMap<String>> msgGrpColList = sendStatusService.getMsgGrpColList(sendStatusVO, sessionInfoVO);
//        model.addAttribute("msgGrpColList", msgGrpColList);
////        System.out.println("msgGrpColList : "+msgGrpColList);

        return "adi/sms/sendStatus/sendStatus";
    }

    /**
     * 문자전송현황 - 조회
     *
     * @param sendStatusVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 06. 18.
     */
    @RequestMapping(value = "/sendStatus/getSendStatusList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSendStatusList(SendStatusVO sendStatusVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = sendStatusService.getSendStatusList(sendStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, sendStatusVO);
    }

    /**
     * 문자전송현황 - 예약취소
     *
     * @param sendStatusVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 06. 18.
     */
    @RequestMapping(value = "/sendStatus/getSendStatusReserveCancelSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getSendStatusReserveCancelSave(@RequestBody SendStatusVO[] sendStatusVOs, HttpServletRequest request,
                                        HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = sendStatusService.getSendStatusReserveCancelSave(sendStatusVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 일자별 전송현황 - 조회
     *
     * @param sendStatusVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 09. 17.
     */
    @RequestMapping(value = "/daySendStatus/getDaySendStatusList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getDaySendStatusList(SendStatusVO sendStatusVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = sendStatusService.getDaySendStatusList(sendStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, sendStatusVO);
    }



    /**
     * 공통 화면 상단 SMS전송(당일) 표시 - [125 SMS전송현황표시]에 등록된 본사 하위 매장인지 조회
     *
     * @param sendStatusVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 11. 17.
     */
    @RequestMapping(value = "/cmmMainTopSmsSend/getCmmMainTopStoreCount.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCmmMainTopStoreCount(SendStatusVO sendStatusVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = sendStatusService.getCmmMainTopStoreCount(sendStatusVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 공통 화면 상단 SMS전송(당일) 표시 - 오늘 SMS전송 건수 조회
     *
     * @param sendStatusVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 11. 17.
     */
    @RequestMapping(value = "/cmmMainTopSmsSend/getCmmMainTopSmsSendCount.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getCmmMainTopSmsSendCount(SendStatusVO sendStatusVO, HttpServletRequest request,
                                            HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = sendStatusService.getCmmMainTopSmsSendCount(sendStatusVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }
}