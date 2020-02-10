package kr.co.solbipos.sale.status.table.dayPeriod.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.table.dayPeriod.service.TableDayPeriodVO;

@Mapper
@Repository
public interface TableDayPeriodMapper {
	
	/** 테이블별 매출 - 월별 리스트 조회 */
	List<DefaultMap<String>> getTableDayPeriodList(TableDayPeriodVO tdpVO);

}
