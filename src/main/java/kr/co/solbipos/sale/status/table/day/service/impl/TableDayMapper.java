package kr.co.solbipos.sale.status.table.day.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.table.day.service.TableDayVO;

@Mapper
@Repository
public interface TableDayMapper {

	/** 테이블별 매출 - 일자별 리스트 조회 */
    List<DefaultMap<String>> getTableDayList(TableDayVO dayVO);
    
    /** 테이블별 매출 - 일자별 테이블 콤보박스 목록 조회 */
    List<DefaultMap<String>> getStoreTableList(TableDayVO dayVO);
   
}
