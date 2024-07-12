package kr.co.solbipos.excclc.excclc.dailyTableKwu2.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.excclc.excclc.dailyTableKwu2.service.DailyTableKwu2VO;

import java.util.List;
import java.util.Map;

/**
 * @Class Name : DailyTableKwu2Service.java
 * @Description : 광운대 > 광운대일마감 > 일일일계표3
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.07.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.07.11
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface DailyTableKwu2Service {

    /** 일일일계표3 - 조회 */
    Map<String, Object> getDailyTableKwu2List(DailyTableKwu2VO dailyTableKwu2VO, SessionInfoVO sessionInfoVO);

    /** 일일일계표3 - 조회 */
    Map<String, Object> getDailyTableKwu2List1(DailyTableKwu2VO dailyTableKwu2VO, SessionInfoVO sessionInfoVO);

    /** 일일일계표3 - 조회 */
    Map<String, Object> getDailyTableKwu2List2(DailyTableKwu2VO dailyTableKwu2VO, SessionInfoVO sessionInfoVO);
}