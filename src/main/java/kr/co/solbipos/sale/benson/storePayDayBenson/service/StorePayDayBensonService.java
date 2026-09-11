package kr.co.solbipos.sale.benson.storePayDayBenson.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : StorePayDayBensonService.java
 * @Description : 벤슨 > 결제수단매출 > 매장-일별결제수단매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StorePayDayBensonService {

    /** 조회 */
    List<DefaultMap<Object>> getStorePayDayBensonList(StorePayDayBensonVO storePayDayBensonVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getStorePayDayBensonExcelList(StorePayDayBensonVO storePayDayBensonVO, SessionInfoVO sessionInfoVO);

}
