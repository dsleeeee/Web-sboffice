package kr.co.solbipos.iostock.frnchs.store.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface FrnchsStoreService {
	/** 매장별 입출고내역 - 매장별 입출고내역 리스트 조회 */
    List<DefaultMap<String>> getFrnchsStoreList(FrnchsStoreVO FrnchsStoreVO, SessionInfoVO sessionInfoVO);
    /** 매장별 입출고내역 상세 레이어- 매장별 입출고내역 매장상세 조회 */
    List<DefaultMap<String>> getFrnchsStoreInfoList(FrnchsStoreVO frnchsStoreVO);
    /** 매장별 입출고내역 상세 레이어- 매장별 입출고내역 상세 리스트 조회 */
    List<DefaultMap<String>> getFrnchsStoreDtlList(FrnchsStoreVO frnchsStoreVO);
    /** 매장별 입출고내역 - 매장별 입출고내역 엑셀리스트 조회 */
	List<DefaultMap<String>> getFrnchsStoreExcelList(FrnchsStoreVO frnchsStoreVO, SessionInfoVO sessionInfoVO);
}
