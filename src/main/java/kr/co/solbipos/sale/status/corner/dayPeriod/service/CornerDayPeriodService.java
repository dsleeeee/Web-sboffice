package kr.co.solbipos.sale.status.corner.dayPeriod.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface CornerDayPeriodService {
	/** 설정기간별탭 - 조회 */
    List<DefaultMap<String>> getCornerDayPeriodList(CornerDayPeriodVO cornerDayPeriodVO, SessionInfoVO sessionInfoVO);

	/** 설정기간별탭 - 엑셀 조회 */
	List<DefaultMap<String>> getCornerDayPeriodExcelList(CornerDayPeriodVO cornerDayPeriodVO, SessionInfoVO sessionInfoVO);

	/** 설정기간별탭 - 상세 조회 */
	List<DefaultMap<String>> getCornerDayPeriodDtlList(CornerDayPeriodVO cornerDayPeriodVO, SessionInfoVO sessionInfoVO);

	/** 설정기간별탭 - 상세 엑셀 조회 */
	List<DefaultMap<String>> getCornerDayPeriodDtlExcelList(CornerDayPeriodVO cornerDayPeriodVO, SessionInfoVO sessionInfoVO);
}