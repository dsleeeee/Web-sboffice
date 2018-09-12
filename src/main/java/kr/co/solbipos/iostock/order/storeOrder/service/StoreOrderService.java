package kr.co.solbipos.iostock.order.storeOrder.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface StoreOrderService {

    /** 주문등록 HD 리스트 조회 */
    List<DefaultMap<String>> getStoreOrderList(StoreOrderVO storeOrderVO);

    /** 주문등록 DT 리스트 조회 */
    List<DefaultMap<String>> getStoreOrderDtlList(StoreOrderDtlVO storeOrderDtlVO);

    /** 주문등록 상품추가 리스트 조회 */
    List<DefaultMap<String>> getStoreOrderRegistList(StoreOrderDtlVO storeOrderDtlVO);

    /** 주문등록 주문상품 저장 */
    int saveStoreOrderRegist(StoreOrderDtlVO[] storeOrderDtlVOs, SessionInfoVO sessionInfoVO);

    /** 주문등록 매장마감여부 조회 */
    List<DefaultMap<String>> getStoreCloseCheck(StoreOrderVO storeOrderVO);

    /** 주문등록 주문진행구분 조회 */
    List<DefaultMap<String>> getOrderProcFgCheck(StoreOrderVO storeOrderVO);

    /** 주문등록 매장여신 조회 */
    List<DefaultMap<String>> getStoreLoan(StoreOrderVO storeOrderVO);

}
