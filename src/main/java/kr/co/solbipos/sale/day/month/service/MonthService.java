package kr.co.solbipos.sale.day.month.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : MonthService.java
 * @Description : 매출관리 > 매출현황 > 기간별매출 > 월별탭
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.12.09  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.12.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MonthService {

    /** 월별종합탭 - 월별종합조회 */
    List<DefaultMap<Object>> getMonthTotalList(MonthVO monthVO, SessionInfoVO sessionInfoVO);

    /** 할인구별별탭 - 할인구분별매출조회 */
    List<DefaultMap<Object>> getMonthDcList(MonthVO monthVO, SessionInfoVO sessionInfoVO);

    /** 과면세별탭 - 과면세별매출조회 */
    List<DefaultMap<Object>> getMonthTaxList(MonthVO monthVO, SessionInfoVO sessionInfoVO);

    /** 시간대별 - 시간대별매출조회 */
    List<DefaultMap<Object>> getMonthTimeList(MonthVO monthVO, SessionInfoVO sessionInfoVO);

    /** 포스별 - 포스별매출조회 */
    List<DefaultMap<Object>> getMonthPosList(MonthVO monthVO, SessionInfoVO sessionInfoVO);
}
