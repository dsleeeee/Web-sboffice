package kr.co.solbipos.base.prod.sideMenuStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.sideMenuStore.service.SideMenuStoreVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SideMenuStoreMapper.java
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
@Mapper
@Repository
public interface SideMenuStoreMapper {

    /** 선택분류(매장별) 탭 - 조회 */
    List<DefaultMap<Object>> getSideMenuClassStoreList(SideMenuStoreVO sideMenuStoreVO);

    /** 선택분류(매장별) 탭 - 저장 merge */
    int getSideMenuClassStoreSaveMerge(SideMenuStoreVO sideMenuStoreVO);

    /** 선택분류(매장별) 탭 - 저장 delete */
    int getSideMenuClassStoreSaveDelete(SideMenuStoreVO sideMenuStoreVO);

    /** 선택분류(선택분류별) 탭 - 조회 */
    List<DefaultMap<Object>> getSideMenuClassList(SideMenuStoreVO sideMenuStoreVO);

    /** 선택상품(매장별) 탭 - 조회 */
    List<DefaultMap<Object>> getSideMenuProdStoreList(SideMenuStoreVO sideMenuStoreVO);

    /** 선택상품(매장별) 탭 - 저장 merge */
    int getSideMenuProdStoreSaveMerge(SideMenuStoreVO sideMenuStoreVO);

    /** 선택상품(매장별) 탭 - 저장 delete */
    int getSideMenuProdStoreSaveDelete(SideMenuStoreVO sideMenuStoreVO);

    /** 선택상품(선택상품별) 탭 - 조회 */
    List<DefaultMap<Object>> getSideMenuProdList(SideMenuStoreVO sideMenuStoreVO);

    /** 선택분류(적용매장) 탭 - 조회 */
    List<DefaultMap<Object>> getSideMenuClassRegStoreList(SideMenuStoreVO sideMenuStoreVO);

    /** 선택분류(적용매장) 탭 - 저장 update */
    int getSideMenuClassRegStoreSaveUpdate(SideMenuStoreVO sideMenuStoreVO);

    /** 선택분류(적용매장) 탭 - 선택분류 적용매장 전체 삭제 */
    int getSideMenuClassRegStoreDeleteAll(SideMenuStoreVO sideMenuStoreVO);

    /** 선택상품(적용매장) 탭 - 조회 */
    List<DefaultMap<Object>> getSideMenuProdRegStoreList(SideMenuStoreVO sideMenuStoreVO);

    /** 선택상품(적용매장) 탭 - 저장 update */
    int getSideMenuProdRegStoreSaveUpdate(SideMenuStoreVO sideMenuStoreVO);

    /** 선택상품(적용매장) 탭 - 선택상품 적용매장 전체 삭제 */
    int getSideMenuProdRegStoreDeleteAll(SideMenuStoreVO sideMenuStoreVO);
}