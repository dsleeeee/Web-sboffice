package kr.co.solbipos.sale.store.storeDayChannel.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.store.storeDayChannel.service.StoreDayChannelVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StoreDayChannelMapper {

    /** 일별 리스트 조회 */
    List<DefaultMap<String>> getDayList(StoreDayChannelVO storeDayChannelVO);
    List<DefaultMap<String>> getDayExcelList(StoreDayChannelVO storeDayChannelVO);

}
