package kr.co.solbipos.sale.status.emp.month.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.emp.month.service.EmpMonthVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EmpMonthMapper {
	
	/** 판매자별 매출 -월별 리스트 조회  */
    List<DefaultMap<String>> getEmpMonthList(EmpMonthVO empMonthVO);
    
    /** 판매자별 매출 -판매자 리스트 조회  */
    List<DefaultMap<String>> getEmpMebList(EmpMonthVO empMonthVO);
}
