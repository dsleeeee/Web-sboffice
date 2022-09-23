package kr.co.solbipos.sale.anals.store.saleAnals.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.anals.store.saleAnals.service.SaleAnalsVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SaleAnalsMapper {

    /** 일별 - 일별 리스트 조회 */
    List<DefaultMap<String>> getDayList(SaleAnalsVO saleAnalsVO);

    /** 월별 - 월별 리스트 조회 */
    List<DefaultMap<String>> getMonthList(SaleAnalsVO saleAnalsVO);

}
