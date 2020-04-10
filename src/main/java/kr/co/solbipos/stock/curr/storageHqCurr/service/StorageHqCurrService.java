package kr.co.solbipos.stock.curr.storageHqCurr.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface StorageHqCurrService {
    /** 창고별현재고현황 - 창고별현재고현황 리스트 조회 */
    List<DefaultMap<String>> getStorageHqCurrList(StorageHqCurrVO storageHqCurrVO, SessionInfoVO sessionInfoVO);
    /** 창고별현재고현황 - 창고별현재고현황 창고 리스트 조회 */
	List<DefaultMap<String>> getStorageList(StorageHqCurrVO storageHqCurrVO, SessionInfoVO sessionInfoVO);

}
