package kr.co.solbipos.iostock.vendr.orderStockInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.vendr.orderStockInfo.service.OrderStockInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderStockInfoMapper {
    /** 거래처 발주대비 입고현황 - 발주대비 입고현황 리스트 조회 */
    List<DefaultMap<String>> getOrderStockInfoList(OrderStockInfoVO orderStockInfoVO);

    /** 거래처 발주대비 입고현황 - 발주대비 입고현황 상세 리스트 조회 */
    List<DefaultMap<String>> getOrderStockInfoDtlList(OrderStockInfoVO orderStockInfoVO);

    /** 거래처 발주대비 입고현황 - 발주대비 입고현황 무발주 상세 리스트 조회 */
    List<DefaultMap<String>> getNoSlipOrderStockInfoDtlList(OrderStockInfoVO orderStockInfoVO);

    /** 거래처 발주대비 입고현황 - 상품 입고현황 리스트 조회 */
    List<DefaultMap<String>> getProdInstockInfoList(OrderStockInfoVO orderStockInfoVO);

}
