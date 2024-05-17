package kr.co.solbipos.sale.status.table.service;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

public interface TableSaleService {

    /** 테이블별 매출 - 일자별 리스트 조회 */
    List<DefaultMap<Object>> getTableDayList(TableSaleVO tableSaleVO, SessionInfoVO sessionInfoVO);

    /** 테이블별 매출 - 일자별 엑셀 리스트 조회 */
    List<DefaultMap<Object>> getTableDayExcelList(TableSaleVO tableSaleVO, SessionInfoVO sessionInfoVO);

    /** 테이블별 매출 - 일자별 테이블 콤보박스 리스트 조회 */
    List<DefaultMap<String>> getStoreTableList(TableSaleVO tableSaleVO, SessionInfoVO sessionInfoVO);

    /** 테이블별 매출 - 요일별 리스트 조회 */
    List<DefaultMap<Object>> getTableDayOfWeekList(TableSaleVO tableSaleVO, SessionInfoVO sessionInfoVO);

    /** 테이블별 매출 - 월별 리스트 조회 */
    List<DefaultMap<Object>> getTableMonthList(TableSaleVO tableSaleVO, SessionInfoVO sessionInfoVO);

    /** 테이블별 매출 - 월별 엑셀 리스트 조회 */
    List<DefaultMap<Object>> getTableMonthExcelList(TableSaleVO tableSaleVO, SessionInfoVO sessionInfoVO);

    /** 설정기간별 매출 - 조회일자별 리스트 조회 */
    List<DefaultMap<String>> getTableDayPeriodList(TableSaleVO tableSaleVO, SessionInfoVO sessionInfoVO);

    /** 설정기간별 매출 - 조회일자별 엑셀 리스트 조회 */
    List<DefaultMap<String>> getTableDayPeriodExcelList(TableSaleVO tableSaleVO, SessionInfoVO sessionInfoVO);

    /** 일자별 - 리스트 총 수량 조회 */
    List<DefaultMap<Object>> getDayListCnt(TableSaleVO tableSaleVO, SessionInfoVO sessionInfoVO);

    /** 월별 - 리스트 총 수량 조회 */
    List<DefaultMap<Object>> getMonthListCnt(TableSaleVO tableSaleVO, SessionInfoVO sessionInfoVO);
}
