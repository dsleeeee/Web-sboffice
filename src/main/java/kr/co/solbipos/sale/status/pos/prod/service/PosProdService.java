package kr.co.solbipos.sale.status.pos.prod.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface PosProdService {
    /** 상품별탭 - 매장 및 포스 리스트 조회 */
	List<DefaultMap<String>> getStorePosList(PosProdVO posProdVO, SessionInfoVO sessionInfoVO);

    /** 상품별탭 - 매장 코너 리스트 조회 */
    List<DefaultMap<String>> getPosNmList(PosProdVO posProdVO, SessionInfoVO sessionInfoVO);

    /** 상품별탭 - 조회 */
    List<DefaultMap<String>> getPosProdList(PosProdVO posProdVO, SessionInfoVO sessionInfoVO);

    /** 상품별탭 - 엑셀 조회 */
    List<DefaultMap<String>> getPosProdExcelList(PosProdVO posProdVO, SessionInfoVO sessionInfoVO);
}