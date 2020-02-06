package kr.co.solbipos.sale.status.emp.dayOfWeek.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface EmpDayOfWeekService {
	
	/** 판매자별 매출 -요일별 리스트 조회  */
    List<DefaultMap<String>> getEmpDayOfWeekList(EmpDayOfWeekVO empDayOfWeekVO, SessionInfoVO sessionInfoVO);
    
}
