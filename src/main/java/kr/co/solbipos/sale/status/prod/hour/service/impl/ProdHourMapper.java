package kr.co.solbipos.sale.status.prod.hour.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.prod.hour.service.ProdHourVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProdHourMapper {
    /** 시간대별탭 - 조회 */
    List<DefaultMap<String>> getProdHourList(ProdHourVO prodHourVO);

    /** 시간대별탭 - 엑셀 조회 */
    List<DefaultMap<String>> getProdHourExcelList(ProdHourVO prodHourVO);
}
