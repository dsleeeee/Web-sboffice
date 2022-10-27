package kr.co.solbipos.excclc.excclc.dailyTableKwu.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.excclc.excclc.dailyTableKwu.service.DailyTableKwuVO;

import java.util.List;
import java.util.Map;

/**
 * @Class Name : DailyTableKwuService.java
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
public interface DailyTableKwuService {

    /** 일일일계표2 - 조회 */
    Map<String, Object> getDailyTableKwuList(DailyTableKwuVO dailyTableKwuVO, SessionInfoVO sessionInfoVO);

    /** 일일일계표2 - 조회 */
    Map<String, Object> getDailyTableKwuList1(DailyTableKwuVO dailyTableKwuVO, SessionInfoVO sessionInfoVO);

    /** 일일일계표2 - 조회 */
    Map<String, Object> getDailyTableKwuList2(DailyTableKwuVO dailyTableKwuVO, SessionInfoVO sessionInfoVO);
}