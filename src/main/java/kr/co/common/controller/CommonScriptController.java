package kr.co.common.controller;

import kr.co.common.service.message.MessageResolveService;
import kr.co.common.service.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Class Name : CommonScriptController.java
 * @Description : 공통 스크립트 컨트롤러
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.03  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/resource/solbipos/js/variables")
public class CommonScriptController {

    private final SessionService sessionService;
    @Qualifier("messageResolveService")
    private final MessageResolveService messageResolveService;

    /** Constructor Injection */
    @Autowired
    public CommonScriptController(SessionService sessionService, MessageResolveService messageResolveService) {
        this.sessionService = sessionService;
        this.messageResolveService = messageResolveService;
    }


    /**
     * 언어 프로퍼티 : JSP에 코딩하여 JS처럼 사용
     *
     * @param request
     * @return String
     * @author 노현수
     * @since 2018. 08. 03.
     */
    @RequestMapping(value = "/lang.js")
    public ModelAndView langProperties(HttpServletRequest request) {
        return new ModelAndView("application/variables/lang", "keys", messageResolveService.getMessages(
            LocaleContextHolder.getLocale()));
    }

    /**
     * 공통 변수 : JSP에 코딩하여 JS처럼 사용
     *
     * @param request
     * @return String
     * @author 노현수
     * @since 2018. 08. 03.
     */
    @RequestMapping(value = "/commonVariables.js")
    public ModelAndView commonVariables(HttpServletRequest request) {
        return new ModelAndView("application/variables/commonVariables");
    }

}
