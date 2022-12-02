package kr.co.solbipos.base.promotion.promotion.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.media.service.MediaVO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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

    /** 프로모션 종류 조회(콤보박스용) */
    List<DefaultMap<String>> getPromotionTypeList();

    /** 프로모션 종류 변경에 따른 필수값 저장 */
    String savePromotionDefaultSet(PromotionVO promotionVO, SessionInfoVO sessionInfoVO);

    /** 프로모션 적용매장 전체삭제 */
    int deletePromotionStoreAll(PromotionVO promotionVO, SessionInfoVO sessionInfoVO);

    /** 프로모션 적용매장 매장 엑셀업로드 */
    int excelUploadPromotionStore(PromotionVO[] promotionVOs, SessionInfoVO sessionInfoVO);

    /** 프로모션 키오스크 배너 조회 */
    List<DefaultMap<String>> getPromotionBanner(MediaVO mediaVO, SessionInfoVO sessionInfoVO);

    /** 프로모션 키오스크 배너 등록 */
    String savePromotionBanner(MultipartHttpServletRequest multi, MediaVO mediaVO, SessionInfoVO sessionInfoVO);

    /** 프로모션 키오스크 배너 삭제 */
    boolean delPromotionBanner(MediaVO mediaVO, SessionInfoVO sessionInfoVO);

    /** 프로모션 키오스크 배너 수정(프로모션관련 정보만 수정) */
    int modPromotionBanner(MediaVO mediaVO, SessionInfoVO sessionInfoVO);

    /** 맘스터치 프로모션 적용매장 선택팝업 매장리스트 조회 */
    List<DefaultMap<String>> getMomsStoreList(PromotionVO promotionVO, SessionInfoVO sessionInfoVO);

}


