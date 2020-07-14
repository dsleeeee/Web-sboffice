package kr.co.solbipos.base.prod.recpOrigin.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : RecpOriginVO.java
 * @Description : 기초관리 > 상품관리 > 원산지관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.07.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.07.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class RecpOriginVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 조회매장 */
    private String storeCd;

    /** 재료코드 */
    private String recipesCd;

    /** 재료명 */
    private String recipesNm;

    /** 원산지명 */
    private String orgplceNm;

    /** 상품코드 */
    private String prodCd;

    /** 상품명 */
    private String prodNm;

    /** 상품분류코드 */
    private String prodClassCd;

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String getRecipesCd() { return recipesCd; }

    public void setRecipesCd(String recipesCd) { this.recipesCd = recipesCd; }

    public String getRecipesNm() { return recipesNm; }

    public void setRecipesNm(String recipesNm) { this.recipesNm = recipesNm; }

    public String getOrgplceNm() { return orgplceNm; }

    public void setOrgplceNm(String orgplceNm) { this.orgplceNm = orgplceNm; }

    public String getProdCd() { return prodCd; }

    public void setProdCd(String prodCd) { this.prodCd = prodCd; }

    public String getProdNm() { return prodNm; }

    public void setProdNm(String prodNm) { this.prodNm = prodNm; }

    public String getProdClassCd() { return prodClassCd; }

    public void setProdClassCd(String prodClassCd) { this.prodClassCd = prodClassCd; }
}