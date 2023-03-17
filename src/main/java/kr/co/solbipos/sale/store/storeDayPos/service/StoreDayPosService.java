package kr.co.solbipos.sale.store.storeDayPos.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface StoreDayPosService {

    /** 일별 리스트 조회 */
    List<DefaultMap<String>> getDayList(StoreDayPosVO storeDayPosVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<String>> getDayExcelList(StoreDayPosVO storeDayPosVO, SessionInfoVO sessionInfoVO);
}
