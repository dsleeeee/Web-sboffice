package kr.co.solbipos.sale.status.corner.dayPeriod.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface CornerDayPeriodService {
    /** 코너별 설정기간별 탭 - 리스트 조회 */
    List<DefaultMap<String>> getCornerDayPeriodList(CornerDayPeriodVO cornerDayPeriodVO, SessionInfoVO sessionInfoVO);

    /** 코너별 설정기간별 탭 - 리스트 상세 조회 */
	List<DefaultMap<String>> getCornerDayPeriodDtlList(CornerDayPeriodVO cornerDayPeriodVO, SessionInfoVO sessionInfoVO);

}
