package kr.co.solbipos.store.manage.storeInfoBatchChange.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : StoreInfoBatchChangeService.java
 * @Description : 기초관리 > 매장정보관리 > 매장정보일괄변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.08.13  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.08.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StoreInfoBatchChangeService {

    /** 매장정보일괄변경 - 조회 */
    List<DefaultMap<Object>> getStoreInfoBatchChangeList(StoreInfoBatchChangeVO storeInfoBatchChangeVO, SessionInfoVO sessionInfoVO);

    /** 대리점코드 콤보박스 조회 */
    List<DefaultMap<Object>> getAgencyCdComboList(StoreInfoBatchChangeVO storeInfoBatchChangeVO, SessionInfoVO sessionInfoVO);

    /** 관리벤사 콤보박스 조회 */
    List<DefaultMap<Object>> getVanCdComboList(StoreInfoBatchChangeVO storeInfoBatchChangeVO, SessionInfoVO sessionInfoVO);

    /** 매장정보일괄변경 - 저장 */
    int getStoreInfoBatchChangeSave(StoreInfoBatchChangeVO[] storeInfoBatchChangeVOs, SessionInfoVO sessionInfoVO);
}