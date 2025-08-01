package kr.co.solbipos.adi.board.board.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.enums.UseYn;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.common.utils.jsp.CmmCodeUtil;
import kr.co.solbipos.adi.board.board.service.BoardService;
import kr.co.solbipos.adi.board.board.service.BoardVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.store.emp.hq.service.HqEmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

/**
 * @Class Name : BoardController.java
 * @Description : 부가서비스 > 게시판 > 일반게시판
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.02.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.02.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Controller
@RequestMapping("/adi/board/board")
public class BoardController {

    private final SessionService sessionService;
    private final BoardService boardService;
    private final HqEmpService hqEmpService;
    private final CmmCodeUtil cmmCodeUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public BoardController(SessionService sessionService, BoardService boardService, HqEmpService hqEmpService, CmmCodeUtil cmmCodeUtil) {
        this.sessionService = sessionService;
        this.boardService = boardService;
        this.hqEmpService = hqEmpService;
        this.cmmCodeUtil = cmmCodeUtil;
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

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        model.addAttribute("boardCd", boardCd);

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            List HqBoardModifyUserId = hqEmpService.getHqNmcodeComboList(sessionInfoVO, "182");
            model.addAttribute("HqBoardModifyUserId", HqBoardModifyUserId.isEmpty() ? CmmUtil.comboListAll() : cmmCodeUtil.assmblObj(HqBoardModifyUserId, "name", "value", UseYn.N));
        } else {
            // 관리자 또는 총판은 빈 콤보박스 셋팅(script 오류 방지)
            model.addAttribute("HqBoardModifyUserId", CmmUtil.comboListAll());
        }

        // 웹 접속 경로
        model.addAttribute("rootUrl", request.getRequestURL().toString().replace(request.getRequestURI(), ""));

        return "adi/board/board/boardList";
    }

    /**
     * 일반게시판 조회
     *
     * @param boardVO
     * @param request
     * @param response
     * @param model
     * @return Object
     * @author 김설아
     * @since 2020. 02. 12.
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
     * @return Object
     * @author 김설아
     * @since 2020. 02. 14.
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
     * @return Object
     * @author 김설아
     * @since 2020. 02. 12.
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
     * @return Object
     * @author 김설아
     * @since 2020. 02. 13.
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
     * @return Object
     * @author 김설아
     * @since 2020. 02. 26.
     */
    @RequestMapping(value = "/board/getBoardInfoAtchSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBoardInfoAtchSave(MultipartHttpServletRequest request) {

//        System.out.println("test1111");
        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

        if (boardService.getBoardInfoAtchSave(request, sessionInfo)) {
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
     * @return Object
     * @author 김설아
     * @since 2020. 02. 17.
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
     * @return Object
     * @author 김설아
     * @since 2020. 02. 18.
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
     * @return Object
     * @author 김설아
     * @since 2020. 03. 03.
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
     * @return Object
     * @author 김설아
     * @since 2020. 03. 04.
     */
    @RequestMapping(value = "/board/getBoardDetailAtchList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBoardDetailAtchList(BoardVO boardVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<BoardVO> result = boardService.getBoardDetailAtchList(boardVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 게시판 첨부파일 다운로드
     *
     * @param boardVO
     * @param request
     * @param response
     * @param model
     * @return Object
     * @author 김설아
     * @since 2020. 03. 09.
     */
    @RequestMapping(value = "/board/getBoardDetailAtchDownload.sb")
    @ResponseBody
    public void getBoardDetailAtchDownload(BoardVO boardVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) throws Exception {

//System.out.println("kjs: request.getParameter(fileNm): "  + request.getParameter("fileNm"));
        System.out.println("kjs: boardVO.getFileNm() : " + boardVO.getFileNm());
//        File file = new File("D:\\Workspace\\javaWeb\\testBoardAtch\\", boardVO.getFileNm());
        String reFileNM = boardVO.getFileNm().replaceAll("../", "").replaceAll("/", "");
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
        response.setHeader("Content-Disposition", "attachment; filename=\"" + orginlFileNm + "." + fileExt + "\"");
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
     * @return Object
     * @author 김설아
     * @since 2020. 03. 03.
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
     * @return Object
     * @author 김설아
     * @since 2020. 02. 19.
     */
    @RequestMapping(value = "/board/getBoardReadingHistList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getBoardReadingHistList(BoardVO boardVO, HttpServletRequest request,
                                          HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = boardService.getBoardReadingHistList(boardVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, boardVO);
    }

    /**
     * 첨부파일에 임시경로 UPDATE 후 게시글 이미지 서버경로로 치환
     *
     * @param boardVOs
     * @param request
     * @param response
     * @param model
     * @return Object
     * @author 이다솜
     * @since 2021. 09. 17.
     */
    @RequestMapping(value = "/board/setServerPathFile.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result setServerPathFile(@RequestBody BoardVO[] boardVOs, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = boardService.setServerPathFile(boardVOs, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 본사매장 목록 조회
     *
     * @param request
     * @param response
     * @param model
     * @param boardVO
     * @return String
     * @author 권지현
     * @since 2022.02.07
     */
    @RequestMapping(value = "/board/selectHqStoreList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result selectHqStoreList(BoardVO boardVO, HttpServletRequest request,
                                    HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<String>> list = boardService.selectHqStoreList(boardVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, list, boardVO);
    }
}