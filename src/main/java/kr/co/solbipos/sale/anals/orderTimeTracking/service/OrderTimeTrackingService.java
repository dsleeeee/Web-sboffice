package kr.co.solbipos.sale.anals.orderTimeTracking.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;


/**
 * @Class Name : OrderTimeTrackingService.java
 * @Description : 매출관리 - 매출분석2 - 주문시간트레킹
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.08.28  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.08.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface OrderTimeTrackingService {

    /** 주문시간트레킹 리스트 조회 */
    List<DefaultMap<String>> getOrderTimeTrackingList(OrderTimeTrackingVO orderTimeTrackingVO, SessionInfoVO sessionInfoVO);

    /** 주문시간트레킹 엑셀다운로드 조회 */
    List<DefaultMap<String>> getOrderTimeTrackingExcelList(OrderTimeTrackingVO orderTimeTrackingVO, SessionInfoVO sessionInfoVO);
}
