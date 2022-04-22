package kr.co.solbipos.store.manage.closeStore.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : CloseStoreService.java
 * @Description : 기초관리 > 매장정보관리 > 폐점예정매장
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.04.22  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.04.22
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface CloseStoreService {

    /** 목록 조회 */
    List<DefaultMap<String>> getCloseStoreList(CloseStoreVO closeStoreVO, SessionInfoVO sessionInfoVO);

    /** 매장 폐점 */
    int saveCloseStore(CloseStoreVO[] closeStoreVOs, SessionInfoVO sessionInfoVO);
}
