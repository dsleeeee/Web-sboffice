package kr.co.solbipos.dlvr.anals.dayDlvr.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface DayDlvrService {
  List<DefaultMap<Object>> getDayDlvrSaleList(DayDlvrVO dayDlvrVO, SessionInfoVO sessionInfoVO);

  List<DefaultMap<Object>> getDayNonDlvrSaleList(DayDlvrVO dayDlvrVO, SessionInfoVO sessionInfoVO);

  List<DefaultMap<Object>> getDaySaleDtlList(DayDlvrVO dayDlvrVO, SessionInfoVO sessionInfoVO);
}
