package kr.co.solbipos.iostock.vendr.vendrOrder.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface VendrOrderService {
    /** 거래처 발주등록 - 거래처 발주등록 리스트 조회 */
    List<DefaultMap<String>> getVendrOrderList(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO);

    /** 거래처 발주등록 - 발주정보 상세 조회 */
    DefaultMap<String> getSlipInfo(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO);

    /** 거래처 발주등록 - 발주정보 저장 */
    DefaultMap<String> saveVendrOrderDtl(VendrOrderVO vendrOrderVO, SessionInfoVO sessionInfoVO);

}
