package kr.co.solbipos.iostock.order.dstbCloseProd.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface DstbCloseProdService {
    /** 분배마감 리스트 조회 */
    List<DefaultMap<String>> getDstbCloseProdList(DstbCloseProdVO dstbCloseProdVO);

    /** 분배마감 리스트 확정 */
    int saveDstbCloseProdConfirm(DstbCloseProdVO[] dstbCloseProdVOs, SessionInfoVO sessionInfoVO);

    /** 분배마감 상세 리스트 조회 */
    List<DefaultMap<String>> getDstbCloseProdDtlList(DstbCloseProdVO dstbCloseProdVO);

    /** 분배마감 상세 리스트 저장 */
    int saveDstbCloseProdDtl(DstbCloseProdVO[] dstbCloseProdVOs, SessionInfoVO sessionInfoVO);

    /** 분배마감 추가등록 상품 리스트 조회 */
    List<DefaultMap<String>> getDstbCloseProdAddProdList(DstbCloseProdVO dstbCloseProdVO);

    /** 분배마감 추가등록 분배등록 리스트 조회 */
    List<DefaultMap<String>> getDstbCloseProdAddRegistList(DstbCloseProdVO dstbCloseProdVO);

    /** 분배마감 추가등록 분배등록 리스트 저장 */
    int saveDstbCloseProdAddRegist(DstbCloseProdVO[] dstbCloseProdVOs, SessionInfoVO sessionInfoVO);

}
