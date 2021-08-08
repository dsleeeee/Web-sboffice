package kr.co.solbipos.sale.status.table.dayOfWeek.service;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

public interface TableDayOfWeekService {
	
	/** 테이블별 매출 - 요일별 리스트 조회 */
    List<DefaultMap<String>> getTableDayOfWeekList(TableDayOfWeekVO tableDayOfWeekVO, SessionInfoVO sessionInfoVO);

	/** 테이블별 매출 - 요일별 테이블 콤보박스 리스트 조회 */
	List<DefaultMap<String>> getStoreTableList(TableDayOfWeekVO tableDayOfWeekVO, SessionInfoVO sessionInfoVO);

}
