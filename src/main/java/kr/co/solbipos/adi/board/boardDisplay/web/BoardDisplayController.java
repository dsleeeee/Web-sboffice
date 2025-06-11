package kr.co.solbipos.adi.board.boardDisplay.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.board.boardDisplay.service.BoardDisplayService;
import kr.co.solbipos.adi.board.boardDisplay.service.BoardDisplayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : BoardDisplayController.java
 * @Description : 부가서비스 > 게시판 > 일반게시판노출순서
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.04  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.06.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/board/boardDisplay")
public class BoardDisplayController {

    private final SessionService sessionService;
    private final BoardDisplayService boardDisplayService;

    /**
     * Constructor Injection
     */
    @Autowired
    public BoardDisplayController(SessionService sessionService, BoardDisplayService boardDisplayService) {
        this.sessionService = sessionService;
        this.boardDisplayService = boardDisplayService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/{boardCd}/list.sb", method = RequestMethod.GET)
    public String boardDisplayView(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable String boardCd) {
        // http://localhost:8080/adi/boardDisplay/boardDisplay/01/list.sb
//        System.out.println("test1111 : " + boardCd);
        model.addAttribute("boardCd", boardCd);

        // 웹 접속 경로
        model.addAttribute("rootUrl" , request.getRequestURL().toString().replace(request.getRequestURI(),""));

        return "adi/board/boardDisplay/boardDisplay";
    }

    /**
     * 일반게시판노출순서 - 조회
     *
     * @param boardDisplayVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2025. 06. 04.
     */
    @RequestMapping(value = "/boardDisplay/getBoardDisplayList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBoardDisplayList(BoardDisplayVO boardDisplayVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = boardDisplayService.getBoardDisplayList(boardDisplayVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, boardDisplayVO);
    }

    /**
     * 일반게시판노출순서 - 저장
     *
     * @param boardDisplayVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2025. 06. 04.
     */
    @RequestMapping(value = "/boardDisplay/getBoardDisplaySave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBoardDisplaySave(@RequestBody BoardDisplayVO[] boardDisplayVOs, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = boardDisplayService.getBoardDisplaySave(boardDisplayVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 상위노출게시물선택 팝업 - 조회
     *
     * @param boardDisplayVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2025. 06. 04.
     */
    @RequestMapping(value = "/boardDisplayAdd/getBoardDisplayAddList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBoardDisplayAddList(BoardDisplayVO boardDisplayVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = boardDisplayService.getBoardDisplayAddList(boardDisplayVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, boardDisplayVO);
    }
}