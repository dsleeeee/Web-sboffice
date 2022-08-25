package kr.co.solbipos.base.prod.kioskKeyMap.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : KioskKeyMapMapper.java
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
@Mapper
@Repository
public interface KioskKeyMapMapper {

    /** 키오스크용 포스 조회 */
    List<DefaultMap<String>> getKioskPosList(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 키맵그룹 조회 */
    List<DefaultMap<String>> getKioskTuClsTypeList(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 키맵그룹코드 생성 */
    String getKiosTuClsTypeCode(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 카테고리(분류) 조회 */
    List<DefaultMap<Object>> getKioskCategory(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 카테고리(분류) 저장 - 생성 시 카테고리 코드 생성 */
    String getKioskCategoryCode(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 카테고리(분류) 저장 - 생성 */
    int insertKioskCategory(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 카테고리(분류) 저장 - 수정 */
    int updateKioskCategory(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 카테고리(분류) 저장 - 삭제 */
    int deleteKioskCategory(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 카테고리(분류) 저장 - 카테고리 삭제 시, 카테고리에 속한 키맵도 삭제 */
    int deleteAllKioskKeyMap(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 키맵 조회 */
    List<DefaultMap<Object>> getKioskKeyMap(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 상품 조회 */
    List<DefaultMap<String>> getKioskProdList(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 키맵 수정 */
    int updateKioskKeyMap(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 키맵 삭제 */
    int deleteKioskKeyMap(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 키맵 관련 코드 조회 */
    DefaultMap<String> getKioskKeyMapCode(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 키맵 등록 */
    int saveKioskKeyMap(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 키맵 그룹복제 - 기존 카테고리(분류) 복사 */
    int copyKioskCategory(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 키맵 그룹복제 - 기존 키맵그룹에 엮여있는 상품 복사 */
    int copyKioskKeyMap(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 키맵 그룹복제전 기존 그룹 삭제(다른 포스로 복제) - 기존 카테고리(분류) 삭제 */
    int deletePosStoreKioskCategory(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 키맵 그룹복제전 기존 그룹 삭제(다른 포스로 복제) - 기존 카테고리(분류) 삭제 */
    int deletePosStoreTuClsType(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 키맵 그룹복제(다른 포스로 복제) - 기존 카테고리(분류) 복사 */
    int copyPosKioskCategory(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 키맵 그룹복제(다른 포스로 복제) - 기존 키맵그룹에 엮여있는 상품 복사 */
    int copyPosKioskKeyMap(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 키맵매장적용 - 매장리스트 조회 */
    List<DefaultMap<String>> getStoreList(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 키맵매장적용 - 매장에서 사용중인 기존 키맵그룹 삭제 */
    int deleteStoreTuClsType(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 키맵매장적용 - 키맵 매장적용 전 기존 키맵그룹에 엮어있는 상품 삭제 */
    int deleteKioskKeyMapByTuClsCd(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 키맵매장적용 - 본사 키맵그룹 매장적용 시 매장 카테고리(분류) 저장 */
    int insertKioskCategoryStoreReg(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 키맵매장적용 - 본사 키맵그룹 매장적용 시 본사 키맵그룹에 엮여있는 상품 매장에도 복사 */
    int copyKioskKeyMapStoreReg(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 매장적용(매장/포장) - 매장 키오스크 포스 리스트 조회 */
    List<DefaultMap<String>> getStoreKioskPosList(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 매장적용(매장/포장) - 본사 환경설정값 저장 */
    int insertHqKioskEnv(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 매장적용(매장/포장) - 매장 키오스크 포스 환경설정값 일괄 저장 */
    int insertStoreKioskPosEnv(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 매장적용(매장/포장) - 키오스크 환경설정 값 가져오기 */
    String getKioskEnv(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 추천메뉴 - 추천메뉴코드 가져오기 */
    List<DefaultMap<Object>> getRecmd(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 추천메뉴 - 추천메뉴코드 자동채번 */
    String getRecmdCd(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 추천메뉴 - 추천메뉴코드 저장 */
    int saveRecmd(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 추천메뉴 - 추천메뉴코드 삭제 */
    int deleteRecmd(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 추천메뉴 - 추천상품 조회 */
    List<DefaultMap<Object>> getRecmdProd(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 추천메뉴 - 추천상품으로 등록할 상품 조회 */
    List<DefaultMap<Object>> getRecmdProdList(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 추천메뉴 - 추천메뉴 순번 */
    String getIndex(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 추천메뉴 - 추천메뉴 삭제 */
    int deleteRecmdProd(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 추천메뉴 - 추천메뉴 저장 */
    int addRecmdProd(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 추천메뉴 매장적용 - 매장에서 사용중인 기존 메뉴추천정보 삭제 */
    int deleteStoreRecmd(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 추천메뉴 매장적용 - 매장에서 사용중인 기존 메뉴추천리스트 삭제 */
    int deleteStoreRecmdProd(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 추천메뉴 매장적용 - 본사에서 사용중인 메뉴추천정보 매장등록 */
    int insertStoreHqRecmd(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 추천메뉴 매장적용 - 본사에서 사용중인 메뉴추천리스트 매장등록 */
    int insertStoreHqRecmdProd(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 카테고리(중분류) 조회 */
    List<DefaultMap<Object>> getKioskCategoryM(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 카테고리(중분류) 저장 - 생성 시 카테고리 코드 생성 */
    String getKioskCategoryCodeM(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 카테고리(중분류) 저장 - 생성 */
    int insertKioskCategoryM(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 카테고리(중분류) 저장 - 수정 */
    int updateKioskCategoryM(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 카테고리(중분류) 저장 - 삭제 */
    int deleteKioskCategoryM(KioskKeyMapVO kioskKeyMapVO);

    /** 키맵그룹에 KIOSK중분류사용 조회 */
    List<DefaultMap<Object>> getKioskKeyMapGroupTuMClsFg(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 키맵 그룹복제 - 키맵그룹에 KIOSK중분류사용 조회 */
    List<DefaultMap<Object>> getCopyKioskKeyMapGroupTuMClsFg(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 키맵 그룹복제 - 중분류 키맵그룹 rowCount 조회 */
    List<DefaultMap<Object>> getKioskMClsCount(KioskKeyMapVO kioskKeyMapVO);

    /** 키오스크 키맵 그룹복제 - 기존 카테고리(중분류) 복사 */
    int copyKioskCategoryM(KioskKeyMapVO kioskKeyMapVO);
}
