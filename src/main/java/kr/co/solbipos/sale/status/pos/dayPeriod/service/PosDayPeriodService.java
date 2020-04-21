package kr.co.solbipos.sale.status.pos.dayPeriod.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface PosDayPeriodService {
    /** 포스별별매출 - 리스트 조회 */
    List<DefaultMap<String>> getPosDayPeriodList(PosDayPeriodVO posDayPeriodVO, SessionInfoVO sessionInfoVO);

    /** 포스별매출 - 상세 리스트 조회 */
	List<DefaultMap<String>> getPosDayPeriodDtlList(PosDayPeriodVO posDayPeriodVO, SessionInfoVO sessionInfoVO);

	/** 포스별별매출 - 리스트 조회 (엑셀)*/
    List<DefaultMap<String>> getPosDayPeriodExcelList(PosDayPeriodVO posDayPeriodVO, SessionInfoVO sessionInfoVO);

    /** 포스별매출 - 상세 리스트 조회 (엑셀)*/
	List<DefaultMap<String>> getPosDayPeriodDtlExcelList(PosDayPeriodVO posDayPeriodVO, SessionInfoVO sessionInfoVO);
}
