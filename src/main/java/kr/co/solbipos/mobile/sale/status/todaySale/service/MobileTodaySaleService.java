package kr.co.solbipos.mobile.sale.status.todaySale.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : MobileTodaySaleService.java
 * @Description : (모바일) 매출현황 > 당일매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.04.02  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2021.04.02
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MobileTodaySaleService {

    /** 당일매출종합 - 조회 */
    DefaultMap<String> getMobileTodaySaleList(MobileTodaySaleVO mobileTodaySaleVO, SessionInfoVO sessionInfoVO);

    /** 결제수단 조회 */
    List<DefaultMap<Object>> getMobileTodaySalePayList(MobileTodaySaleVO mobileTodaySaleVO, SessionInfoVO sessionInfoVO);

    /** 할인내역 조회 */
    List<DefaultMap<Object>> getMobileTodaySaleDcList(MobileTodaySaleVO mobileTodaySaleVO, SessionInfoVO sessionInfoVO);

    /** 내점/배달/포장 조회 */
    List<DefaultMap<Object>> getMobileTodaySaleDlvrList(MobileTodaySaleVO mobileTodaySaleVO, SessionInfoVO sessionInfoVO);

    /** 시간대별 조회 */
    List<DefaultMap<Object>> getMobileTodaySaleTimeList(MobileTodaySaleVO mobileTodaySaleVO, SessionInfoVO sessionInfoVO);
}