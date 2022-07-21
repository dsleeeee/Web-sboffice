package kr.co.solbipos.stock.curr.dayCurr.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface DayCurrService {
    /** 현재고현황 - 현재고현황 리스트 조회 */
    List<DefaultMap<String>> getDayCurrList(DayCurrVO dayCurrVO, SessionInfoVO sessionInfoVO);

    /** 현재고현황 - 현재고현황 엑셀 전체 리스트 조회 */
    List<DefaultMap<String>> getDayCurrExcelList(DayCurrVO dayCurrVO, SessionInfoVO sessionInfoVO);

}
