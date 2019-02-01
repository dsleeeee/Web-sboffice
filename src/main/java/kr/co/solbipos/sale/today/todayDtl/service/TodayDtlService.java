package kr.co.solbipos.sale.today.todayDtl.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface TodayDtlService {
    /** 당일매출상세현황 - 결제수단 컬럼 리스트 조회 */
    List<DefaultMap<String>> getPayColList(TodayDtlVO todayDtlVO, SessionInfoVO sessionInfoVO);

    /** 당일매출상세현황 - 할인 컬럼 리스트 조회 */
    List<DefaultMap<String>> getDcColList(TodayDtlVO todayDtlVO, SessionInfoVO sessionInfoVO);

    /** 당일매출상세현황 - 객수 컬럼 리스트 조회 */
    List<DefaultMap<String>> getGuestColList(TodayDtlVO todayDtlVO, SessionInfoVO sessionInfoVO);

    /** 당일매출상세현황 - 매출종합 리스트 조회 */
    List<DefaultMap<String>> getTodayDtlList(TodayDtlVO todayDtlVO, SessionInfoVO sessionInfoVO);

    /** 당일매출상세현황 - 매출상세 리스트 조회 */
    List<DefaultMap<String>> getTodayDtlDetailList(TodayDtlVO todayDtlVO, SessionInfoVO sessionInfoVO);

}
