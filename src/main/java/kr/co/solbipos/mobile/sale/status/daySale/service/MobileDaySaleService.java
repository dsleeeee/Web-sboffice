package kr.co.solbipos.mobile.sale.status.daySale.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : MobileDaySaleService.java
 * @Description : (모바일) 매출현황 > 일별매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.04  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.05.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MobileDaySaleService {

    /** 매출종합 - 조회 */
    DefaultMap<String> getMobileDaySaleTotalList(MobileDaySaleVO mobileDaySaleVO, SessionInfoVO sessionInfoVO);

    /** 결제수단 - 조회 */
    List<DefaultMap<Object>> getMobileDaySalePayList(MobileDaySaleVO mobileDaySaleVO, SessionInfoVO sessionInfoVO);

    /** 할인내역 - 조회 */
    List<DefaultMap<Object>> getMobileDaySaleDcList(MobileDaySaleVO mobileDaySaleVO, SessionInfoVO sessionInfoVO);

    /** 내점현황 - 조회 */
    DefaultMap<String> getMobileDaySaleShopList(MobileDaySaleVO mobileDaySaleVO, SessionInfoVO sessionInfoVO);

    /** 내점/배달/포장 - 조회 */
    List<DefaultMap<Object>> getMobileDaySaleDlvrList(MobileDaySaleVO mobileDaySaleVO, SessionInfoVO sessionInfoVO);

    /** 내점/배달/포장 - 차트 조회 */
    List<DefaultMap<Object>> getMobileDaySaleDlvrChartList(MobileDaySaleVO mobileDaySaleVO, SessionInfoVO sessionInfoVO);

    /** 일자별 매출현황 - 조회 */
    List<DefaultMap<Object>> getMobileDaySaleDtlList(MobileDaySaleVO mobileDaySaleVO, SessionInfoVO sessionInfoVO);
}