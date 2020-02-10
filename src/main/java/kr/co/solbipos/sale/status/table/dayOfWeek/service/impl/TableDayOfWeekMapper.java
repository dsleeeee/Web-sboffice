package kr.co.solbipos.sale.status.table.dayOfWeek.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.table.day.service.TableDayVO;
import kr.co.solbipos.sale.status.table.dayOfWeek.service.TableDayOfWeekVO;

@Mapper
@Repository
public interface TableDayOfWeekMapper {
	
	/** 테이블별 매출 - 요일별 리스트 조회 */
    List<DefaultMap<String>> getTableDayOfWeekList(TableDayOfWeekVO tableDayOfWeekVO);
    
    /** 테이블별 매출 - 요일별 테이블 콤보박스 목록 조회 */
    List<DefaultMap<String>> getStoreTableList(TableDayOfWeekVO tableDayOfWeekVO);

}
