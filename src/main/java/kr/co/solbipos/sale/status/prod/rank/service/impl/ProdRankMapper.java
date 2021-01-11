package kr.co.solbipos.sale.status.prod.rank.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.prod.rank.service.ProdRankVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProdRankMapper {

    /** 상품매출순위탭 - 조회 */
    List<DefaultMap<String>> getProdRankList(ProdRankVO prodRankVO);

    /** 상품매출순위탭 - 차트 조회 */
    List<DefaultMap<String>> getProdRankChartList(ProdRankVO prodRankVO);
    
    /** 상품매출순위탭 - 엑셀 조회 */
    List<DefaultMap<String>> getProdRankExcelList(ProdRankVO prodRankVO);
}