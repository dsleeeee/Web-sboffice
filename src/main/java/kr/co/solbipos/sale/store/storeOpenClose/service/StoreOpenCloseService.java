package kr.co.solbipos.sale.store.storeOpenClose.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.store.storeOpenClose.service.StoreOpenCloseVO;

import java.util.List;

/**
 * @Class Name : StoreOpenCloseService.java
 * @Description : 맘스터치 > 점포매출 > 매장 오픈/마감 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 202D  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StoreOpenCloseService {

    /** 매장 오픈/마감 현황 - 일별 탭 조회 */
    List<DefaultMap<String>> getStoreOpenCloseDayList(StoreOpenCloseVO storeOpenCloseVO, SessionInfoVO sessionInfoVO);

    /** 매장 오픈/마감 현황 - 일별 탭 상세 조회 */
    List<DefaultMap<String>> getStoreOpenCloseDayDtlList(StoreOpenCloseVO storeOpenCloseVO, SessionInfoVO sessionInfoVO);

    /** 매장 오픈/마감 현황 - 월별 탭 조회 */
    List<DefaultMap<String>> getStoreOpenCloseMonthList(StoreOpenCloseVO storeOpenCloseVO, SessionInfoVO sessionInfoVO);

    /** 매장 오픈/마감 현황 - 월별 탭 상세 조회 */
    List<DefaultMap<String>> getStoreOpenCloseMonthDtlList(StoreOpenCloseVO storeOpenCloseVO, SessionInfoVO sessionInfoVO);
}
