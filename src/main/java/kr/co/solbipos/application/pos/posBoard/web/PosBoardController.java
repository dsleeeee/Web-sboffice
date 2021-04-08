package kr.co.solbipos.application.pos.posBoard.web;

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
 * @Class Name : PosBoardController.java
 * @Description : POS 화면에서 게시판(포스용)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.03.30  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2021. 03.30
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping(value = "/application/pos/posBoard/")
public class PosBoardController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final PosBoardService posBoardService;
    private final SessionService sessionService;
    private final AuthService authService;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public PosBoardController(PosBoardService posBoardService, SessionService sessionService,
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
    @RequestMapping(value = "posBoardTest.sb", method = RequestMethod.GET)
    public String posBoardTestView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "application/pos/posBoard/posBoardTest";
    }

    /**
     * 게시판 조회 화면
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "posBoard.sb", method = RequestMethod.GET)
    public String posBoardView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "application/pos/posBoard/posBoard";
    }

    /**
     * 공지사항 메뉴 사용 권한 에러
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "boardMenuAuth.sb", method = RequestMethod.GET)
    public String boardMenuAuthView(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "application/pos/posBoard/boardMenuAuth";
    }

    /**
     * TODO
     * POS 화면에서 띄우는건 메뉴등이 필요없기때문에 tiles 에 예외등록해두고 우선은 해당 컨트롤러에서 STORE_CD 와 HW_AUTH_KEY 로 인증체크 후 세션을 맺도록 함.
     * POS 화면 웹 로그인
     * @param sessionInfoVO
     * @param bindingResult
     * @param request
     * @param response
     * @param model
     * @return
     */
//    @RequestMapping(value = "posLogin.sb", method = RequestMethod.POST)
//    public String posLoginProcess(@Validated(Login.class) SessionInfoVO sessionInfoVO,
//                                  BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response, Model model) {
//
//        String returnUrl = "";
//
//        if(!isEmpty(request.getParameter("storeCd")) && !isEmpty(request.getParameter("hwAuthKey")) && !isEmpty(request.getParameter("url"))) {
//            LOGGER.info("posLogin store : {} , hwAuthKey : {} , url : {}", request.getParameter("storeCd"), request.getParameter("hwAuthKey"), request.getParameter("url"));
//
//            sessionInfoVO.setLoginIp(getClientIp(request));
//            sessionInfoVO.setBrwsrInfo(request.getHeader("User-Agent"));
//
//            // 로그인 시도
//            SessionInfoVO posSi = authService.posLogin(sessionInfoVO);
//            // 세션 생성
//            sessionService.setSessionInfo(request, response, posSi);
//
//            returnUrl = "application/pos/posBoard/"+request.getParameter("url");
//
//
//            LOGGER.info("posLogin userId : {} , readYn : {} , noticePopupYn : {}", request.getParameter("userId"), request.getParameter("readYn"), request.getParameter("noticePopupYn"));
//
//            /** 포스에서 받아올 값 */
//            // PosBoardVO posBoardVO = new PosBoardVO();
//            // posBoardVO.setReadYn(readYn);
//            // 열람구분(포스에서 받는 수신여부)
//            String readYn = request.getParameter("readYn");
//            model.addAttribute("readYn", readYn);
//            // 공지팝업 여부(미열람 공지사항 띄움)
//            String noticePopupYn = request.getParameter("noticePopupYn");
//            model.addAttribute("noticePopupYn", noticePopupYn);
//
//            /** userId 체크 */
//            if(isEmpty(request.getParameter("userId"))) {
//                throw new AuthenticationException(messageService.get("cmm.access.denied"), "/application/pos/posBoard/boardMenuAuth.sb");
//            }
//
//            /** 공지사항 페이지이동 권한체크 */
//            SessionInfoVO sessionInfoVO_check = sessionService.getSessionInfo(request);
//            String board_auth = "N";
//            // 세션 권한이 사용할 수 있는 메뉴 목록
//            List<ResrceInfoBaseVO> menuList = sessionInfoVO_check.getMenuData();
//            // url 값 비교
//            for (ResrceInfoBaseVO resrceInfoBaseVO : menuList) {
//                String authUrl = resrceInfoBaseVO.getUrl();
//                if ( !isEmpty(authUrl) ) {
//                    // 등록된 URL 에 파라미터가 있는 경우 파라미터 제거
//                    if ( authUrl.contains("?") ) {
//                        authUrl = authUrl.substring(0, authUrl.indexOf("?"));
//                    }
//                    if ( authUrl.equals("/adi/board/board/01/list.sb") ) {
//                        board_auth = "Y";
//                    }
//                }
//            }
////            model.addAttribute("board_auth", board_auth);
//            if(board_auth == "N") {
//                throw new AuthenticationException(messageService.get("cmm.access.denied"), "/application/pos/posBoard/boardMenuAuth.sb");
//            }
//        }
//        else {
//            throw new AuthenticationException(messageService.get("login.pos.error"), "/error/application/pos/403.sb");
//        }
//
//        return returnUrl;
//    }

    /**
     * 게시판 조회
     *
     * @param posBoardVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2021. 03. 30.
     */
    @RequestMapping(value = "/posBoard/getPosBoardList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getPosBoardList(PosBoardVO posBoardVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = posBoardService.getPosBoardList(posBoardVO, sessionInfoVO);

        return returnListJson(Status.OK, result, posBoardVO);
    }

}