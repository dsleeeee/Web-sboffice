package kr.co.solbipos.membr.info.dlvr.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface DlvrService {
  List<DefaultMap<Object>> getDlvrList(DlvrVO dlvrVO, SessionInfoVO sessionInfoVO);

  List<DefaultMap<Object>> getDlvrTelList(DlvrVO dlvrVO, SessionInfoVO sessionInfoVO);

  int deleteDlvr(DlvrVO[] dlvrVOs, SessionInfoVO sessionInfoVO);

  int deleteDlvrTel(DlvrVO[] dlvrVOs, SessionInfoVO sessionInfoVO);


  List getDlvrLzoneList(DlvrVO dlvrVO, SessionInfoVO sessionInfoVO);

    List getMembrClassList(SessionInfoVO sessionInfoVO);

  DefaultMap<Object> getDlvrMzoneList(DlvrVO dlvrVO, SessionInfoVO sessionInfoVO);

  int saveDlvr(DlvrVO[] dlvrVOs, SessionInfoVO sessionInfoVO);

  int saveDlvrTel(DlvrVO[] dlvrVOs, SessionInfoVO sessionInfoVO);
}
