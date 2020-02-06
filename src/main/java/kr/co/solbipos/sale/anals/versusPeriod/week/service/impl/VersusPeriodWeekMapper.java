package kr.co.solbipos.sale.anals.versusPeriod.week.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.anals.versusPeriod.week.service.VersusPeriodWeekVO;
import kr.co.solbipos.sale.status.prod.day.service.ProdDayVO;

@Mapper
@Repository
public interface VersusPeriodWeekMapper {
    /** 상품별 매출 - 일자별 리스트 조회 */
    List<DefaultMap<String>> getVersusPeriodWeekList(VersusPeriodWeekVO versusPeriodWeekVO);

}
