package kr.co.solbipos.sale.status.prod.day.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface ProdDayService {
    /** 일자별탭 - 조회 */
    List<DefaultMap<String>> getProdDayList(ProdDayVO prodDayVO, SessionInfoVO sessionInfoVO);

    /** 일자별탭 - 엑셀 조회 */
    List<DefaultMap<String>> getProdDayExcelList(ProdDayVO prodDayVO, SessionInfoVO sessionInfoVO);
}