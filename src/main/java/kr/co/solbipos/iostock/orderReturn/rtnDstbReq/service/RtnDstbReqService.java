package kr.co.solbipos.iostock.orderReturn.rtnDstbReq.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface RtnDstbReqService {
    /** 반품등록 리스트 조회 */
    List<DefaultMap<String>> getRtnDstbReqList(RtnDstbReqVO rtnDstbReqVO);

    /** 반품등록 분배완료 */
    int saveDstbConfirm(RtnDstbReqVO[] rtnDstbReqVOs, SessionInfoVO sessionInfoVO);

    /** 반품등록 상세 리스트 조회 */
    List<DefaultMap<String>> getRtnDstbReqDtlList(RtnDstbReqVO rtnDstbReqVO);

    /** 반품등록 상세 리스트 저장 */
    int saveRtnDstbReqDtl(RtnDstbReqVO[] rtnDstbReqVOs, SessionInfoVO sessionInfoVO);

}
