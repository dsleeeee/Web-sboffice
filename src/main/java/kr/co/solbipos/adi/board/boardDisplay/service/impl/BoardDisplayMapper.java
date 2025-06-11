package kr.co.solbipos.adi.board.boardDisplay.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.board.boardDisplay.service.BoardDisplayVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : BoardDisplayMapper.java
 * @Description : 부가서비스 > 게시판 > 일반게시판노출순서
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.04  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.06.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface BoardDisplayMapper {

    /** 일반게시판노출순서 - 조회 */
    List<DefaultMap<Object>> getBoardDisplayList(BoardDisplayVO boardDisplayVO);

    /** 일반게시판노출순서 - 저장 (순서, 강조여부) */
    int getBoardDisplaySaveDispSeq(BoardDisplayVO boardDisplayVO);

    /** 일반게시판노출순서 - 저장 (노출여부) */
    int getBoardDisplaySaveTopYn(BoardDisplayVO boardDisplayVO);

    /** 상위노출게시물선택 팝업 - 조회 */
    List<DefaultMap<Object>> getBoardDisplayAddList(BoardDisplayVO boardDisplayVO);
}