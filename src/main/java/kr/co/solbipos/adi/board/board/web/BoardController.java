package kr.co.solbipos.adi.board.board.web;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.common.service.session.SessionService;
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
    @RequestMapping(value = "{boardCd}/list.sb", method = RequestMethod.GET)
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
    @RequestMapping(value = "board/getBoardDetailList.sb", method = RequestMethod.POST)
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
    @RequestMapping(value = "board/getBoardInfoList.sb", method = RequestMethod.POST)
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



//        System.out.println("test1111 : ");
//        List<MultipartFile> fileList = mtfRequest.getFiles("file");
//        String src = mtfRequest.getParameter("src");
//        System.out.println("test1111 src value : " + src);
//
//        String path = "C:\\image\\";
//
//        for (MultipartFile mf : fileList) {
//            String originFileName = mf.getOriginalFilename(); // 원본 파일 명
//            long fileSize = mf.getSize(); // 파일 사이즈
//
//            System.out.println("originFileName : " + originFileName);
//            System.out.println("fileSize : " + fileSize);
//
//            String safeFile = path + System.currentTimeMillis() + originFileName;
////            try {
////                mf.transferTo(new File(safeFile));
////            } catch (IllegalStateException e) {
////                // TODO Auto-generated catch block
////                e.printStackTrace();
////            } catch (IOException e) {
////                // TODO Auto-generated catch block
////                e.printStackTrace();
////            }
//        }
//    @RequestMapping(value = "requestupload2")
//    public String requestupload2(MultipartHttpServletRequest mtfRequest) {
//
//        List<MultipartFile> fileList = mtfRequest.getFiles("file");
//        String src = mtfRequest.getParameter("src");
//        System.out.println("src value : " + src);
//
//        String path = "C:\\image\\";
//
//        for (MultipartFile mf : fileList) {
//            String originFileName = mf.getOriginalFilename(); // 원본 파일 명
//            long fileSize = mf.getSize(); // 파일 사이즈
//
//            System.out.println("originFileName : " + originFileName);
//            System.out.println("fileSize : " + fileSize);
//
//            String safeFile = path + System.currentTimeMillis() + originFileName;
////            try {
////                mf.transferTo(new File(safeFile));
////            } catch (IllegalStateException e) {
////                // TODO Auto-generated catch block
////                e.printStackTrace();
////            } catch (IOException e) {
////                // TODO Auto-generated catch block
////                e.printStackTrace();
////            }
//        }
//
//        return "redirect:/";
//    }


//    @RequestMapping(value = "/fileUpload") // method = RequestMethod.GET
//     public Map fileUpload(HttpServletRequest req, HttpServletResponse rep) {
//        //파일이 저장될 path 설정
//         String path = "c://aaa"; Map returnObject = new HashMap();
//         try {
//                  // MultipartHttpServletRequest 생성
//                  MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) req;
//                  Iterator iter = mhsr.getFileNames();
//
//                  MultipartFile mfile = null;
//                  String fieldName = "";
//                  List resultList = new ArrayList();
//
//                  // 디레토리가 없다면 생성
//                  File dir = new File(path);
//                  if (!dir.isDirectory()) { dir.mkdirs();
//              }
//              // 값이 나올때까지
//              while (iter.hasNext()) {
//                      fieldName = iter.next();  // 내용을 가져와서
//                       mfile = mhsr.getFile(fieldName);
//                       String origName;
//
//                       origName = new String(mfile.getOriginalFilename().getBytes("8859_1"), "UTF-8"); //한글꺠짐 방지
//                      // 파일명이 없다면
//                       if ("".equals(origName))  {
//                           continue;
//                         }
//
//                   // 파일 명 변경(uuid로 암호화)
//                   String ext = origName.substring(origName.lastIndexOf('.')); // 확장자 String saveFileName = getUuid() + ext; // 설정한 path에 파일저장 File serverFile = new File(path + File.separator + saveFileName); mfile.transferTo(serverFile); Map file = new HashMap(); file.put("origName", origName); file.put("sfile", serverFile); resultList.add(file); } returnObject.put("files", resultList); returnObject.put("params", mhsr.getParameterMap()); } catch (UnsupportedEncodingException e) { // TODO Auto-generated catch block e.printStackTrace(); }catch (IllegalStateException e) { // TODO Auto-generated catch block e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); } return null; } //uuid생성 public static String getUuid() { return UUID.randomUUID().toString().replaceAll("-", ""); }
//
//              }
}