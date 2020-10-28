package kr.co.solbipos.dlvr.anals.dlvrInfo.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface DlvrInfoService {
  public List<DefaultMap<Object>> getDlvrInfoList(DlvrInfoVO dlvrInfoVO, SessionInfoVO sessionInfoVO);

  DefaultMap<String> getBillInfo(DlvrInfoVO dlvrInfoVO, SessionInfoVO sessionInfoVO);

  List<DefaultMap<Object>> getBillInfoList(DlvrInfoVO dlvrInfoVO, SessionInfoVO sessionInfoVO);
}
