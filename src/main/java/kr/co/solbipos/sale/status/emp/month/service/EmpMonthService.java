package kr.co.solbipos.sale.status.emp.month.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.status.emp.day.service.EmpDayVO;

import java.util.List;

public interface EmpMonthService {
	
	/** 판매자별 매출 -월별 리스트 조회  */
    List<DefaultMap<String>> getEmpMonthList(EmpMonthVO empMonthVO, SessionInfoVO sessionInfoVO);
    
	/** 판매자별 매출 -월별 리스트(엑셀) 조회  */
    List<DefaultMap<String>> getEmpMonthExcelList(EmpMonthVO empMonthVO, SessionInfoVO sessionInfoVO);
    
    /** 판매자별 매출 -판매자 리스트 조회  */
    List<DefaultMap<String>> getEmpMebList(EmpMonthVO empMonthVO, SessionInfoVO sessionInfoVO);
}
