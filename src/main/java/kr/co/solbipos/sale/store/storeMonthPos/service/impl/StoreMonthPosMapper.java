package kr.co.solbipos.sale.store.storeMonthPos.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.store.storeMonthPos.service.StoreMonthPosVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StoreMonthPosMapper {

    /** 월별 리스트 조회 */
    List<DefaultMap<String>> getMonthList(StoreMonthPosVO storeMonthPosVO);
    List<DefaultMap<String>> getMonthExcelList(StoreMonthPosVO storeMonthPosVO);

}
