package kr.co.solbipos.iostock.order.dstbReq.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface DstbReqService {

    /** 분배등록 리스트 조회 */
    List<DefaultMap<String>> getDstbReqList(DstbReqVO dstbReqVO);

    /** 분배등록 분배완료 */
    int saveDstbConfirm(DstbReqVO[] dstbReqVOs, SessionInfoVO sessionInfoVO);

    /** 분배등록 상세 리스트 조회 */
    List<DefaultMap<String>> getDstbReqDtlList(DstbReqVO dstbReqVO);

    /** 분배등록 상세 리스트 저장 */
    int saveDstbReqDtl(DstbReqVO[] dstbReqVOs, SessionInfoVO sessionInfoVO);

}
