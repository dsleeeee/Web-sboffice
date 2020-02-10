package kr.co.solbipos.sale.day.dayPeriod.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : DayPeriodService.java
 * @Description : 매출관리 > 매출현황 > 기간별매출 > 설정기간별탭
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.01.23  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.01.23
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface DayPeriodService {

    /** 시간대별탭 - 시간대별 매출조회 */
    List<DefaultMap<Object>> getDayPeriodTimeList(DayPeriodVO dayPeriodVO, SessionInfoVO sessionInfoVO);

    /** 시간대별탭 - 시간대별 매출상세조회 */
    List<DefaultMap<Object>> getDayPeriodTimeDetailList(DayPeriodVO dayPeriodVO, SessionInfoVO sessionInfoVO);

    /** 상품분류별탭 - 상품분류별 매출조회 */
    List<DefaultMap<Object>> getDayPeriodProdClassList(DayPeriodVO dayPeriodVO, SessionInfoVO sessionInfoVO);

    /** 상품분류별탭 - 상품분류별 매출상세조회 */
    List<DefaultMap<Object>> getDayPeriodProdClassDetailList(DayPeriodVO dayPeriodVO, SessionInfoVO sessionInfoVO);

    /** 외식테이블별탭 - 외식테이블별 매출조회 */
    List<DefaultMap<Object>> getDayPeriodTableList(DayPeriodVO dayPeriodVO, SessionInfoVO sessionInfoVO);

    /** 코너별탭 - 코너별 매출조회 */
    List<DefaultMap<Object>> getDayPeriodCornerList(DayPeriodVO dayPeriodVO, SessionInfoVO sessionInfoVO);

    /** 코너별탭 - 코너별 매출상세조회 */
    List<DefaultMap<Object>> getDayPeriodCornerDetailList(DayPeriodVO dayPeriodVO, SessionInfoVO sessionInfoVO);

    /** 상품권별탭 - 상품권별 매출조회 */
    List<DefaultMap<Object>> getDayPeriodGiftList(DayPeriodVO dayPeriodVO, SessionInfoVO sessionInfoVO);

    /** 상품권별탭 - 상품권별 매출상세조회 */
    List<DefaultMap<Object>> getDayPeriodGiftDetailList(DayPeriodVO dayPeriodVO, SessionInfoVO sessionInfoVO);
}
