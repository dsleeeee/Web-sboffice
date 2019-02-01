package kr.co.solbipos.sale.today.todayGnrlz.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface TodayGnrlzService {
    /** 당일매출종합현황 - 매출종합 리스트 조회 */
    List<DefaultMap<String>> getTodayGnrlzList(TodayGnrlzVO todayGnrlzVO, SessionInfoVO sessionInfoVO);

    /** 당일매출종합현황 - 결제수단별 매출 리스트 조회 */
    List<DefaultMap<String>> getTodayGnrlzPayList(TodayGnrlzVO todayGnrlzVO, SessionInfoVO sessionInfoVO);

    /** 당일매출종합현황 - 회원 Point 적립/사용 리스트 조회 */
    List<DefaultMap<String>> getTodayGnrlzMemberList(TodayGnrlzVO todayGnrlzVO, SessionInfoVO sessionInfoVO);

    /** 당일매출종합현황 - 상품별 매출현황 리스트 조회 */
    List<DefaultMap<String>> getTodayGnrlzProdList(TodayGnrlzVO todayGnrlzVO, SessionInfoVO sessionInfoVO);

}
