package kr.co.solbipos.stock.status.storeday.service;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

public interface StoreDayService {

	/** 매장일수불 리스트 조회 */
	List<DefaultMap<String>> storeDayList(StoreDayVO storeDayVO, SessionInfoVO sessionInfoVO);
	
	/** 매장일수불 전체 엑셀 리스트 조회 */
	List<DefaultMap<String>> storeDayExcelList(StoreDayVO storeDayVO, SessionInfoVO sessionInfoVO);

}
