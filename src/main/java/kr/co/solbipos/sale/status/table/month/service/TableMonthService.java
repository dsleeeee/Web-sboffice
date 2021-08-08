package kr.co.solbipos.sale.status.table.month.service;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

public interface TableMonthService {

	/** 테이블별 매출 - 월별 리스트 조회 */
    List<DefaultMap<String>> getTableMonthList(TableMonthVO dayVO, SessionInfoVO sessionInfoVO);

    /** 테이블별 매출 - 월별 엑셀 리스트 조회 */
    List<DefaultMap<String>> getTableMonthExcelList(TableMonthVO dayVO, SessionInfoVO sessionInfoVO);

    /** 테이블별 매출 - 월별 테이블 콤보박스 리스트 조회 */
	List<DefaultMap<String>> getStoreTableList(TableMonthVO tableMonthVO, SessionInfoVO sessionInfoVO);

}
