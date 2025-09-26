package kr.co.solbipos.base.multilingual.storeKioskSideOption.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.multilingual.kioskSideOption.service.KioskSideOptionVO;
import kr.co.solbipos.base.multilingual.storeKioskSideOption.service.StoreKioskSideOptionVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StoreKioskSideOptionMapper.java
 * @Description : 기초관리 - 다국어관리 - 다국어관리(키오스크/사이드/옵션)(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.19  이다솜       최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.09.19
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface StoreKioskSideOptionMapper {

    /** 키오스크(카테고리명) 탭 리스트 조회(매장) */
    List<DefaultMap<String>> getStoreKioskCategoryList(StoreKioskSideOptionVO storeKioskSideOptionVO);

    /** 키오스크(카테고리명) 영문, 중문, 일문 저장(매장) */
    int saveStoreKioskCategory(StoreKioskSideOptionVO storeKioskSideOptionVO);

    /** 키오스크 포스 조회(중분류 사용 포스만 조회) */
    List<DefaultMap<String>> getKioskPosComboList(StoreKioskSideOptionVO storeKioskSideOptionVO);

    /** 키오스크중분류(카테고리명) 카테고리(대분류) 콤보박스 조회(매장) */
    List<DefaultMap<String>> getStoreKioskCategoryComboList(StoreKioskSideOptionVO storeKioskSideOptionVO);

    /** 키오스크중분류(카테고리명) 탭 리스트 조회(매장) */
    List<DefaultMap<String>> getStoreKioskMClsList(StoreKioskSideOptionVO storeKioskSideOptionVO);

    /** 키오스크중분류(카테고리명) 영문, 중문, 일문 저장(매장) */
    int saveStoreKioskMCls(StoreKioskSideOptionVO storeKioskSideOptionVO);

    /** 사이드(선택그룹명) 탭 리스트 조회(매장) */
    List<DefaultMap<String>> getStoreSideSdselGrpList(StoreKioskSideOptionVO storeKioskSideOptionVO);

    /** 사이드(선택그룹명) 영문, 중문, 일문 저장(매장) */
    int saveStoreSideSdselGrp(StoreKioskSideOptionVO storeKioskSideOptionVO);

    /** 사이드(선택분류명) 탭 리스트 조회(매장) */
    List<DefaultMap<String>> getStoreSideSdselClassList(StoreKioskSideOptionVO storeKioskSideOptionVO);

    /** 사이드(선택분류명) 영문, 중문, 일문 저장(매장) */
    int saveStoreSideSdselClass(StoreKioskSideOptionVO storeKioskSideOptionVO);

    /** 옵션(그룹명) 탭 리스트 조회(매장) */
    List<DefaultMap<String>> getStoreOptionGrpList(StoreKioskSideOptionVO storeKioskSideOptionVO);

    /** 옵션(그룹명) 영문, 중문, 일문 저장(매장) */
    int saveStoreOptionGrp(StoreKioskSideOptionVO storeKioskSideOptionVO);

    /** 옵션(옵션명) 탭 리스트 조회(매장) */
    List<DefaultMap<String>> getStoreOptionValList(StoreKioskSideOptionVO storeKioskSideOptionVO);

    /** 옵션(옵션명) 영문, 중문, 일문 저장(매장) */
    int saveStoreOptionVal(StoreKioskSideOptionVO storeKioskSideOptionVO);
}
