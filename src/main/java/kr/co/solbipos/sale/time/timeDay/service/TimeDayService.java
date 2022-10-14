package kr.co.solbipos.sale.time.timeDay.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.time.timeDay.service.TimeDayVO;

import java.util.List;

/**
 * @Class Name : TimeDayService.java
 * @Description : 맘스터치 > 시간대별매출 > 일자별 시간대 매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.13  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface TimeDayService {

    /** 조회 */
    List<DefaultMap<Object>> getTimeDayList(TimeDayVO timeDayVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getTimeDayExcelList(TimeDayVO timeDayVO, SessionInfoVO sessionInfoVO);

}