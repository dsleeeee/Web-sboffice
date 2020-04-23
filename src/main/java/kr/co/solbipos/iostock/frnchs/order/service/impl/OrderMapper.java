package kr.co.solbipos.iostock.frnchs.order.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.iostock.frnchs.order.service.OrderVO;

@Mapper
@Repository
public interface OrderMapper {
    /** 본사 주문대비 입고현황 - 주문대비 입고현황 리스트 조회 */
    List<DefaultMap<String>> getOrderList(OrderVO orderVO);

    /** 본사 주문대비 입고현황 - 주문대비 입고현황 상세 리스트 조회 */
    List<DefaultMap<String>> getOrderDtlList(OrderVO orderVO);

	/** 본사 주문대비 입출고현황 - 조회조건 진행상황 콤보 리스트 조회 */
	List<DefaultMap<String>> getSrchOrderProcFgList(OrderVO orderVO);

	/** 본사 주문대비 입고현황 - 주문대비 입고현황 엑셀리스트 조회 */
	List<DefaultMap<String>> getOrderExcelList(OrderVO orderVO);

	/** 본사 주문대비 입고현황 - 주문대비 입고현황 상세 엑셀리스트 조회 */
	List<DefaultMap<String>> getOrderDtlExcelList(OrderVO orderVO);
}
