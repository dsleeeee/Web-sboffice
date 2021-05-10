package kr.co.solbipos.base.promotion.eventMessage.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.promotion.eventMessage.service.EventMessageService;
import kr.co.solbipos.base.promotion.eventMessage.service.EventMessageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;

/**
 * @Class Name : EventMessageController.java
 * @Description : 기초관리 - 프로모션관리 - 이벤트문구출력관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.03  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021 .05. 03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/base/promotion/eventMessage")
public class EventMessageController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SessionService sessionService;
    private final EventMessageService eventMessageService;
    private final CmmEnvUtil cmmEnvUtil;

    /** Constructor Injection */
    @Autowired
    public EventMessageController(SessionService sessionService, EventMessageService eventMessageService, CmmEnvUtil cmmEnvUtil) {
        this.sessionService = sessionService;
        this.eventMessageService = eventMessageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /**
     * 이벤트문구출력관리 화면이동
     *
     * @param model
     * @author 이다솜
     * @since 2021.05.03
     * @return
     */
    @RequestMapping(value = "/view.sb", method = RequestMethod.GET)
    public String view(HttpServletRequest request, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        return "base/promotion/eventMessage/eventMessage";
    }

    /**
     * 이벤트문구출력관리 리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.05.03
     * @return
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEventMessageList(EventMessageVO eventMessageVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = eventMessageService.getEventMessageList(eventMessageVO, sessionInfoVO);

        return returnListJson(Status.OK, list, eventMessageVO);
    }

    /**
     * 이벤트문구출력물 등록/수정
     * @param eventMessageVO
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.05.03
     * @return
     */
    @RequestMapping(value = "/save.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveEventMessage(@RequestBody EventMessageVO eventMessageVO, HttpServletRequest request,
                                HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        String result = eventMessageService.saveEventMessage(eventMessageVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 이벤트문구출력물 상세 조회
     * @param eventMessageVO
     * @param request
     * @author 이다솜
     * @since 2021.05.03
     * @return
     */
    @RequestMapping(value = "/detail.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEventMessageDetail(EventMessageVO eventMessageVO, HttpServletRequest request) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = eventMessageService.getEventMessageDetail(eventMessageVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }




    /**
     * 이벤트문구출력물 적용상품 리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.05.03
     * @return
     */
    @RequestMapping(value = "/getEventMessageProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEventMessageProdList(EventMessageVO eventMessageVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = eventMessageService.getEventMessageProdList(eventMessageVO, sessionInfoVO);

        return returnListJson(Status.OK, list, eventMessageVO);
    }

    /**
     * 이벤트문구출력물 적용상품 선택팝업 상품리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.05.03
     * @return
     */
    @RequestMapping(value = "/getProdList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getProdList(EventMessageVO eventMessageVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = eventMessageService.getProdList(eventMessageVO, sessionInfoVO);

        return returnListJson(Status.OK, list, eventMessageVO);
    }

    /**
     * 이벤트문구출력물 적용상품 선택팝업 상품추가/수정/삭제
     *
     * @param eventMessageVOs
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.05.03
     * @return
     */
    @RequestMapping(value = "/saveEventMessageProd.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveEventMessageProd(@RequestBody EventMessageVO[] eventMessageVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = eventMessageService.saveEventMessageProd(eventMessageVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 이벤트문구출력물 적용매장 리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.05.03
     * @return
     */
    @RequestMapping(value = "/getEventMessageStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getEventMessageStoreList(EventMessageVO eventMessageVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = eventMessageService.getEventMessageStoreList(eventMessageVO, sessionInfoVO);

        return returnListJson(Status.OK, list, eventMessageVO);
    }

    /**
     * 이벤트문구출력물 적용매장 선택팝업 매장리스트 조회
     *
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.05.03
     * @return
     */
    @RequestMapping(value = "/getStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getStoreList(EventMessageVO eventMessageVO, HttpServletRequest request, HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = eventMessageService.getStoreList(eventMessageVO, sessionInfoVO);

        return returnListJson(Status.OK, list, eventMessageVO);
    }

    /**
     * 이벤트문구출력물 적용매장 선택팝업 전매장적용
     *
     * @param eventMessageVO
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.05.03
     * @return
     */
    @RequestMapping(value = "/insertEventMessageStoreAll.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result insertEventMessageStoreAll(@RequestBody EventMessageVO eventMessageVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result= eventMessageService.insertEventMessageStoreAll(eventMessageVO, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }

    /**
     * 이벤트문구출력물 적용매장 선택팝업 매장추가/삭제
     *
     * @param eventMessageVOs
     * @param request
     * @param response
     * @param model
     * @author 이다솜
     * @since 2021.05.03
     * @return
     */
    @RequestMapping(value = "/saveEventMessageStore.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result saveEventMessageStore(@RequestBody EventMessageVO[] eventMessageVOs, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = eventMessageService.saveEventMessageStore(eventMessageVOs, sessionInfoVO);

        return returnListJson(Status.OK, result);
    }
}
