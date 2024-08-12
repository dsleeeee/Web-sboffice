package kr.co.solbipos.base.prod.basicSideMenu.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : BasicSideMenuService.java
 * @Description : 기초관리 > 상품관리 > (기준)사이드메뉴
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.08.07  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2024.08.07
 * @version 1.0
 * @See
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface BasicSideMenuService {

    /** (기준)사이드메뉴 - 선택메뉴 - 선택그룹 목록 조회 */
    List<DefaultMap<String>> getMenuGrpList(BasicSideMenuSelGroupVO basicSideMenuSelGroupVO, SessionInfoVO sessionInfoVO);

    /** (기준)사이드메뉴 - 선택메뉴 - 선택그룹 저장 */
    int saveMenuGrpList(BasicSideMenuSelGroupVO[] basicSideMenuSelGroupVOs, SessionInfoVO sessionInfoVO);

    /** (기준)사이드메뉴 - 선택메뉴 - 선택분류 목록 조회 */
    List<DefaultMap<String>> getMenuClassList(BasicSideMenuSelClassVO basicSideMenuSelClassVO, SessionInfoVO sessionInfoVO);

    /** (기준)사이드메뉴 - 선택메뉴 - 선택분류 저장 */
    int saveMenuClassList(BasicSideMenuSelClassVO[] basicSideMenuSelClassVOs, SessionInfoVO sessionInfoVO);

    /** (기준)사이드메뉴 - 선택메뉴 - 선택상품 추가팝업 상품리스트 조회 */
    List<DefaultMap<String>> getProdList(BasicSideMenuSelProdVO basicSideMenuSelProdVO, SessionInfoVO sessionInfoVO);

    /** (기준)사이드메뉴 - 선택메뉴 - 선택상품 목록 조회 */
    List<DefaultMap<String>> getMenuProdList(BasicSideMenuSelProdVO basicSideMenuSelProdVO, SessionInfoVO sessionInfoVO);

    /** (기준)사이드메뉴 - 선택메뉴 - 선택상품 저장 */
    int saveMenuProdList(BasicSideMenuSelProdVO[] basicSideMenuSelProdVOs, SessionInfoVO sessionInfoVO);





}
