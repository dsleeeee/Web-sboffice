package kr.co.solbipos.sale.status.barcd.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.barcd.service.BarcdVO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BarcdMapper {
    /** 코너별매출 일자별 탭 - 리스트 조회 */
    List<DefaultMap<String>> getBarcdList(BarcdVO barcdVO);

    /** 코너별매출 일자별 탭 - 상세 리스트 조회 */
	List<DefaultMap<String>> getBarcdDtlList(BarcdVO barcdVO);
	
	/** 코너별매출 일자별 탭 - 엑셀 전체 리스트 조회 */
	List<DefaultMap<String>> getBarcdExcelList(BarcdVO barcdVO);
	
	/** 코너별매출 일자별 탭 - 엑셀 전체 상세 리스트 조회 */
	List<DefaultMap<String>> getBarcdDtlExcelList(BarcdVO barcdVO);
}
