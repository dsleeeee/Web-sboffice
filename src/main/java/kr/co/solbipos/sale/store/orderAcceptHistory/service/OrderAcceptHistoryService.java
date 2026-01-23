package kr.co.solbipos.sale.store.orderAcceptHistory.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name  : OrderAcceptHistoryService.java
 * @Description : 맘스터치 > 매장분석 > 온라인주문-수신상태이력
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.01.23  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.01.23
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface OrderAcceptHistoryService {

    /** 온라인주문-수신상태이력 - 조회 */
    List<DefaultMap<String>> getSearchOrderAcceptHistory(OrderAcceptHistoryVO orderAcceptHistoryVO, SessionInfoVO sessionInfoVO);
}
