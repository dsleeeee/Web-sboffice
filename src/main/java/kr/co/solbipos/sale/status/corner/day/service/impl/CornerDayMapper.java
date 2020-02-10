package kr.co.solbipos.sale.status.corner.day.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.corner.day.service.CornerDayVO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CornerDayMapper {
    /** 코너별매출 일자별 탭 - 리스트 조회 */
    List<DefaultMap<String>> getCornerDayList(CornerDayVO cornerDayVO);

    /** 코너별매출 - 매장 코너 리스트 조회 */
	List<DefaultMap<String>> getCornerNmList(CornerDayVO cornerDayVO);

}
