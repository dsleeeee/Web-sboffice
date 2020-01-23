package kr.co.solbipos.sale.day.dayOfWeek.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : DayOfWeekService.java
 * @Description : 매출관리 > 매출현황 > 기간별매출 > 요일별탭
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.11.29  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.11.29
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface DayOfWeekService {

    /** 주간종합탭 - 주간종합조회 */
    List<DefaultMap<Object>> getDayOfWeekTotalList(DayOfWeekVO dayOfWeekVO, SessionInfoVO sessionInfoVO);

    /** 할인구별별탭 - 할인구분별매출조회 */
    List<DefaultMap<Object>> getDayOfWeekDcList(DayOfWeekVO dayOfWeekVO, SessionInfoVO sessionInfoVO);

    /** 과면세별탭 - 과면세별매출조회 */
    List<DefaultMap<Object>> getDayOfWeekTaxList(DayOfWeekVO dayOfWeekVO, SessionInfoVO sessionInfoVO);

    /** 시간대별 - 시간대별매출조회 */
    List<DefaultMap<Object>> getDayOfWeekTimeList(DayOfWeekVO dayOfWeekVO, SessionInfoVO sessionInfoVO);

    /** 코너별 - 코너별 매출조회 */
    List<DefaultMap<Object>> getDayOfWeekCornerList(DayOfWeekVO dayOfWeekVO, SessionInfoVO sessionInfoVO);

    /** 외식테이블별 - 외식테이블별매출조회 */
    List<DefaultMap<Object>> getDayOfWeekTableList(DayOfWeekVO dayOfWeekVO, SessionInfoVO sessionInfoVO);

    /** 포스별 - 포스별매출조회 */
    List<DefaultMap<Object>> getDayOfWeekPosList(DayOfWeekVO dayOfWeekVO, SessionInfoVO sessionInfoVO);
}
