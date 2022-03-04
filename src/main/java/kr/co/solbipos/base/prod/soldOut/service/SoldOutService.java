package kr.co.solbipos.base.prod.soldOut.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : SoldOutService.java
 * @Description : 기초관리 - 상품관리 - 품절관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.02.28  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.02.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SoldOutService {

    /**
     * 세션의 가맹점코드로 해당 가맹점의 상품정보 조회
     * @param soldOutVO 세션정보
     * @return XML_String
     */
    List<DefaultMap<String>> getProdList(SoldOutVO soldOutVO, SessionInfoVO sessionInfoVO);

    /**
     * 세션의 가맹점코드로 해당 가맹점의 상품정보 상세 조회
     * @param soldOutVO 세션정보
     * @return XML_String
     */
    DefaultMap<String> getProdDetail(SoldOutVO soldOutVO, SessionInfoVO sessionInfoVO);

    // 상품 품절여부 저장
    int getProdSoldOutSave(SoldOutVO[] soldOutVOs, SessionInfoVO sessionInfoVO);

    /** 사이드메뉴-선택메뉴탭-선택상품 목록 조회 */
    List<DefaultMap<String>> getMenuGrpList(SoldOutVO soldOutVO, SessionInfoVO sessionInfoVO);

    /** 사이드메뉴-선택메뉴탭-선택상품 목록 조회 */
    List<DefaultMap<String>> getMenuClassList(SoldOutVO soldOutVO, SessionInfoVO sessionInfoVO);

    /** 사이드메뉴-선택메뉴탭-선택상품 목록 조회 */
    List<DefaultMap<String>> getMenuProdList(SoldOutVO soldOutVO, SessionInfoVO sessionInfoVO);

    // 사이드메뉴 품절여부 저장
    int getSideMenuSoldOutSave(SoldOutVO[] soldOutVOs, SessionInfoVO sessionInfoVO);

}
