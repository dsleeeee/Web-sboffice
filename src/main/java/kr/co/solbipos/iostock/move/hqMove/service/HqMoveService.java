package kr.co.solbipos.iostock.move.hqMove.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface HqMoveService {
    /** 매장이동관리 - 매장이동관리 리스트 조회 */
    List<DefaultMap<String>> getHqMoveList(HqMoveVO hqMoveVO);

    /** 매장이동관리 - 매장이동관리 전표상세 상세 조회 */
    DefaultMap<String> getSlipNoInfo(HqMoveVO hqMoveVO);

    /** 매장이동관리 - 매장이동관리 상세 리스트 조회 */
    List<DefaultMap<String>> getHqMoveDtlList(HqMoveVO hqMoveVO);

    /** 매장이동관리 - 매장이동관리 신규등록 상품 리스트 조회 */
    List<DefaultMap<String>> getHqMoveRegistList(HqMoveVO hqMoveVO);

    /** 매장이동관리 - 매장이동관리 상품추가 상품 리스트 조회 */
    List<DefaultMap<String>> getHqMoveAddProdList(HqMoveVO hqMoveVO);

    /** 매장이동관리 - 매장이동관리 상세 리스트 저장 */
    int saveHqMoveDtl(HqMoveVO[] hqMoveVOs, SessionInfoVO sessionInfoVO);

    /** 매장이동관리 - 매장이동관리 상세 삭제 */
    int deleteHqMoveDtl(HqMoveVO hqMoveVO, SessionInfoVO sessionInfoVO);

    /** 매장이동관리 - 매장이동관리 신규등록 리스트 저장 */
    int saveHqMoveRegist(HqMoveVO[] hqMoveVOs, SessionInfoVO sessionInfoVO);

    /** 매장이동관리 - 매장이동관리 상품추가 리스트 저장 */
    int saveHqMoveAddProd(HqMoveVO[] hqMoveVOs, SessionInfoVO sessionInfoVO);

}
