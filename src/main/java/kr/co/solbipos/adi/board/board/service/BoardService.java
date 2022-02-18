package kr.co.solbipos.adi.board.board.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
 * @Class Name : BoardService.java
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
public interface BoardService {

    /** 일반게시판 조회 */
    List<DefaultMap<Object>> getBoardList(BoardVO boardVO, SessionInfoVO sessionInfoVO);

    /** 게시판 상세 팝업 - 조회 */
    DefaultMap<String> getBoardDetailList(BoardVO boardVO, SessionInfoVO sessionInfoVO);

    /** 게시판 신규등록,수정 팝업 - 상세조회 */
    DefaultMap<String> getBoardInfoList(BoardVO boardVO, SessionInfoVO sessionInfoVO);

    /** 게시판 신규등록,수정 팝업 - 저장 */
    int getBoardInfoSave(BoardVO boardVO, SessionInfoVO sessionInfoVO);

    /** 게시판 신규등록,수정 팝업 - 첨부파일 저장 */
    boolean getBoardInfoAtchSave(MultipartHttpServletRequest multi, SessionInfoVO sessionInfo);

    /** 게시판 댓글 조회 */
    List<DefaultMap<Object>> getBoardDetailAnswerList(BoardVO boardVO, SessionInfoVO sessionInfoVO);

    /** 게시판 댓글 저장 */
    int getBoardDetailAnswerSave(BoardVO boardVO, SessionInfoVO sessionInfoVO);

    /** 게시판 첨부파일 조회 */
    List<DefaultMap<Object>> getBoardInfoAtchList(BoardVO boardVO, SessionInfoVO sessionInfoVO);

    /** 게시판 첨부파일 조회 */
    List<BoardVO> getBoardDetailAtchList(BoardVO boardVO, SessionInfoVO sessionInfoVO);

    /** 게시판 첨부파일 삭제 */
    int getBoardInfoAtchDel(BoardVO boardVO, SessionInfoVO sessionInfoVO);

    /** 열람자목록 팝업 - 검색 */
    List<DefaultMap<Object>> getBoardReadingHistList(BoardVO boardVO, SessionInfoVO sessionInfoVO);

    /** 첨부파일에 임시경로 UPDATE 후 게시글 이미지 서버경로로 치환 */
    int setServerPathFile(BoardVO[] boardVOs, SessionInfoVO sessionInfoVO);


    /** 팝업 공고 조회 */
    List<DefaultMap<String>> getPopUpBoardList(SessionInfoVO sessionInfoVO);

    /** 본사/매장 목록 조회 */
    List<DefaultMap<String>> selectHqStoreList(BoardVO boardVO , SessionInfoVO sessionInfoVO);
}
