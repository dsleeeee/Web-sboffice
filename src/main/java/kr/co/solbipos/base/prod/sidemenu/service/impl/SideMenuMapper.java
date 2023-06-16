package kr.co.solbipos.base.prod.sidemenu.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.sidemenu.service.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SideMenuMapper.java
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
@Mapper
@Repository
public interface SideMenuMapper {

    /** 사이드메뉴-속성탭-속성분류 목록 조회 */
    List<DefaultMap<String>> getAttrClassList(SideMenuAttrClassVO sideMenuAttrClassVO);

    /** 사이드메뉴-속성탭-속성분류 분류코드 생성 */
    String getAttrClassCode(SideMenuAttrClassVO sideMenuAttrClassVO);

    /** 사이드메뉴-속성탭-속성분류 생성 */
    int insertAttrClassList(SideMenuAttrClassVO sideMenuAttrClassVO);

    /** 사이드메뉴-속성탭-속성분류 수정 */
    int updateAttrClassList(SideMenuAttrClassVO sideMenuAttrClassVO);

    /** 사이드메뉴-속성탭-속성분류 삭제 */
    int deleteAttrClassList(SideMenuAttrClassVO sideMenuAttrClassVO);

    /** 사이드메뉴-속성탭-속성분류 생성시 매장 적용 */
    String insertHqAttrClassListToStore(SideMenuAttrClassVO sideMenuAttrClassVO);

    /** 사이드메뉴-속성탭-속성분류 수정시 매장 적용 */
    String updateHqAttrClassListToStore(SideMenuAttrClassVO sideMenuAttrClassVO);

    /** 사이드메뉴-속성탭-속성분류 삭제시 매장 적용 */
    String deleteHqAttrClassListToStore(SideMenuAttrClassVO sideMenuAttrClassVO);

    /** 사이드메뉴-속성탭-속성 목록 조회 */
    List<DefaultMap<String>> getAttrCdList(SideMenuAttrCdVO sideMenuAttrCdVO);

    /** 사이드메뉴-속성탭-속성 속성코드 생성 */
    String getAttrCode(SideMenuAttrCdVO sideMenuAttrCdVO);

    /** 사이드메뉴-속성탭-속성 생성 */
    int insertAttrCdList(SideMenuAttrCdVO sideMenuAttrCdVO);

    /** 사이드메뉴-속성탭-속성 수정 */
    int updateAttrCdList(SideMenuAttrCdVO sideMenuAttrCdVO);

    /** 사이드메뉴-속성탭-속성 삭제 */
    int deleteAttrCdList(SideMenuAttrCdVO sideMenuAttrCdVO);

    /** 사이드메뉴-속성탭-속성 생성시 매장적용 */
    String insertHqAttrCdListToStore(SideMenuAttrCdVO sideMenuAttrCdVO);

    /** 사이드메뉴-속성탭-속성 수정시 매장적용 */
    String updateHqAttrCdListToStore(SideMenuAttrCdVO sideMenuAttrCdVO);

    /** 사이드메뉴-속성탭-속성 삭제시 매장적용 */
    String deleteHqAttrCdListToStore(SideMenuAttrCdVO sideMenuAttrCdVO);

    /** 사이드메뉴-선택메뉴탭-선택그룹 목록 조회 */
    List<DefaultMap<String>> getMenuGrpList(SideMenuSelGroupVO sideMenuSelGroupVO);

    /** 사이드메뉴-선택메뉴탭-선택그룹 그룹코드 생성 */
    String getMenuGrpCode(SideMenuSelGroupVO sideMenuSelGroupVO);

    /** 사이드메뉴-선택메뉴탭-선택그룹 생성 */
    int insertMenuGrpList(SideMenuSelGroupVO sideMenuSelGroupVO);

    /** 사이드메뉴-선택메뉴탭-선택그룹 수정 */
    int updateMenuGrpList(SideMenuSelGroupVO sideMenuSelGroupVO);

    /** 사이드메뉴-선택메뉴탭-선택그룹 삭제 */
    int deleteMenuGrpList(SideMenuSelGroupVO sideMenuSelGroupVO);

