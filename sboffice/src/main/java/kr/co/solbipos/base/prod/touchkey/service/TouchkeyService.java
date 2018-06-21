package kr.co.solbipos.base.prod.touchkey.service;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.data.structure.Result;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
 * @Class Name : TouchkeyService.java
 * @Description : 기초관리 - 상품관리 - 판매터치키등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2015.05.01  조병준      최초생성
 *
 * @author NHN한국사이버결제 KCP 조병준
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface TouchkeyService {

    /**
     * 세션의 가맹점코드로 해당 가맹점의 상품정보 조회
     * @param sessionInfoVO 세션정보
     * @return XML_String
     */
    List<DefaultMap<String>> selectProdByStore(SessionInfoVO sessionInfoVO);

    /**
     * 세션의 가맹점코드로 해당 가맹점의 판매터치키 조회
     * @param sessionInfoVO 세션정보
     * @return XML_String
     */
    String selectTouchkeyByStore(SessionInfoVO sessionInfoVO);

    /**
     * 세션의 가맹점코드에 판매터치키 저장
     * @param sessionInfoVO 세션정보
     * @param xml 클라이언트로 부터 받은 xml 문자열
     * @return Result
     */
    Result setTouchkey(SessionInfoVO sessionInfoVO, String xml);

}
