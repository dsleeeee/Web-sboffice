package kr.co.solbipos.iostock.frnchs.prod.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface FrnchsProdService {
    /** 본사 매장간 입출고내역 - 상품별 입출고내역 리스트 조회 */
    List<DefaultMap<String>> getProdFrnchsList(FrnchsProdVO frnchsProdVO, SessionInfoVO sessionInfoVO);
    /** 본사 매장간 입출고내역 - 상품별 입출고내역 팝업 리스트 조회 */
    List<DefaultMap<String>> getProdInOutstockInfoList(FrnchsProdVO frnchsProdVO, SessionInfoVO sessionInfoVO);
    /** 본사 매장간 입출고내역 - 상품별 입출고내역 엑셀리스트 조회 */
    List<DefaultMap<String>> getProdFrnchsExcelList(FrnchsProdVO frnchsProdVO, SessionInfoVO sessionInfoVO);

}
