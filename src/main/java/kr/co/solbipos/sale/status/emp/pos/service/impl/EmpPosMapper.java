package kr.co.solbipos.sale.status.emp.pos.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.emp.pos.service.EmpPosVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EmpPosMapper {
	
	/** 판매자별 매출 -포스별 리스트 조회  */
    List<DefaultMap<String>> getEmpPosList(EmpPosVO empPosVO);
	/** 판매자별 매출 -포스별 리스트(엑셀) 조회  */
    List<DefaultMap<String>> getEmpPosExcelList(EmpPosVO empPosVO); 
    /** 판매자별 매출 -판매자 리스트 조회  */
    List<DefaultMap<String>> getEmpMebList(EmpPosVO empPosVO);
}
