package kr.co.solbipos.base.prod.foodAllergy.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : FoodAllergyVO.java
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
public class FoodAllergyVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    private String hqOfficeCd;

    private String hqBrandCd;

    /**
     * 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** 회원소속코드 */
    private String membrOrgnCd;

    /** 조회매장 */
    private String storeCd;

    /** 재료코드 */
    private String recipesCd;

    /** 재료명 */
    private String recipesNm;

    /** 상품코드 */
    private String prodCd;

    /** 상품명 */
    private String prodNm;

    /** 상품분류코드 */
    private String prodClassCd;

    /** 알레르기명 */
    private String allergieNm;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getHqBrandCd() {
        return hqBrandCd;
    }

    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
    }

    public String getOrgnFg() { return orgnFg; }

    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

    public String getMembrOrgnCd() { return membrOrgnCd; }

    public void setMembrOrgnCd(String membrOrgnCd) { this.membrOrgnCd = membrOrgnCd; }

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String getRecipesCd() { return recipesCd; }

    public void setRecipesCd(String recipesCd) { this.recipesCd = recipesCd; }

    public String getRecipesNm() { return recipesNm; }

    public void setRecipesNm(String recipesNm) { this.recipesNm = recipesNm; }

    public String getProdCd() { return prodCd; }

    public void setProdCd(String prodCd) { this.prodCd = prodCd; }

    public String getProdNm() { return prodNm; }

    public void setProdNm(String prodNm) { this.prodNm = prodNm; }

    public String getProdClassCd() { return prodClassCd; }

    public void setProdClassCd(String prodClassCd) { this.prodClassCd = prodClassCd; }

    public String getAllergieNm() { return allergieNm; }

    public void setAllergieNm(String allergieNm) { this.allergieNm = allergieNm; }
}