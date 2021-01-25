package kr.co.solbipos.sale.status.pos.dayPeriod.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.pos.dayPeriod.service.PosDayPeriodVO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PosDayPeriodMapper {

    /** 설정기간별탭 - 조회 */
    List<DefaultMap<String>> getPosDayPeriodList(PosDayPeriodVO posDayPeriodVO);

    /** 설정기간별탭 - 엑셀 조회 */
    List<DefaultMap<String>> getPosDayPeriodExcelList(PosDayPeriodVO posDayPeriodVO);

    /** 설정기간별탭 - 상세 조회 */
	List<DefaultMap<String>> getPosDayPeriodDtlList(PosDayPeriodVO posDayPeriodVO);

    /** 설정기간별탭 - 상세 엑셀 조회 */
	List<DefaultMap<String>> getPosDayPeriodDtlExcelList(PosDayPeriodVO posDayPeriodVO);
}