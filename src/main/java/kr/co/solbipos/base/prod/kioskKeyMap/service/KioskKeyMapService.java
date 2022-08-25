package kr.co.solbipos.base.prod.kioskKeyMap.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : KioskKeyMapService.java
 * @Description : 기초관리 - 상품관리 - 키오스크키맵관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.09.03  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 이다솜
 * @since 2020. 09.03
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface KioskKeyMapService {

    /** 키오스크용 포스 조회 */
    List<DefaultMap<String>> getKioskPosList(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 키맵그룹 조회 */
    List<DefaultMap<String>> getKioskTuClsTypeList(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 카테고리(분류) 조회 */
    List<DefaultMap<Object>> getKioskCategory(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 카테고리(분류) 저장 */
    int saveKioskCategory(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO);

    /** 키오스크 키맵 조회 */
    List<DefaultMap<Object>> getKioskKeyMap(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 키맵 수정 */
    int updateKioskKeyMap(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO);

    /** 키오스크 상품 조회 */
    List<DefaultMap<String>> getKioskProdList(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 키맵 등록 */
    int saveKioskKeyMap(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO);

    /** 키오스크 키맵 신규그룹추가 */
    int createKioskTuClsType(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 키맵 그룹복제(신규생성) */
    int copyKioskTuClsType(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 키맵 그룹복제(delete insert) */
    int copyStoreKioskTuClsType(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 키맵매장적용 - 매장리스트 조회 */
    List<DefaultMap<String>> getStoreList(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 키맵매장적용 */
    int saveKioskKeyMapStore(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO);

    /** 키오스크 매장적용(매장/포장) - 매장 키오스크 포스 리스트 조회 */
    List<DefaultMap<String>> getStoreKioskPosList(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 매장적용(매장/포장) - 본사/매장 환경설정값 저장 */
    int saveHqStoreKioskPosEnv(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO);

    /** 키오스크 매장적용(매장/포장) - 키오스크 환경설정 값 가져오기 */
    String getKioskEnv(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 추천메뉴 - 추천메뉴코드 가져오기 */
    List<DefaultMap<Object>> getRecmd(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 추천메뉴 - 추천메뉴코드 저장 */
    int saveRecmd(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO);

    /** 키오스크 추천메뉴 - 추천상품 조회 */
    List<DefaultMap<Object>> getRecmdProd(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 추천메뉴 - 추천상품으로 등록할 상품 조회 */
    List<DefaultMap<Object>> getRecmdProdList(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 추천메뉴 - 추천메뉴 저장(하위 왼쪽 그리드) */
    int saveRecmdProd(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO);

    /** 키오스크 추천메뉴 - 추천메뉴 기존메뉴 삭제 */
    int deleteRecmdProd(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 추천메뉴 - 추천메뉴 저장(하위 오른쪽 그리드) */
    int addRecmdProd(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO);

    /** 키오스크 추천메뉴 매장적용 */
    int saveRecmdStore(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO);

    /** 키오스크 카테고리(중분류) 조회 */
    List<DefaultMap<Object>> getKioskCategoryM(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO);

    /** 키오스크 카테고리(중분류) 저장 */
    int saveKioskCategoryM(KioskKeyMapVO[] kioskKeyMapVOs, SessionInfoVO sessionInfoVO);

    /** 키맵그룹에 KIOSK중분류사용 조회 */
    List<DefaultMap<Object>> getKioskKeyMapGroupTuMClsFg(KioskKeyMapVO kioskKeyMapVO, SessionInfoVO sessionInfoVO);
}
