package kr.co.solbipos.stock.status.storemonth.service.impl;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.stock.status.storemonth.service.StockStoreMonthVO;

public interface StockStoreMonthMapper {

	/** 매장월수불 리스트 조회 */
	List<DefaultMap<String>> stockStoreMonthList(StockStoreMonthVO stockStoreMonthVO);
}
