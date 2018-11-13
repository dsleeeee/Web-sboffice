package kr.co.solbipos.base.prod.prod.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.prod.info.service.ProductClassVO;

import java.util.List;

/**
 * @Class Name : ProdService.java
 * @Description : 기초관리 - 상품관리 - 상품조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.06  장혁수      최초생성
 *
 * @author NHN한국사이버결제 KCP 장혁수
 * @since 2018. 08.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface ProdService {

    /**
     * 세션의 가맹점코드로 해당 가맹점의 상품정보 조회
     * @param prodVO 세션정보
     * @return XML_String
     */
    List<DefaultMap<String>> getProdList(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /**
     * 세션의 가맹점코드로 해당 가맹점의 상품정보 상세 조회
     * @param prodVO 세션정보
     * @return XML_String
     */
    DefaultMap<String> getProdDetail(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 상품분류 플랫 조회 */
    String getProdClassCdNm(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 상품분류 트리 조회 */
    List<ProductClassVO> getProdClassTree(ProdVO prodVO, SessionInfoVO sessionInfoVO);

    /** 상품정보 저장 */
    int saveProductInfo(ProdVO prodVO, SessionInfoVO sessionInfoVO);

}
