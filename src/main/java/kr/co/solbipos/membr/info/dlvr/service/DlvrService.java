package kr.co.solbipos.membr.info.dlvr.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface DlvrService {
  List<DefaultMap<Object>> getDlvrList(DlvrVO dlvrVO, SessionInfoVO sessionInfoVO);

  List<DefaultMap<Object>> getDlvrTelList(DlvrVO dlvrVO, SessionInfoVO sessionInfoVO);
}
