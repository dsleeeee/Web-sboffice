package kr.co.solbipos.sale.anals.versusPeriod.hour.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.anals.versusPeriod.hour.service.VersusPeriodHourVO;

@Mapper
@Repository
public interface VersusPeriodHourMapper {
    /** 상품별 매출 - 시간대별 리스트 조회 */
    List<DefaultMap<String>> getVersusPeriodHourList(VersusPeriodHourVO versusPeriodHourVO);

}
