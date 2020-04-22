package kr.co.solbipos.stock.status.periodiostock.service;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

public interface PeriodIostockService {
	/** 기간수불현황 - 기간수불현황 리스트 조회 */
	public List<DefaultMap<String>> getPeriodIostockList(PeriodIostockVO periodIostockVO, SessionInfoVO sessionInfoVO);
	
	/** 기간수불현황 - 기간수불현황 상품코드 선택 상세 리스트 조회 */
	public List<DefaultMap<String>> getPeriodiostockProdDtlList(PeriodIostockVO periodIostockVO, SessionInfoVO sessionInfoVO);
	
	/** 기간수불현황 - 기간수불현황 리스트 조회 */
	public List<DefaultMap<String>> getPeriodIostockExcelList(PeriodIostockVO periodIostockVO, SessionInfoVO sessionInfoVO);
}
