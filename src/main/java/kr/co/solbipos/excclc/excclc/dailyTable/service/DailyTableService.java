package kr.co.solbipos.excclc.excclc.dailyTable.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.excclc.excclc.dailyTable.service.DailyTableVO;

import java.util.List;
import java.util.Map;

/**
 * @Class Name : DailyTableService.java
 * @Description : 광운대 > 광운대일마감 > 일일일계표
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.15  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.09.15
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface DailyTableService {

    /** 조회 */
    Map<String, Object> getDailyTableList(DailyTableVO dailyTableVO, SessionInfoVO sessionInfoVO);

}
