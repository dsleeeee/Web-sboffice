package kr.co.solbipos.sys.admin.posSmartOrderCon.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : PosSmartOrderConService.java
 * @Description : 시스템관리 > 관리자기능 > 스마트오더연결상태
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.06.20  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.06.20
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface PosSmartOrderConService {

    /** 매장코드 조회 */
    List<DefaultMap<String>> getPosSmartOrderConList(PosSmartOrderConVO posSmartOrderConVO, SessionInfoVO sessionInfoVO);
}
