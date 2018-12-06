package kr.co.solbipos.iostock.vendr.orderStockInfo.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface OrderStockInfoService {
    /** 거래처 발주대비 입고현황 - 발주대비 입고현황 리스트 조회 */
    List<DefaultMap<String>> getOrderStockInfoList(OrderStockInfoVO orderStockInfoVO, SessionInfoVO sessionInfoVO);

    /** 거래처 발주대비 입고현황 - 발주대비 입고현황 상세 리스트 조회 */
    List<DefaultMap<String>> getOrderStockInfoDtlList(OrderStockInfoVO orderStockInfoVO, SessionInfoVO sessionInfoVO);

    /** 거래처 발주대비 입고현황 - 상품 입고현황 리스트 조회 */
    List<DefaultMap<String>> getProdInstockInfoList(OrderStockInfoVO orderStockInfoVO, SessionInfoVO sessionInfoVO);

}
