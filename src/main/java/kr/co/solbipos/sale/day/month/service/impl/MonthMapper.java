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

    /** 할인구별별탭 - 할인구분별 매출조회 */
    List<DefaultMap<Object>> getMonthDcList(MonthVO monthVO);

    /** 과면세별탭 - 과면세별 매출조회 */
    List<DefaultMap<Object>> getMonthTaxList(MonthVO monthVO);

    /** 시간대별탭 - 시간대별 매출조회 */
    List<DefaultMap<Object>> getMonthTimeList(MonthVO monthVO);

    /** 상품분류별탭 - 상품분류별 매출조회 */
    List<DefaultMap<Object>> getMonthProdClassList(MonthVO monthVO);

    /** 코너별탭 - 코너별 매출조회 */
    List<DefaultMap<Object>> getMonthCornerList(MonthVO monthVO);

    /** 외식테이블별탭 - 외식테이블별 매출조회 */
    List<DefaultMap<Object>> getMonthTableList(MonthVO monthVO);

    /** 포스별탭 - 포스별 매출조회 */
    List<DefaultMap<Object>> getMonthPosList(MonthVO monthVO);

    /** 사원카드별탭 - 사원카드별 매출조회 */
    List<DefaultMap<Object>> getMonthEmpCardList(MonthVO monthVO);
}
