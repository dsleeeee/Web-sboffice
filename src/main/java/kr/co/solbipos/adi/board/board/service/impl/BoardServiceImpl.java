package kr.co.solbipos.adi.board.board.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.system.BaseEnv;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.adi.board.board.service.BoardService;
import kr.co.solbipos.adi.board.board.service.BoardVO;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

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

    /**
     * Constructor Injection
     */
    @Autowired
    public BoardServiceImpl(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    /** 일반게시판 조회 */
    @Override
    public List<DefaultMap<Object>> getBoardList(BoardVO boardVO, SessionInfoVO sessionInfoVO) {

        // 접속사용자의 권한(M : 시스템, A : 대리점, H : 본사, S : 매장)
        boardVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        boardVO.setUserId(sessionInfoVO.getUserId());

        return boardMapper.getBoardList(boardVO);
    }

    /** 게시판 상세 팝업 - 조회 */
    @Override
    public DefaultMap<String> getBoardDetailList(BoardVO boardVO,  SessionInfoVO sessionInfoVO) {

        DefaultMap<String> resultMap = new DefaultMap<String>();

        String currentDt = currentDateTimeString();

        boardVO.setRegDt(currentDt);
        boardVO.setRegId(sessionInfoVO.getUserId());

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
        boardVO.setModId(sessionInfoVO.getUserId());
        boardVO.setRegDt(currentDt);
        boardVO.setRegId(sessionInfoVO.getUserId());

        if (boardVO.getStatus() == GridDataFg.INSERT) {
            // 게시판 게시일련번호 조회(자동채번)
            String boardSeqNo = boardMapper.getBoardBoardSeqNo(boardVO);
            boardVO.setBoardSeqNo(boardSeqNo);

            procCnt = boardMapper.getBoardInfoSaveInsert(boardVO);

            // 공개대상
            if(String.valueOf(2).equals(boardVO.getTargetFg())) {

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
            if(String.valueOf(2).equals(boardVO.getTargetFg())) {

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
            boardMapper.getBoardDetailAnswerSaveTotDelete(boardVO);

            // 게시판 전체 첨부파일 delete
            boardMapper.getBoardInfoAtchDel(boardVO);

            // 게시판 공개대상 delete
            boardMapper.getBoardPartStoreSaveDelete(boardVO);
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
        boolean isSuccess = false;

        try{

            // 업로드 파일 읽기
            BoardVO boardInfo = new BoardVO();

            // 현재 일자
            String currentDate = currentDateString();

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

                orgFileName = orgFileName.substring(0, orgFileName.lastIndexOf("."));
                // orgFileName
                // IE에선 C:\Users\김설아\Desktop\123\new2.txt
                // 크롬에선 new2.txt
                if ( orgFileName.contains("\\") ) {
                    orgFileName = orgFileName.substring(orgFileName.lastIndexOf("\\"));
                    orgFileName = orgFileName.substring(1);
                }

                if(mFile.getOriginalFilename().lastIndexOf('.') > 1) {

//                    orgFileName = mFile.getOriginalFilename().substring(0, mFile.getOriginalFilename().lastIndexOf('.'));
                    // 파일경로
                    boardInfo.setFilePath(path);
                    // 파일명 (물리적으로 저장되는 파일명)
                    boardInfo.setFileNm(newFileName);
                    // 원본 파일명
                    boardInfo.setOrginlFileNm(orgFileName);
                    // 파일확장자
                    boardInfo.setFileExt(fileExt);
                }

                // 파일 저장하는 부분
                try {
                    mFile.transferTo(new File(path+newFileName));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String currentDt = currentDateTimeString();
                boardInfo.setModDt(currentDt);
                boardInfo.setModId(sessionInfo.getUserId());
                boardInfo.setRegDt(currentDt);
                boardInfo.setRegId(sessionInfo.getUserId());

//                boardInfo.setStatusS((String)multi.getParameter("status"));
                boardInfo.setBoardCd((String)multi.getParameter("boardCd"));
                boardInfo.setBoardSeqNo((String)multi.getParameter("boardSeqNo"));

                // 게시물이 신규 일때 boardSeqNo 가져오기
//                if(String.valueOf(GridDataFg.INSERT).equals(multi.getParameter("status"))) {
//
//                    boardInfo.setTitle((String)multi.getParameter("title"));
//                    boardInfo.setUserNm((String)multi.getParameter("userNm"));
//
//                    // 게시판 신규등록시 boardSeqNo 가져오기
//                    String boardSeqNo = boardMapper.getBoardAtchBoardSeqNo(boardInfo);
//                    boardInfo.setBoardSeqNo(boardSeqNo);
//                }

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
            
        }catch(Exception e){

            isSuccess = false;
        }
        return isSuccess;
    }

    /** 게시판 댓글 조회 */
    @Override
    public List<DefaultMap<Object>> getBoardDetailAnswerList(BoardVO boardVO, SessionInfoVO sessionInfoVO) {

        boardVO.setUserId(sessionInfoVO.getUserId());

        return boardMapper.getBoardDetailAnswerList(boardVO);
    }

    /** 게시판 댓글 저장 */
    @Override
    public int getBoardDetailAnswerSave(BoardVO boardVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        boardVO.setModDt(currentDt);
        boardVO.setModId(sessionInfoVO.getUserId());

        if (boardVO.getStatus() == GridDataFg.INSERT) {

            boardVO.setRegDt(currentDt);
            boardVO.setRegId(sessionInfoVO.getUserId());
            boardVO.setUserId(sessionInfoVO.getUserId());

            // 게시판 댓글번호 조회(자동채번)
            String idx = boardMapper.getBoardAnswerIdx(boardVO);
            boardVO.setIdx(idx);

            // 아이디에 따른 작성자 조회
            String userNm = boardMapper.getBoardUserNm(boardVO);
            boardVO.setUserNm(userNm);

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
}
