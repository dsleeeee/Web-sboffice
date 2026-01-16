package kr.co.solbipos.sale.anals.onlineOrder.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name  : OnlineOrderService.java
 * @Description : 매출관리 > 매출분석 > 온라인주문확인
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.01.16  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.01.16
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface OnlineOrderService {

    /** 온라인주문확인 - 조회 */
    List<DefaultMap<String>> getSearchOnlineOrderList(OnlineOrderVO onlineOrderVO, SessionInfoVO sessionInfoVO);
}
