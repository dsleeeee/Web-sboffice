package kr.co.solbipos.stock.status.periodiostock.service.impl;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.stock.status.periodiostock.service.PeriodIostockVO;

public interface PeriodIostockMapper {
	/** 기간수불현황 - 기간수불현황 리스트 조회(본사) */
    List<DefaultMap<String>> getPeriodIostockList(PeriodIostockVO periodIostockVO);
    
    /** 기간수불현황 - 기간수불현황 리스트 조회(매장) */
    List<DefaultMap<String>> getPeriodIostockStoreList(PeriodIostockVO periodIostockVO);
    
    /** 기간수불현황 - 기간수불현황 상세 리스트 조회(본사) */
    List<DefaultMap<String>> getPeriodiostockProdDtlList(PeriodIostockVO periodIostockVO);
    
    /** 기간수불현황 - 기간수불현황 상세 리스트 조회(매장) */
    List<DefaultMap<String>> getPeriodiostockProdStoreDtlList(PeriodIostockVO periodIostockVO);
}
