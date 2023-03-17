package kr.co.solbipos.base.prod.kioskDisplay.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.kioskDisplay.service.KioskDisplayVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : KioskDisplayMapper.java
 * @Description : 기초관리 - 상품관리 - 비노출관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.10  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.03.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface KioskDisplayMapper {

    /**
     * 상품 조회
     * @param kioskDisplayVO
     * @return List
     */
    List<DefaultMap<String>> getProdList(KioskDisplayVO kioskDisplayVO);

    /**
     * 상품 상세 조회
     * @param kioskDisplayVO
     * @return DefaultMap
     */
    DefaultMap<String> getProdDetail(KioskDisplayVO kioskDisplayVO);

    /**
     * 연결상품 조회
     * @param kioskDisplayVO
     * @return List
     */
    List<DefaultMap<String>> getLinkedProdList(KioskDisplayVO kioskDisplayVO);

    // 상품 품절여부 저장
    int getProdKioskDisplaySave(KioskDisplayVO kioskDisplayVO);

    /** 사이드메뉴-선택그룹-선택그룹 목록 조회 */
    List<DefaultMap<String>> getMenuGrpList(KioskDisplayVO kioskDisplayVO);

    /** 사이드메뉴-선택분류-선택분류 목록 조회 */
    List<DefaultMap<String>> getMenuClassList(KioskDisplayVO kioskDisplayVO);

    /** 사이드메뉴-선택메뉴탭-선택상품 목록 조회 */
    List<DefaultMap<String>> getMenuProdList(KioskDisplayVO kioskDisplayVO);

    // 사이드메뉴 품절여부 저장
    int getSideMenuKioskDisplaySave(KioskDisplayVO kioskDisplayVO);

}