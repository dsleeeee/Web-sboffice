package kr.co.solbipos.mobile.sale.status.prod.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface MobileProdSaleService {

    /** 모바일 매출현황 - 상품별매출현황 */
    List<DefaultMap<String>> getProdSaleList(MobileProdSaleVO mobileProdSaleVO, SessionInfoVO sessionInfoVO);

    /** 모바일 매출현황 - 다중매장조회 */
    List<DefaultMap<String>> getMultiStoreList(MobileProdSaleVO mobileProdSaleVO, SessionInfoVO sessionInfoVO);
}
