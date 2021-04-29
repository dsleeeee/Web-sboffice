package kr.co.solbipos.base.promotion.promotion.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : PromotionService.java
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
public interface PromotionService {

    /** 프로모션관리 리스트 조회 */
    List<DefaultMap<String>> getPromotionList(PromotionVO promotionVO, SessionInfoVO sessionInfoVO);

    /** 프로모션 등록/수정 */
    String savePromotion(PromotionVO promotionVO, SessionInfoVO sessionInfoVO);

    /** 프로모션 상세 조회 */
    DefaultMap<String> getPromotionDetail(PromotionVO promotionVO, SessionInfoVO sessionInfoVO);

    /** 프로모션 적용상품 리스트 조회 */
    List<DefaultMap<String>> getPromotionProdList(PromotionVO promotionVO, SessionInfoVO sessionInfoVO);

    /** 프로모션 적용상품 선택팝업 상품리스트 조회 */
    List<DefaultMap<String>> getProdList(PromotionVO promotionVO, SessionInfoVO sessionInfoVO);

    /** 프로모션 적용상품 선택팝업 분류리스트 조회 */
    List<DefaultMap<String>> getClassList(PromotionVO promotionVO, SessionInfoVO sessionInfoVO);

    /** 프로모션 적용상품 선택팝업 상품추가/수정/삭제 */
    int savePromotionProd(PromotionVO[] promotionVOs, SessionInfoVO sessionInfoVO);

    /** 프로모션 적용매장 리스트 조회 */
    List<DefaultMap<String>> getPromotionStoreList(PromotionVO promotionVO, SessionInfoVO sessionInfoVO);

    /** 프로모션 적용매장 선택팝업 매장리스트 조회 */
    List<DefaultMap<String>> getStoreList(PromotionVO promotionVO, SessionInfoVO sessionInfoVO);

    /** 프로모션 적용매장 선택팝업 전매장적용 */
    int insertPromotionStoreAll(PromotionVO promotionVO, SessionInfoVO sessionInfoVO);

    /** 프로모션 적용매장 선택팝업 매장추가/삭제 */
    int savePromotionStore(PromotionVO[] promotionVOs, SessionInfoVO sessionInfoVO);

    /** 프로모션 혜택상품 리스트 조회 */
    List<DefaultMap<String>> getPromotionPresentList(PromotionVO promotionVO, SessionInfoVO sessionInfoVO);

    /** 프로모션 혜택상품 선택팝업 상품리스트 조회 */
    List<DefaultMap<String>> getPresentProdList(PromotionVO promotionVO, SessionInfoVO sessionInfoVO);

    /** 프로모션 혜택상품 선택팝업 상품추가/수정/삭제 */
    int savePromotionPresent(PromotionVO[] promotionVOs, SessionInfoVO sessionInfoVO);
}


