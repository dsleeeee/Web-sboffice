package kr.co.solbipos.sale.anals.monthlyMoms.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.anals.monthlyMoms.service.SaleAnalsMonthlyMomsVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SaleAnalsMonthlyMomsMapper {
    /** 월력판매분석 - 월력판매분석 리스트 조회(매장) */
    List<DefaultMap<String>> getSaleAnalsMonthlyMomsList(SaleAnalsMonthlyMomsVO saleAnalsMonthlyMomsVO);
    /** 월력판매분석 - 결제수단별 팝업 리스트 조회 */
	List<DefaultMap<String>> getSaleAnalsMonthlyMomsStoreList(SaleAnalsMonthlyMomsVO saleAnalsMonthlyMomsVO);
	List<DefaultMap<String>> getSaleAnalsMonthlyMomsStoreDtlList(SaleAnalsMonthlyMomsVO saleAnalsMonthlyMomsVO);
}
