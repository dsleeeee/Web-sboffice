package kr.co.solbipos.sale.anals.store.rank.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.store.rank.service.StoreRankVO;
import kr.co.solbipos.sale.status.prod.payFg.service.ProdPayFgVO;

import java.util.List;

public interface StoreRankService {
	
	/**매장순위 - 매장순위 리스트 조회   */
    List<DefaultMap<String>> getStoreRankList(StoreRankVO storeRankVO, SessionInfoVO sessionInfoVO);
    
    /**매장순위 - 결제수단 컬럼 리스트 조회 */
    List<DefaultMap<String>> getPayColList(StoreRankVO storeRankVO, SessionInfoVO sessionInfoVO);
}
