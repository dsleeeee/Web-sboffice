package kr.co.solbipos.sale.cmmSalePopup.dayBillInfo.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : DayBillInfoService.java
 * @Description : 매출 공통팝업 > 매장별 영수건수 팝업
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.12.18  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.12.18
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface DayBillInfoService {

    /** 매장별 영수건수 팝업 - 매장별 영수건수 리스트 조회 */
    List<DefaultMap<Object>> getDayStoreBillList(DayBillInfoVO dayBillInfoVO, SessionInfoVO sessionInfoVO);

    /** 매장별 영수건수 팝업 - 매장별 영수건수 리스트 조회 */
    List<DefaultMap<Object>> getDayStoreBillList2(DayBillInfoVO dayBillInfoVO, SessionInfoVO sessionInfoVO);
}
