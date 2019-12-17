package kr.co.solbipos.sale.day.month.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.day.month.service.MonthVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MonthMapper {

    /** 월별종합탭 - 월별종합조회 */
    List<DefaultMap<Object>> getMonthTotalList(MonthVO monthVO);

    /** 할인구별별탭 - 할인구분별매출조회 */
    List<DefaultMap<Object>> getMonthDcList(MonthVO monthVO);

    /** 과면세별탭 - 과면세별매출조회 */
    List<DefaultMap<Object>> getMonthTaxList(MonthVO monthVO);

    /** 시간대별 - 시간대별매출조회 */
    List<DefaultMap<Object>> getMonthTimeList(MonthVO monthVO);

    /** 포스별 - 포스별매출조회 */
    List<DefaultMap<Object>> getMonthPosList(MonthVO monthVO);
}
