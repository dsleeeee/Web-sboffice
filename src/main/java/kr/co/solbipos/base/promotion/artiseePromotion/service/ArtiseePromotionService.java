package kr.co.solbipos.base.promotion.artiseePromotion.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : ArtiseePromotionService.java
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
public interface ArtiseePromotionService {

    /** 아티제전용프로모션 리스트 조회 */
    List<DefaultMap<String>> getPromotionList(ArtiseePromotionVO artiseePromotionVO, SessionInfoVO sessionInfoVO);

    /** 아티제전용프로모션 등록/수정 */
    String savePromotion(ArtiseePromotionVO artiseePromotionVO, SessionInfoVO sessionInfoVO);

    /** 아티제전용프로모션 상세 조회 */
    DefaultMap<String> getPromotionDetail(ArtiseePromotionVO artiseePromotionVO, SessionInfoVO sessionInfoVO);

    /** 아티제전용프로모션 적용상품 리스트 조회 */
    List<DefaultMap<String>> getPromotionProdList(ArtiseePromotionVO artiseePromotionVO, SessionInfoVO sessionInfoVO);

    /** 아티제전용프로모션 적용상품 선택팝업 상품리스트 조회 */
    List<DefaultMap<String>> getProdList(ArtiseePromotionVO artiseePromotionVO, SessionInfoVO sessionInfoVO);

    /** 아티제전용프로모션 적용분류 선택팝업 분류리스트 조회 */
    List<DefaultMap<String>> getClassList(ArtiseePromotionVO artiseePromotionVO, SessionInfoVO sessionInfoVO);

    /** 아티제전용프로모션 적용상품, 적용분류 선택팝업 상품추가/수정/삭제 */
    int savePromotionProd(ArtiseePromotionVO[] artiseePromotionVOs, SessionInfoVO sessionInfoVO);

    /** 아티제전용프로모션 적용매장 리스트 조회 */
    List<DefaultMap<String>> getPromotionStoreList(ArtiseePromotionVO artiseePromotionVO, SessionInfoVO sessionInfoVO);

    /** 아티제전용프로모션 적용매장 선택팝업 매장리스트 조회 */
    List<DefaultMap<String>> getStoreList(ArtiseePromotionVO artiseePromotionVO, SessionInfoVO sessionInfoVO);

    /** 아티제전용프로모션 적용매장 선택팝업 전매장적용 */
    int insertPromotionStoreAll(ArtiseePromotionVO artiseePromotionVO, SessionInfoVO sessionInfoVO);

    /** 아티제전용프로모션 적용매장 선택팝업 매장추가/삭제 */
    int savePromotionStore(ArtiseePromotionVO[] artiseePromotionVOs, SessionInfoVO sessionInfoVO);
}
