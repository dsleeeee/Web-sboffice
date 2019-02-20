package kr.co.solbipos.sale.day.day.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.day.day.service.DayVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DayMapper {
    /** 일자별 - 결제수단 컬럼 리스트 조회 */
    List<DefaultMap<String>> getPayColList(DayVO dayVO);

    /** 일자별 - 할인 컬럼 리스트 조회 */
    List<DefaultMap<String>> getDcColList(DayVO dayVO);

    /** 일자별 - 일별종합 리스트 조회 */
    List<DefaultMap<String>> getDayTotalList(DayVO dayVO);

    /** 일자별(일별종합 탭) - 일자 매장별 매출현황 리스트 조회 */
    List<DefaultMap<String>> getDayStoreDtlList(DayVO dayVO);

    /** 일자별(일별종합 탭) - 일자 매장별 할인현황 리스트 조회 */
    List<DefaultMap<String>> getDayStoreDcList(DayVO dayVO);

    /** 일자별(할인구분별 탭) - 할인구분 리스트 조회 */
    List<DefaultMap<String>> getDayDcList(DayVO dayVO);

}
