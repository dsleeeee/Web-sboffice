package kr.co.solbipos.sale.status.prod.day.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.prod.day.service.ProdDayVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProdDayMapper {
    /** 일자별탭 - 조회 */
    List<DefaultMap<String>> getProdDayList(ProdDayVO prodDayVO);
    
    /** 일자별탭 - 엑셀 조회 */
    List<DefaultMap<String>> getProdDayExcelList(ProdDayVO prodDayVO);
}