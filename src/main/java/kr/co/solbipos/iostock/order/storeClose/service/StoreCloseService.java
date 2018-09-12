package kr.co.solbipos.iostock.order.storeClose.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface StoreCloseService {
    /** 매장요청마감 마감월 리스트 조회 */
    List<DefaultMap<String>> getStoreCloseList(StoreCloseVO storeCloseVO);

    /** 매장요청마감 마감월 상세 리스트 조회 */
    List<DefaultMap<String>> getStoreCloseDtlList(StoreCloseVO storeCloseVO);

    /** 매장요청마감 마감일 마감여부 저장 */
    int saveStoreClose(StoreCloseVO[] storeCloseVOs, SessionInfoVO sessionInfoVO);

}
