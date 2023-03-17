package kr.co.solbipos.sale.store.storeDayPos.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.store.storeDayPos.service.StoreDayPosVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StoreDayPosMapper {

    /** 일별 리스트 조회 */
    List<DefaultMap<String>> getDayList(StoreDayPosVO storeDayPosVO);
    List<DefaultMap<String>> getDayExcelList(StoreDayPosVO storeDayPosVO);

}
