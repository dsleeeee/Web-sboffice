package kr.co.solbipos.sale.day.dayMoms.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.dayMoms.service.DayMomsVO;

import java.util.List;

/**
 * @Class Name : DayMomsService.java
 * @Description : 맘스터치 > 매출분석 > 일별 매출 리스트
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
public interface DayMomsService {

    /** 조회 */
    List<DefaultMap<Object>> getDayMomsList(DayMomsVO dayMomsVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getDayMomsExcelList(DayMomsVO dayMomsVO, SessionInfoVO sessionInfoVO);

}