package kr.co.solbipos.sale.status.pos.month.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface PosMonthService {
	/** 포스별매출일자별 - 매장 포스 리스트 조회 */
	List<DefaultMap<String>> getStorePosList(PosMonthVO posMonthVO, SessionInfoVO sessionInfoVO);

    /** 포스별매출일자별 - 리스트 조회 */
    List<DefaultMap<String>> getPosMonthList(PosMonthVO posMonthVO, SessionInfoVO sessionInfoVO);

    /** 포스별매출 - 매장 포스 리스트 조회 */
	List<DefaultMap<String>> getPosNmList(PosMonthVO posMonthVO, SessionInfoVO sessionInfoVO);

}
