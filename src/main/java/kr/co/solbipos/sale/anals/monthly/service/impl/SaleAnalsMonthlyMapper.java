package kr.co.solbipos.sale.anals.monthly.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.anals.monthly.service.SaleAnalsMonthlyVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SaleAnalsMonthlyMapper {
    /** 월력판매분석 - 월력판매분석 리스트 조회(매장) */
    List<DefaultMap<String>> getStSaleAnalsMonthlyList(SaleAnalsMonthlyVO saleAnalsMonthlyVO);
    /** 월력판매분석 - 결제수단별 팝업 리스트 조회 */
	List<DefaultMap<String>> getSaleAnalsMonthlyPopupList(SaleAnalsMonthlyVO saleAnalsMonthlyVO);
}
