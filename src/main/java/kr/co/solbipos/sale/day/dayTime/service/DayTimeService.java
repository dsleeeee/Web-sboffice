package kr.co.solbipos.sale.day.dayTime.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.day.dayTime.service.DayTimeVO;

import java.util.List;

/**
 * @Class Name : DayTimeService.java
 * @Description : 맘스터치 > 매출분석 > 일별시간대
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.11.23  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.11.23
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface DayTimeService {

    /** 조회 */
    List<DefaultMap<Object>> getDayTimeList(DayTimeVO dayTimeVO, SessionInfoVO sessionInfoVO);

}