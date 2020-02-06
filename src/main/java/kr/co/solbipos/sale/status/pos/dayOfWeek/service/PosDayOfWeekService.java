package kr.co.solbipos.sale.status.pos.dayOfWeek.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface PosDayOfWeekService {
	/** 포스별매출요일별 - 매장 포스 리스트 조회 */
	List<DefaultMap<String>> getStorePosList(PosDayOfWeekVO posDayOfWeekVO, SessionInfoVO sessionInfoVO);

    /** 포스별매출요일별 - 리스트 조회 */
    List<DefaultMap<String>> getPosDayOfWeekList(PosDayOfWeekVO posDayOfWeekVO, SessionInfoVO sessionInfoVO);

    /** 포스별매출 - 매장 포스 리스트 조회 */
	List<DefaultMap<String>> getPosNmList(PosDayOfWeekVO posDayOfWeekVO, SessionInfoVO sessionInfoVO);

}
