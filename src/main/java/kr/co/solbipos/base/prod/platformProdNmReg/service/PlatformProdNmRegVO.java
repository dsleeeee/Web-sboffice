package kr.co.solbipos.base.prod.platformProdNmReg.service;

import kr.co.solbipos.application.common.service.PageVO;

public class PlatformProdNmRegVO extends PageVO {

    private static final long serialVersionUID = -6155295606123168142L;

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
    /** 사용여부 */
    private String useYn;
    /** 사용자별 브랜드코드 */
    private String[] userBrandList;
    /** 사용자별 브랜드코드 */
    private String userBrands;
    /** 상품브랜드코드 */
    private String prodHqBrandCd;
    /** POS 상품명1 */
    private String posProdNm1;
    /** POS 상품명2 */
    private String posProdNm2;
    /** 키오스크 상품명1 */
    private String kioskProdNm1;
    /** 키오스크 상품명2 */
    private String kioskProdNm2;
    /** 등록일시 */
    private String regDt;
    /** 등록자 */
    private String regId;
    /** 수정일시 */
    private String modDt;
    /** 수정자 */
    private String modId;

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

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String[] getUserBrandList() {
        return userBrandList;
    }

    public void setUserBrandList(String[] userBrandList) {
        this.userBrandList = userBrandList;
    }

    public String getUserBrands() {
        return userBrands;
    }

    public void setUserBrands(String userBrands) {
        this.userBrands = userBrands;
    }

    public String getProdHqBrandCd() {
        return prodHqBrandCd;
    }

    public void setProdHqBrandCd(String prodHqBrandCd) {
        this.prodHqBrandCd = prodHqBrandCd;
    }

    public String getPosProdNm1() {
        return posProdNm1;
    }

    public void setPosProdNm1(String posProdNm1) {
        this.posProdNm1 = posProdNm1;
    }

    public String getPosProdNm2() {
        return posProdNm2;
    }

    public void setPosProdNm2(String posProdNm2) {
        this.posProdNm2 = posProdNm2;
    }

    public String getKioskProdNm1() {
        return kioskProdNm1;
    }

    public void setKioskProdNm1(String kioskProdNm1) {
        this.kioskProdNm1 = kioskProdNm1;
    }

    public String getKioskProdNm2() {
        return kioskProdNm2;
    }

    public void setKioskProdNm2(String kioskProdNm2) {
        this.kioskProdNm2 = kioskProdNm2;
    }

    public String getRegDt() {
        return regDt;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getModDt() {
        return modDt;
    }

    public void setModDt(String modDt) {
        this.modDt = modDt;
    }

    public String getModId() {
        return modId;
    }

    public void setModId(String modId) {
        this.modId = modId;
    }
}
