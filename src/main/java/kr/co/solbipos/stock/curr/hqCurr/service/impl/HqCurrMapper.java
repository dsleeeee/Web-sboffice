package kr.co.solbipos.stock.curr.hqCurr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.stock.curr.hqCurr.service.HqCurrVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HqCurrMapper {
    /** 현재고현황 - 현재고현황 리스트 조회 */
    List<DefaultMap<String>> getHqCurrList(HqCurrVO hqCurrVO);

}
