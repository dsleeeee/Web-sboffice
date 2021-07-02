package kr.co.solbipos.base.store.storeType.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : StoreTypeService.java
 * @Description : 기초관리 - 매장관리 - 매장타입관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.28  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021.06.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StoreTypeService {

    /** 매장타입관리 - 매장타입조회 */
    List<DefaultMap<Object>> getStoreType(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO);

    /** 매장타입관리 - 매장타입저장 */
    int saveStoreType(StoreTypeVO[] storeTypeVOs, SessionInfoVO sessionInfoVO);

    /** 매장타입관리 - 매장연결조회 */
    List<DefaultMap<Object>> getStoreMapping(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO);

    /** 매장타입관리 - 매장연결삭제 */
    int deleteStoreMapping(StoreTypeVO[] storeTypeVOs, SessionInfoVO sessionInfoVO);

    /** 매장타입관리 - 매장조회 */
    List<DefaultMap<Object>> getStoreList(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO);

    /** 매장타입관리 - 매장연결등록 */
    int saveStoreMapping(StoreTypeVO[] storeTypeVOs, SessionInfoVO sessionInfoVO);

    /** 매장타입관리 - 메뉴그룹연결조회 */
    List<DefaultMap<Object>> getMenuGroupMapping(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO);

    /** 매장타입관리 - 메뉴그룹연결삭제 */
    int deleteMenuGroupMapping(StoreTypeVO[] storeTypeVOs, SessionInfoVO sessionInfoVO);

    /** 매장타입관리 - 메뉴그룹조회 */
    List<DefaultMap<Object>> getMenuGroupList(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO);

    /** 매장타입관리 - 메뉴그룹연결등록 */
    int saveMenuGroupMapping(StoreTypeVO[] storeTypeVOs, SessionInfoVO sessionInfoVO);

    /** 메뉴그룹관리 - 메뉴그룹조회 */
    List<DefaultMap<Object>> getMenuGroup(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO);

    /** 메뉴그룹관리 - 메뉴그룹저장 */
    int saveMenuGroup(StoreTypeVO[] storeTypeVOs, SessionInfoVO sessionInfoVO);

    /** 메뉴그룹관리 - 상품연결조회 */
    List<DefaultMap<Object>> getProdMapping(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO);

    /** 메뉴그룹관리 - 상품조회 */
    List<DefaultMap<Object>> getProdList(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO);

    /** 메뉴그룹관리 - 상품연결저장 */
    int saveProdMapping(StoreTypeVO[] storeTypeVOs, SessionInfoVO sessionInfoVO);

    /** 메뉴그룹관리 - 브랜드리스트(콤보박스용) */
    List<DefaultMap<Object>> getBrandList(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO);
}
