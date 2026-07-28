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
    /** POS상품명(영문) 1,2 */
    private String prodEnNmDisp1;
    private String prodEnNmDisp2;
    /** POS상품명(중문) 1,2 */
    private String prodCnNmDisp1;
    private String prodCnNmDisp2;
    /** POS상품명(일문) 1,2 */
    private String prodJpNmDisp1;
    private String prodJpNmDisp2;
    /** 키오스크상품명(영문) 1,2 */
    private String prodEnNmDispKiosk1;
    private String prodEnNmDispKiosk2;
    /** 키오스크상품명(중문) 1,2 */
    private String prodCnNmDispKiosk1;
    private String prodCnNmDispKiosk2;
    /** 키오스크상품명(일문) 1,2 */
    private String prodJpNmDispKiosk1;
    private String prodJpNmDispKiosk2;

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

    public String getProdEnNmDisp1() {
        return prodEnNmDisp1;
    }

    public void setProdEnNmDisp1(String prodEnNmDisp1) {
        this.prodEnNmDisp1 = prodEnNmDisp1;
    }

    public String getProdEnNmDisp2() {
        return prodEnNmDisp2;
    }

    public void setProdEnNmDisp2(String prodEnNmDisp2) {
        this.prodEnNmDisp2 = prodEnNmDisp2;
    }

    public String getProdCnNmDisp1() {
        return prodCnNmDisp1;
    }

    public void setProdCnNmDisp1(String prodCnNmDisp1) {
        this.prodCnNmDisp1 = prodCnNmDisp1;
    }

    public String getProdCnNmDisp2() {
        return prodCnNmDisp2;
    }

    public void setProdCnNmDisp2(String prodCnNmDisp2) {
        this.prodCnNmDisp2 = prodCnNmDisp2;
    }

    public String getProdJpNmDisp1() {
        return prodJpNmDisp1;
    }

    public void setProdJpNmDisp1(String prodJpNmDisp1) {
        this.prodJpNmDisp1 = prodJpNmDisp1;
    }

    public String getProdJpNmDisp2() {
        return prodJpNmDisp2;
    }

    public void setProdJpNmDisp2(String prodJpNmDisp2) {
        this.prodJpNmDisp2 = prodJpNmDisp2;
    }

    public String getProdEnNmDispKiosk1() {
        return prodEnNmDispKiosk1;
    }

    public void setProdEnNmDispKiosk1(String prodEnNmDispKiosk1) {
        this.prodEnNmDispKiosk1 = prodEnNmDispKiosk1;
    }

    public String getProdEnNmDispKiosk2() {
        return prodEnNmDispKiosk2;
    }

    public void setProdEnNmDispKiosk2(String prodEnNmDispKiosk2) {
        this.prodEnNmDispKiosk2 = prodEnNmDispKiosk2;
    }

    public String getProdCnNmDispKiosk1() {
        return prodCnNmDispKiosk1;
    }

    public void setProdCnNmDispKiosk1(String prodCnNmDispKiosk1) {
        this.prodCnNmDispKiosk1 = prodCnNmDispKiosk1;
    }

    public String getProdCnNmDispKiosk2() {
        return prodCnNmDispKiosk2;
    }

    public void setProdCnNmDispKiosk2(String prodCnNmDispKiosk2) {
        this.prodCnNmDispKiosk2 = prodCnNmDispKiosk2;
    }

    public String getProdJpNmDispKiosk1() {
        return prodJpNmDispKiosk1;
    }

    public void setProdJpNmDispKiosk1(String prodJpNmDispKiosk1) {
        this.prodJpNmDispKiosk1 = prodJpNmDispKiosk1;
    }

    public String getProdJpNmDispKiosk2() {
        return prodJpNmDispKiosk2;
    }

    public void setProdJpNmDispKiosk2(String prodJpNmDispKiosk2) {
        this.prodJpNmDispKiosk2 = prodJpNmDispKiosk2;
    }
}
