package kr.co.solbipos.stock.disuse.disuse.service;

import kr.co.solbipos.application.common.service.PageVO;

public class DisuseVO extends PageVO {

    private static final long serialVersionUID = 2679641790662789202L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 창고코드 */
    private String storageCd;
    /** 조정조사 일자 */
    private String disuseDate;
    /** 헤드 인덱스(DAY SEQUENCE) */
    private Integer seqNo;
    /** 제목 */
    private String disuseTitle;
    /** 처리구분 (0:입력, 1:확정) */
    private String procFg;
    /** 조정등록건수 */
    private Integer dtlCnt;
    /** 총조정금액 */
    private Integer totAdjAmt;
    /** 확정일자 */
    private String confmDate;
    /** 확정자 */
    private String confmId;
    /** 브랜드코드 */
    private String hqBrandCd;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 원가 */
    private Integer costUprc;
    /** 조정 재고수량 */
    private Integer disuseQty;
    /** 조정금액 */
    private Integer disuseAmt;
    /** 전산 재고수량 */
    private Integer currQty;
    /** 주문단위-입수량 */
    private Integer poUnitQty;
    /** 조정비고 */
    private String remark;
    /** 바코드 */
    private String barcdCd;
    /** 상품분류 */
    private String prodClassCd;
    /** 거래처코드 */
    private String vendrCd;
    /** 조정 상품 STATUS */
    private String disuseProdStatus;
    /** 확정여부 */
    private String confirmFg;
    /** 상품코드 or 바코드 */
    private String prodBarcdCd;
    /** 조정구분 */
    private String disuseFg;

    /**
     * @return the hqOfficeCd
     */
    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    /**
     * @param hqOfficeCd the hqOfficeCd to set
     */
    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    /**
     * @return the storeCd
     */
    public String getStoreCd() {
        return storeCd;
    }

    /**
     * @param storeCd the storeCd to set
     */
    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    /**
     * @return the storageCd
     */
    public String getStorageCd() {
        return storageCd;
    }

    /**
     * @param storageCd the storageCd to set
     */
    public void setStorageCd(String storageCd) {
        this.storageCd = storageCd;
    }

    /**
     * @return the disuseDate
     */
    public String getDisuseDate() {
        return disuseDate;
    }

    /**
     * @param disuseDate the disuseDate to set
     */
    public void setDisuseDate(String disuseDate) {
        this.disuseDate = disuseDate;
    }

    /**
     * @return the seqNo
     */
    public Integer getSeqNo() {
        return seqNo;
    }

    /**
     * @param seqNo the seqNo to set
     */
    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    /**
     * @return the disuseTitle
     */
    public String getDisuseTitle() {
        return disuseTitle;
    }

    /**
     * @param disuseTitle the disuseTitle to set
     */
    public void setDisuseTitle(String disuseTitle) {
        this.disuseTitle = disuseTitle;
    }

    /**
     * @return the procFg
     */
    public String getProcFg() {
        return procFg;
    }

    /**
     * @param procFg the procFg to set
     */
    public void setProcFg(String procFg) {
        this.procFg = procFg;
    }

    /**
     * @return the dtlCnt
     */
    public Integer getDtlCnt() {
        return dtlCnt;
    }

    /**
     * @param dtlCnt the dtlCnt to set
     */
    public void setDtlCnt(Integer dtlCnt) {
        this.dtlCnt = dtlCnt;
    }

    /**
     * @return the totAdjAmt
     */
    public Integer getTotAdjAmt() {
        return totAdjAmt;
    }

    /**
     * @param totAdjAmt the totAdjAmt to set
     */
    public void setTotAdjAmt(Integer totAdjAmt) {
        this.totAdjAmt = totAdjAmt;
    }

    /**
     * @return the confmDate
     */
    public String getConfmDate() {
        return confmDate;
    }

    /**
     * @param confmDate the confmDate to set
     */
    public void setConfmDate(String confmDate) {
        this.confmDate = confmDate;
    }

    /**
     * @return the confmId
     */
    public String getConfmId() {
        return confmId;
    }

