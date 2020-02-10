package kr.co.solbipos.sale.status.emp.dayPeriod.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface EmpDayPeriodService {
    /** 판매자별 설정기간별 탭 - 리스트 조회 */
    List<DefaultMap<String>> getEmpDayPeriodList(EmpDayPeriodVO empDayPeriodVO, SessionInfoVO sessionInfoVO);

    /** 판매자별 설정기간별 탭 - 리스트 상세 조회 */
	List<DefaultMap<String>> getEmpDayPeriodDtlList(EmpDayPeriodVO empDayPeriodVO, SessionInfoVO sessionInfoVO);

}
