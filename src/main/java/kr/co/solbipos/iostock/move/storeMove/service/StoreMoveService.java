package kr.co.solbipos.iostock.move.storeMove.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface StoreMoveService {
    /** 매장이동관리 - 매장이동관리 리스트 조회 */
    List<DefaultMap<String>> getStoreMoveList(StoreMoveVO storeMoveVO);

    /** 매장이동관리 - 매장이동관리 전표상세 상세 조회 */
    DefaultMap<String> getSlipNoInfo(StoreMoveVO storeMoveVO);

    /** 매장이동관리 - 매장이동관리 상세 리스트 조회 */
    List<DefaultMap<String>> getStoreMoveDtlList(StoreMoveVO storeMoveVO);

    /** 매장이동관리 - 매장이동관리 신규등록 상품 리스트 조회 */
    List<DefaultMap<String>> getStoreMoveRegistList(StoreMoveVO storeMoveVO);

    /** 매장이동관리 - 매장이동관리 상품추가 상품 리스트 조회 */
    List<DefaultMap<String>> getStoreMoveAddProdList(StoreMoveVO storeMoveVO);

    /** 매장이동관리 - 매장이동관리 상세 리스트 저장 */
    int saveStoreMoveDtl(StoreMoveVO[] storeMoveVOs, SessionInfoVO sessionInfoVO);

    /** 매장이동관리 - 매장이동관리 상세 삭제 */
    int deleteStoreMoveDtl(StoreMoveVO storeMoveVO, SessionInfoVO sessionInfoVO);

    /** 매장이동관리 - 매장이동관리 신규등록 리스트 저장 */
    int saveStoreMoveRegist(StoreMoveVO[] storeMoveVOs, SessionInfoVO sessionInfoVO);

    /** 매장이동관리 - 매장이동관리 상품추가 리스트 저장 */
    int saveStoreMoveAddProd(StoreMoveVO[] storeMoveVOs, SessionInfoVO sessionInfoVO);
}
