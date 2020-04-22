package kr.co.solbipos.sale.status.corner.month.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.corner.month.service.CornerMonthVO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CornerMonthMapper {
    /** 코너별매출 월별 탭 - 리스트 조회 */
    List<DefaultMap<String>> getCornerMonthList(CornerMonthVO cornerMonthVO);

    /** 코너별매출 월별 탭 - 엑셀 리스트 조회 */
	List<DefaultMap<String>> getCornerMonthExcelList(CornerMonthVO cornerMonthVO);

}
