package kr.co.solbipos.common.popup.selectTable.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : SelectTableService.java
 * @Description : (공통) 테이블 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.10.17  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.10.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SelectTableService {

    /** 테이블 공통 - 테이블 리스트 조회 */
    List<DefaultMap<String>> getSelectTableList(SelectTableVO selectTableVO, SessionInfoVO sessionInfoVO);
}