package kr.co.solbipos.iostock.frnchs.order.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface OrderService {
    /** 본사 주문대비 입고현황 - 주문대비 입고현황 리스트 조회 */
    List<DefaultMap<String>> getOrderList(OrderVO orderVO, SessionInfoVO sessionInfoVO);

    /** 본사 주문대비 입고현황 - 주문대비 입고현황 상세 리스트 조회 */
    List<DefaultMap<String>> getOrderDtlList(OrderVO orderVO, SessionInfoVO sessionInfoVO);

	/** 본사 주문대비 입출고현황 - 조회조건 진행상태 콤보 리스트 조회 */
	List<DefaultMap<String>> getSrchOrderProcFgList(OrderVO orderVO, SessionInfoVO sessionInfoVO);

	/** 본사 주문대비 입고현황 - 주문대비 입고현황 엑셀리스트 조회 */
	List<DefaultMap<String>> getOrderExcelList(OrderVO orderVO, SessionInfoVO sessionInfoVO);

	/** 본사 주문대비 입고현황 - 주문대비 입고현황 상세 엑셀리스트 조회 */
	List<DefaultMap<String>> getOrderDtlExcelList(OrderVO orderVO, SessionInfoVO sessionInfoVO);
}
