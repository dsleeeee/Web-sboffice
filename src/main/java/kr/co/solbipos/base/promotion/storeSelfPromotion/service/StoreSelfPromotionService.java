package kr.co.solbipos.base.promotion.storeSelfPromotion.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.promotion.promotion.service.PromotionVO;

import java.util.List;

/**
 * @Class Name : StoreSelfPromotionService.java
 * @Description : 기초관리 - 프로모션관리 - 매장자체프로모션현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.07  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021 .09. 07
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StoreSelfPromotionService {

    /** 매장자체프로모션현황 - 매장자체 프로모션 조회 */
    List<DefaultMap<String>> getStoreSelfPromotionList(StoreSelfPromotionVO storeSelfPromotionVO, SessionInfoVO sessionInfoVO);

    /** 매장자체프로모션현황 - 매장자체 프로모션 상세 조회 */
    DefaultMap<String> getStoreSelfPromotionDtl(StoreSelfPromotionVO storeSelfPromotionVO, SessionInfoVO sessionInfoVO);

    /** 매장자체프로모션현황 - 적용상품 리스트 조회 */
    List<DefaultMap<String>> getStoreSelfPromotionProdList(StoreSelfPromotionVO storeSelfPromotionVO, SessionInfoVO sessionInfoVO);

    /** 매장자체프로모션현황 - 혜택상품 리스트 조회 */
    List<DefaultMap<String>> getStoreSelfPromotionPresentList(StoreSelfPromotionVO storeSelfPromotionVO, SessionInfoVO sessionInfoVO);
}
