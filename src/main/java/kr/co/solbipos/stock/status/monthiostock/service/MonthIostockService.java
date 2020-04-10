package kr.co.solbipos.stock.status.monthiostock.service;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

public interface MonthIostockService {

	/** 월수불현황 리스트 조회 */
	List<DefaultMap<String>> monthIostockList(MonthIostockVO monthIostockVO, SessionInfoVO sessionInfoVO);

}
