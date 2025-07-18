package kr.co.solbipos.adi.board.board.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.CmmUtil;
import kr.co.solbipos.adi.board.board.service.BoardService;
import kr.co.solbipos.adi.board.board.service.BoardVO;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;
import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : BoardServiceImpl.java
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
@Service("boardService")
@Transactional
public class BoardServiceImpl implements BoardService {
    private final BoardMapper boardMapper;
    private final MessageService messageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public BoardServiceImpl(BoardMapper boardMapper, MessageService messageService){
        this.boardMapper = boardMapper;
        this.messageService = messageService;
    }

    /** 일반게시판 조회 */
    @Override
    public List<DefaultMap<Object>> getBoardList(BoardVO boardVO, SessionInfoVO sessionInfoVO) {

        // 접속사용자의 권한(M : 시스템, A : 대리점, H : 본사, S : 매장)
        boardVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        boardVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            boardVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        boardVO.setUserId(sessionInfoVO.getUserId());

        String currentDate = currentDateString();
        boardVO.setDate(currentDate);

        boardVO.setOrgnGrpCd(sessionInfoVO.getOrgnGrpCd());

        return boardMapper.getBoardList(boardVO);
    }

    /** 게시판 상세 팝업 - 조회 */
    @Override
    public DefaultMap<String> getBoardDetailList(BoardVO boardVO,  SessionInfoVO sessionInfoVO) {

        DefaultMap<String> resultMap = new DefaultMap<String>();

        String currentDt = currentDateTimeString();

        boardVO.setRegDt(currentDt);
        boardVO.setRegId(boardVO.getUserId());

        // 게시판 열람 이력 인덱스 조회(자동채번)
        String Idx = boardMapper.getBoardReadingHistIdx(boardVO);
        boardVO.setIdx(Idx);
        // 게시판 열람 이력 insert
        boardMapper.getBoardReadingHistInsert(boardVO);

        resultMap = boardMapper.getBoardDetailList(boardVO);

        // 게시판 조회수 조회
        String viewCnt = boardMapper.getBoardViewCnt(boardVO);
        boardVO.setViewCnt(viewCnt);
        // 게시판 조회수 update
        boardMapper.getBoardViewCntUpdate(boardVO);

        return resultMap;
    }

    /** 게시판 신규등록,수정 팝업 - 상세조회 */
    @Override
    public DefaultMap<String> getBoardInfoList(BoardVO boardVO,  SessionInfoVO sessionInfoVO) {

        return boardMapper.getBoardDetailList(boardVO);
    }

