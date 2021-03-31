package kr.co.solbipos.application.pos.posBoard.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : PosBoardService.java
 * @Description : POS 화면에서 게시판(포스용)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.03.30  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2021.03.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface PosBoardService {

    /** 게시판 조회 */
    List<DefaultMap<Object>> getPosBoardList(PosBoardVO posBoardVO, SessionInfoVO sessionInfoVO);
}