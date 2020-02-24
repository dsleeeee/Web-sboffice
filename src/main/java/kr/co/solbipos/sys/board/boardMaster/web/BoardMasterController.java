package kr.co.solbipos.sys.board.boardMaster.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.board.boardMaster.service.BoardMasterService;
import kr.co.solbipos.sys.board.boardMaster.service.BoardMasterVO;
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

@Controller
@RequestMapping("/sys/board/boardMaster")
public class BoardMasterController {

    private final SessionService sessionService;
    private final BoardMasterService boardMasterService;

    /**
     * Constructor Injection
     */
    @Autowired
    public BoardMasterController(SessionService sessionService, BoardMasterService boardMasterService) {
        this.sessionService = sessionService;
        this.boardMasterService = boardMasterService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/list.sb", method = RequestMethod.GET)
    public String boardMasterView(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "sys/board/boardMaster/boardMaster";
    }

    /**
     * 게시판관리 조회
     *
     * @param boardMasterVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 02. 10.
     */
    @RequestMapping(value = "/boardMaster/getBoardMasterList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBoardMasterList(BoardMasterVO boardMasterVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = boardMasterService.getBoardMasterList(boardMasterVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, boardMasterVO);
    }

    /**
     * 게시판관리 저장
     *
     * @param boardMasterVOs
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 02. 10.
     */
    @RequestMapping(value = "/boardMaster/getBoardMasterSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBoardMasterSave(@RequestBody BoardMasterVO[] boardMasterVOs, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = boardMasterService.getBoardMasterSave(boardMasterVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }
}