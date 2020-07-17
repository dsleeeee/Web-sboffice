package kr.co.solbipos.kds.anals.chart.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface KdsService {
    List<DefaultMap<String>> getKdsDay(KdsVO kdsVO, SessionInfoVO sessionInfoVO);

    List<DefaultMap<String>> getKdsDayTime(KdsVO kdsVO, SessionInfoVO sessionInfoVO);

    List<DefaultMap<String>> getKdsDayTimeChart(KdsVO kdsVO, SessionInfoVO sessionInfoVO);

    List<DefaultMap<String>> getKdsDayProd(KdsVO kdsVO, SessionInfoVO sessionInfoVO);
}
