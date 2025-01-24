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
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2020.07.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class RecpOriginVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

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

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사명 */
    private String hqOfficeNm;

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

    /** 전체기간체크 */
    private boolean chkDt;

    /** 바코드 */
    private String barCd;

    /** 사용여부 */
    private String useYn;

    /** 원산지등록 사용여부 */
    private String recpOriginUseYn;

    /** 원산지출력순서 */
    private String recpSeq;

    /** 브랜드코드 */
    private String hqBrandCd;

    /** 브랜드코드 */
    private String hqBrandCdCombo;

    /** 사용자 아이디 */
    private String userId;

    /** 사용자별 브랜드코드(상품) */
    private String[] userProdBrandList;

    /** 사용자별 브랜드코드(상품) */
    private String userProdBrands;

    /** 상품브랜드코드 */
    private String prodHqBrandCd;

    /** 원산지관리코드 */
    private String originCd;

    /** 출력물코드 */
    private String prtCd;
    
    /** 원산지관리명 */
    private String originNm;

    /** 원산지 정보 TEXT */
    private String originTxt1;
    private String originTxt2;
    private String originTxt3;
    private String originTxt4;
    private String originTxt5;

    public String getOrgnFg() { return orgnFg; }

    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

    public String getMembrOrgnCd() { return membrOrgnCd; }

    public void setMembrOrgnCd(String membrOrgnCd) { this.membrOrgnCd = membrOrgnCd; }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getHqOfficeNm() {
        return hqOfficeNm;
    }

    public void setHqOfficeNm(String hqOfficeNm) {
        this.hqOfficeNm = hqOfficeNm;
    }

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

    public boolean getChkDt() { return chkDt; }

    public void setChkDt(boolean chkDt) { this.chkDt = chkDt; }

    public String getBarCd() { return barCd; }

    public void setBarCd(String barCd) { this.barCd = barCd; }

    public String getUseYn() { return useYn; }

    public void setUseYn(String useYn) { this.useYn = useYn; }

    public String getRecpOriginUseYn() { return recpOriginUseYn; }

    public void setRecpOriginUseYn(String recpOriginUseYn) { this.recpOriginUseYn = recpOriginUseYn; }

    public String getRecpSeq() { return recpSeq; }

    public void setRecpSeq(String recpSeq) { this.recpSeq = recpSeq; }

    public String getHqBrandCd() { return hqBrandCd; }

    public void setHqBrandCd(String hqBrandCd) { this.hqBrandCd = hqBrandCd; }

    public String getHqBrandCdCombo() { return hqBrandCdCombo; }

    public void setHqBrandCdCombo(String hqBrandCdCombo) { this.hqBrandCdCombo = hqBrandCdCombo; }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String[] getUserProdBrandList() {
        return userProdBrandList;
    }

    public void setUserProdBrandList(String[] userProdBrandList) {
        this.userProdBrandList = userProdBrandList;
    }

    public String getUserProdBrands() {
        return userProdBrands;
    }

    public void setUserProdBrands(String userProdBrands) {
        this.userProdBrands = userProdBrands;
    }

    public String getProdHqBrandCd() {
        return prodHqBrandCd;
    }

    public void setProdHqBrandCd(String prodHqBrandCd) {
        this.prodHqBrandCd = prodHqBrandCd;
    }

    public String getOriginCd() {
        return originCd;
    }

    public void setOriginCd(String originCd) {
        this.originCd = originCd;
    }

    public String getPrtCd() {
        return prtCd;
    }

    public void setPrtCd(String prtCd) {
        this.prtCd = prtCd;
    }

    public String getOriginNm() {
        return originNm;
    }

    public void setOriginNm(String originNm) {
        this.originNm = originNm;
    }

    public String getOriginTxt1() {
        return originTxt1;
    }

    public void setOriginTxt1(String originTxt1) {
        this.originTxt1 = originTxt1;
    }

    public String getOriginTxt2() {
        return originTxt2;
    }

    public void setOriginTxt2(String originTxt2) {
        this.originTxt2 = originTxt2;
    }

    public String getOriginTxt3() {
        return originTxt3;
    }

    public void setOriginTxt3(String originTxt3) {
        this.originTxt3 = originTxt3;
    }

    public String getOriginTxt4() {
        return originTxt4;
    }

    public void setOriginTxt4(String originTxt4) {
        this.originTxt4 = originTxt4;
    }

    public String getOriginTxt5() {
        return originTxt5;
    }

    public void setOriginTxt5(String originTxt5) {
        this.originTxt5 = originTxt5;
    }
}