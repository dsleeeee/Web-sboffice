package kr.co.solbipos.base.store.storeType.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.store.storeType.service.StoreTypeVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StoreTypeMapper.java
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
@Mapper
@Repository
public interface StoreTypeMapper {

    /** 매장타입관리 - 매장타입조회 */
    List<DefaultMap<Object>> getStoreType(StoreTypeVO storeTypeVO);

    /** 매장타입관리 - 매장타입코드 생성 */
    String getStoreTypeCode(StoreTypeVO storeTypeVO);

    /** 매장타입관리 - 매장타입생성 */
    int insertStoreType(StoreTypeVO storeTypeVO);

    /** 매장타입관리 - 매장타입수정 */
    int updateStoreType(StoreTypeVO storeTypeVO);

    /** 매장타입관리 - 매장연결조회 */
    List<DefaultMap<Object>> getStoreMapping(StoreTypeVO storeTypeVO);

    /** 매장타입관리 - 매장연결삭제 */
    int deleteStoreMapping(StoreTypeVO storeTypeVO);

    /** 매장타입관리 - 매장조회 */
    List<DefaultMap<Object>> getStoreList(StoreTypeVO storeTypeVO);

    /** 매장타입관리 - 매장중복체크 */
    int getStoreMappingYn(StoreTypeVO storeTypeVO);

    /** 매장타입관리 - 매장연결등록 */
    int saveStoreMapping(StoreTypeVO storeTypeVO);

    /** 매장타입관리 - 메뉴그룹연결조회 */
    List<DefaultMap<Object>> getMenuGroupMapping(StoreTypeVO storeTypeVO);

    /** 매장타입관리 - 메뉴그룹연결삭제 */
    int deleteMenuGroupMapping(StoreTypeVO storeTypeVO);

    /** 매장타입관리 - 메뉴그룹조회 */
    List<DefaultMap<Object>> getMenuGroupList(StoreTypeVO storeTypeVO);

    /** 매장타입관리 - 메뉴그룹연결등록 */
    int saveMenuGroupMapping(StoreTypeVO storeTypeVO);

    /** 메뉴그룹관리 - 메뉴그룹조회 */
    List<DefaultMap<Object>> getMenuGroup(StoreTypeVO storeTypeVO);

    /** 메뉴그룹관리 - 메뉴그룹코드 생성 */
    String getMenuGroupCode(StoreTypeVO storeTypeVO);

    /** 메뉴그룹관리 - 메뉴그룹생성 */
    int insertMenuGroup(StoreTypeVO storeTypeVO);

    /** 메뉴그룹관리 - 메뉴그룹수정 */
    int updateMenuGroup(StoreTypeVO storeTypeVO);

    /** 메뉴그룹관리 - 상품연결조회 */
    List<DefaultMap<Object>> getProdMapping(StoreTypeVO storeTypeVO);

    /** 메뉴그룹관리 - 상품연결삭제 */
    int deleteProdMapping(StoreTypeVO storeTypeVO);

    /** 메뉴그룹관리 - 상품연결수정 */
    int updateProdMapping(StoreTypeVO storeTypeVO);

    /** 메뉴그룹관리 - 상품조회 */
    List<DefaultMap<Object>> getProdList(StoreTypeVO storeTypeVO);

    /** 메뉴그룹관리 - 상품연결등록 */
    int saveProdMapping(StoreTypeVO storeTypeVO);

    /** 메뉴그룹관리 - 브랜드조회(콤보박스용) */
    List<DefaultMap<Object>> getBrandList(StoreTypeVO storeTypeVO);

    /** 매장타입관리 - 매장타입 매장적용 팝업 매장리스트 조회 */
    List<DefaultMap<Object>> getStoreTypeApplyStoreList(StoreTypeVO storeTypeVO);

    /** 매장타입관리 - 매장타입조회(콤보박스용) */
    List<DefaultMap<Object>> getStoreTypeCombo(StoreTypeVO storeTypeVO);

    /** 매장타입관리 - 매장타입 매장적용 팝업 매장적용 */
    int saveStoreTypeApplyStore(StoreTypeVO storeTypeVO);

    /** 매장타입관리 - 메뉴그룹연결 IUD 발생시 매장타입적용 관리에 기록 */
    int saveStoreTypeApplyStoreMenuGroup(StoreTypeVO storeTypeVO);

    /** 매장타입관리 - 상품연결 IUD 발생시 매장타입적용 관리에 기록 */
    int saveStoreTypeApplyStoreProd(StoreTypeVO storeTypeVO);

    /** 매장타입관리 - 메뉴그룹조회(콤보박스용) */
    List<DefaultMap<Object>> getStoreGroupCombo(StoreTypeVO storeTypeVO);
}
