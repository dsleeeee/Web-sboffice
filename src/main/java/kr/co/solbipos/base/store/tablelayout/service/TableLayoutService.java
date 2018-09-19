package kr.co.solbipos.base.store.tablelayout.service;

import kr.co.common.data.structure.Result;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
 * @Class Name : TableLayoutService.java
 * @Description : 기초관리 > 매장관리 > 테이블관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  조병준      최초생성
 *
 * @author NHN한국사이버결제 KCP 조병준
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
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
