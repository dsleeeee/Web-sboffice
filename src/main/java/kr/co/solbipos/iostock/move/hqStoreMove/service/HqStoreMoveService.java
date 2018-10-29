package kr.co.solbipos.iostock.move.hqStoreMove.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface HqStoreMoveService {
    /** 매장이동관리 - 매장이동관리 리스트 조회 */
    List<DefaultMap<String>> getHqStoreMoveList(HqStoreMoveVO hqStoreMoveVO);

    /** 매장이동관리 - 매장이동관리 전표상세 상세 조회 */
    DefaultMap<String> getSlipNoInfo(HqStoreMoveVO hqStoreMoveVO);

    /** 매장이동관리 - 매장이동관리 상세 리스트 조회 */
    List<DefaultMap<String>> getHqStoreMoveDtlList(HqStoreMoveVO hqStoreMoveVO);

    /** 매장이동관리 - 매장이동관리 신규등록 상품 리스트 조회 */
    List<DefaultMap<String>> getHqStoreMoveRegistList(HqStoreMoveVO hqStoreMoveVO);

    /** 매장이동관리 - 매장이동관리 상품추가 상품 리스트 조회 */
    List<DefaultMap<String>> getHqStoreMoveAddProdList(HqStoreMoveVO hqStoreMoveVO);

    /** 매장이동관리 - 매장이동관리 상세 리스트 저장 */
    int saveHqStoreMoveDtl(HqStoreMoveVO[] hqStoreMoveVOs, SessionInfoVO sessionInfoVO);

    /** 매장이동관리 - 매장이동관리 상세 삭제 */
    int deleteHqStoreMoveDtl(HqStoreMoveVO hqStoreMoveVO, SessionInfoVO sessionInfoVO);

    /** 매장이동관리 - 매장이동관리 신규등록 리스트 저장 */
    int saveHqStoreMoveRegist(HqStoreMoveVO[] hqStoreMoveVOs, SessionInfoVO sessionInfoVO);

    /** 매장이동관리 - 매장이동관리 상품추가 리스트 저장 */
    int saveHqStoreMoveAddProd(HqStoreMoveVO[] hqStoreMoveVOs, SessionInfoVO sessionInfoVO);

}
