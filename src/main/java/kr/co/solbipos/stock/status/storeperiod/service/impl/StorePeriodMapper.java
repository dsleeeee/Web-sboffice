package kr.co.solbipos.stock.status.storeperiod.service.impl;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.stock.status.storeperiod.service.StorePeriodVO;
public interface StorePeriodMapper {
	/** 매장기간수불 - 매장기간수불 리스트 조회 */
    List<DefaultMap<String>> getStorePeriodList(StorePeriodVO storePeriodVO);
    
    /** 매장기간수불 - 매장기간수불 상품코드 선택 상세 리스트 조회 */
    List<DefaultMap<String>> getStorePeriodDtlList(StorePeriodVO storePeriodVO);
    
    /** 매장기간수불 - 매장기간수불 수량 선택 상세 리스트 조회 */
    List<DefaultMap<String>> getStorePeriodQtyDtlList(StorePeriodVO storePeriodVO);
}