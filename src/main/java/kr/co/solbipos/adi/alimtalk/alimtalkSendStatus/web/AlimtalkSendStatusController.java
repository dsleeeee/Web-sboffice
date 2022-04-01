package kr.co.solbipos.adi.alimtalk.alimtalkSendStatus.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.alimtalk.alimtalkSendStatus.service.AlimtalkSendStatusService;
import kr.co.solbipos.adi.alimtalk.alimtalkSendStatus.service.AlimtalkSendStatusVO;
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
 * @Class Name : AlimtalkSendStatusController.java
 * @Description : 부가서비스 > 알림톡관리 > 알림톡 전송결과
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
@RequestMapping("/adi/alimtalk/alimtalkSendStatus")
public class AlimtalkSendStatusController {

    private final SessionService sessionService;
    private final AlimtalkSendStatusService alimtalkSendStatusService;

    /**
     * Constructor Injection
     */
    @Autowired
    public AlimtalkSendStatusController(SessionService sessionService, AlimtalkSendStatusService alimtalkSendStatusService) {
        this.sessionService = sessionService;
        this.alimtalkSendStatusService = alimtalkSendStatusService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
//    @RequestMapping(value = "/alimtalkSendStatus/list.sb", method = RequestMethod.GET)
//    public String alimtalkSendStatusView(HttpServletRequest request, HttpServletResponse response, Model model) {
//
//        return "adi/alimtalk/alimtalkSendStatus/alimtalkSendStatus";
//    }

    /**
     * 알림톡 전송결과 - 조회
     *
     * @param alimtalkSendStatusVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 30.
     */
    @RequestMapping(value = "/alimtalkSendStatus/getAlimtalkSendStatusList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkSendStatusList(AlimtalkSendStatusVO alimtalkSendStatusVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = alimtalkSendStatusService.getAlimtalkSendStatusList(alimtalkSendStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, alimtalkSendStatusVO);
    }

    /**
     * 알림톡 전송결과 - 예약취소
     *
     * @param alimtalkSendStatusVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 03. 30.
     */
    @RequestMapping(value = "/alimtalkSendStatus/getAlimtalkSendStatusReserveCancelSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkSendStatusReserveCancelSave(@RequestBody AlimtalkSendStatusVO[] alimtalkSendStatusVOs, HttpServletRequest request,
                                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = alimtalkSendStatusService.getAlimtalkSendStatusReserveCancelSave(alimtalkSendStatusVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 알림톡 일자별 전송현황 - 조회
     *
     * @param alimtalkSendStatusVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 04. 01.
     */
    @RequestMapping(value = "/alimtalkDaySendStatus/getAlimtalkDaySendStatusList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkDaySendStatusList(AlimtalkSendStatusVO alimtalkSendStatusVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = alimtalkSendStatusService.getAlimtalkDaySendStatusList(alimtalkSendStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, alimtalkSendStatusVO);
    }

    /**
     * 알림톡 기간별 전송현황 - 조회
     *
     * @param alimtalkSendStatusVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2022. 04. 01.
     */
    @RequestMapping(value = "/alimtalkPeriodSendStatus/getAlimtalkPeriodSendStatusList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getAlimtalkPeriodSendStatusList(AlimtalkSendStatusVO alimtalkSendStatusVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = alimtalkSendStatusService.getAlimtalkPeriodSendStatusList(alimtalkSendStatusVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, alimtalkSendStatusVO);
    }
}