    /**
     * @param confmId the confmId to set
     */
    public void setConfmId(String confmId) {
        this.confmId = confmId;
    }

    /**
     * @return the hqBrandCd
     */
    public String getHqBrandCd() {
        return hqBrandCd;
    }

    /**
     * @param hqBrandCd the hqBrandCd to set
     */
    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
    }

    /**
     * @return the prodCd
     */
    public String getProdCd() {
        return prodCd;
    }

    /**
     * @param prodCd the prodCd to set
     */
    public void setProdCd(String prodCd) {
        this.prodCd = prodCd;
    }

    /**
     * @return the prodNm
     */
    public String getProdNm() {
        return prodNm;
    }

    /**
     * @param prodNm the prodNm to set
     */
    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    /**
     * @return the costUprc
     */
    public Integer getCostUprc() {
        return costUprc;
    }

    /**
     * @param costUprc the costUprc to set
     */
    public void setCostUprc(Integer costUprc) {
        this.costUprc = costUprc;
    }

    /**
     * @return the disuseQty
     */
    public Integer getDisuseQty() {
        return disuseQty;
    }

    /**
     * @param disuseQty the disuseQty to set
     */
    public void setDisuseQty(Integer disuseQty) {
        this.disuseQty = disuseQty;
    }

    /**
     * @return the disuseAmt
     */
    public Integer getDisuseAmt() {
        return disuseAmt;
    }

    /**
     * @param disuseAmt the disuseAmt to set
     */
    public void setDisuseAmt(Integer disuseAmt) {
        this.disuseAmt = disuseAmt;
    }

    /**
     * @return the currQty
     */
    public Integer getCurrQty() {
        return currQty;
    }

    /**
     * @param currQty the currQty to set
     */
    public void setCurrQty(Integer currQty) {
        this.currQty = currQty;
    }

    /**
     * @return the poUnitQty
     */
    public Integer getPoUnitQty() {
        return poUnitQty;
    }

    /**
     * @param poUnitQty the poUnitQty to set
     */
    public void setPoUnitQty(Integer poUnitQty) {
        this.poUnitQty = poUnitQty;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the barcdCd
     */
    public String getBarcdCd() {
        return barcdCd;
    }

    /**
     * @param barcdCd the barcdCd to set
     */
    public void setBarcdCd(String barcdCd) {
        this.barcdCd = barcdCd;
    }

    /**
     * @return the prodClassCd
     */
    public String getProdClassCd() {
        return prodClassCd;
    }

    /**
     * @param prodClassCd the prodClassCd to set
     */
    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    /**
     * @return the vendrCd
     */
    public String getVendrCd() {
        return vendrCd;
    }

    /**
     * @param vendrCd the vendrCd to set
     */
    public void setVendrCd(String vendrCd) {
        this.vendrCd = vendrCd;
    }

    /**
     * @return the disuseProdStatus
     */
    public String getDisuseProdStatus() {
        return disuseProdStatus;
    }

    /**
     * @param disuseProdStatus the disuseProdStatus to set
     */
    public void setDisuseProdStatus(String disuseProdStatus) {
        this.disuseProdStatus = disuseProdStatus;
    }

    /**
     * @return the confirmFg
     */
    public String getConfirmFg() {
        return confirmFg;
    }

    /**
     * @param confirmFg the confirmFg to set
     */
    public void setConfirmFg(String confirmFg) {
        this.confirmFg = confirmFg;
    }

    /**
     * @return the prodBarcdCd
     */
    public String getProdBarcdCd() {
        return prodBarcdCd;
    }

    /**
     * @param prodBarcdCd the prodBarcdCd to set
     */
    public void setProdBarcdCd(String prodBarcdCd) {
        this.prodBarcdCd = prodBarcdCd;
    }

    /**
     * @return the disuseFg
     */
    public String getDisuseFg() {
        return disuseFg;
    }

    /**
     * @param disuseFg the disuseFg to set
     */
    public void setDisuseFg(String disuseFg) {
        this.disuseFg = disuseFg;
    }
}
