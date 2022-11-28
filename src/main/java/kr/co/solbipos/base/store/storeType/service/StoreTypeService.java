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

    /** 메뉴그룹관리 - 브랜드조회(콤보박스용) */
    List<DefaultMap<Object>> getBrandList(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO);

    /** 매장타입관리 - 매장타입 매장적용 팝업 매장리스트 조회 */
    List<DefaultMap<Object>> getStoreTypeApplyStoreList(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO);

    /** 매장타입관리 - 매장타입조회(콤보박스용) */
    List<DefaultMap<Object>> getStoreTypeCombo(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO);

    /** 매장타입관리 - 매장타입 매장적용 팝업 매장적용(매장타입적용관리 테이블에 등록) */
    int saveStoreTypeApplyStore(StoreTypeVO[] storeTypeVOs, SessionInfoVO sessionInfoVO);

    /** 매장타입관리 - 매장타입적용관리 테이블 조회하여 본사상품 > 매장등록 PKG 호출 (스케쥴러에서 사용, PKG_HQ_STORE_TYPE_APP_ALL -> PKG_HQ_STORE_TYPE_APP 호출) */
    String insertHqProductToStoreAll(StoreTypeVO storeTypeVO);

    /** 매장타입관리 - 메뉴그룹조회(콤보박스용) */
    List<DefaultMap<Object>> getStoreGroupCombo(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO);

    /** 매장타입관리 - 매장타입변경이력조회 */
    List<DefaultMap<Object>> getStoreTypeChgHist(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO);

    /** 매장타입관리 - 메뉴그룹변경이력조회 */
    List<DefaultMap<Object>> getMenuGroupChgHist(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO);

    /** 매장적용이력 탭 - 조회 */
    List<DefaultMap<Object>> getStoreApplyChgHistList(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO);

    /** 매장적용이력 탭 - 취소 */
    int getStoreApplyChgHistSaveUpdate(StoreTypeVO[] storeTypeVOs, SessionInfoVO sessionInfoVO);

    /** 매장적용이력 상세 팝업 - 조회 */
    List<DefaultMap<Object>> getStoreApplyChgHistDtlList(StoreTypeVO storeTypeVO, SessionInfoVO sessionInfoVO);
}
