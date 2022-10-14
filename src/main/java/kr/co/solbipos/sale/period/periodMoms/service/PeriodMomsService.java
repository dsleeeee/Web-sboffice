package kr.co.solbipos.sale.period.periodMoms.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.period.periodMoms.service.PeriodMomsVO;

import java.util.List;

/**
 * @Class Name : PeriodMomsService.java
 * @Description : 맘스터치 > 매출분석 > 대비기간별매출
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
public interface PeriodMomsService {

    /** 조회 */
    List<DefaultMap<Object>> getPeriodMomsList(PeriodMomsVO periodMomsVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getPeriodMomsExcelList(PeriodMomsVO periodMomsVO, SessionInfoVO sessionInfoVO);

}