    /** 게시판 신규등록,수정 팝업 - 저장 */
    @Override
    public int getBoardInfoSave(BoardVO boardVO, SessionInfoVO sessionInfoVO) {

//        System.out.println("test1111");
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        boardVO.setModDt(currentDt);
        boardVO.setModId(boardVO.getUserId());
        boardVO.setRegDt(currentDt);
        boardVO.setRegId(boardVO.getUserId());
        if (boardVO.getStatus() == GridDataFg.INSERT || boardVO.getStatus() == GridDataFg.UPDATE)
        {
            boardVO.setContent(CmmUtil.removeInputValueHtml(boardVO.getContent()));
        }

        if (boardVO.getStatus() == GridDataFg.INSERT) {
            // 게시판 게시일련번호 조회(자동채번)
            String boardSeqNo = boardMapper.getBoardBoardSeqNo(boardVO);
            boardVO.setBoardSeqNo(boardSeqNo);

            procCnt = boardMapper.getBoardInfoSaveInsert(boardVO);

            // 공개대상
            if(boardVO.getTargetFg().equals("6")) {
                // 매장 array 값 세팅
                String[] storeCds = boardVO.getStoreCds().split(",");
                for(int i=0; i<storeCds.length; i++) {
                    boardVO.setStoreCd(storeCds[i]);

                    // 게시판 공개대상 insert
                    boardMapper.getBoardPartStoreSaveInsert(boardVO);
                }
            }

        } else if(boardVO.getStatus() == GridDataFg.UPDATE) {
            procCnt = boardMapper.getBoardInfoSaveUpdate(boardVO);

            // 게시판 공개대상 delete
            boardMapper.getBoardPartStoreSaveDelete(boardVO);

            // 공개대상
            if(boardVO.getTargetFg().equals("6")) {

                // 매장 array 값 세팅
                String[] storeCds = boardVO.getStoreCds().split(",");
                for(int i=0; i<storeCds.length; i++) {
                    boardVO.setStoreCd(storeCds[i]);

                    // 게시판 공개대상에 선택한 매장이있는지 select
                    String partOrgnCd = boardMapper.getBoardParStorePartOrgnCd(boardVO);

                    if(String.valueOf(0).equals(partOrgnCd)) {
                        // 게시판 공개대상 insert
                        boardMapper.getBoardPartStoreSaveInsert(boardVO);
                    } else {
                        // 게시판 공개대상 update
                        boardMapper.getBoardPartStoreSaveUpdate(boardVO);
                    }
                }
            }

        } else if (boardVO.getStatus() == GridDataFg.DELETE) {
            // 게시판 delete
            procCnt = boardMapper.getBoardInfoSaveDelete(boardVO);

            // 게시판 전체 댓글 delete
//            boardMapper.getBoardDetailAnswerSaveTotDelete(boardVO);

            // 게시판 전체 첨부파일 delete
//            boardMapper.getBoardInfoAtchTotDel(boardVO);

            // 게시판 전체 공개대상 delete
//            boardMapper.getBoardPartStoreSaveDelete(boardVO);
        }

        // 첨부파일 저장할때 쓰려고
        if (procCnt > 0){
            if (boardVO.getStatus() == GridDataFg.INSERT) {
                procCnt = Integer.parseInt(boardVO.getBoardSeqNo());
            }
        }

        return procCnt;
    }

