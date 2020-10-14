package kr.co.solbipos.base.prod.foodAllergy.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : FoodAllergyService.java
 * @Description : 기초관리 > 상품관리 > 식품 알레르기 정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.10.06  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.10.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface FoodAllergyService {

    /** 식품 알레르기 정보관리 조회 */
    List<DefaultMap<Object>> getFoodAllergyList(FoodAllergyVO foodAllergyVO, SessionInfoVO sessionInfoVO);

    /** 식품 알레르기 정보관리 저장 */
    int getFoodAllergySave(FoodAllergyVO[] foodAllergyVOs, SessionInfoVO sessionInfoVO);

    /** 알레르기-상품 조회 */
    List<DefaultMap<Object>> getFoodAllergyDetailList(FoodAllergyVO foodAllergyVO, SessionInfoVO sessionInfoVO);

    /** 알레르기-상품 등록 팝업 - 상품조회 */
    List<DefaultMap<Object>> getFoodAllergyProdList(FoodAllergyVO foodAllergyVO, SessionInfoVO sessionInfoVO);

    /** 알레르기-상품 저장 */
    int getFoodAllergyDetailSave(FoodAllergyVO[] foodAllergyVOs, SessionInfoVO sessionInfoVO);
}