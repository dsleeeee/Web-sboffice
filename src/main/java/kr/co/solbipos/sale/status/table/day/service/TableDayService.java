package kr.co.solbipos.sale.status.table.day.service;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

public interface TableDayService {

	/** 테이블별 매출 - 일자별 리스트 조회 */
    List<DefaultMap<String>> getTableDayList(TableDayVO dayVO, SessionInfoVO sessionInfoVO);

    /** 테이블별 매출 - 일자별 엑셀 리스트 조회 */
    List<DefaultMap<String>> getTableDayExcelList(TableDayVO dayVO, SessionInfoVO sessionInfoVO);

    /** 테이블별 매출 - 일자별 테이블 콤보박스 리스트 조회 */
    List<DefaultMap<String>> getStoreTableList(TableDayVO dayVO, SessionInfoVO sessionInfoVO);

}
