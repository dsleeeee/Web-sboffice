package kr.co.solbipos.base.multilingual.storeKioskSideOption.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.multilingual.kioskSideOption.service.KioskSideOptionVO;

import java.util.List;

/**
 * @Class Name : StoreKioskSideOptionService.java
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
public interface StoreKioskSideOptionService {

    /** 키오스크(카테고리명) 탭 리스트 조회(매장) */
    List<DefaultMap<String>> getStoreKioskCategoryList(StoreKioskSideOptionVO storeKioskSideOptionVO, SessionInfoVO sessionInfoVO);

    /** 키오스크(카테고리) 영문, 중문, 일문 저장(매장) */
    int saveStoreKioskCategory(StoreKioskSideOptionVO[] storeKioskSideOptionVOs, SessionInfoVO sessionInfoVO);

    /** 키오스크 포스 조회(중분류 사용 포스만 조회) */
    List<DefaultMap<String>> getKioskPosComboList(StoreKioskSideOptionVO storeKioskSideOptionVO, SessionInfoVO sessionInfoVO);

    /** 키오스크중분류(카테고리명) 카테고리(대분류) 콤보박스 조회(매장) */
    List<DefaultMap<String>> getStoreKioskCategoryComboList(StoreKioskSideOptionVO storeKioskSideOptionVO, SessionInfoVO sessionInfoVO);

    /** 키오스크중분류(카테고리명) 탭 리스트 조회(매장) */
    List<DefaultMap<String>> getStoreKioskMClsList(StoreKioskSideOptionVO storeKioskSideOptionVO, SessionInfoVO sessionInfoVO);

    /** 키오스크중분류(카테고리명) 영문, 중문, 일문 저장(매장) */
    int saveStoreKioskMCls(StoreKioskSideOptionVO[] storeKioskSideOptionVOs, SessionInfoVO sessionInfoVO);

    /** 사이드(선택그룹명) 탭 리스트 조회(매장) */
    List<DefaultMap<String>> getStoreSideSdselGrpList(StoreKioskSideOptionVO storeKioskSideOptionVO, SessionInfoVO sessionInfoVO);

    /** 사이드(선택그룹명) 영문, 중문, 일문 저장(매장) */
    int saveStoreSideSdselGrp(StoreKioskSideOptionVO[] storeKioskSideOptionVOs, SessionInfoVO sessionInfoVO);

    /** 사이드(선택분류명) 탭 리스트 조회(매장) */
    List<DefaultMap<String>> getStoreSideSdselClassList(StoreKioskSideOptionVO storeKioskSideOptionVO, SessionInfoVO sessionInfoVO);

    /** 사이드(선택분류명) 영문, 중문, 일문 저장(매장) */
    int saveStoreSideSdselClass(StoreKioskSideOptionVO[] storeKioskSideOptionVOs, SessionInfoVO sessionInfoVO);

    /** 옵션(그룹명) 탭 리스트 조회(매장) */
    List<DefaultMap<String>> getStoreOptionGrpList(StoreKioskSideOptionVO StoreKioskSideOptionVO, SessionInfoVO sessionInfoVO);

    /** 옵션(그룹명) 영문, 중문, 일문 저장(매장) */
    int saveStoreOptionGrp(StoreKioskSideOptionVO[] storeKioskSideOptionVOs, SessionInfoVO sessionInfoVO);

    /** 옵션(옵션명) 탭 리스트 조회(매장) */
    List<DefaultMap<String>> getStoreOptionValList(StoreKioskSideOptionVO StoreKioskSideOptionVO, SessionInfoVO sessionInfoVO);

    /** 옵션(옵션명) 영문, 중문, 일문 저장(매장) */
    int saveStoreOptionVal(StoreKioskSideOptionVO[] storeKioskSideOptionVOs, SessionInfoVO sessionInfoVO);
}
