package kr.co.solbipos.store.storeMoms.lsmStore.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.storeMoms.lsmStore.service.LsmStoreVO;

import java.util.List;

/**
 * @Class Name : LsmStoreService.java
 * @Description : 맘스터치 > 매장관리 > LSM사용매장조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.04.26  권지현      최초생성
 *
 * @author 솔비포스
 * @since 2023.04.26
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface LsmStoreService {

    /** 조회 */
    List<DefaultMap<String>> getLsmStoreList(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<String>> getLsmStoreExcelList(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO);
}
