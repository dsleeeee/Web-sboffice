package kr.co.solbipos.base.prod.soldOut.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.soldOut.service.SoldOutVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SoldOutMapper.java
 * @Description : 기초관리 - 상품관리 - 품절관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.02.28  권지현       최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.02.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface SoldOutMapper {

    /**
     * 상품 조회
     * @param soldOutVO
     * @return List
     */
    List<DefaultMap<String>> getProdList(SoldOutVO soldOutVO);

    /**
     * 상품 상세 조회
     * @param soldOutVO
     * @return DefaultMap
     */
    DefaultMap<String> getProdDetail(SoldOutVO soldOutVO);

    /**
     * 연결상품 조회
     * @param soldOutVO
     * @return List
     */
    List<DefaultMap<String>> getLinkedProdList(SoldOutVO soldOutVO);

    // 상품 품절여부 저장
    int getProdSoldOutSave(SoldOutVO soldOutVO);

    /** 사이드메뉴-선택그룹-선택그룹 목록 조회 */
    List<DefaultMap<String>> getMenuGrpList(SoldOutVO soldOutVO);

    /** 사이드메뉴-선택분류-선택분류 목록 조회 */
    List<DefaultMap<String>> getMenuClassList(SoldOutVO soldOutVO);

    /** 사이드메뉴-선택메뉴탭-선택상품 목록 조회 */
    List<DefaultMap<String>> getMenuProdList(SoldOutVO soldOutVO);

    // 사이드메뉴 품절여부 저장
    int getSideMenuSoldOutSave(SoldOutVO soldOutVO);

}