package kr.co.solbipos.sale.status.emp.pos.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface EmpPosService {
	
	/** 판매자별 매출 -월별 리스트 조회  */
    List<DefaultMap<String>> getEmpPosList(EmpPosVO empPosVO, SessionInfoVO sessionInfoVO);
	/** 판매자별 매출 -월별 리스트 조회  */
    List<DefaultMap<String>> getEmpPosExcelList(EmpPosVO empPosVO, SessionInfoVO sessionInfoVO); 
    /** 판매자별 매출 -판매자 리스트 조회  */
    List<DefaultMap<String>> getEmpMebList(EmpPosVO empPosVO, SessionInfoVO sessionInfoVO);
}
