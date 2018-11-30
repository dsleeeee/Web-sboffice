package kr.co.solbipos.iostock.vendr.vendrInstock.service;

import kr.co.solbipos.application.common.service.PageVO;

public class VendrInstockVO extends PageVO {

    private static final long serialVersionUID = 4423634834285795632L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 전표번호 YYMM(4)+SEQ(4) */
    private String slipNo;
    /** 전표구분 1:입고 -1:출고 */
    private Integer slipFg;
    /** 거래처코드 */
    private String vendrCd;
    /** 처리구분 0:등록 1:확정 */
    private String procFg;
    /** 발주/반출 전표번호 YYMM(4)+SEQ(4) */
    private String orderSlipNo;
    /** 입고일자 */
    private String instockDate;
    /** 상세건수 */
    private Integer dtlCnt;
    /** 입고/출고수량합계 낱개 */
    private Integer inTotQty;
    /** 입고/출고금액 */
    private Long inAmt;
    /** 입고/출고VAT */
    private Long inVat;
    /** 입고/출고합계금액 */
    private Long inTot;
    /** 비고 */
    private String remark;
    /** 등록일시 */
    private String inRegDt;
    /** 등록자 */
    private String inRegId;
    /** 확정일시 */
    private String confmDt;
    /** 확정자 */
    private String confmId;
    /** 창고코드 */
    private String storageCd;
    /** 브랜드코드 */
    private String hqBrandCd;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 바코드 */
    private String barcdCd;
    /** 상품분류코드 */
    private String prodClassCd;
    /** 원가단가 */
    private Float costUprc;
    /** 발주단위구분 */
    private String poUnitFg;
    /** 발주단위수량 */
    private Integer poUnitQty;
    /** 입고/출고수량 주문단위 */
    private Integer inUnitQty;
    /** 입고/출고수량 나머지 */
    private Integer inEtcQty;
    /** 발주/반출수량 주문단위 */
    private Integer prevInUnitQty;
    /** 발주/반출수량 나머지 */
    private Integer prevInEtcQty;
    /** 발주/반출수량합계 낱개 */
    private Integer prevInTotQty;
    /** 년월 */
    private String yymm;
    /** 조회일자구분 */
    private String dateFg;
    /** 발주/무발주 구분 */
    private String instockType;
    /** 매장공급가 동시저장 구분 */
    private String storeSplyFg;
    /** 공급가 */
    private Long splyUprc;

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
     * @return the slipNo
     */
    public String getSlipNo() {
        return slipNo;
    }

    /**
     * @param slipNo the slipNo to set
     */
    public void setSlipNo(String slipNo) {
        this.slipNo = slipNo;
    }

    /**
     * @return the slipFg
     */
    public Integer getSlipFg() {
        return slipFg;
    }

