package kr.co.solbipos.sale.status.corner.dayPeriod.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.corner.dayPeriod.service.CornerDayPeriodVO;

@Mapper
@Repository
public interface CornerDayPeriodMapper {
    /** 설정기간별탭 - 조회 */
    List<DefaultMap<String>> getCornerDayPeriodList(CornerDayPeriodVO cornerDayPeriodVO);

	/** 설정기간별탭 - 엑셀 조회 */
	List<DefaultMap<String>> getCornerDayPeriodExcelList(CornerDayPeriodVO cornerDayPeriodVO);

	/** 설정기간별탭 - 상세 조회 */
	List<DefaultMap<String>> getCornerDayPeriodDtlList(CornerDayPeriodVO cornerDayPeriodVO);

	/** 설정기간별탭 - 상세 엑셀 조회 */
	List<DefaultMap<String>> getCornerDayPeriodDtlExcelList(CornerDayPeriodVO cornerDayPeriodVO);
}