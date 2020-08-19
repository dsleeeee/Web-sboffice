package kr.co.solbipos.stock.status.monthiostock.service.impl;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.stock.status.monthiostock.service.MonthIostockVO;

public interface MonthIostockMapper {

	/** 월수불현황 리스트 조회(매장권한) */
	List<DefaultMap<String>> storeMonthIostockList(MonthIostockVO monthIostockVO);

	/** 월수불현황 리스트 조회(본사권한) */
	List<DefaultMap<String>> hqMonthIostockList(MonthIostockVO monthIostockVO);

}
