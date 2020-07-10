package kr.co.solbipos.kds.anals.day.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface KdsDayService {
    List<DefaultMap<String>> getKdsDay(KdsDayVO kdsDayVO, SessionInfoVO sessionInfoVO);
}
