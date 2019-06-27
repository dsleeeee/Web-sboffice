package kr.co.solbipos.sale.day.day.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface DayService {
    /** 일자별 - 결제수단 컬럼 리스트 조회 */
    List<DefaultMap<String>> getPayColList(DayVO dayVO, SessionInfoVO sessionInfoVO);

    /** 일자별 - 할인 컬럼 리스트 조회 */
    List<DefaultMap<String>> getDcColList(DayVO dayVO, SessionInfoVO sessionInfoVO);

    /** 일자별(일별종합 탭) - 일별종합 리스트 조회 */
    List<DefaultMap<String>> getDayTotalList(DayVO dayVO, SessionInfoVO sessionInfoVO);

    /** 일자별(일별종합 탭) - 일자 매장별 매출현황 리스트 조회 */
    List<DefaultMap<String>> getDayStoreDtlList(DayVO dayVO, SessionInfoVO sessionInfoVO);

    /** 일자별(일별종합 탭) - 일자 매장별 할인현황 리스트 조회 */
    List<DefaultMap<String>> getDayStoreDcList(DayVO dayVO, SessionInfoVO sessionInfoVO);

    /** 일자별(할인구분별 탭) - 할인구분 리스트 조회 */
    List<DefaultMap<String>> getDayDcList(DayVO dayVO, SessionInfoVO sessionInfoVO);

    /** 일자별(과면세별 탭) - 과면세 리스트 조회 */
    List<DefaultMap<String>> getDayTaxList(DayVO dayVO, SessionInfoVO sessionInfoVO);

    /** 일자별(시간대별 탭) - 시간대별 리스트 조회 */
    List<DefaultMap<String>> getDayTimeList(DayVO dayVO, SessionInfoVO sessionInfoVO);

}
