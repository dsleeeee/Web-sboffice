package kr.co.solbipos.store.manage.accountManage.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : AccountManageService.java
 * @Description : 기초관리 > 매장정보관리 > 계정관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.02.14  이다솜      최초생성
 *
 * @author 솔비포스 IT개발실 WEB개발팀 이다솜
 * @since 2024.02.14
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface AccountManageService {

    /** 계정관리 - 장기미사용 계정리스트 조회 */
    List<DefaultMap<Object>> getLongTermUnusedList(AccountManageVO accountManageVO, SessionInfoVO sessionInfoVO);

    /** 계정관리 - 장기미사용 계정리스트 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getLongTermUnusedExcelList(AccountManageVO accountManageVO, SessionInfoVO sessionInfoVO);

    /** 계정관리 - 장기미사용 계정 상태 변경 */
    int saveAccountStatChg(AccountManageVO[] accountManageVOs, SessionInfoVO sessionInfoVO);
}
