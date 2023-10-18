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
    List<DefaultMap<String>> getTableDayList(TableSaleVO tableSaleVO);

    /** 테이블별 매출 - 일자별 엑셀 리스트 조회 */
    List<DefaultMap<String>> getTableDayExcelList(TableSaleVO tableSaleVO);

    /** 테이블별 매출 - 일자별 테이블 콤보박스 목록 조회 */
    List<DefaultMap<String>> getStoreTableList(TableSaleVO tableSaleVO);

    /** 테이블별 매출 - 요일별 리스트 조회 */
    List<DefaultMap<String>> getTableDayOfWeekList(TableSaleVO tableSaleVO);

    /** 테이블별 매출 - 월별 리스트 조회 */
    List<DefaultMap<String>> getTableMonthList(TableSaleVO tableSaleVO);

    /** 테이블별 매출 - 월별 엑셀 리스트 조회 */
    List<DefaultMap<String>> getTableMonthExcelList(TableSaleVO tableSaleVO);

    /** 테이블별 매출 - 월별 리스트 조회 */
    List<DefaultMap<String>> getTableDayPeriodList(TableSaleVO tableSaleVO);

    /** 테이블별 매출 - 월별 엑셀 리스트 조회 */
    List<DefaultMap<String>> getTableDayPeriodExcelList(TableSaleVO tableSaleVO);}
