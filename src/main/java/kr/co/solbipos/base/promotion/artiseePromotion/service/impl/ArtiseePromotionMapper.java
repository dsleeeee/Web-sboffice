package kr.co.solbipos.base.promotion.artiseePromotion.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.promotion.artiseePromotion.service.ArtiseePromotionVO;
import kr.co.solbipos.base.promotion.promotion.service.PromotionVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : ArtiseePromotionMapper.java
 * @Description : 기초관리 - 프로모션관리 - 아티제전용프로모션
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.06.13  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.06.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface ArtiseePromotionMapper {

    /** 아티제전용프로모션 리스트 조회 */
    List<DefaultMap<String>> getPromotionList(ArtiseePromotionVO artiseePromotionVO);

    /** 아티제전용프로모션 코드 생성 */
    String getPromotionCode(ArtiseePromotionVO artiseePromotionVO);

    /** 아티제전용프로모션 마스터 정보 저장 */
    int savePromotion(ArtiseePromotionVO artiseePromotionVO);

    /** 아티제전용프로모션 상세 조회 */
    DefaultMap<String> getPromotionDetail(ArtiseePromotionVO artiseePromotionVO);

    /** 아티제전용프로모션 적용상품 리스트 조회 */
    List<DefaultMap<String>> getPromotionProdList(ArtiseePromotionVO artiseePromotionVO);

    /** 아티제전용프로모션 적용상품 선택팝업 상품리스트 조회 */
    List<DefaultMap<String>> getProdList(ArtiseePromotionVO artiseePromotionVO);

    /** 아티제전용프로모션 적용분류 선택팝업 분류리스트 조회 */
    List<DefaultMap<String>> getClassList(ArtiseePromotionVO artiseePromotionVO);

    /** 아티제전용프로모션 적용상품, 적용분류 선택팝업 상품추가 */
    int insertPromotionProd(ArtiseePromotionVO artiseePromotionVO);

    /** 아티제전용프로모션 적용상품, 적용분류 조건수량 수정 */
    int updatePromotionProd(ArtiseePromotionVO artiseePromotionVO);

    /** 아티제전용프로모션 적용상품, 적용분류 삭제 */
    int deletePromotionProd(ArtiseePromotionVO artiseePromotionVO);

    /** 아티제전용프로모션 적용상품, 적용분류 전체삭제 */
    int deletePromotionProdAll(ArtiseePromotionVO artiseePromotionVO);

    /** 아티제전용프로모션 적용매장 리스트 조회 */
    List<DefaultMap<String>> getPromotionStoreList(ArtiseePromotionVO artiseePromotionVO);

    /** 아티제전용프로모션 적용매장 선택팝업 매장리스트 조회 */
    List<DefaultMap<String>> getStoreList(ArtiseePromotionVO artiseePromotionVO);

    /** 아티제전용프로모션 적용매장 선택팝업 전매장적용 */
    int insertPromotionStoreAll(ArtiseePromotionVO artiseePromotionVO);

    /** 아티제전용프로모션 적용매장 선택팝업 매장추가 */
    int insertPromotionStore(ArtiseePromotionVO artiseePromotionVO);

    /** 아티제전용프로모션 적용매장 삭제 */
    int deletePromotionStore(ArtiseePromotionVO artiseePromotionVO);
}
