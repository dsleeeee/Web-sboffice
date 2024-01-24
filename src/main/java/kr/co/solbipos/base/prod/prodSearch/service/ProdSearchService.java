package kr.co.solbipos.base.prod.prodSearch.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : ProdSearchService.java
 * @Description : 기초관리 - 상품관리2 - 상품정보조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.09.13   권지현       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.09.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface ProdSearchService {

    /**
     * 세션의 가맹점코드로 해당 가맹점의 상품정보 조회
     * @param prodSearchVO 세션정보
     * @return XML_String
     */
    List<DefaultMap<String>> getProdList(ProdSearchVO prodSearchVO, SessionInfoVO sessionInfoVO);

    /**
     * 세션의 가맹점코드로 해당 가맹점의 상품정보 조회(엑셀다운로드용)
     * @param prodSearchVO 세션정보
     * @return XML_String
     */
    List<DefaultMap<String>> getProdExcelList(ProdSearchVO prodSearchVO, SessionInfoVO sessionInfoVO);

    /**
     * 세션의 가맹점코드로 해당 가맹점의 상품정보 조회2
     * @param prodSearchVO 세션정보
     * @return XML_String
     */
    List<DefaultMap<String>> getProdList2(ProdSearchVO prodSearchVO, SessionInfoVO sessionInfoVO);

    /**
     * 세션의 가맹점코드로 해당 가맹점의 상품정보 조회2(엑셀다운로드용)
     * @param prodSearchVO 세션정보
     * @return XML_String
     */
    List<DefaultMap<String>> getProdExcelList2(ProdSearchVO prodSearchVO, SessionInfoVO sessionInfoVO);
}
