package kr.co.solbipos.sale.status.prod.hour.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface ProdHourService {
	/** 시간대별탭 - 조회 */
    List<DefaultMap<String>> getProdHourList(ProdHourVO prodHourVO, SessionInfoVO sessionInfoVO);
    
    /** 시간대별탭 - 엑셀 조회 */
    List<DefaultMap<String>> getProdHourExcelList(ProdHourVO prodHourVO, SessionInfoVO sessionInfoVO);
}