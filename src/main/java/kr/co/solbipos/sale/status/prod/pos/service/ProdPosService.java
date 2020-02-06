package kr.co.solbipos.sale.status.prod.pos.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface ProdPosService {
	/** 포스별매출상품별 - 매장 포스 리스트 조회 */
	List<DefaultMap<String>> getStorePosList(ProdPosVO prodPosVO, SessionInfoVO sessionInfoVO);

    /** 포스별매출상품별 - 리스트 조회 */
    List<DefaultMap<String>> getProdPosList(ProdPosVO prodPosVO, SessionInfoVO sessionInfoVO);

    /** 포스별매출 - 매장 포스 리스트 조회 */
	List<DefaultMap<String>> getPosNmList(ProdPosVO prodPosVO, SessionInfoVO sessionInfoVO);

}
