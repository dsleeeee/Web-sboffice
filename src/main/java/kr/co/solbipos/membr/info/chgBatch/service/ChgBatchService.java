package kr.co.solbipos.membr.info.chgBatch.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface ChgBatchService {
    String getMembrChgBatchList(SessionInfoVO sessionInfoVO);

    List<DefaultMap<String>> getMembrClassList(SessionInfoVO sessionInfoVO);

    List<DefaultMap<String>> getMemberChgBatchList(ChgBatchVO chgBatchVO, SessionInfoVO sessionInfoVO);

    int saveChgBatchList(ChgBatchVO[] chgBatchVOs, SessionInfoVO sessionInfoVO);
}
