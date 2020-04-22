package kr.co.solbipos.stock.status.storeperiod.service;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

public interface StorePeriodService {
	/** 매장기간수불 - 매장기간수불 리스트 조회 */
	public List<DefaultMap<String>> getStorePeriodList(StorePeriodVO storePeriodVO, SessionInfoVO sessionInfoVO);
	
	/** 매장기간수불 - 매장기간수불 상품코드 선택 상세 리스트 조회 */
	public List<DefaultMap<String>> getStorePeriodDtlList(StorePeriodVO storePeriodVO, SessionInfoVO sessionInfoVO);
	
	/** 매장기간수불 - 매장기간수불 수량 선택 상세 리스트 조회 */
	public List<DefaultMap<String>> getStorePeriodQtyDtlList(StorePeriodVO storePeriodVO, SessionInfoVO sessionInfoVO);
	
	/** 매장기간수불 - 매장기간수불 엑셀 전체 리스트 조회 */
	public List<DefaultMap<String>> getStoreperiodExcelList(StorePeriodVO storePeriodVO, SessionInfoVO sessionInfoVO);
}
