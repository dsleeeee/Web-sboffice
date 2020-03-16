package kr.co.solbipos.adi.board.library.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.board.board.service.BoardVO;
import kr.co.solbipos.adi.board.library.service.LibraryVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : LibraryMapper.java
 * @Description : 부가서비스 > 자료실 > 자료실
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.03.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.03.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface LibraryMapper {

    /** 자료실 조회 */
    List<DefaultMap<Object>> getLibraryList(LibraryVO libraryVO);


    /** 자료실 상세 팝업 - 첨부파일 조회 */
    List<LibraryVO> getLibraryDetailAtchList(LibraryVO libraryVO);


    /** 자료실 신규등록,수정 팝업 - 자료실 조회 */
    List<DefaultMap<Object>> getLibraryInfoList(LibraryVO libraryVO);


    /** 자료실 게시일련번호 조회(자동채번)  */
    String getBoardBoardSeqNo(LibraryVO libraryVO);

    /** 아이디에 따른 작성자 조회 */
    String getBoardUserNm(LibraryVO libraryVO);

    /** 첨부파일 저장시 IDX (자동채번)  */
    String getBoardAtchIdx(LibraryVO libraryVO);

    /** 첨부파일 저장 isert */
    int getLibraryInfoAtchSaveIsert(LibraryVO libraryVO);

    /** 첨부파일 자료명 저장 insert */
    int getLibraryInfoSaveInsert(LibraryVO libraryVO);

    /** 첨부파일 자료명 저장 update */
    int getLibraryInfoSaveUpate(LibraryVO libraryVO);


    /** 자료실 신규등록,수정 팝업 - 저장 delete */
    int getLibraryInfoSaveDelete(LibraryVO libraryVO);

    /** 자료실 신규등록,수정 팝업 - 저장 delete */
    int getLibraryInfoAtchSaveDelete(LibraryVO libraryVO);


    /** 자료실 첨부파일 삭제 */
    int getLibraryInfoAtchDel(LibraryVO libraryVO);
}