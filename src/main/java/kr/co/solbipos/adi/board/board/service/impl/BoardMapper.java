package kr.co.solbipos.adi.board.board.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.board.board.service.BoardVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : BoardMapper.java
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

@Mapper
@Repository
public interface BoardMapper {

    /** 일반게시판 조회 */
    List<DefaultMap<Object>> getBoardList(BoardVO boardVO);


    /** 게시판 상세 팝업 - 조회 */
    DefaultMap<String> getBoardDetailList(BoardVO boardVO);

    /** 게시판 조회수 조회 */
    String getBoardViewCnt(BoardVO boardVO);

    /**  게시판 조회수 update */
    int getBoardViewCntUpdate(BoardVO boardVO);

    /** 게시판 열람 이력 인덱스 조회(자동채번) */
    String getBoardReadingHistIdx(BoardVO boardVO);

    /**  게시판 열람 이력 insert */
    int getBoardReadingHistInsert(BoardVO boardVO);


    /** 게시판 게시일련번호 조회(자동채번)  */
    String getBoardBoardSeqNo(BoardVO boardVO);

    /** 게시판 신규등록,수정 팝업 - 저장 isert */
    int getBoardInfoSaveInsert(BoardVO boardVO);

    /** 게시판 신규등록,수정 팝업 - 저장 update */
    int getBoardInfoSaveUpdate(BoardVO boardVO);

    /** 게시판 신규등록,수정 팝업 - 저장 delete */
    int getBoardInfoSaveDelete(BoardVO boardVO);

    /** 게시판 전체 댓글 delete */
//    int getBoardDetailAnswerSaveTotDelete(BoardVO boardVO);


    /** 게시판 공개대상 insert */
    int getBoardPartStoreSaveInsert(BoardVO boardVO);

    /** 게시판 공개대상에 선택한 매장이있는지 select */
    String getBoardParStorePartOrgnCd(BoardVO boardVO);

    /** 게시판 공개대상 update */
    int getBoardPartStoreSaveUpdate(BoardVO boardVO);

    /** 게시판 공개대상 delete */
    int getBoardPartStoreSaveDelete(BoardVO boardVO);


    /** 첨부파일 저장시 IDX (자동채번) */
    String getBoardAtchIdx(BoardVO boardVO);

    /** 첨부파일 저장 isert */
    int getBoardInfoAtchSaveIsert(BoardVO boardVO);


    /** 게시판 댓글 조회 */
    List<DefaultMap<Object>> getBoardDetailAnswerList(BoardVO boardVO);


    /** 사용자이름 조회 */
    String getBoardUserNm(BoardVO boardVO);

    /** 게시판 댓글번호 조회(자동채번) */
    String getBoardAnswerIdx(BoardVO boardVO);

    /** 게시판 댓글 저장 isert */
    int getBoardDetailAnswerSaveInsert(BoardVO boardVO);

    /** 게시판 댓글 저장 update */
    int getBoardDetailAnswerSaveUpdate(BoardVO boardVO);

    /** 게시판 댓글 저장 delete */
    int getBoardDetailAnswerSaveDelete(BoardVO boardVO);

    /** 게시판 댓글수 조회 */
    String getBoardAnswerCnt(BoardVO boardVO);

    /** 게시판 댓글수 update */
    int getBoardAnswerCntUpdate(BoardVO boardVO);


    /** 게시판 첨부파일 조회 */
    List<DefaultMap<Object>> getBoardInfoAtchList(BoardVO boardVO);

    /** 게시판 첨부파일 조회 */
    List<BoardVO> getBoardDetailAtchList(BoardVO boardVO);

    /** 게시판 첨부파일 삭제 */
    int getBoardInfoAtchDel(BoardVO boardVO);


    /** 열람자목록 팝업 - 검색 */
    List<DefaultMap<Object>> getBoardReadingHistList(BoardVO boardVO);

    /** 첨부파일에 임시경로 UPDATE */
    int setTempPath(BoardVO boardVO);

    /** 게시글에 속한 첨부파일 조회 */
    List<DefaultMap<Object>> getAtchList(BoardVO boardVO);

    /** 게시글 내 이미지 파일 임시경로 서버경로로 치환 */
    int saveBoardInfoContent(BoardVO boardVO);


    /** 팝업 공고 조회 */
    List<DefaultMap<String>> getPopUpBoardList(BoardVO boardVO);
}