package kr.co.solbipos.sale.status.corner.day.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface CornerDayService {
    /** 코너별매출일자별 - 리스트 조회 */
    List<DefaultMap<String>> getCornerDayList(CornerDayVO cornerDayVO, SessionInfoVO sessionInfoVO);

    /** 코너별매출 - 매장 코너 리스트 조회 */
	List<DefaultMap<String>> getCornerNmList(CornerDayVO cornerDayVO, SessionInfoVO sessionInfoVO);

}
