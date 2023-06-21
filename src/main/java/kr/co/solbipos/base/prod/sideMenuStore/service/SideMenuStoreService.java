package kr.co.solbipos.base.prod.sideMenuStore.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : SideMenuStoreService.java
 * @Description : 기초관리 > 상품관리2 > 매장별사이드관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.06.07  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.06.07
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SideMenuStoreService {

    /** 선택분류(매장별) 탭 - 조회 */
    List<DefaultMap<Object>> getSideMenuClassStoreList(SideMenuStoreVO sideMenuStoreVO, SessionInfoVO sessionInfoVO);

    /** 선택분류(매장별) 탭 - 저장 */
    int getSideMenuClassStoreSave(SideMenuStoreVO[] sideMenuStoreVOs, SessionInfoVO sessionInfoVO);

    /** 선택분류(선택분류별) 탭 - 조회 */
    List<DefaultMap<Object>> getSideMenuClassList(SideMenuStoreVO sideMenuStoreVO, SessionInfoVO sessionInfoVO);

    /** 선택상품(매장별) 탭 - 조회 */
    List<DefaultMap<Object>> getSideMenuProdStoreList(SideMenuStoreVO sideMenuStoreVO, SessionInfoVO sessionInfoVO);

    /** 선택상품(매장별) 탭 - 저장 */
    int getSideMenuProdStoreSave(SideMenuStoreVO[] sideMenuStoreVOs, SessionInfoVO sessionInfoVO);

    /** 선택상품(선택상품별) 탭 - 조회 */
    List<DefaultMap<Object>> getSideMenuProdList(SideMenuStoreVO sideMenuStoreVO, SessionInfoVO sessionInfoVO);

    /** 선택분류(적용매장) 탭 - 조회 */
    List<DefaultMap<Object>> getSideMenuClassRegStoreList(SideMenuStoreVO sideMenuStoreVO, SessionInfoVO sessionInfoVO);

    /** 선택분류(적용매장) 탭 - 저장 */
    int getSideMenuClassRegStoreSave(SideMenuStoreVO[] sideMenuStoreVOs, SessionInfoVO sessionInfoVO);

    /** 선택분류(적용매장) 탭 - 선택분류 적용매장 전체 삭제 */
    int getSideMenuClassRegStoreDeleteAll(SideMenuStoreVO[] sideMenuStoreVOs, SessionInfoVO sessionInfoVO);
}