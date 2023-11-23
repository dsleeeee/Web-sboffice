package kr.co.solbipos.base.multilingual.kioskSideOption.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : KioskSideOptionService.java
 * @Description : 기초관리 - 다국어관리 - 다국어관리(키오스크(카테고리)/사이드/옵션)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.11.20  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.11.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface KioskSideOptionService {

    /** 키오스크(카테고리) 탭 리스트 조회 */
    List<DefaultMap<String>> getKioskCategoryList(KioskSideOptionVO kioskSideOptionVO, SessionInfoVO sessionInfoVO);

    /** 키오스크(카테고리) 영문, 중문, 일문 저장 */
    int saveKioskCategory(KioskSideOptionVO[] kioskSideOptionVOs, SessionInfoVO sessionInfoVO);
}
