package kr.co.solbipos.base.prod.prodImg.service;

import kr.co.solbipos.application.common.service.PageVO;

public class ProdImgVO extends PageVO {

    private static final long serialVersionUID = -7407958154295035218L;

    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 판매가격구분 (본사판매가 :1, 매장판매가:2) */
    private String salePrcFg;
    /** 전체기간 여부 */
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
    /** 이미지 구분(복사원본) */
    private String orgImgFg;
    /** 이미지 구분 */
    private String imgFg;
    /** 이미지 URL */
    private String imgUrl;
    /** 이미지 파일명 */
    private String imgFileNm;
    /** 이미지 파일명 */
    private String orgImgFileNm;
    /** 이미지 변경일시 */
    private String imgChgDt;
    /** 비고 */
    private String remark;
    /** 이미지 구분명 */
    private String imgFgType;
    /** 사용자 아이디 */
    private String userId;
    /** 매장명 */
    private String storeNm;
    /** 매장 상태 */
    private String sysStatFg;
    /** 이미지구분 array */
    private String arrImgFg[];
    /** 복사구분 */
    private String gubun;
    /** 사용자별 브랜드코드(상품) */
    private String[] userProdBrandList;
    /** 사용자별 브랜드코드(상품) */
    private String userProdBrands;
    /** 상품브랜드코드 */
    private String prodHqBrandCd;

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getSalePrcFg() {
        return salePrcFg;
    }

    public void setSalePrcFg(String salePrcFg) {
        this.salePrcFg = salePrcFg;
    }

    public boolean isChkDt() {
        return chkDt;
    }

    public void setChkDt(boolean chkDt) {
        this.chkDt = chkDt;
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

    public String getBarCd() {
        return barCd;
    }

    public void setBarCd(String barCd) {
        this.barCd = barCd;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getImgFg() {
        return imgFg;
    }

    public void setImgFg(String imgFg) {
        this.imgFg = imgFg;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgFileNm() {
        return imgFileNm;
    }

    public void setImgFileNm(String imgFileNm) {
        this.imgFileNm = imgFileNm;
    }

    public String getOrgImgFileNm() {
        return orgImgFileNm;
    }

    public void setOrgImgFileNm(String orgImgFileNm) {
        this.orgImgFileNm = orgImgFileNm;
    }

    public String getImgChgDt() {
        return imgChgDt;
    }

    public void setImgChgDt(String imgChgDt) {
        this.imgChgDt = imgChgDt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImgFgType() {
        return imgFgType;
    }

    public void setImgFgType(String imgFgType) {
        this.imgFgType = imgFgType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStoreNm() {
        return storeNm;
    }

    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    public String getSysStatFg() {
        return sysStatFg;
    }

    public void setSysStatFg(String sysStatFg) {
        this.sysStatFg = sysStatFg;
    }

    public String[] getArrImgFg() {
        return arrImgFg;
    }

    public void setArrImgFg(String[] arrImgFg) {
        this.arrImgFg = arrImgFg;
    }

    public String getOrgImgFg() {
        return orgImgFg;
    }

    public void setOrgImgFg(String orgImgFg) {
        this.orgImgFg = orgImgFg;
    }

    public String getGubun() {
        return gubun;
    }

    public void setGubun(String gubun) {
        this.gubun = gubun;
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
