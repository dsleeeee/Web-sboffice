package kr.co.solbipos.base.multilingual.prodLang.service;

import kr.co.solbipos.application.common.service.PageVO;

public class ProdLangVO extends PageVO {

    private static final long serialVersionUID = -6155295606123168141L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 사용자아이디 */
    private String userId;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 상품분류 */
    private String prodClassCd;
    /** 사용자별 브랜드코드(상품) */
    private String[] userProdBrandList;
    /** 사용자별 브랜드코드(상품) */
    private String userProdBrands;
    /** 사용여부 */
    private String useYn;
    /** 상품명(영문)*/
    private String prodEnNm;
    /** 상품명(중문)*/
    private String prodCnNm;
    /** 상품명(일문)*/
    private String prodJpNm;
    /** 상품설명(영문)*/
    private String prodEnInfo;
    /** 상품설명(중문)*/
    private String prodCnInfo;
    /** 상품설명(일문)*/
    private String prodJpInfo;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProdCd() {
        return prodCd;
    }

    public void setProdCd(String prodCd) {
        this.prodCd = prodCd;
    }

    public String getProdNm() {
        return prodNm;
    }

    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    public String getProdClassCd() {
        return prodClassCd;
    }

    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
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

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getProdEnNm() {
        return prodEnNm;
    }

    public void setProdEnNm(String prodEnNm) {
        this.prodEnNm = prodEnNm;
    }

    public String getProdCnNm() {
        return prodCnNm;
    }

    public void setProdCnNm(String prodCnNm) {
        this.prodCnNm = prodCnNm;
    }

    public String getProdJpNm() {
        return prodJpNm;
    }

    public void setProdJpNm(String prodJpNm) {
        this.prodJpNm = prodJpNm;
    }

    public String getProdEnInfo() {
        return prodEnInfo;
    }

    public void setProdEnInfo(String prodEnInfo) {
        this.prodEnInfo = prodEnInfo;
    }

    public String getProdCnInfo() {
        return prodCnInfo;
    }

    public void setProdCnInfo(String prodCnInfo) {
        this.prodCnInfo = prodCnInfo;
    }

    public String getProdJpInfo() {
        return prodJpInfo;
    }

    public void setProdJpInfo(String prodJpInfo) {
        this.prodJpInfo = prodJpInfo;
    }
}
