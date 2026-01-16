package kr.co.solbipos.stock.curr.storeCurr.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface StoreCurrService {
    /** 현재고현황 - 현재고현황 리스트 조회 */
    List<DefaultMap<String>> getStoreCurrList(StoreCurrVO storeCurrVO, SessionInfoVO sessionInfoVO);

    /** 매장현재고현황 - 매장현재고수량현황 팝업 리스트 조회 */
    List<DefaultMap<String>> getSearchStoreCurrDtlList(StoreCurrVO storeCurrVO, SessionInfoVO sessionInfoVO);
}
