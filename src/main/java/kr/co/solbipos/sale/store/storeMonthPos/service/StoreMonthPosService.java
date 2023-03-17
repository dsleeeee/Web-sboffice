package kr.co.solbipos.sale.store.storeMonthPos.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface StoreMonthPosService {

    /** 월별 리스트 조회 */
    List<DefaultMap<String>> getMonthList(StoreMonthPosVO storeMonthPosVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<String>> getMonthExcelList(StoreMonthPosVO storeMonthPosVO, SessionInfoVO sessionInfoVO);
}
