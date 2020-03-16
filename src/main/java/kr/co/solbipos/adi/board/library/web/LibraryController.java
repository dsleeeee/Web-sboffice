package kr.co.solbipos.adi.board.library.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.grid.ReturnUtil;
import kr.co.solbipos.adi.board.board.service.BoardVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.board.library.service.LibraryService;
import kr.co.solbipos.adi.board.library.service.LibraryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.List;

import static kr.co.common.utils.grid.ReturnUtil.returnJson;

@Controller
@RequestMapping("/adi/board/library")
public class LibraryController {

    private final SessionService sessionService;
    private final LibraryService libraryService;

    /**
     * Constructor Injection
     */
    @Autowired
    public LibraryController(SessionService sessionService, LibraryService libraryService) {
        this.sessionService = sessionService;
        this.libraryService = libraryService;
    }

    /**
     * 페이지 이동
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/{boardCd}/list.sb", method = RequestMethod.GET)
    public String libraryView(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable String boardCd) {

        model.addAttribute("boardCd", boardCd);

        return "adi/board/library/libraryList";
    }

    /**
     * 자료실 조회
     *
     * @param libraryVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 03. 11.
     */
    @RequestMapping(value = "/library/getLibraryList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getLibraryList(LibraryVO libraryVO, HttpServletRequest request,
                               HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = libraryService.getLibraryList(libraryVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, libraryVO);
    }

    /**
     * 자료실 상세 팝업 - 첨부파일 조회
     *
     * @param libraryVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 03. 11.
     */
    @RequestMapping(value = "/library/getLibraryDetailAtchList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getLibraryDetailAtchList(LibraryVO libraryVO, HttpServletRequest request,
                                         HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<LibraryVO> result =  libraryService.getLibraryDetailAtchList(libraryVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 게시판 첨부파일 다운로드
     *
     * @param libraryVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 03. 11.
     */
    @RequestMapping(value="/library/getLibraryDetailAtchDownload.sb")
    @ResponseBody
    public void getLibraryDetailAtchDownload(LibraryVO libraryVO, HttpServletRequest request,
                                           HttpServletResponse response, Model model) throws Exception {

//        System.out.println("test1111 : " + libraryVO.getFileNm());

//        File file = new File("D:\\Workspace\\javaWeb\\testBoardAtch\\", libraryVO.getFileNm());
        File file = new File(BaseEnv.FILE_UPLOAD_DIR + "board/", libraryVO.getFileNm());

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

        //User-Agent : 어떤 운영체제로  어떤 브라우저를 서버( 홈페이지 )에 접근하는지 확인함
        String header = request.getHeader("User-Agent");
        String fileName;
        String orginlFileNm;
        String fileExt;
        if ((header.contains("MSIE")) || (header.contains("Trident")) || (header.contains("Edge"))) {
            //인터넷 익스플로러 10이하 버전, 11버전, 엣지에서 인코딩
            fileName = URLEncoder.encode(libraryVO.getFileNm(), "UTF-8");
            orginlFileNm = URLEncoder.encode(libraryVO.getOrginlFileNm(), "UTF-8");
            fileExt = URLEncoder.encode(libraryVO.getFileExt(), "UTF-8");
        } else {
            //나머지 브라우저에서 인코딩
            fileName = new String(libraryVO.getFileNm().getBytes("UTF-8"), "iso-8859-1");
            orginlFileNm = new String(libraryVO.getOrginlFileNm().getBytes("UTF-8"), "iso-8859-1");
            fileExt = new String(libraryVO.getFileExt().getBytes("UTF-8"), "iso-8859-1");
        }

        //형식을 모르는 파일첨부용 contentType
        response.setContentType("application/octet-stream");
        //다운로드와 다운로드될 파일이름
        response.setHeader("Content-Disposition", "attachment; filename=\""+ orginlFileNm + "." + fileExt + "\"");
        //파일복사
        FileCopyUtils.copy(in, response.getOutputStream());
        in.close();
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    /**
     * 자료실 신규등록,수정 팝업 - 자료실 조회
     *
     * @param libraryVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 03. 12.
     */
    @RequestMapping(value = "/library/getLibraryInfoList.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getLibraryInfoList(LibraryVO libraryVO, HttpServletRequest request,
                                 HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        List<DefaultMap<Object>> result = libraryService.getLibraryInfoList(libraryVO, sessionInfoVO);

        return ReturnUtil.returnListJson(Status.OK, result, libraryVO);
    }

    /**
     * 자료실 신규등록,수정 팝업 - 첨부파일 저장
     *
     * @return  Object
     * @author  김설아
     * @since   2020. 03. 11.
     */
    @RequestMapping(value = "/library/getLibraryInfoAtchSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getLibraryInfoAtchSave(MultipartHttpServletRequest request) {

        SessionInfoVO sessionInfo = sessionService.getSessionInfo(request);

        if(libraryService.getLibraryInfoAtchSave(request, sessionInfo)) {
            return returnJson(Status.OK);
        } else {
            return returnJson(Status.FAIL);
        }
    }

    /**
     * 자료실 신규등록,수정 팝업 - 저장
     *
     * @param libraryVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 03. 12.
     */
    @RequestMapping(value = "/library/getLibraryInfoSave.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getLibraryInfoSave(@RequestBody LibraryVO libraryVO, HttpServletRequest request,
                                   HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = libraryService.getLibraryInfoSave(libraryVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

    /**
     * 자료실 첨부파일 삭제
     *
     * @param libraryVO
     * @param request
     * @param response
     * @param model
     * @return  Object
     * @author  김설아
     * @since   2020. 03. 12.
     */
    @RequestMapping(value = "/library/getLibraryInfoAtchDel.sb", method = RequestMethod.POST)
    @ResponseBody
    public Result getLibraryInfoAtchDel(@RequestBody LibraryVO libraryVO, HttpServletRequest request,
                                      HttpServletResponse response, Model model) {

        SessionInfoVO sessionInfoVO = sessionService.getSessionInfo(request);

        int result = libraryService.getLibraryInfoAtchDel(libraryVO, sessionInfoVO);

        return returnJson(Status.OK, result);
    }

}