    /** 게시판 신규등록,수정 팝업 - 첨부파일 저장 */
    @Override
    public boolean getBoardInfoAtchSave(MultipartHttpServletRequest multi, SessionInfoVO sessionInfo) {

//        System.out.println("test1111");
//        boolean isSuccess = false;
        boolean isSuccess = true;

        try{

            // 업로드 파일 읽기
            BoardVO boardInfo = new BoardVO();

            // 현재 일자
            String currentDt = currentDateTimeString();

            boardInfo.setUserId((String)multi.getParameter("userId"));

            boardInfo.setModDt(currentDt);
            boardInfo.setModId(boardInfo.getUserId());
            boardInfo.setRegDt(currentDt);
            boardInfo.setRegId(boardInfo.getUserId());

            boardInfo.setBoardCd((String)multi.getParameter("boardCd"));
            boardInfo.setBoardSeqNo((String)multi.getParameter("boardSeqNo"));

            // 저장 경로 설정 (개발시 로컬)
//            String path = "D:\\Workspace\\javaWeb\\testBoardAtch\\";

            // 파일서버 대응 경로 지정 (운영)
            String path = BaseEnv.FILE_UPLOAD_DIR + "board/";

            // 업로드 되는 파일명
            String newFileName = "";
            // 원본 파일명
            String orgFileName="";

            // 경로에 폴도가 있는지 체크
            File dir = new File(path);
            if(!dir.isDirectory()){
                dir.mkdir();
            }

//            Iterator<String> files = multi.getFileNames();

            List<MultipartFile> fileList = multi.getFiles("file");
            for(MultipartFile mFile : fileList)
            {
                newFileName = String.valueOf(System.currentTimeMillis()); // 파일명 (물리적으로 저장되는 파일명)
                orgFileName = mFile.getOriginalFilename(); // 원본 파일명
                String fileExt = FilenameUtils.getExtension(orgFileName); // 파일확장자

                // orgFileName
                if ( orgFileName.contains(".") ) {
                    orgFileName = orgFileName.substring(0, orgFileName.lastIndexOf("."));
                }
                // IE에선 C:\Users\김설아\Desktop\123\new2.txt
                // 크롬에선 new2.txt
                if ( orgFileName.contains("\\") ) {
                    orgFileName = orgFileName.substring(orgFileName.lastIndexOf("\\"));
                    orgFileName = orgFileName.substring(1);
                }

                if(mFile.getOriginalFilename().lastIndexOf('.') > 0) { // 파일명 최소 한글자 이상은 되어야함.
//                    orgFileName = mFile.getOriginalFilename().substring(0, mFile.getOriginalFilename().lastIndexOf('.'));
                    // 파일경로
                    boardInfo.setFilePath(path);
                    // 파일명 (물리적으로 저장되는 파일명)
                    boardInfo.setFileNm(newFileName);
                    // 원본 파일명
                    boardInfo.setOrginlFileNm(orgFileName);
                    // 파일확장자
                    boardInfo.setFileExt(fileExt);

                    // 파일 저장하는 부분
                    try {
                        mFile.transferTo(new File(path+newFileName));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // 첨부파일 저장시 IDX (자동채번)
                    String idx = boardMapper.getBoardAtchIdx(boardInfo);
                    boardInfo.setIdx(idx);

                    // 첨부파일 저장 isert
                    if(boardMapper.getBoardInfoAtchSaveIsert(boardInfo) > 0) {
                        isSuccess = true;
                    } else {
                        isSuccess = false;
                    }
                }
            }

        }catch(Exception e){

            isSuccess = false;
        }
        return isSuccess;
    }

    /** 게시판 댓글 조회 */
    @Override
    public List<DefaultMap<Object>> getBoardDetailAnswerList(BoardVO boardVO, SessionInfoVO sessionInfoVO) {

        boardVO.setUserId(boardVO.getUserId());

        return boardMapper.getBoardDetailAnswerList(boardVO);
    }

    /** 게시판 댓글 저장 */
    @Override
    public int getBoardDetailAnswerSave(BoardVO boardVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        boardVO.setModDt(currentDt);
        boardVO.setModId(boardVO.getUserId());

        if (boardVO.getStatus() == GridDataFg.INSERT) {

            boardVO.setRegDt(currentDt);
            boardVO.setRegId(boardVO.getUserId());
            boardVO.setUserId(boardVO.getUserId());
//            boardVO.setUserNm(boardVO.getUserNm());

            // 사용자이름 조회(포스용 공지사항때문에)
            String userNm = boardMapper.getBoardUserNm(boardVO);
            boardVO.setUserNm(userNm);

            // 게시판 댓글번호 조회(자동채번)
            String idx = boardMapper.getBoardAnswerIdx(boardVO);
            boardVO.setIdx(idx);

            procCnt = boardMapper.getBoardDetailAnswerSaveInsert(boardVO);

        } else if(boardVO.getStatus() == GridDataFg.UPDATE) {
            procCnt = boardMapper.getBoardDetailAnswerSaveUpdate(boardVO);

        } else if (boardVO.getStatus() == GridDataFg.DELETE) {
            procCnt = boardMapper.getBoardDetailAnswerSaveDelete(boardVO);
        }

        // 게시판 댓글수 조회
        String answerCnt = boardMapper.getBoardAnswerCnt(boardVO);
        boardVO.setAnswerCnt(answerCnt);
        // 게시판 댓글수 update
        boardMapper.getBoardAnswerCntUpdate(boardVO);

        return procCnt;
    }

    /** 게시판 첨부파일 조회 */
    @Override
    public List<DefaultMap<Object>> getBoardInfoAtchList(BoardVO boardVO, SessionInfoVO sessionInfoVO) {

        return boardMapper.getBoardInfoAtchList(boardVO);
    }

    /** 게시판 첨부파일 조회 */
    @Override
    public List<BoardVO> getBoardDetailAtchList(BoardVO boardVO, SessionInfoVO sessionInfoVO) {

        return boardMapper.getBoardDetailAtchList(boardVO);
    }

    /** 게시판 첨부파일 삭제 */
    @Override
    public int getBoardInfoAtchDel(BoardVO boardVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        boardVO.setModDt(currentDt);
        boardVO.setModId(sessionInfoVO.getUserId());

        procCnt = boardMapper.getBoardInfoAtchDel(boardVO);

        return procCnt;
    }

    /** 열람자목록 팝업 - 검색 */
    @Override
    public List<DefaultMap<Object>> getBoardReadingHistList(BoardVO boardVO, SessionInfoVO sessionInfoVO) {

        boardVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            boardVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return boardMapper.getBoardReadingHistList(boardVO);
    }

    /** 첨부파일에 임시경로 UPDATE 후 게시글 이미지 서버경로로 치환 */
    @Override
    public int setServerPathFile(BoardVO[] boardVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        int result = 0;
        String currentDt = currentDateTimeString();
        String rootUrl = boardVOs[0].getRootUrl(); // 웹 접속경로

        // 첨부파일에 임시경로 UPDATE
        for(BoardVO boardVO : boardVOs){
            boardVO.setModDt(currentDt);
            boardVO.setModId(sessionInfoVO.getUserId());
            procCnt = boardMapper.setTempPath(boardVO);
            if(procCnt < 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));
        }

        // 첨부파일 정보 조회
        BoardVO boardContentVO = new BoardVO();
        boardContentVO.setBoardCd(boardVOs[0].getBoardCd());
        boardContentVO.setBoardSeqNo(boardVOs[0].getBoardSeqNo());
        boardContentVO.setModDt(currentDt);
        boardContentVO.setModId(sessionInfoVO.getUserId());

        // 게시글 내 이미지 파일 임시경로 서버경로로 치환
        List<DefaultMap<Object>> list = boardMapper.getAtchList(boardContentVO);

        for(int i = 0; i < list.size(); i++) {

            boardContentVO.setTempPath(list.get(i).getStr("tempPath"));
            boardContentVO.setRootUrl(rootUrl + "/Board/" + list.get(i).getStr("fileNm"));
            result = boardMapper.saveBoardInfoContent(boardContentVO);
            if(result < 0) throw new JsonException(Status.SERVER_ERROR, messageService.get("cmm.saveFail"));

        }

        return result;
    }

