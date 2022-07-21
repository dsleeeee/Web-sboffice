package kr.co.solbipos.stock.curr.dayCurr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.stock.curr.dayCurr.service.DayCurrVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DayCurrMapper {
    /** 현재고현황 - 본사 현재고현황 리스트 조회 */
    List<DefaultMap<String>> getDayCurrList(DayCurrVO dayCurrVO);

    /** 현재고현황 - 매장 현재고현황 리스트 조회 */
    List<DefaultMap<String>> getHqStoreCurrList(DayCurrVO dayCurrVO);

    /** 현재고현황 - 본사 현재고현황 엑셀 전체 리스트 조회 */
    List<DefaultMap<String>> getDayCurrExcelList(DayCurrVO dayCurrVO);
    
    /** 현재고현황 - 매장 현재고현황 엑셀 전체 리스트 조회 */
    List<DefaultMap<String>> getHqStoreCurrExcelList(DayCurrVO dayCurrVO);
}
