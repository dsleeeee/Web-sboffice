package kr.co.solbipos.mobile.sale.status.multiStoreSale.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : MobileMultiStoreSaleService.java
 * @Description : (모바일) 매출현황 > 다중매장매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.20  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2021.05.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MobileMultiStoreSaleService {

    /** 다중매장매출현황 - 조회 */
    List<DefaultMap<Object>> getMobileMultiStoreSaleList(MobileMultiStoreSaleVO mobileMultiStoreSaleVO, SessionInfoVO sessionInfoVO);

    /** 다중매장매출현황 - 차트 조회 */
    List<DefaultMap<Object>> getMobileMultiStoreSaleChartList(MobileMultiStoreSaleVO mobileMultiStoreSaleVO, SessionInfoVO sessionInfoVO);

    /** 일자-매장별 매출현황 - 조회 */
    List<DefaultMap<Object>> getMobileMultiStoreSaleDayStoreList(MobileMultiStoreSaleVO mobileMultiStoreSaleVO, SessionInfoVO sessionInfoVO);
}