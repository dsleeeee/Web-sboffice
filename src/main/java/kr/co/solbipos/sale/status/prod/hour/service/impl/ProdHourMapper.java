package kr.co.solbipos.sale.status.prod.hour.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.prod.hour.service.ProdHourVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProdHourMapper {
    /** 상품별 매출 - 시간대별 리스트 조회 */
    List<DefaultMap<String>> getProdHourList(ProdHourVO prodHourVO);
    
    /** 상품별 매출 - 시간대별 엑셀다운로드 조회 */
    List<DefaultMap<String>> getProdHourExcelList(ProdHourVO prodHourVO);
}
