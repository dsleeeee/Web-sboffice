package kr.co.solbipos.iostock.frnchs.unusual.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface UnusualService {
    /** 본사 매장간 입출고내역 - 특이사항별 입출고내역 리스트 조회 */
    List<DefaultMap<String>> getUnusualList(UnusualVO unusualVO, SessionInfoVO sessionInfoVO);

    /** 본사 매장간 입출고내역 - 특이사항별 입출고내역 엑셀리스트 조회 */
	List<DefaultMap<String>> getUnusualExcelList(UnusualVO unusualVO, SessionInfoVO sessionInfoVO);
 
}
