package kr.co.solbipos.stock.status.storeday.service.impl;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.stock.status.storeday.service.StoreDayVO;

public interface StoreDayMapper {

	/** 매장일수불 리스트 조회 */
	List<DefaultMap<String>> storeDayList(StoreDayVO storeDayVO);
	
	/** 매장일수불 엑셀 전체 리스트 조회 */
	List<DefaultMap<String>> storeDayExcelList(StoreDayVO storeDayVO);
}
