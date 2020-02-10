package kr.co.solbipos.sale.anals.store.brand.service;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

public interface StoreBrandService {
	
	/**브랜드별 매출 - 브랜드별 매출 리스트 조회   */
    List<DefaultMap<String>> getStoreBrandList(StoreBrandVO storeBrandVO, SessionInfoVO sessionInfoVO);
    
    /**브랜드별 매출 - 조회조건 정렬구분 리스트 조회 */
    List<DefaultMap<String>> getSortFgComboList(StoreBrandVO storeBrandVO, SessionInfoVO sessionInfoVO);
}
