package kr.co.solbipos.dlvr.info.regist.service;

import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

public interface DlvrRegistService {
    String getDlvrManageList(SessionInfoVO sessionInfoVO);

    int saveDlvrRegistList(DlvrRegistVO[] dlvrRegistVOs, SessionInfoVO sessionInfoVO);
}
