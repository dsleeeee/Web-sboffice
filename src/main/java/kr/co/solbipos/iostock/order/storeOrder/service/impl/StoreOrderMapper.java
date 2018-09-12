package kr.co.solbipos.iostock.order.storeOrder.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderDtlVO;
import kr.co.solbipos.iostock.order.storeOrder.service.StoreOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StoreOrderMapper {
    /** 주문등록 HD 리스트 조회 */
    List<DefaultMap<String>> getStoreOrderList(StoreOrderVO storeOrderVO);

    /** 주문등록 DT 리스트 조회 */
    List<DefaultMap<String>> getStoreOrderDtlList(StoreOrderDtlVO storeOrderDtlVO);

    /** 주문등록 상품추가 리스트 조회 */
    List<DefaultMap<String>> getStoreOrderRegistList(StoreOrderDtlVO storeOrderDtlVO);

    /** 주문등록 주문상품 수정 */
    int updateStoreOrderDtl(StoreOrderDtlVO storeOrderDtlVO);

    /** 주문등록 주문상품 등록 */
    int insertStoreOrderDtl(StoreOrderDtlVO storeOrderDtlVO);

    /** 주문등록 주문상품 삭제 */
    int deleteStoreOrderDtl(StoreOrderDtlVO storeOrderDtlVO);

    /** 주문등록 주문상품HD 수정 */
    int updateStoreOrder(StoreOrderVO storeOrderVO);

    /** 주문등록 주문상품HD 등록 */
    int insertStoreOrder(StoreOrderVO storeOrderVO);

    /** 주문등록 주문상품HD 삭제 */
    int deleteStoreOrder(StoreOrderVO storeOrderVO);

    /** 주문요청일의 상품건수 조회 */
    int getDtlCnt(StoreOrderVO storeOrderVO);

    /** 주문요청일의 HD 내용이 존재하는지 여부 조회 */
    String getHdExist(StoreOrderVO storeOrderVO);

    /** 주문등록 매장마감여부 조회 */
    List<DefaultMap<String>> getStoreCloseCheck(StoreOrderVO storeOrderVO);

    /** 주문등록 주문진행구분 조회 */
    List<DefaultMap<String>> getOrderProcFgCheck(StoreOrderVO storeOrderVO);

    /** 주문등록 매장여신 조회 */
    List<DefaultMap<String>> getStoreLoan(StoreOrderVO storeOrderVO);

}
