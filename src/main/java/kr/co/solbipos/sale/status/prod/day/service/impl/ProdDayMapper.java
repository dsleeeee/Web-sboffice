package kr.co.solbipos.sale.status.prod.day.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.prod.day.service.ProdDayVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProdDayMapper {
    /** 상품별 매출 - 일자별 리스트 조회 */
    List<DefaultMap<String>> getProdDayList(ProdDayVO prodDayVO);
    
    /** 상품별 매출 - 일자별 엑셀 다운로드 조회 */
    List<DefaultMap<String>> getProdDayExcelList(ProdDayVO prodDayVO);
}
