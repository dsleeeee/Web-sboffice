package kr.co.solbipos.adi.board.board.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.board.board.service.BoardService;
import kr.co.solbipos.adi.board.board.service.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.net.URLEncoder;
import org.springframework.util.FileCopyUtils;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

@Controller
@RequestMapping("/adi/board/board")
public class BoardController {

    private final SessionService sessionService;
    private final BoardService boardService;

    /**
     * Constructor Injection
     */
    @Autowired
    public BoardController(SessionService sessionService, BoardService boardService) {
        this.sessionService = sessionService;
        this.boardService = boardService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/{boardCd}/list.sb", method = RequestMethod.GET)
    public String boardView(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable String boardCd) {
        // http://localhost:8080/adi/board/board/01/list.sb
//        System.out.println("test1111 : " + boardCd);
        model.addAttribute("boardCd", boardCd);

        return "adi/board/board/boardList";
    }

    /**
     * 일반게시판 조회
     *
     * @param boardVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 02. 12.
     */
    @RequestMapping(value = "/board/getBoardList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBoardList(BoardVO boardVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = boardService.getBoardList(boardVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, boardVO);
    }

    /**
     * 게시판 상세 팝업 - 조회
     *
     * @param boardVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 02. 14.
     */
    @RequestMapping(value = "/board/getBoardDetailList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBoardDetailList(BoardVO boardVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = boardService.getBoardDetailList(boardVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 게시판 신규등록,수정 팝업 - 상세조회
     *
     * @param boardVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 02. 12.
     */
    @RequestMapping(value = "/board/getBoardInfoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBoardInfoList(BoardVO boardVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        DefaultMap<String> result = boardService.getBoardInfoList(boardVO, sessionInfoVO);

        DefaultMap<Object> resultMap = new DefaultMap<Object>();
        resultMap.put("result", result);

        return returnJson(Status.OK, resultMap);
    }

    /**
     * 게시판 신규등록,수정 팝업 - 저장
     *
     * @param boardVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 02. 13.
     */
    @RequestMapping(value = "/board/getBoardInfoSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBoardInfoSave(@RequestBody BoardVO boardVO, HttpServletRequest request,
                                     HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = boardService.getBoardInfoSave(boardVO, sessionInfoVO);
//        System.out.println("test1111 : " + result);

        return returnJson(Status.OK, result);
    }

    /**
     * 게시판 신규등록,수정 팝업 - 첨부파일 저장
     *
     * @return  Object
     * @author  김설아
     * @since   2020. 02. 26.
     */
    @RequestMapping(value = "/board/getBoardInfoAtchSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBoardInfoAtchSave(MultipartHttpServletRequest request) {

//        System.out.println("test1111");
        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

        if(boardService.getBoardInfoAtchSave(request, sessionInfo)) {
            return returnJson(Status.OK);
        } else {
            return returnJson(Status.FAIL);
        }
    }

    /**
     * 게시판 댓글 조회
     *
     * @param boardVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 02. 17.
     */
    @RequestMapping(value = "/board/getBoardDetailAnswerList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBoardDetailAnswerList(BoardVO boardVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = boardService.getBoardDetailAnswerList(boardVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, boardVO);
    }

    /**
     * 게시판 댓글 저장
     *
     * @param boardVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 02. 18.
     */
    @RequestMapping(value = "/board/getBoardDetailAnswerSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBoardDetailAnswerSave(@RequestBody BoardVO boardVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = boardService.getBoardDetailAnswerSave(boardVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 게시판 첨부파일 조회
     *
     * @param boardVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 03. 03.
     */
    @RequestMapping(value = "/board/getBoardInfoAtchList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBoardInfoAtchList(BoardVO boardVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = boardService.getBoardInfoAtchList(boardVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, boardVO);
    }

    /**
     * 게시판 첨부파일 조회
     *
     * @param boardVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 03. 04.
     */
    @RequestMapping(value = "/board/getBoardDetailAtchList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBoardDetailAtchList(BoardVO boardVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<BoardVO> result =  boardService.getBoardDetailAtchList(boardVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 게시판 첨부파일 다운로드
     *
     * @param boardVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 03. 09.
     */
    @RequestMapping(value="/board/getBoardDetailAtchDownload.sb")
    @ResponseBody
    public void getBoardDetailAtchDownload(BoardVO boardVO, HttpServletRequest request,
                                       HttpServletResponse response, Model model) throws Exception {

System.out.println("kjs: request.getParameter(fileNm): "  + request.getParameter("fileNm"));
System.out.println("kjs: boardVO.getFileNm() : "  + boardVO.getFileNm());

//        File file = new File("D:\\Workspace\\javaWeb\\testBoardAtch\\", boardVO.getFileNm());
        String reFileNM = boardVO.getFileNm().replaceAll("..", "").replaceAll("/", "");
System.out.println("kjs: reFileNM : " + reFileNM);
        File file = new File(BaseEnv.FILE_UPLOAD_DIR + "board/", reFileNM);

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

        //User-Agent : 어떤 운영체제로  어떤 브라우저를 서버( 홈페이지 )에 접근하는지 확인함
        String header = request.getHeader("User-Agent");
        String fileName;
        String orginlFileNm;
        String fileExt;
        if ((header.contains("MSIE")) || (header.contains("Trident")) || (header.contains("Edge"))) {
            //인터넷 익스플로러 10이하 버전, 11버전, 엣지에서 인코딩
            fileName = URLEncoder.encode(reFileNM, "UTF-8");
            orginlFileNm = URLEncoder.encode(boardVO.getOrginlFileNm(), "UTF-8");
            fileExt = URLEncoder.encode(boardVO.getFileExt(), "UTF-8");
        } else {
            //나머지 브라우저에서 인코딩
            fileName = new String(reFileNM.getBytes("UTF-8"), "iso-8859-1");
            orginlFileNm = new String(boardVO.getOrginlFileNm().getBytes("UTF-8"), "iso-8859-1");
            fileExt = new String(boardVO.getFileExt().getBytes("UTF-8"), "iso-8859-1");
        }

        //형식을 모르는 파일첨부용 contentType
        response.setContentType("application/octet-stream");
        //다운로드와 다운로드될 파일이름
//        response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + "\"");
        response.setHeader("Content-Disposition", "attachment; filename=\""+ orginlFileNm + "." + fileExt + "\"");
        //파일복사
        FileCopyUtils.copy(in, response.getOutputStream());
        in.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    /**
     * 게시판 첨부파일 삭제
     *
     * @param boardVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 03. 03.
     */
    @RequestMapping(value = "/board/getBoardInfoAtchDel.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBoardInfoAtchDel(@RequestBody BoardVO boardVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = boardService.getBoardInfoAtchDel(boardVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 열람자목록 팝업 - 검색
     *
     * @param boardVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 02. 19.
     */
    @RequestMapping(value = "/board/getBoardReadingHistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBoardReadingHistList(BoardVO boardVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = boardService.getBoardReadingHistList(boardVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, boardVO);
    }
}