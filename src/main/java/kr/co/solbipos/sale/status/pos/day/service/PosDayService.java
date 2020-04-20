package kr.co.solbipos.sale.status.pos.day.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface PosDayService {
	/** 포스별매출일자별 - 매장 포스 리스트 조회 */
	List<DefaultMap<String>> getStorePosList(PosDayVO posDayVO, SessionInfoVO sessionInfoVO);

    /** 포스별매출일자별 - 리스트 조회 */
    List<DefaultMap<String>> getPosDayList(PosDayVO posDayVO, SessionInfoVO sessionInfoVO);

    /** 포스별매출일자별 - 리스트 조회(엑셀) */
    List<DefaultMap<String>> getPosDayExcelList(PosDayVO posDayVO, SessionInfoVO sessionInfoVO);

    /** 포스별매출 - 매장 포스 리스트 조회 */
	List<DefaultMap<String>> getPosNmList(PosDayVO posDayVO, SessionInfoVO sessionInfoVO);

}
