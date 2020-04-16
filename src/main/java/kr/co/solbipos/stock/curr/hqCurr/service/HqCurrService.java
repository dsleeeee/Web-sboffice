package kr.co.solbipos.stock.curr.hqCurr.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface HqCurrService {
    /** 현재고현황 - 현재고현황 리스트 조회 */
    List<DefaultMap<String>> getHqCurrList(HqCurrVO hqCurrVO, SessionInfoVO sessionInfoVO);
    
    /** 현재고현황 - 현재고현황 상세리스트 조회 */
    List<DefaultMap<String>> gethqCurrDtlList(HqCurrVO hqCurrVO, SessionInfoVO sessionInfoVO);

}
