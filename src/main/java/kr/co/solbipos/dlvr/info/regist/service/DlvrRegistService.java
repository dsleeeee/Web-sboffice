package kr.co.solbipos.dlvr.info.regist.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface DlvrRegistService {
    String getDlvrManageList(SessionInfoVO sessionInfoVO);

    int saveDlvrRegistList(DlvrRegistVO[] dlvrRegistVOs, SessionInfoVO sessionInfoVO);


    List<DefaultMap<String>> dlvrDetailList(DlvrRegistVO dlvrRegistVO, SessionInfoVO sessionInfoVO);

    int saveDlvrDetailRegistList(DlvrRegistVO[] dlvrRegistVOs, SessionInfoVO sessionInfoVO);

    List<DefaultMap<String>> dlvrManageList(SessionInfoVO sessionInfoVO);
}