    /**
     * @param slipFg the slipFg to set
     */
    public void setSlipFg(Integer slipFg) {
        this.slipFg = slipFg;
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
     * @return the orderSlipNo
     */
    public String getOrderSlipNo() {
        return orderSlipNo;
    }

    /**
     * @param orderSlipNo the orderSlipNo to set
     */
    public void setOrderSlipNo(String orderSlipNo) {
        this.orderSlipNo = orderSlipNo;
    }

    /**
     * @return the instockDate
     */
    public String getInstockDate() {
        return instockDate;
    }

    /**
     * @param instockDate the instockDate to set
     */
    public void setInstockDate(String instockDate) {
        this.instockDate = instockDate;
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
     * @return the inTotQty
     */
    public Integer getInTotQty() {
        return inTotQty;
    }

    /**
     * @param inTotQty the inTotQty to set
     */
    public void setInTotQty(Integer inTotQty) {
        this.inTotQty = inTotQty;
    }

    /**
     * @return the inAmt
     */
    public Long getInAmt() {
        return inAmt;
    }

    /**
     * @param inAmt the inAmt to set
     */
    public void setInAmt(Long inAmt) {
        this.inAmt = inAmt;
    }

    /**
     * @return the inVat
     */
    public Long getInVat() {
        return inVat;
    }

    /**
     * @param inVat the inVat to set
     */
    public void setInVat(Long inVat) {
        this.inVat = inVat;
    }

    /**
     * @return the inTot
     */
    public Long getInTot() {
        return inTot;
    }

    /**
     * @param inTot the inTot to set
     */
    public void setInTot(Long inTot) {
        this.inTot = inTot;
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
     * @return the inRegDt
     */
    public String getInRegDt() {
        return inRegDt;
    }

    /**
     * @param inRegDt the inRegDt to set
     */
    public void setInRegDt(String inRegDt) {
        this.inRegDt = inRegDt;
    }

    /**
     * @return the inRegId
     */
    public String getInRegId() {
        return inRegId;
    }

    /**
     * @param inRegId the inRegId to set
     */
    public void setInRegId(String inRegId) {
        this.inRegId = inRegId;
    }

    /**
     * @return the confmDt
     */
    public String getConfmDt() {
        return confmDt;
    }

    /**
     * @param confmDt the confmDt to set
     */
    public void setConfmDt(String confmDt) {
        this.confmDt = confmDt;
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
     * @return the costUprc
     */
    public Float getCostUprc() {
        return costUprc;
    }

    /**
     * @param costUprc the costUprc to set
     */
    public void setCostUprc(Float costUprc) {
        this.costUprc = costUprc;
    }

    /**
     * @return the poUnitFg
     */
    public String getPoUnitFg() {
        return poUnitFg;
    }

    /**
     * @param poUnitFg the poUnitFg to set
     */
    public void setPoUnitFg(String poUnitFg) {
        this.poUnitFg = poUnitFg;
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
     * @return the inUnitQty
     */
    public Integer getInUnitQty() {
        return inUnitQty;
    }

    /**
     * @param inUnitQty the inUnitQty to set
     */
    public void setInUnitQty(Integer inUnitQty) {
        this.inUnitQty = inUnitQty;
    }

    /**
     * @return the inEtcQty
     */
    public Integer getInEtcQty() {
        return inEtcQty;
    }

    /**
     * @param inEtcQty the inEtcQty to set
     */
    public void setInEtcQty(Integer inEtcQty) {
        this.inEtcQty = inEtcQty;
    }

    /**
     * @return the prevInUnitQty
     */
    public Integer getPrevInUnitQty() {
        return prevInUnitQty;
    }

    /**
     * @param prevInUnitQty the prevInUnitQty to set
     */
    public void setPrevInUnitQty(Integer prevInUnitQty) {
        this.prevInUnitQty = prevInUnitQty;
    }

    /**
     * @return the prevInEtcQty
     */
    public Integer getPrevInEtcQty() {
        return prevInEtcQty;
    }

    /**
     * @param prevInEtcQty the prevInEtcQty to set
     */
    public void setPrevInEtcQty(Integer prevInEtcQty) {
        this.prevInEtcQty = prevInEtcQty;
    }

    /**
     * @return the prevInTotQty
     */
    public Integer getPrevInTotQty() {
        return prevInTotQty;
    }

    /**
     * @param prevInTotQty the prevInTotQty to set
     */
    public void setPrevInTotQty(Integer prevInTotQty) {
        this.prevInTotQty = prevInTotQty;
    }

    /**
     * @return the yymm
     */
    public String getYymm() {
        return yymm;
    }

    /**
     * @param yymm the yymm to set
     */
    public void setYymm(String yymm) {
        this.yymm = yymm;
    }

    /**
     * @return the dateFg
     */
    public String getDateFg() {
        return dateFg;
    }

    /**
     * @param dateFg the dateFg to set
     */
    public void setDateFg(String dateFg) {
        this.dateFg = dateFg;
    }

    /**
     * @return the instockType
     */
    public String getInstockType() {
        return instockType;
    }

    /**
     * @param instockType the instockType to set
     */
    public void setInstockType(String instockType) {
        this.instockType = instockType;
    }

    /**
     * @return the storeSplyFg
     */
    public String getStoreSplyFg() {
        return storeSplyFg;
    }

    /**
     * @param storeSplyFg the storeSplyFg to set
     */
    public void setStoreSplyFg(String storeSplyFg) {
        this.storeSplyFg = storeSplyFg;
    }

    /**
     * @return the splyUprc
     */
    public Long getSplyUprc() {
        return splyUprc;
    }

    /**
     * @param splyUprc the splyUprc to set
     */
    public void setSplyUprc(Long splyUprc) {
        this.splyUprc = splyUprc;
    }
}
