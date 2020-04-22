package kr.co.solbipos.iostock.move.standMove.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface StandMoveService {
    /** 매대이동관리 - 매대이동관리 리스트 조회 */
    List<DefaultMap<String>> getStandMoveList(StandMoveVO standMoveVO);

    /** 매대이동관리 - 매대이동관리 상세 리스트 조회 */
    List<DefaultMap<String>> getStandMoveDtlList(StandMoveVO standMoveVO);

    /** 매대이동관리 - 매대이동관리 신규등록 상품 리스트 조회 */
    List<DefaultMap<String>> getStandMoveRegistList(StandMoveVO standMoveVO);

    /** 매대이동관리 - 매대이동관리 상품추가 상품 리스트 조회 */
    List<DefaultMap<String>> getStandMoveAddProdList(StandMoveVO standMoveVO);

    /** 매대이동관리 - 매대이동관리 상세 리스트 저장 */
    int saveStandMoveDtl(StandMoveVO[] standMoveVOs, SessionInfoVO sessionInfoVO);
        
    /** 매대이동관리 - 매대이동관리 신규등록 리스트 저장 */
    int saveStandMoveRegist(StandMoveVO[] standMoveVOs, SessionInfoVO sessionInfoVO);

    /** 매대이동관리 - 매대이동관리 상품추가 리스트 저장 */
    int saveStandMoveAddProd(StandMoveVO[] standMoveVOs, SessionInfoVO sessionInfoVO);
}
