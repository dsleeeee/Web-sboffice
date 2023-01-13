package kr.co.solbipos.sale.store.storeMonthChannel.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface StoreMonthChannelService {

    /** 월별 리스트 조회 */
    List<DefaultMap<String>> getMonthList(StoreMonthChannelVO storeMonthChannelVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<String>> getMonthExcelList(StoreMonthChannelVO storeMonthChannelVO, SessionInfoVO sessionInfoVO);
}
