package kr.co.solbipos.mobile.sale.status.monthSale.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : MobileMonthSaleService.java
 * @Description : (모바일) 매출현황 > 월별매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2021.05.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MobileMonthSaleService {

    /** 매출종합 - 조회 */
    DefaultMap<String> getMobileMonthSaleTotalList(MobileMonthSaleVO mobileMonthSaleVO, SessionInfoVO sessionInfoVO);

    /** 결제수단 - 조회 */
    List<DefaultMap<Object>> getMobileMonthSalePayList(MobileMonthSaleVO mobileMonthSaleVO, SessionInfoVO sessionInfoVO);

    /** 할인내역 - 조회 */
    List<DefaultMap<Object>> getMobileMonthSaleDcList(MobileMonthSaleVO mobileMonthSaleVO, SessionInfoVO sessionInfoVO);

    /** 내점현황 - 조회 */
    DefaultMap<String> getMobileMonthSaleShopList(MobileMonthSaleVO mobileMonthSaleVO, SessionInfoVO sessionInfoVO);

    /** 내점/배달/포장 - 조회 */
    List<DefaultMap<Object>> getMobileMonthSaleDlvrList(MobileMonthSaleVO mobileMonthSaleVO, SessionInfoVO sessionInfoVO);

    /** 내점/배달/포장 - 차트 조회 */
    List<DefaultMap<Object>> getMobileMonthSaleDlvrChartList(MobileMonthSaleVO mobileMonthSaleVO, SessionInfoVO sessionInfoVO);

    /** 월자별 매출현황 - 조회 */
    List<DefaultMap<Object>> getMobileMonthSaleDtlList(MobileMonthSaleVO mobileMonthSaleVO, SessionInfoVO sessionInfoVO);
}