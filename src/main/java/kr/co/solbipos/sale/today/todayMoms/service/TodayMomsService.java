package kr.co.solbipos.sale.today.todayMoms.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.today.todayMoms.service.TodayMomsVO;

import java.util.List;

/**
 * @Class Name : TodayMomsService.java
 * @Description : 맘스터치 > 매출분석 > 당일 매출 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.06  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface TodayMomsService {

    /** 조회 */
    List<DefaultMap<Object>> getTodayMomsList(TodayMomsVO todayMomsVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getTodayMomsExcelList(TodayMomsVO todayMomsVO, SessionInfoVO sessionInfoVO);

}