    /** 사이드메뉴-선택메뉴탭-선택그룹 생성시 매장적용 */
    String insertHqMenuGrpListToStore(SideMenuSelGroupVO sideMenuSelGroupVO);

    /** 사이드메뉴-선택메뉴탭-선택그룹 수정시 매장적용 */
    String updateHqMenuGrpListToStore(SideMenuSelGroupVO sideMenuSelGroupVO);

    /** 사이드메뉴-선택메뉴탭-선택그룹 삭제시 매장적용 */
    String deleteHqMenuGrpListToStore(SideMenuSelGroupVO sideMenuSelGroupVO);

    /** 사이드메뉴-선택메뉴탭-선택분류 목록 조회 */
    List<DefaultMap<String>> getMenuClassList(SideMenuSelClassVO sideMenuSelClassVO);

    /** 사이드메뉴-선택메뉴탭-선택분류 분류코드 생성 */
    String getMenuClassCode(SideMenuSelClassVO sideMenuSelClassVO);

    /** 사이드메뉴-선택메뉴탭-선택분류 생성 */
    int insertMenuClassList(SideMenuSelClassVO sideMenuSelClassVO);

    /** 사이드메뉴-선택메뉴탭-선택분류 수정 */
    int updateMenuClassList(SideMenuSelClassVO sideMenuSelClassVO);

    /** 사이드메뉴-선택메뉴탭-선택분류 삭제 */
    int deleteMenuClassList(SideMenuSelClassVO sideMenuSelClassVO);

    /** 사이드메뉴-선택메뉴탭-선택분류 생성시 매장적용 */
    String insertHqMenuClassListToStore(SideMenuSelClassVO sideMenuSelClassVO);

    /** 사이드메뉴-선택메뉴탭-선택분류 수정시 매장적용 */
    String saveHqMenuClassListToStore(SideMenuSelClassVO sideMenuSelClassVO);

    /** 사이드메뉴-선택메뉴탭-선택분류 삭제시 매장적용 */
    String deleteHqMenuClassListToStore(SideMenuSelClassVO sideMenuSelClassVO);

    /** 사이드메뉴-선택메뉴 - 선택할 상품 목록 조회 */
    List<DefaultMap<String>> getProdList(SideMenuSelProdVO sideMenuSelProdVO);

    /** 사이드메뉴-선택메뉴탭-선택상품 목록 조회 */
    List<DefaultMap<String>> getMenuProdList(SideMenuSelProdVO sideMenuSelProdVO);

    /** 사이드메뉴-선택메뉴탭-선택상품 생성 */
    int insertMenuProdList(SideMenuSelProdVO sideMenuSelProdVO);

    /** 사이드메뉴-선택메뉴탭-선택상품 수정 */
    int updateMenuProdList(SideMenuSelProdVO sideMenuSelProdVO);

    /** 사이드메뉴-선택메뉴탭-선택상품 삭제 */
    int deleteMenuProdList(SideMenuSelProdVO sideMenuSelProdVO);

    /** 사이드메뉴-선택메뉴탭-선택상품 생성시 매장적용 */
    String insertHqMenuProdListToStore(SideMenuSelProdVO sideMenuSelProdVO);

    /** 사이드메뉴-선택메뉴탭-선택상품 수정시 매장적용 */
    String saveHqMenuProdListToStore(SideMenuSelProdVO sideMenuSelProdVO);

    /** 사이드메뉴-선택메뉴탭-선택상품 삭제시 매장적용 */
    String deleteHqMenuProdListToStore(SideMenuSelProdVO sideMenuSelProdVO);



//    /** 사이드메뉴-선택메뉴탭- 선택상품 삭제시 선택분류에 해당하는 상품 개수 체크 */
//    int getSideMenuSelClassProdCnt(SideMenuSelClassVO sideMenuSelClassVO);
//
//    /** 사이드메뉴-선택메뉴탭- 선택분류 삭제시 선택그룹에 해당하는 분류 개수 체크 */
//    int getSideMenuSelGrpClassCnt(SideMenuSelGroupVO sideMenuSelGroupVO);

    /** 사이드메뉴-사이드메뉴관리탭 상품 목록 조회 */
    List<DefaultMap<Object>> getSideMenuManageProdList(SideMenuManageVO sideMenuManageVO);

