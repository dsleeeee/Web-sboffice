package kr.co.solbipos.stock.status.currUnity.service.impl;

import java.util.List;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.stock.status.currUnity.service.CurrUnityVO;

public interface CurrUnityMapper {

	/** 본사매장통합현재고 - 본사매장통합현재고 리스트 조회 */
    List<DefaultMap<String>> getCurrUnityList(CurrUnityVO currUnityVO);
    /** 본사매장통합현재고 - 본사매장통합현재고 본사 상세 리스트 조회 */
    List<DefaultMap<String>> getCurrUnityHqDtlList(CurrUnityVO currUnityVO);
    /** 본사매장통합현재고 - 본사매장통합현재고 매장 상세 리스트 조회 */
    List<DefaultMap<String>> getCurrUnityStoreDtlList(CurrUnityVO currUnityVO);

}
