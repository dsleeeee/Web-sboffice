package kr.co.solbipos.sys.board.boardMaster.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sys.board.boardMaster.service.BoardMasterVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : BoardMasterMapper.java
 * @Description : 시스템관리 > 게시판관리 > 게시판관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.02.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.02.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Mapper
@Repository
public interface BoardMasterMapper {

    /** 게시판관리 조회 */
    List<DefaultMap<Object>> getBoardMasterList(BoardMasterVO boardMasterVO);

    /** 게시판관리 코드조회(자동채번) */
    String getBoardMasterBoardCd(BoardMasterVO boardMasterVO);

    /** 게시판관리 저장 insert */
    int getBoardMasterSaveInsert(BoardMasterVO boardMasterVO);

    /** 게시판관리 저장 update */
    int getBoardMasterSaveUpdate(BoardMasterVO boardMasterVO);

    /** 게시판관리 저장 delete */
    int getBoardMasterSaveDelete(BoardMasterVO boardMasterVO);
}