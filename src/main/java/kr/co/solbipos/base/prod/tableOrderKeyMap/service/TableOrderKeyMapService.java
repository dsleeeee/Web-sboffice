package kr.co.solbipos.base.prod.tableOrderKeyMap.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapVO;

import java.util.List;

/**
 * @Class Name : TableOrderKeyMapService.java
 * @Description : 기초관리 > 상품관리2 > 테이블오더키맵관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.24  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.07.26
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface TableOrderKeyMapService {

    /** 테이블오더키맵 매장적용 팝업 - 조회 */
    List<DefaultMap<Object>> getTableOrderKeyMapStoreRegistList(TableOrderKeyMapVO tableOrderKeyMapVO, SessionInfoVO sessionInfoVO);

    /** 테이블오더 - 키오스크 카테고리(분류) 등록 */
    int saveKioskCategory(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO);

    /** 테이블오더 - 키오스크 키맵 수정 */
    int updateKioskKeyMap(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO);

    /** 테이블오더 - 키오스크 키맵 등록 */
    int saveKioskKeyMap(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO);

    /** 테이블오더 - 키맵매장적용 */
    int saveKioskKeyMapStore(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO);
}