package kr.co.solbipos.iostock.orderReturn.rtnDstbCloseProd.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface RtnDstbCloseProdService {
    /** 분배마감 리스트 조회 */
    List<DefaultMap<String>> getRtnDstbCloseProdList(RtnDstbCloseProdVO rtnDstbCloseProdVO);

    /** 분배마감 리스트 확정 */
    int saveRtnDstbCloseProdConfirm(RtnDstbCloseProdVO[] rtnDstbCloseProdVOs, SessionInfoVO sessionInfoVO);

    /** 분배마감 상세 리스트 조회 */
    List<DefaultMap<String>> getRtnDstbCloseProdDtlList(RtnDstbCloseProdVO rtnDstbCloseProdVO);

    /** 분배마감 상세 리스트 저장 */
    int saveRtnDstbCloseProdDtl(RtnDstbCloseProdVO[] rtnDstbCloseProdVOs, SessionInfoVO sessionInfoVO);

    /** 분배마감 추가등록 상품 리스트 조회 */
    List<DefaultMap<String>> getRtnDstbCloseProdAddProdList(RtnDstbCloseProdVO rtnDstbCloseProdVO);

    /** 분배마감 추가등록 분배등록 리스트 조회 */
    List<DefaultMap<String>> getRtnDstbCloseProdAddRegistList(RtnDstbCloseProdVO rtnDstbCloseProdVO);

    /** 분배마감 추가등록 분배등록 리스트 저장 */
    int saveRtnDstbCloseProdAddRegist(RtnDstbCloseProdVO[] rtnDstbCloseProdVOs, SessionInfoVO sessionInfoVO);
}
