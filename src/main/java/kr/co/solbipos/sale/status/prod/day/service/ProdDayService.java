package kr.co.solbipos.sale.status.prod.day.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface ProdDayService {
	/** 상품별 매출 - 일자별 리스트 조회 */
    List<DefaultMap<String>> getProdDayList(ProdDayVO prodDayVO, SessionInfoVO sessionInfoVO);
    
    /** 상품별 매출 - 일자별 엑셀 다운로드 조회 */
    List<DefaultMap<String>> getProdDayExcelList(ProdDayVO prodDayVO, SessionInfoVO sessionInfoVO);
}
