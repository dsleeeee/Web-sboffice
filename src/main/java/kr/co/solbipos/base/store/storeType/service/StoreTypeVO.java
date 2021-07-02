package kr.co.solbipos.base.store.storeType.service;

import kr.co.solbipos.application.common.service.PageVO;

public class StoreTypeVO extends PageVO {

    private static final long serialVersionUID = 8050354827837735132L;

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
    /** 매장타입코드 */
    private String storeTypeCd;
    /** 매장타입명 */
    private String storeTypeNm;
    /** 비고 */
    private String remark;
    /** 옵션1 */
    private String option01;
    /** 사용여부 */
    private String useYn;
    /** 매장명 */
    private String storeNm;
    /** 매장상태 */
    private String sysStatFg;
    /** 메뉴그룹코드 */
    private String storeGroupCd;
    /** 메뉴그룹명 */
    private String storeGroupNm;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 상품분류코드 */
    private String prodClassCd;
    /** 바코드 */
    private String barCd;
    /** 상품유형구분 */
    private String prodTypeFg;
    /** 등록여부 */
    private String regYn;
    /** 전체기간 여부 */
    private boolean chkDt;
    /** 판매가 */
    private String saleUprc;
    /** 브랜드코드 */
    private String hqBrandCd;


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

    public String getStoreTypeCd() {
        return storeTypeCd;
    }

    public void setStoreTypeCd(String storeTypeCd) {
        this.storeTypeCd = storeTypeCd;
    }

    public String getStoreTypeNm() {
        return storeTypeNm;
    }

    public void setStoreTypeNm(String storeTypeNm) {
        this.storeTypeNm = storeTypeNm;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOption01() {
        return option01;
    }

    public void setOption01(String option01) {
        this.option01 = option01;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
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

    public String getStoreGroupCd() {
        return storeGroupCd;
    }

    public void setStoreGroupCd(String storeGroupCd) {
        this.storeGroupCd = storeGroupCd;
    }

    public String getStoreGroupNm() {
        return storeGroupNm;
    }

    public void setStoreGroupNm(String storeGroupNm) {
        this.storeGroupNm = storeGroupNm;
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

    public String getProdTypeFg() {
        return prodTypeFg;
    }

    public void setProdTypeFg(String prodTypeFg) {
        this.prodTypeFg = prodTypeFg;
    }

    public String getRegYn() {
        return regYn;
    }

    public void setRegYn(String regYn) {
        this.regYn = regYn;
    }

    public boolean isChkDt() {
        return chkDt;
    }

    public void setChkDt(boolean chkDt) {
        this.chkDt = chkDt;
    }

    public String getSaleUprc() {
        return saleUprc;
    }

    public void setSaleUprc(String saleUprc) {
        this.saleUprc = saleUprc;
    }

    public String getHqBrandCd() {
        return hqBrandCd;
    }

    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
    }
}
