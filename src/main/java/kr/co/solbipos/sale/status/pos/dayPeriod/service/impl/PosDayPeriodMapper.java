package kr.co.solbipos.sale.status.pos.dayPeriod.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.pos.dayPeriod.service.PosDayPeriodVO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PosDayPeriodMapper {
    /** 코너별매출 일자별 탭 - 리스트 조회 */
    List<DefaultMap<String>> getPosDayPeriodList(PosDayPeriodVO posDayPeriodVO);

    /** 코너별매출 일자별 탭 - 리스트 조회 */
	List<DefaultMap<String>> getPosDayPeriodDtlList(PosDayPeriodVO posDayPeriodVO);


}
