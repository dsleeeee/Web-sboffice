package kr.co.solbipos.excclc.excclc.dayClose.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : DayCloseService.java
 * @Description : 광운대 > 광운대일마감 > 광운대일마감
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.07  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.09.07
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface DayCloseService {

    /** 조회 */
    List<DefaultMap<String>> getDayCloseList(DayCloseVO dayCloseVO, SessionInfoVO sessionInfoVO);
    /** 마감데이터 조회 */
    DefaultMap<String> getDayCloseDtl(DayCloseVO dayCloseVO, SessionInfoVO sessionInfoVO);
    int saveClose(DayCloseVO dayCloseVO, SessionInfoVO sessionInfoVO);
    int closeCancel(DayCloseVO dayCloseVO, SessionInfoVO sessionInfoVO);

}
