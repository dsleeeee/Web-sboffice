package kr.co.solbipos.stock.status.storemonth.service;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

public interface StockStoreMonthService {

	/** 매장월수불 리스트 조회 */
	List<DefaultMap<String>> stockStoreMonthList(StockStoreMonthVO stockStoreMonthVO, SessionInfoVO sessionInfoVO);

}
