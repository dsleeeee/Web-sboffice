package kr.co.solbipos.base.prod.sidemenu.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : SideMenuService.java
 * @Description : 기초관리 > 상품관리 > 사이드메뉴
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.14  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @See
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SideMenuService {

    /** 사이드메뉴-속성탭-속성분류 목록 조회 */
    List<DefaultMap<String>> getAttrClassList(SideMenuAttrClassVO sideMenuAttrClassVO, SessionInfoVO sessionInfoVO);

    /** 사이드메뉴-속성탭-속성분류 저장 */
    int saveAttrClassList(SideMenuAttrClassVO[] sideMenuAttrClassVOs, SessionInfoVO sessionInfoVO);

    /** 사이드메뉴-속성탭-속성 목록 조회 */
    List<DefaultMap<String>> getAttrCdList(SideMenuAttrCdVO sideMenuAttrCdVO, SessionInfoVO sessionInfoVO);

    /** 사이드메뉴-속성탭-속성 저장 */
    int saveAttrCdList(SideMenuAttrCdVO[] sideMenuAttrCdVOS, SessionInfoVO sessionInfoVO);

    /** 사이드메뉴-선택메뉴탭-선택그룹 목록 조회 */
    List<DefaultMap<String>> getMenuGrpList(SideMenuSelGroupVO sideMenuSelGroupVO, SessionInfoVO sessionInfoVO);

    /** 사이드메뉴-선택메뉴탭-선택그룹 저장 */
    int saveMenuGrpList(SideMenuSelGroupVO[] sideMenuSelGroupVOs, SessionInfoVO sessionInfoVO);

    /** 사이드메뉴-선택메뉴탭-선택분류 목록 조회 */
    List<DefaultMap<String>> getMenuClassList(SideMenuSelClassVO sideMenuSelClassVO, SessionInfoVO sessionInfoVO);

    /** 사이드메뉴-선택메뉴탭-선택분류 저장 */
    int saveMenuClassList(SideMenuSelClassVO[] sideMenuSelClassVOs, SessionInfoVO sessionInfoVO);

    /** 사이드메뉴-선택메뉴 - 선택할 상품 목록 조회 */
    List<DefaultMap<String>> getProdList(SideMenuSelProdVO sideMenuSelProdVO, SessionInfoVO sessionInfoVO);

    /** 사이드메뉴-선택메뉴탭-선택상품 목록 조회 */
    List<DefaultMap<String>> getMenuProdList(SideMenuSelProdVO sideMenuSelProdVO, SessionInfoVO sessionInfoVO);

    /** 사이드메뉴-선택메뉴탭-선택상품 저장 */
    int saveMenuProdList(SideMenuSelProdVO[] sideMenuSelProdVOs, SessionInfoVO sessionInfoVO);

    /** 사이드메뉴-사이드메뉴관리탭 상품 목록 조회 */
    List<DefaultMap<Object>> getSideMenuManageProdList(SideMenuManageVO sideMenuManageVO, SessionInfoVO sessionInfoVO);

    /** 사이드메뉴-사이드메뉴관리탭 상품정보일괄변경 저장(사이드메뉴여부, 속성, 선택메뉴) */
    int saveSideMenuManageProdBatch(SideMenuManageVO[] sideMenuManageVOs, SessionInfoVO sessionInfoVO);

    /** 사이드메뉴-사이드메뉴관리탭 속성 콤보박스 */
    List<DefaultMap<String>> getSideMenuAttrClassCombo(SideMenuManageVO sideMenuManageVO, SessionInfoVO sessionInfoVO);

    /** 사이드메뉴-사이드메뉴관리탭 선택메뉴 콤보박스 */
    List<DefaultMap<String>> getSideMenuSdselGrpCdCombo(SideMenuManageVO sideMenuManageVO, SessionInfoVO sessionInfoVO);

}
