package kr.co.solbipos.sale.day.dayOfWeek.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.day.dayOfWeek.service.DayOfWeekVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DayOfWeekMapper {

    /** 주간종합탭 - 주간종합조회 */
    List<DefaultMap<Object>> getDayOfWeekTotalList(DayOfWeekVO dayOfWeekVO);

    /** 할인구별별탭 - 할인구분별매출조회 */
    List<DefaultMap<Object>> getDayOfWeekDcList(DayOfWeekVO dayOfWeekVO);

    /** 과면세별탭 - 과면세별매출조회 */
    List<DefaultMap<Object>> getDayOfWeekTaxList(DayOfWeekVO dayOfWeekVO);

    /** 시간대별 - 시간대별매출조회 */
    List<DefaultMap<Object>> getDayOfWeekTimeList(DayOfWeekVO dayOfWeekVO);

    /** 외식테이블별 - 외식테이블별매출조회 */
    List<DefaultMap<Object>> getDayOfWeekTableList(DayOfWeekVO dayOfWeekVO);

    /** 포스별 - 포스별매출조회 */
    List<DefaultMap<Object>> getDayOfWeekPosList(DayOfWeekVO dayOfWeekVO);
}
