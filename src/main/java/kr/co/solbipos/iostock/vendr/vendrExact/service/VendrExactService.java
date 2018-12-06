package kr.co.solbipos.iostock.vendr.vendrExact.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

public interface VendrExactService {
    /** 거래처 정산관리 - 거래처별 정산 리스트 조회 */
    List<DefaultMap<String>> getVendrExactList(VendrExactVO vendrExactVO, SessionInfoVO sessionInfoVO);

    /** 거래처 정산관리 - 거래처 정산 상세 리스트 조회 */
    List<DefaultMap<String>> getVendrExactDtlList(VendrExactVO vendrExactVO, SessionInfoVO sessionInfoVO);

    /** 거래처 정산관리 - 지급액 상세 조회 */
    DefaultMap<String> getExactInfo(VendrExactVO vendrExactVO, SessionInfoVO sessionInfoVO);

    /** 거래처 정산관리 - 지급액 저장 */
    int saveVendrExactRegist(VendrExactVO vendrExactVO, SessionInfoVO sessionInfoVO);

    /** 거래처 정산관리 - 지급액 삭제 */
    int deleteVendrExactRegist(VendrExactVO vendrExactVO, SessionInfoVO sessionInfoVO);

}
