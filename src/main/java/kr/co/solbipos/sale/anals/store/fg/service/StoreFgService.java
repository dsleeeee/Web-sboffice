package kr.co.solbipos.sale.anals.store.fg.service;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

public interface StoreFgService {
	
	/**매장형태별 매출 - 매장형태별 매출 리스트 조회   */
    List<DefaultMap<String>> getStoreFgList(StoreFgVO storeFgVO, SessionInfoVO sessionInfoVO);
    
    /**매장형태별 매출 - 매장형태별 매장구분 콤보 리스트 조회 */
	List<DefaultMap<String>> getStoreFgComboList(StoreFgVO storeFgVO, SessionInfoVO sessionInfoVO);
}
