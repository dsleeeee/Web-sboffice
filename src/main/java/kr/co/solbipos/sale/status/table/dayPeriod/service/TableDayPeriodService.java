package kr.co.solbipos.sale.status.table.dayPeriod.service;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

public interface TableDayPeriodService {
	
	/** 설정기간별 매출 - 조회일자별 리스트 조회 */
	List<DefaultMap<String>> getTableDayPeriodList(TableDayPeriodVO tdpVO, SessionInfoVO sessionInfoVO);

}
