package kr.co.solbipos.stock.acins.acins.service;

import kr.co.solbipos.application.common.service.PageVO;

public class AcinsVO extends PageVO {

    private static final long serialVersionUID = -4157116571678476011L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 창고코드 */
    private String storageCd;
    /** 실사조사 일자 */
    private String acinsDate;
    /** 헤드 인덱스(DAY SEQUENCE) */
    private Integer seqNo;
    /** 제목 */
    private String acinsTitle;
    /** 처리구분 (0:입력, 1:확정) */
    private String procFg;
    /** 실사등록건수 */
    private Integer dtlCnt;
    /** 조정건수 */
    private Integer adjCnt;
    /** 총실사금액 */
    private Integer totAcinsAmt;
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
    /** 조정금액 */
    private Integer adjAmt;
    /** 전산 재고수량 */
    private Integer cmptCurrQty;
    /** 실사 재고수량 */
    private Integer acinsQty;
    /** 조정 재고수량 */
    private Integer adjQty;
    /** 실사금액 */
    private Integer acinsAmt;
    /** 주문단위-입수량 */
    private Integer poUnitQty;
    /** 실사비고 */
    private String remark;
    /** 바코드 */
    private String barcdCd;
    /** 상품분류 */
    private String prodClassCd;
    /** 거래처코드 */
    private String vendrCd;
    /** 거래처코드 array */
    private String arrVendrCd[];
    /** 실사 상품 STATUS */
    private String acinsProdStatus;
    /** 확정여부 */
    private String confirmFg;
    /** 상품코드 or 바코드 */
    private String prodBarcdCd;
    /** 실사구분 */
    private String acinsFg;
    /** 권역별 창고 코드 */
    private String  areaFg;
    /** 선택창고 */
    private String adjStorageCd;
    /** 사유코드 */
    private String acinsReason;
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
     * @return the acinsDate
     */
    public String getAcinsDate() {
        return acinsDate;
    }

    /**
     * @param acinsDate the acinsDate to set
     */
    public void setAcinsDate(String acinsDate) {
        this.acinsDate = acinsDate;
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
     * @return the acinsTitle
     */
    public String getAcinsTitle() {
        return acinsTitle;
    }

    /**
     * @param acinsTitle the acinsTitle to set
     */
    public void setAcinsTitle(String acinsTitle) {
        this.acinsTitle = acinsTitle;
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
     * @return the adjCnt
     */
    public Integer getAdjCnt() {
        return adjCnt;
    }

    /**
     * @param adjCnt the adjCnt to set
     */
    public void setAdjCnt(Integer adjCnt) {
        this.adjCnt = adjCnt;
    }

    /**
     * @return the totAcinsAmt
     */
    public Integer getTotAcinsAmt() {
        return totAcinsAmt;
    }

    /**
     * @param totAcinsAmt the totAcinsAmt to set
     */
    public void setTotAcinsAmt(Integer totAcinsAmt) {
        this.totAcinsAmt = totAcinsAmt;
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
     * @return the adjAmt
     */
    public Integer getAdjAmt() {
        return adjAmt;
    }

    /**
     * @param adjAmt the adjAmt to set
     */
    public void setAdjAmt(Integer adjAmt) {
        this.adjAmt = adjAmt;
    }

    /**
     * @return the cmptCurrQty
     */
    public Integer getCmptCurrQty() {
        return cmptCurrQty;
    }

    /**
     * @param cmptCurrQty the cmptCurrQty to set
     */
    public void setCmptCurrQty(Integer cmptCurrQty) {
        this.cmptCurrQty = cmptCurrQty;
    }

    /**
     * @return the acinsQty
     */
    public Integer getAcinsQty() {
        return acinsQty;
    }

    /**
     * @param acinsQty the acinsQty to set
     */
    public void setAcinsQty(Integer acinsQty) {
        this.acinsQty = acinsQty;
    }

    /**
     * @return the adjQty
     */
    public Integer getAdjQty() {
        return adjQty;
    }

    /**
     * @param adjQty the adjQty to set
     */
    public void setAdjQty(Integer adjQty) {
        this.adjQty = adjQty;
    }

    /**
     * @return the acinsAmt
     */
    public Integer getAcinsAmt() {
        return acinsAmt;
    }

    /**
     * @param acinsAmt the acinsAmt to set
     */
    public void setAcinsAmt(Integer acinsAmt) {
        this.acinsAmt = acinsAmt;
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
     * @return the arrVendrCd
     */
    public String[] getArrVendrCd() {
        return arrVendrCd;
    }

    /**
     * @param arrVendrCd the arrVendrCd to set
     */
    public void setArrVendrCd(String[] arrVendrCd) {
        this.arrVendrCd = arrVendrCd;
    }

    /**
     * @return the acinsProdStatus
     */
    public String getAcinsProdStatus() {
        return acinsProdStatus;
    }

    /**
     * @param acinsProdStatus the acinsProdStatus to set
     */
    public void setAcinsProdStatus(String acinsProdStatus) {
        this.acinsProdStatus = acinsProdStatus;
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
     * @return the acinsFg
     */
    public String getAcinsFg() {
        return acinsFg;
    }

    /**
     * @param acinsFg the acinsFg to set
     */
    public void setAcinsFg(String acinsFg) {
        this.acinsFg = acinsFg;
    }
    
    public String getAreaFg() {
		return areaFg;
	}

	public void setAreaFg(String areaFg) {
		this.areaFg = areaFg;
	}
	
	public String getAdjStorageCd() {
		return adjStorageCd;
	}

	public void setAdjStorageCd(String adjStorageCd) {
		this.adjStorageCd = adjStorageCd;
	}

    public String getAcinsReason() {
        return acinsReason;
    }

    public void setAcinsReason(String acinsReason) {
        this.acinsReason = acinsReason;
    }
}
