package kr.co.solbipos.base.prod.kioskDisplay.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : KioskDisplayService.java
 * @Description : 기초관리 - 상품관리 - 비노출관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.10  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.03.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface KioskDisplayService {

    /**
     * 세션의 가맹점코드로 해당 가맹점의 상품정보 조회
     * @param kioskDisplayVO 세션정보
     * @return XML_String
     */
    List<DefaultMap<String>> getProdList(KioskDisplayVO kioskDisplayVO, SessionInfoVO sessionInfoVO);

    /**
     * 세션의 가맹점코드로 해당 가맹점의 상품정보 상세 조회
     * @param kioskDisplayVO 세션정보
     * @return XML_String
     */
    DefaultMap<String> getProdDetail(KioskDisplayVO kioskDisplayVO, SessionInfoVO sessionInfoVO);

    // 상품 품절여부 저장
    int getProdKioskDisplaySave(KioskDisplayVO[] kioskDisplayVOs, SessionInfoVO sessionInfoVO);


}
