package kr.co.solbipos.sale.store.storeMonthChannel.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.store.storeMonthChannel.service.StoreMonthChannelVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StoreMonthChannelMapper {

    /** 월별 리스트 조회 */
    List<DefaultMap<String>> getMonthList(StoreMonthChannelVO storeMonthChannelVO);
    List<DefaultMap<String>> getMonthExcelList(StoreMonthChannelVO storeMonthChannelVO);

}
