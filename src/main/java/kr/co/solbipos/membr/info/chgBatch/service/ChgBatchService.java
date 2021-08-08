package kr.co.solbipos.membr.info.chgBatch.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface ChgBatchService {
    String getMembrChgBatchList(SessionInfoVO sessionInfoVO);

    /** 회원등급 리스트 조회 */
    List<DefaultMap<String>> getMembrClassList(SessionInfoVO sessionInfoVO);

    /** 회원정보 리스트 조회 */
    List<DefaultMap<String>> getMemberChgBatchList(ChgBatchVO chgBatchVO, SessionInfoVO sessionInfoVO);

    /** 등급포인트 적립 저장 */
    int saveChgBatchList(ChgBatchVO[] chgBatchVOs, SessionInfoVO sessionInfoVO);
}
