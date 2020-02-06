package kr.co.solbipos.sale.status.barcd.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface BarcdService {
    /** 바코드별매출 - 리스트 조회 */
    List<DefaultMap<String>> getBarcdList(BarcdVO barcdVO, SessionInfoVO sessionInfoVO);

    /** 바코드별매출 - 상세 리스트 조회 */
	List<DefaultMap<String>> getBarcdDtlList(BarcdVO barcdVO, SessionInfoVO sessionInfoVO);
}
