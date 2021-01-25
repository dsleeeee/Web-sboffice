package kr.co.solbipos.sale.status.pos.dayPeriod.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface PosDayPeriodService {
    /** 설정기간별탭 - 조회 */
    List<DefaultMap<String>> getPosDayPeriodList(PosDayPeriodVO posDayPeriodVO, SessionInfoVO sessionInfoVO);

    /** 설정기간별탭 - 엑셀 조회 */
    List<DefaultMap<String>> getPosDayPeriodExcelList(PosDayPeriodVO posDayPeriodVO, SessionInfoVO sessionInfoVO);

    /** 설정기간별탭 - 상세 조회 */
	List<DefaultMap<String>> getPosDayPeriodDtlList(PosDayPeriodVO posDayPeriodVO, SessionInfoVO sessionInfoVO);

    /** 설정기간별탭 - 상세 엑셀 조회 */
	List<DefaultMap<String>> getPosDayPeriodDtlExcelList(PosDayPeriodVO posDayPeriodVO, SessionInfoVO sessionInfoVO);
}