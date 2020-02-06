package kr.co.solbipos.sale.anals.store.month.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.store.month.service.StoreMonthVO;

import java.util.List;

public interface StoreMonthService {
	
	/**매장월별순위 - 매장순위 리스트 조회   */
    List<DefaultMap<String>> getStoreMonthList(StoreMonthVO storeMonthVO, SessionInfoVO sessionInfoVO);
    
    /**매장월별순위 - 결제수단 컬럼 리스트 조회 */
    List<DefaultMap<String>> getMonthColList(StoreMonthVO storeMonthVO, SessionInfoVO sessionInfoVO);
}
