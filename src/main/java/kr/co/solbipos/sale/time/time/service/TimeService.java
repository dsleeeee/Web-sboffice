package kr.co.solbipos.sale.time.time.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.time.time.service.TimeVO;

import java.util.List;

/**
 * @Class Name : TimeService.java
 * @Description : 맘스터치 > 시간대별매출 > 시간대별 일 매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.12  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.12
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface TimeService {

    /** 조회 */
    List<DefaultMap<Object>> getTimeList(TimeVO timeVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getTimeExcelList(TimeVO timeVO, SessionInfoVO sessionInfoVO);

}