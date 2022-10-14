package kr.co.solbipos.sale.month.monthMoms.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.month.monthMoms.service.MonthMomsVO;

import java.util.List;

/**
 * @Class Name : MonthMomsService.java
 * @Description : 맘스터치 > 매출분석 > 월별 매출 현황
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
public interface MonthMomsService {

    /** 조회 */
    List<DefaultMap<Object>> getMonthMomsList(MonthMomsVO monthMomsVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getMonthMomsExcelList(MonthMomsVO monthMomsVO, SessionInfoVO sessionInfoVO);

}