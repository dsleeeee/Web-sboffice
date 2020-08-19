package kr.co.solbipos.sale.anals.versusPeriod.week.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface VersusPeriodWeekService {
	/** 상품별 매출 - 일자별 리스트 조회 */
    List<DefaultMap<String>> getVersusPeriodWeekList(VersusPeriodWeekVO prodDayVO, SessionInfoVO sessionInfoVO);
	/** 상품별 매출 - 차트 조회 */
    List<DefaultMap<String>> getVersusPeriodWeekChartList(VersusPeriodWeekVO prodDayVO, SessionInfoVO sessionInfoVO);

}
