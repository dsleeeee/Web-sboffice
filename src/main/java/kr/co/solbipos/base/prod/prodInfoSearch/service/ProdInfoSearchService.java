package kr.co.solbipos.base.prod.prodInfoSearch.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : ProdInfoSearchService.java
 * @Description : 기초관리 > 상품관리 > 상품정보조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.23  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.12.23
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface ProdInfoSearchService {

    /** 상품분류 조회 */
    List<DefaultMap<String>> getProdClassList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO);

    /** 상품분류 엑셀 조회 */
    List<DefaultMap<String>> getProdClassExcelList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO);

    /** 사이드-속성 조회 */
    List<DefaultMap<String>> getSideAttrList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO);

    /** 사이드-속성 엑셀 조회 */
    List<DefaultMap<String>> getSideAttrExcelList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO);

    /** 사이드-선택메뉴 조회 */
    List<DefaultMap<String>> getSideMenuList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO);

    /** 사이드-선택메뉴 엑셀 조회 */
    List<DefaultMap<String>> getSideMenuExcelList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO);

    /** 옵션 조회 */
    List<DefaultMap<String>> getOptionList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO);

    /** 옵션 엑셀 조회 */
    List<DefaultMap<String>> getOptionExcelList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO);

    /** 상품-속성/선택메뉴/옵션 조회 */
    List<DefaultMap<String>> getProdInfoList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO);

    /** 상품-속성/선택메뉴/옵션 엑셀 조회 */
    List<DefaultMap<String>> getProdInfoExcelList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO);

    /** 원산지 조회 */
    List<DefaultMap<String>> getOrgplceList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO);

    /** 원산지 엑셀 조회 */
    List<DefaultMap<String>> getOrgplceExcelList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO);

    /** 알레르기 조회 */
    List<DefaultMap<String>> getAllergyList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO);

    /** 알레르기 엑셀 조회 */
    List<DefaultMap<String>> getAllergyExcelList(ProdInfoSearchVO prodInfoSearchVO, SessionInfoVO sessionInfoVO);

}