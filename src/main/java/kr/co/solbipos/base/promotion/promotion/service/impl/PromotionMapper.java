package kr.co.solbipos.base.promotion.promotion.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.promotion.promotion.service.PromotionVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : PromotionMapper.java
 * @Description : 기초관리 - 프로모션관리 - 프로모션관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.04.13  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021 .04. 13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface PromotionMapper {

    /** 프로모션관리 리스트 조회 */
    List<DefaultMap<String>> getPromotionList(PromotionVO promotionVO);

    /** 프로모션 코드 생성 */
    String getPromotionCode(PromotionVO promotionVO);

    /** 프로모션 마스터 정보 저장 */
    int savePromotionH(PromotionVO promotionVO);

    /** 프로모션 적용조건 정보 저장 */
    int savePromotionCondi(PromotionVO promotionVO);

    /** 프로모션 적용혜책 정보 저장 */
    int savePromotionBene(PromotionVO promotionVO);

    /** 프로모션 적용상품 전체 삭제 */
    int deletePromotionProdAll(PromotionVO promotionVO);

    /** 프로모션 혜택상품 전체 삭제 */
    int deletePromotionPresentAll(PromotionVO promotionVO);

    /** 프로모션 상세 조회 */
    DefaultMap<String> getPromotionDetail(PromotionVO promotionVO);

    /** 프로모션 적용상품 리스트 조회 */
    List<DefaultMap<String>> getPromotionProdList(PromotionVO promotionVO);

    /** 프로모션 적용상품 선택팝업 상품리스트 조회 */
    List<DefaultMap<String>> getProdList(PromotionVO promotionVO);

    /** 프로모션 적용상품 선택팝업 분류리스트 조회 */
    List<DefaultMap<String>> getClassList(PromotionVO promotionVO);

    /** 프로모션 적용상품 선택팝업 상품추가 */
    int insertPromotionProd(PromotionVO promotionVO);

    /** 프로모션 적용상품 조건수량 수정 */
    int updatePromotionProd(PromotionVO promotionVO);

    /** 프로모션 적용상품 삭제 */
    int deletePromotionProd(PromotionVO promotionVO);

    /** 프로모션 적용매장 리스트 조회 */
    List<DefaultMap<String>> getPromotionStoreList(PromotionVO promotionVO);

    /** 프로모션 적용매장 선택팝업 매장리스트 조회 */
    List<DefaultMap<String>> getStoreList(PromotionVO promotionVO);

    /** 프로모션 적용매장 선택팝업 전매장적용 */
    int insertPromotionStoreAll(PromotionVO promotionVO);

    /** 프로모션 적용매장 선택팝업 매장추가 */
    int insertPromotionStore(PromotionVO promotionVO);

    /** 프로모션 적용매장 삭제 */
    int deletePromotionStore(PromotionVO promotionVO);

    /** 프로모션 혜택상품 리스트 조회 */
    List<DefaultMap<String>> getPromotionPresentList(PromotionVO promotionVO);

    /** 프로모션 혜택상품 선택팝업 상품리스트 조회 */
    List<DefaultMap<String>> getPresentProdList(PromotionVO promotionVO);

    /** 프로모션 혜택상품 선택팝업 상품추가 */
    int insertPromotionPresent(PromotionVO promotionVO);

    /** 프로모션 혜택상품 조건수량 수정 */
    int updatePromotionPresent(PromotionVO promotionVO);

    /** 프로모션 혜택상품 삭제 */
    int deletePromotionPresent(PromotionVO promotionVO);
}
