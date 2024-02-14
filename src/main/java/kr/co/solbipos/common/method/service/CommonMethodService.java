package kr.co.solbipos.common.method.service;

import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
 * @Class Name : CommonMethodService.java
 * @Description : (공통) 화면 공통 사용 메소드 모음
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.02.13  이다솜      최초생성
 *
 * @author 솔비포스 IT개발실 WEB개발팀 이다솜
 * @since 2024.02.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface CommonMethodService {

    /** 사용자 행위 기록 */
    int saveUserAct(CommonMethodVO commonMethodVO, SessionInfoVO sessionInfoVO);
}
