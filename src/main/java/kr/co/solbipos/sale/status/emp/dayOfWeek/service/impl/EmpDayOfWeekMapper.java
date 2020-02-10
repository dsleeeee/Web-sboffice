package kr.co.solbipos.sale.status.emp.dayOfWeek.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.emp.dayOfWeek.service.EmpDayOfWeekVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EmpDayOfWeekMapper {
	
	/** 판매자별 매출 -요일별 리스트 조회  */
    List<DefaultMap<String>> getEmpDayOfWeekList(EmpDayOfWeekVO empDayOfWeekVO);
    
    /** 판매자별 매출 -판매자 리스트 조회  */
    List<DefaultMap<String>> getEmpMebList(EmpDayOfWeekVO empDayOfWeekVO);
}
