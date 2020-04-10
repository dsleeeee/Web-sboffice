package kr.co.solbipos.stock.status.dayiostock.service.impl;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.stock.status.dayiostock.service.DayIostockVO;

public interface DayIostockMapper {

	/** 일수불현황 리스트 조회(매장권한) */
	List<DefaultMap<String>> storeDayIostockList(DayIostockVO dayIostockVO);

	/** 일수불현황 리스트 조회(본사권한) */
	List<DefaultMap<String>> hqDayIostockList(DayIostockVO dayIostockVO);

}
