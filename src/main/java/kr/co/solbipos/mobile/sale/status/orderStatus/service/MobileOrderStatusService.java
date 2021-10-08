package kr.co.solbipos.mobile.sale.status.orderStatus.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.orderStatus.orderStatus.service.OrderStatusVO;

import java.util.List;

/**
 * @Class Name : MobileOrderStatusService.java
 * @Description : 매출현황 > 주문현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.10.01  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2021.10.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface MobileOrderStatusService {
    /** 주문현황 - 조회 */
    List<DefaultMap<String>> getMobileOrderStatusList(MobileOrderStatusVO mobileOrderStatusVO, SessionInfoVO sessionInfoVO);

    /** 주문현황 상세 팝업 - 조회 */
    List<DefaultMap<String>> getMobileOrderStatusDtlList(MobileOrderStatusVO mobileOrderStatusVO, SessionInfoVO sessionInfoVO);
}
