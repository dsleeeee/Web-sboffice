package kr.co.solbipos.sale.anals.store.saleAnals.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface SaleAnalsService {

    /** 일별 - 일별 리스트 조회 */
    List<DefaultMap<String>> getDayList(SaleAnalsVO saleAnalsVO, SessionInfoVO sessionInfoVO);
    /** 월별 - 월별 리스트 조회 */
    List<DefaultMap<String>> getMonthList(SaleAnalsVO saleAnalsVO, SessionInfoVO sessionInfoVO);
}
