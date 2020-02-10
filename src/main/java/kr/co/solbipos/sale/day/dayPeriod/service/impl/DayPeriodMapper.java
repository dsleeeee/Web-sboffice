package kr.co.solbipos.sale.day.dayPeriod.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.day.dayPeriod.service.DayPeriodVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DayPeriodMapper {

    /** 시간대별탭 - 시간대별 매출조회 */
    List<DefaultMap<Object>> getDayPeriodTimeList(DayPeriodVO dayPeriodVO);

    /** 시간대별탭 - 시간대별 매출상세조회 */
    List<DefaultMap<Object>> getDayPeriodTimeDetailList(DayPeriodVO dayPeriodVO);

    /** 상품분류별탭 - 상품분류별 매출조회 */
    List<DefaultMap<Object>> getDayPeriodProdClassList(DayPeriodVO dayPeriodVO);

    /** 상품분류별탭 - 상품분류별 매출상세조회 */
    List<DefaultMap<Object>> getDayPeriodProdClassDetailList(DayPeriodVO dayPeriodVO);

    /** 외식테이블별탭 - 외식테이블별 매출조회 */
    List<DefaultMap<Object>> getDayPeriodTableList(DayPeriodVO dayPeriodVO);

    /** 코너별탭 - 코너별 매출조회 */
    List<DefaultMap<Object>> getDayPeriodCornerList(DayPeriodVO dayPeriodVO);

    /** 코너별탭 - 코너별 매출상세조회 */
    List<DefaultMap<Object>> getDayPeriodCornerDetailList(DayPeriodVO dayPeriodVO);

    /** 상품권별탭 - 상품권별 매출조회 */
    List<DefaultMap<Object>> getDayPeriodGiftList(DayPeriodVO dayPeriodVO);

    /** 상품권별탭 - 상품권별 매출상세조회 */
    List<DefaultMap<Object>> getDayPeriodGiftDetailList(DayPeriodVO dayPeriodVO);
}