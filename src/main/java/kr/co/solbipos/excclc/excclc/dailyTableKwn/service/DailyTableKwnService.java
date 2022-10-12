package kr.co.solbipos.excclc.excclc.dailyTableKwn.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.excclc.excclc.dailyTableKwn.service.DailyTableKwnVO;

import java.util.List;
import java.util.Map;

/**
 * @Class Name : DailyTableKwnService.java
 * @Description : 광운대 > 광운대일마감 > 일일일계표2
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.05  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.10.05
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface DailyTableKwnService {

    /** 일일일계표2 - 조회 */
    Map<String, Object> getDailyTableKwnList(DailyTableKwnVO dailyTableKwnVO, SessionInfoVO sessionInfoVO);

    /** 일일일계표2 - 조회 */
    Map<String, Object> getDailyTableKwnList1(DailyTableKwnVO dailyTableKwnVO, SessionInfoVO sessionInfoVO);

    /** 일일일계표2 - 조회 */
    Map<String, Object> getDailyTableKwnList2(DailyTableKwnVO dailyTableKwnVO, SessionInfoVO sessionInfoVO);
}