package kr.co.solbipos.sale.status.pos.hour.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface PosHourService {
	/** 포스별매출일자별 - 매장 포스 리스트 조회 */
	List<DefaultMap<String>> getStorePosList(PosHourVO posHourVO, SessionInfoVO sessionInfoVO);

    /** 포스별매출일자별 - 리스트 조회 */
    List<DefaultMap<String>> getPosDayList(PosHourVO posHourVO, SessionInfoVO sessionInfoVO);

    /** 포스별매출 - 매장 포스 리스트 조회 */
	List<DefaultMap<String>> getPosNmList(PosHourVO posHourVO, SessionInfoVO sessionInfoVO);

}
