package kr.co.solbipos.sale.period.periodStore.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.period.periodStore.service.PeriodStoreVO;

import java.util.List;

/**
 * @Class Name : PeriodStoreService.java
 * @Description : 맘스터치 > 매출분석 > 대비기간별매출(매장합산)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.11  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface PeriodStoreService {

    /** 조회 */
    List<DefaultMap<Object>> getPeriodStoreList(PeriodStoreVO periodStoreVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getPeriodStoreExcelList(PeriodStoreVO periodStoreVO, SessionInfoVO sessionInfoVO);

}