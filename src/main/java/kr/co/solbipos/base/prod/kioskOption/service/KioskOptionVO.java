package kr.co.solbipos.base.prod.kioskOption.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : KioskOptionVO.java
 * @Description : 기초관리 > 상품관리 > 키오스크옵션관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.08.19  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.08.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class KioskOptionVO extends PageVO {

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

    /** 조회매장 */
    private String storeCd;

    /** 전체기간체크 */
    private boolean chkDt;

    /** 상품코드 */
    private String prodCd;

    /** 상품명 */
    private String prodNm;

    /** 상품분류코드 */
    private String prodClassCd;

    /** 바코드 */
    private String barCd;

    /** 사용여부 */
    private String useYn;

    /** 판매가격구분 (본사판매가 :1, 매장판매가:2) */
    private String salePrcFg;

    /** 옵션구분 (0:기본옵션, 1:무료옵션, 2:유료옵션) */
    private String optionFg;

    /** 옵션상품코드 */
    private String optionProdCd;

    /** 옵션상품명 */
    private String optionProdNm;

    /** 표기순번 */
    private int dispSeq;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 사용자별 브랜드코드(상품) */
    private String[] userProdBrandList;

    /** 사용자별 브랜드코드(상품) */
    private String userProdBrands;

    /** 상품브랜드코드 */
    private String prodHqBrandCd;

    public String getOrgnFg() { return orgnFg; }

    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

    public String getMembrOrgnCd() { return membrOrgnCd; }

    public void setMembrOrgnCd(String membrOrgnCd) { this.membrOrgnCd = membrOrgnCd; }

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public boolean getChkDt() { return chkDt; }

    public void setChkDt(boolean chkDt) { this.chkDt = chkDt; }

    public String getProdCd() { return prodCd; }

    public void setProdCd(String prodCd) { this.prodCd = prodCd; }

    public String getProdNm() { return prodNm; }

    public void setProdNm(String prodNm) { this.prodNm = prodNm; }

    public String getProdClassCd() { return prodClassCd; }

    public void setProdClassCd(String prodClassCd) { this.prodClassCd = prodClassCd; }

    public String getBarCd() { return barCd; }

    public void setBarCd(String barCd) { this.barCd = barCd; }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getSalePrcFg() {
        return salePrcFg;
    }

    public void setSalePrcFg(String salePrcFg) {
        this.salePrcFg = salePrcFg;
    }

    public String getOptionFg() {
        return optionFg;
    }

    public void setOptionFg(String optionFg) {
        this.optionFg = optionFg;
    }

    public String getOptionProdCd() {
        return optionProdCd;
    }

    public void setOptionProdCd(String optionProdCd) {
        this.optionProdCd = optionProdCd;
    }

    public String getOptionProdNm() { return optionProdNm; }

    public void setOptionProdNm(String optionProdNm) { this.optionProdNm = optionProdNm; }

    public int getDispSeq() {
        return dispSeq;
    }

    public void setDispSeq(int dispSeq) {
        this.dispSeq = dispSeq;
    }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
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
}