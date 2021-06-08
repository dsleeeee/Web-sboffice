package kr.co.solbipos.mobile.sale.status.timeDaySale.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : MobileTimeDaySaleService.java
 * @Description : (모바일) 매출현황 > 시간대별(일자별)매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.07  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.06.07
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MobileTimeDaySaleService {

    /** 일자-시간대별 - 조회 */
    List<DefaultMap<Object>> getMobileTimeDaySaleDateTimeList(MobileTimeDaySaleVO mobileTimeDaySaleVO, SessionInfoVO sessionInfoVO);

    /** 시간대별 - 조회 */
    List<DefaultMap<Object>> getMobileTimeDaySaleTimeList(MobileTimeDaySaleVO mobileTimeDaySaleVO, SessionInfoVO sessionInfoVO);

    /** 시간대별 - 차트 조회 */
    List<DefaultMap<Object>> getMobileTimeDaySaleTimeChartList(MobileTimeDaySaleVO mobileTimeDaySaleVO, SessionInfoVO sessionInfoVO);
}