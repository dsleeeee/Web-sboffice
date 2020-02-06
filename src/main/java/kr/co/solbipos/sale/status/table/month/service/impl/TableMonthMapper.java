package kr.co.solbipos.sale.status.table.month.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.table.day.service.TableDayVO;
import kr.co.solbipos.sale.status.table.month.service.TableMonthVO;

@Mapper
@Repository
public interface TableMonthMapper {
	
	/** 테이블별 매출 - 월별 리스트 조회 */
    List<DefaultMap<String>> getTableMonthList(TableMonthVO tableMonthVO);
    
    /** 테이블별 매출 - 월별 테이블 콤보박스 목록 조회 */
    List<DefaultMap<String>> getStoreTableList(TableMonthVO tableMonthVO);

}
