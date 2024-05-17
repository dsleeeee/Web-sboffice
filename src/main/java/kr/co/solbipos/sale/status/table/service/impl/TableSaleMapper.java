package kr.co.solbipos.sale.status.table.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.table.service.TableSaleVO;

@Mapper
@Repository
public interface TableSaleMapper {

    /** 테이블별 매출 - 일자별 리스트 조회 */
    List<DefaultMap<Object>> getTableDayList(TableSaleVO tableSaleVO);

    /** 테이블별 매출 - 일자별 엑셀 리스트 조회 */
    List<DefaultMap<Object>> getTableDayExcelList(TableSaleVO tableSaleVO);

    /** 테이블별 매출 - 일자별 테이블 콤보박스 목록 조회 */
    List<DefaultMap<String>> getStoreTableList(TableSaleVO tableSaleVO);

    /** 테이블별 매출 - 요일별 리스트 조회 */
    List<DefaultMap<Object>> getTableDayOfWeekList(TableSaleVO tableSaleVO);

    /** 테이블별 매출 - 월별 리스트 조회 */
    List<DefaultMap<Object>> getTableMonthList(TableSaleVO tableSaleVO);

    /** 테이블별 매출 - 월별 엑셀 리스트 조회 */
    List<DefaultMap<Object>> getTableMonthExcelList(TableSaleVO tableSaleVO);

    /** 테이블별 매출 - 월별 리스트 조회 */
    List<DefaultMap<String>> getTableDayPeriodList(TableSaleVO tableSaleVO);

    /** 테이블별 매출 - 월별 엑셀 리스트 조회 */
    List<DefaultMap<String>> getTableDayPeriodExcelList(TableSaleVO tableSaleVO);

    /** 일자별 - 리스트 총 수량 조회 */
    List<DefaultMap<Object>> getDayListCnt(TableSaleVO tableSaleVO);

    /** 일자별 - 날짜 조회 */
    List<DefaultMap<Object>> getSearchSaleDay(TableSaleVO tableSaleVO);

    /** 월별 - 리스트 총 수량 조회 */
    List<DefaultMap<Object>> getMonthListCnt(TableSaleVO tableSaleVO);

    /** 월별 - 날짜 조회 */
    List<DefaultMap<Object>> getSearchSaleMonth(TableSaleVO tableSaleVO);
}
