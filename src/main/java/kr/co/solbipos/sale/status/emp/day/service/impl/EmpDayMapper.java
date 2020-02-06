package kr.co.solbipos.sale.status.emp.day.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.emp.day.service.EmpDayVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EmpDayMapper {
	
	/** 판매자별 매출 -일자별 리스트 조회  */
    List<DefaultMap<String>> getEmpDayList(EmpDayVO empDayVO);
    
    /** 판매자별 매출 -판매자 리스트 조회  */
    List<DefaultMap<String>> getEmpMebList(EmpDayVO empDayVO);
}
