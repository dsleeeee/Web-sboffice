package kr.co.solbipos.sale.pay.storePayDay.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : StorePayDayService.java
 * @Description : 맘스터치 > 결제수단매출 > 매장-일별결제수단매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.21  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.12.21
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StorePayDayService {

    /** 조회 */
    List<DefaultMap<Object>> getStorePayDayList(StorePayDayVO storePayDayVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getStorePayDayExcelList(StorePayDayVO storePayDayVO, SessionInfoVO sessionInfoVO);

}