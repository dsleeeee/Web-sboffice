package kr.co.solbipos.sale.status.emp.day.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface EmpDayService {
	/** 판매자별 매출 -일자별 리스트 조회  */
    List<DefaultMap<String>> getEmpDayList(EmpDayVO empDayVO, SessionInfoVO sessionInfoVO);
	/** 판매자별 매출 -일자별 리스트(엑셀) 조회  */
    List<DefaultMap<String>> getEmpDayExcelList(EmpDayVO empDayVO, SessionInfoVO sessionInfoVO);
    /** 판매자별 매출 -판매자 리스트 조회  */
    List<DefaultMap<String>> getEmpMebList(EmpDayVO empDayVO, SessionInfoVO sessionInfoVO);

}
