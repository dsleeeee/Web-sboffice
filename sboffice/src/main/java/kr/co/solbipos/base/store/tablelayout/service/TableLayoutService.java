package kr.co.solbipos.base.store.tablelayout.service;

import kr.co.common.data.structure.Result;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
 * 기초관리 - 매장관리 - 테이블구성
 *
 * @author 조병준
 *
 */
public interface TableLayoutService {

    /**
     * 세션의 가맹점코드로 해당 가맹점의 테이블 구성 조회
     * @param sessionInfoVO 세션정보
     * @return XML_String
     */
    String selectTableLayoutByStore(SessionInfoVO sessionInfoVO);

    /**
     * 세션의 가맹점코드에 테이블 구성 저장
     * @param sessionInfoVO 세션정보
     * @param xml 클라이언트로 부터 받은 xml 문자열
     * @return Result
     */
    Result setTableLayout(SessionInfoVO sessionInfoVO, String xml);


}
