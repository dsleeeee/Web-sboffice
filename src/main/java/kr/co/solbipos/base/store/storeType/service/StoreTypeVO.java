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
    /** 적용구분 */
    private String applyFg;
    /** 매장타입자동적용(1106) */
    private String storeTypeAutoEnvstVal;
    /** 매장타입판매가설정(1107) */
    private String storeTypeApplyEnvstVal;
    /** 매장타입적용관리 코멘트용 remark */
    private String commentRemark;
    /** 내점가 */
    private String stinSaleUprc;
    /** 배달가 */
    private String dlvrSaleUprc;
    /** 포장가 */
    private String packSaleUprc;
    /** 프로시져 실행 결과 */
    private String result;
    /** 입력구분 */
    private String procFg;
    /** 사용자 아이디 */
    private String userId;
    /** 적용일시 */
    private String applyDt;
    /** 처리구분 */
    private String applyProcFg;

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

    public String getApplyFg() {
        return applyFg;
    }

    public void setApplyFg(String applyFg) {
        this.applyFg = applyFg;
    }

    public String getStoreTypeAutoEnvstVal() {
        return storeTypeAutoEnvstVal;
    }

    public void setStoreTypeAutoEnvstVal(String storeTypeAutoEnvstVal) {
        this.storeTypeAutoEnvstVal = storeTypeAutoEnvstVal;
    }

    public String getStoreTypeApplyEnvstVal() {
        return storeTypeApplyEnvstVal;
    }

    public void setStoreTypeApplyEnvstVal(String storeTypeApplyEnvstVal) {
        this.storeTypeApplyEnvstVal = storeTypeApplyEnvstVal;
    }

    public String getCommentRemark() {
        return commentRemark;
    }

    public void setCommentRemark(String commentRemark) {
        this.commentRemark = commentRemark;
    }

    public String getStinSaleUprc() {
        return stinSaleUprc;
    }

    public void setStinSaleUprc(String stinSaleUprc) {
        this.stinSaleUprc = stinSaleUprc;
    }

    public String getDlvrSaleUprc() {
        return dlvrSaleUprc;
    }

    public void setDlvrSaleUprc(String dlvrSaleUprc) {
        this.dlvrSaleUprc = dlvrSaleUprc;
    }

    public String getPackSaleUprc() {
        return packSaleUprc;
    }

    public void setPackSaleUprc(String packSaleUprc) {
        this.packSaleUprc = packSaleUprc;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getProcFg() {
        return procFg;
    }

    public void setProcFg(String procFg) {
        this.procFg = procFg;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApplyDt() {
        return applyDt;
    }

    public void setApplyDt(String applyDt) {
        this.applyDt = applyDt;
    }

    public String getApplyProcFg() {
        return applyProcFg;
    }

    public void setApplyProcFg(String applyProcFg) {
        this.applyProcFg = applyProcFg;
    }
}
