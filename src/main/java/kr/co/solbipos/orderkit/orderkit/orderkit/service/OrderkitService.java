package kr.co.solbipos.orderkit.orderkit.orderkit.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
  * @Class Name  : OrderkitService.java
  * @Description : 오더킷 > 오더킷 > 오더킷
  * @Modification Information
  * @
  * @  수정일      수정자              수정내용
  * @ ----------  ---------   -------------------------------
  * @ 2025.10.30  이다솜      최초생성
  *
  * @author 링크 개발실 개발1팀 이다솜
  * @since 2025.10.30
  * @version 1.0
  *
  *  Copyright (C) by LYNK CORP. All right reserved.
  */
public interface OrderkitService {

    /** 매장정보 조회 */
    DefaultMap<Object> getStoreInfo(OrderkitVO orderkitVO, SessionInfoVO sessionInfoVO);

    /** 개발/운영 Api URL 조회 */
    DefaultMap<Object> getApiUrl(OrderkitVO orderkitVO, SessionInfoVO sessionInfoVO);

}
