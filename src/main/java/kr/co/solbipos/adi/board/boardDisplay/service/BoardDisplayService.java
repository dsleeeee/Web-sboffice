package kr.co.solbipos.adi.board.boardDisplay.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : BoardDisplayService.java
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
public interface BoardDisplayService {

    /** 일반게시판노출순서 - 조회 */
    List<DefaultMap<Object>> getBoardDisplayList(BoardDisplayVO boardDisplayVO, SessionInfoVO sessionInfoVO);

    /** 일반게시판노출순서 - 저장 */
    int getBoardDisplaySave(BoardDisplayVO[] boardDisplayVOs, SessionInfoVO sessionInfoVO);

    /** 상위노출게시물선택 팝업 - 조회 */
    List<DefaultMap<Object>> getBoardDisplayAddList(BoardDisplayVO boardDisplayVO, SessionInfoVO sessionInfoVO);
}
