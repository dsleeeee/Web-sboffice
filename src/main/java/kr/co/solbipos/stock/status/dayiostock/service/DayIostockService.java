package kr.co.solbipos.stock.status.dayiostock.service;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

public interface DayIostockService {

	/** 일수불현황 리스트 조회 */
	List<DefaultMap<String>> dayIostockList(DayIostockVO dayIostockVO, SessionInfoVO sessionInfoVO);

}
