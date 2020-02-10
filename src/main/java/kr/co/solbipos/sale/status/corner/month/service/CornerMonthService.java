package kr.co.solbipos.sale.status.corner.month.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface CornerMonthService {
    /** 코너별별요일별 탭 - 리스트 조회 */
    List<DefaultMap<String>> getCornerMonthList(CornerMonthVO cornerMonthVO, SessionInfoVO sessionInfoVO);

}