    /** 팝업 공고 조회 */
    @Override
    public List<DefaultMap<String>> getPopUpBoardList(SessionInfoVO sessionInfoVO) {
        BoardVO boardVO = new BoardVO();

        List<DefaultMap<String>> result = new ArrayList<DefaultMap<String>>();

        boardVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        if(sessionInfoVO.getOrgnFg().equals(OrgnFg.MASTER)){
            boardVO.setOrgnCd(sessionInfoVO.getOrgnCd());
            boardVO.setUserId(sessionInfoVO.getUserId());

            result = boardMapper.getSystemPopUpBoardList(boardVO);

        } else if(sessionInfoVO.getOrgnFg().equals(OrgnFg.AGENCY)){

            boardVO.setOrgnCd(sessionInfoVO.getOrgnCd());
            boardVO.setUserId(sessionInfoVO.getUserId());
            boardVO.setOrgnGrpCd(sessionInfoVO.getOrgnGrpCd());

            result = boardMapper.getAgencyPopUpBoardList(boardVO);

        } else {
            boardVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            boardVO.setStoreCd(sessionInfoVO.getStoreCd());

            result = boardMapper.getPopUpBoardList(boardVO);
        }

        return result;
    }

    @Override
    public List<DefaultMap<String>> selectHqStoreList(BoardVO boardVO, SessionInfoVO sessionInfoVO){
        boardVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        boardVO.setOrgnGrpCd(sessionInfoVO.getOrgnGrpCd());
        boardVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return boardMapper.selectHqStoreList(boardVO);
    }
}
