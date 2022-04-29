package kr.co.solbipos.mobile.sale.status.storeTimeSale.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : MobileStoreTimeSaleService.java
 * @Description : (모바일) 매장매출 > 시간대별
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.04.27  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.04.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MobileStoreTimeSaleService {

    /** 시간대별 - 조회 */
    List<DefaultMap<Object>> getMobileStoreTimeSaleList(MobileStoreTimeSaleVO mobileStoreTimeSaleVO, SessionInfoVO sessionInfoVO);

}