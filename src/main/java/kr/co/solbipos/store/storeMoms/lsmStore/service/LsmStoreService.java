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

    /** 터치키 리스트 조회 */
    List<DefaultMap<String>> getLsmStoreList(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO);
    /** 터치키 엑셀 조회 */
    List<DefaultMap<String>> getLsmStoreExcelList(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 리스트 조회 */
    List<DefaultMap<String>> getLsmKioskStoreList(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 엑셀 조회 */
    List<DefaultMap<String>> getLsmKioskStoreExcelList(LsmStoreVO lsmStoreVO, SessionInfoVO sessionInfoVO);
}
