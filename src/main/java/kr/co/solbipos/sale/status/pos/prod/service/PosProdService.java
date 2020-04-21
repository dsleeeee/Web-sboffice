package kr.co.solbipos.sale.status.pos.prod.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface PosProdService {
	/** 포스별매출상품별 - 매장 포스 리스트 조회 */
	List<DefaultMap<String>> getStorePosList(PosProdVO posProdVO, SessionInfoVO sessionInfoVO);

    /** 포스별매출상품별 - 리스트 조회 */
    List<DefaultMap<String>> getPosProdList(PosProdVO posProdVO, SessionInfoVO sessionInfoVO);

    /** 포스별매출상품별 - 리스트 조회(엑셀) */
    List<DefaultMap<String>> getPosProdExcelList(PosProdVO posProdVO, SessionInfoVO sessionInfoVO);

    /** 포스별매출 - 매장 포스 리스트 조회 */
	List<DefaultMap<String>> getPosNmList(PosProdVO posProdVO, SessionInfoVO sessionInfoVO);

}
