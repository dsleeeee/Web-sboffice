package kr.co.solbipos.base.prod.foodAllergy.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.base.prod.foodAllergy.service.FoodAllergyVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : FoodAllergyMapper.java
 * @Description : 기초관리 > 상품관리 > 식품 알레르기 정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.10.06  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2020.10.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface FoodAllergyMapper {

    /** 식품 알레르기 정보관리 조회 */
    List<DefaultMap<Object>> getFoodAllergyList(FoodAllergyVO foodAllergyVO);

    /** 브랜드 콤보박스 리스트 조회 */
    List<DefaultMap<Object>> getBrandComboList(FoodAllergyVO foodAllergyVO);

    /** 재료코드(자동채번) */
    String getFoodAllergyRecipesCd(FoodAllergyVO foodAllergyVO);

    /** 식품 알레르기 정보관리 저장 insert */
    int getFoodAllergySaveInsert(FoodAllergyVO foodAllergyVO);

    /** 식품 알레르기 정보관리 저장 update */
    int getFoodAllergySaveUpdate(FoodAllergyVO foodAllergyVO);

    /** 식품 알레르기 정보관리 저장 delete */
    int getFoodAllergySaveDelete(FoodAllergyVO foodAllergyVO);

    /** 식품 알레르기 정보관리 저장 delete 시, 알레르기-상품 저장 delete */
    int getFoodAllergySaveDeleteAll(FoodAllergyVO foodAllergyVO);

    /** 알레르기-상품 조회 */
    List<DefaultMap<Object>> getFoodAllergyDetailList(FoodAllergyVO foodAllergyVO);

    /** 알레르기-상품 등록 팝업 - 상품조회 */
    List<DefaultMap<Object>> getFoodAllergyProdList(FoodAllergyVO foodAllergyVO);

    /** 알레르기-상품 저장 insert */
    int getFoodAllergyProdSaveInsert(FoodAllergyVO foodAllergyVO);

    /** 알레르기-상품 저장 delete */
    int getFoodAllergyProdSaveDelete(FoodAllergyVO foodAllergyVO);
}