package kr.co.solbipos.sale.anals.store.prod.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.store.prod.service.StoreProdVO;

import java.util.List;

public interface StoreProdService {
	
	/**매장상품순위 - 매장상품순위 리스트 조회   */
    List<DefaultMap<String>> getStoreProdList(StoreProdVO storeProdVO, SessionInfoVO sessionInfoVO);
	/**매장상품순위 - 매장상품순위 리스트(엑셀) 조회   */
    List<DefaultMap<String>> getStoreProdExcelList(StoreProdVO storeProdVO, SessionInfoVO sessionInfoVO);
}
