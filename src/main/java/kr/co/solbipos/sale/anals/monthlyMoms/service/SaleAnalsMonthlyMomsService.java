package kr.co.solbipos.sale.anals.monthlyMoms.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface SaleAnalsMonthlyMomsService {
    /** 월력판매분석 - 월력판매 리스트 조회 */
    List<DefaultMap<String>> getSaleAnalsMonthlyMomsList(SaleAnalsMonthlyMomsVO saleAnalsMonthlyMomsVO, SessionInfoVO sessionInfoVO);
    /** 월력판매분석 - 결제수단별 팝업 리스트 조회 */
	List<DefaultMap<String>> getSaleAnalsMonthlyMomsStoreList(SaleAnalsMonthlyMomsVO saleAnalsMonthlyMomsVO, SessionInfoVO sessionInfoVO);
	List<DefaultMap<String>> getSaleAnalsMonthlyMomsStoreDtlList(SaleAnalsMonthlyMomsVO saleAnalsMonthlyMomsVO, SessionInfoVO sessionInfoVO);
}
