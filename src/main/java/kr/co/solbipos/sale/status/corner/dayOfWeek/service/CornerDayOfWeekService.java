package kr.co.solbipos.sale.status.corner.dayOfWeek.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface CornerDayOfWeekService {
    /** 코너별별요일별 탭 - 리스트 조회 */
    List<DefaultMap<String>> getCornerDayOfWeekList(CornerDayOfWeekVO cornerDayOfWeekVO, SessionInfoVO sessionInfoVO);

}