    /** 사이드메뉴-사이드메뉴관리탭 상품정보일괄변경 저장(사이드메뉴여부, 속성, 선택메뉴) */
    int saveSideMenuManageProdBatch(SideMenuManageVO sideMenuManageVO);

    /** 사이드메뉴-사이드메뉴관리탭 상품정보일괄변경 저장 매장에 update(사이드메뉴여부, 속성, 선택메뉴) */
    int saveSideMenuManageProdBatchStoreUpdate(SideMenuManageVO sideMenuManageVO);

    /** 사이드메뉴-사이드메뉴관리탭 속성 콤보박스 */
    List<DefaultMap<String>> getSideMenuAttrClassCombo(SideMenuManageVO sideMenuManageVO);

    /** 사이드메뉴-사이드메뉴관리탭 선택메뉴 콤보박스 */
    List<DefaultMap<String>> getSideMenuSdselGrpCdCombo(SideMenuManageVO sideMenuManageVO);


    /** 선택분류 생성시 표기순번 자동채번 */
    String getDispSeqCode(SideMenuSelClassVO SideMenuSelClassVO);

    /** 사이드메뉴-선택메뉴 탭-선택분류복사 팝업 - 선택분류 복사 insert */
    int getSdselClassCopySaveInsertClass(SideMenuSelClassVO SideMenuSelClassVO);

    /** 사이드메뉴-선택메뉴 탭-선택분류복사 팝업 - 선택상품 복사 merge */
    int getSdselClassCopySaveMergeProd(SideMenuSelClassVO sideMenuSelClassVO);

    /** 사이드메뉴-선택메뉴 탭-선택분류복사 팝업 - 선택상품 매장에 내려줌 */
    int getSdselClassCopySaveMergeProdStore(SideMenuSelClassVO sideMenuSelClassVO);

    /** 선택분류 적용매장등록 팝업 - 선택분류 조회 */
    List<DefaultMap<Object>> getSdselClassCodeComboList(SideMenuSelClassVO sideMenuSelClassVO);

    /** 선택분류 적용매장등록 팝업 - 적용매장 조회 */
    List<DefaultMap<Object>> getSdselClassRegStoreList(SideMenuSelClassVO sideMenuSelClassVO);

    /** 선택분류 적용매장등록 팝업 - 미적용매장 조회 */
    List<DefaultMap<Object>> getSdselClassNoRegStoreList(SideMenuSelClassVO sideMenuSelClassVO);

    /** 선택분류 적용매장등록 팝업 - 저장 insert */
    int getSdselClassRegStoreSaveInsert(SideMenuSelClassVO sideMenuSelClassVO);

    /** 선택분류 적용매장등록 팝업 - 저장 delete */
    int getSdselClassRegStoreSaveDelete(SideMenuSelClassVO sideMenuSelClassVO);

    /** 선택상품 적용매장등록 팝업 - 선택상품 조회 */
    List<DefaultMap<Object>> getSdselProdCodeComboList(SideMenuSelProdVO sideMenuSelProdVO);

    /** 선택상품 적용매장등록 팝업 - 적용매장 조회 */
    List<DefaultMap<Object>> getSdselProdRegStoreList(SideMenuSelProdVO sideMenuSelProdVO);

    /** 선택상품 적용매장등록 팝업 - 미적용매장 조회 */
    List<DefaultMap<Object>> getSdselProdNoRegStoreList(SideMenuSelProdVO sideMenuSelProdVO);

    /** 선택상품 적용매장등록 팝업 - 저장 insert */
    int getSdselProdRegStoreSaveInsert(SideMenuSelProdVO sideMenuSelProdVO);

    /** 선택상품 적용매장등록 팝업 - 저장 delete */
    int getSdselProdRegStoreSaveDelete(SideMenuSelProdVO sideMenuSelProdVO);

    /** 선택분류 적용매장 전체 삭제 */
    int getSdselClassRegStoreDeleteAll(SideMenuSelClassVO sideMenuSelClassVO);

    /** 선택상품 적용매장 전체 삭제 */
    int getSdselProdRegStoreDeleteAll(SideMenuSelProdVO sideMenuSelProdVO);
}
