package kr.co.solbipos.sys.board.boardMaster.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : BoardMasterService.java
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
public interface BoardMasterService {

    /** 게시판관리 조회 */
    List<DefaultMap<Object>> getBoardMasterList(BoardMasterVO boardMasterVO, SessionInfoVO sessionInfoVO);

    /** 게시판관리 저장 */
    int getBoardMasterSave(BoardMasterVO[] boardMasterVOs, SessionInfoVO sessionInfoVO);
}
