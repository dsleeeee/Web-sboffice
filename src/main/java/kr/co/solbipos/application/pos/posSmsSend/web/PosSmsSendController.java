package kr.co.solbipos.application.pos.posSmsSend.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.exception.AuthenticationException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.session.SessionService;
import kr.co.common.validate.Login;
import kr.co.solbipos.application.common.service.ResrceInfoBaseVO;
import kr.co.solbipos.application.pos.posBoard.service.PosBoardService;
import kr.co.solbipos.application.pos.posBoard.service.PosBoardVO;
import kr.co.solbipos.application.session.auth.service.AuthService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.HttpUtils.getClientIp;
import static kr.co.common.utils.grid.ReturnUtil.returnListJson;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * @Class Name : PosSmsSendController.java
 * @Description : POS 화면에서 SMS전송(포스용)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.10.22  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.10.22
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/application/pos/posSmsSend/")
public class PosSmsSendController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final PosBoardService posBoardService;
    private final SessionService sessionService;
    private final AuthService authService;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public PosSmsSendController(PosBoardService posBoardService, SessionService sessionService,
                              AuthService authService, MessageService messageService) {
        this.posBoardService = posBoardService;
        this.sessionService = sessionService;
        this.authService = authService;
        this.messageService = messageService;
    }

    /**
     * 조회 화면 전,
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "posSmsSendTest.sb", method = RequestMethod.GET)
    public String posSmsSendTestView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "application/pos/posSmsSend/posSmsSendTest";
    }

    /**
     * SMS전송 조회 화면
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "posSmsSend.sb", method = RequestMethod.GET)
    public String posSmsSendView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "application/pos/posSmsSend/posSmsSend";
    }

    /**
     * SMS전송 메뉴 사용 권한 에러
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "smsSendMenuAuth.sb", method = RequestMethod.GET)
    public String smsSendMenuAuthView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "application/pos/posSmsSend/smsSendMenuAuth";
    }
}