package kr.co.solbipos.iostock.vendr.vendrOrder.service;

import kr.co.solbipos.application.common.service.PageVO;

public class VendrOrderVO extends PageVO {

    private static final long serialVersionUID = -251491479232705165L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 전표번호 YYMM(4)+SEQ(4) */
    private String slipNo;
    /** 전표구분 1:발주 -1:반출 */
    private Integer slipFg;
    /** 거래처코드 */
    private String vendrCd;
    /** 처리구분 0:등록 1:확정 2:보류 3:업체확인 4:분할입고/반출 5:입고/반출완료 */
    private String procFg;
    /** 발주/반출타입 */
    private String orderType;
    /** 반출사유코드 */
    private String rtnReasonCd;
    /** 발주/반출일자 */
    private String orderDate;
    /** 입고/반출요청일자 */
    private String orderReqDate;
    /** 상세건수 */
    private Integer dtlCnt;
    /** 발주/반출수량합계 낱개 */
    private Integer orderQty;
    /** 발주/반출금액 */
    private Long orderAmt;
    /** 발주/반출VAT */
    private Long orderVat;
    /** 발주/반출합계금액 */
    private Long orderTot;
    /** 입고/출고수량합계 낱개 */
    private Integer inTotQty;
    /** 입고/출고금액 */
    private Long inAmt;
    /** 입고/출고VAT */
    private Long inVat;
    /** 입고/출고합계금액 */
    private Long inTot;
    /** 입고/출고최초일자 */
    private String inFirstDate;
    /** 입고/출고최종일자 */
    private String inLastDate;
    /** 비고 */
    private String remark;
    /** 등록일시 */
    private String orderRegDt;
    /** 등록자 */
    private String orderRegId;
    /** 확정일시 */
    private String confmDt;
    /** 확정자 */
    private String confmId;
    /** 업체확인일시 */
    private String vendrChkDt;
    /** 업체확인자 */
    private String vendrChkId;
    /** 완료일시 */
    private String endDt;
    /** 완료자 */
    private String endId;
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
    /** 발주/반출수량 주문단위 */
    private Integer orderUnitQty;
    /** 발주/반출수량 나머지 */
    private Integer orderEtcQty;
    /** 발주/반출수량합계 낱개 */
    private Integer orderTotQty;
    /** 발주/반출수량 주문단위 */
    private Integer prevOrderUnitQty;
    /** 발주/반출수량 나머지 */
    private Integer prevOrderEtcQty;
    /** 발주/반출수량합계 낱개 */
    private Integer prevOrderTotQty;
    /** 입고/출고수량 주문단위 */
    private Integer inUnitQty;
    /** 입고/출고수량 나머지 */
    private Integer inEtcQty;
    /** 년월 */
    private String yymm;
    /** 안전재고이하구분 */
    private String safeStockFg;

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
     * @return the orderType
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * @param orderType the orderType to set
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * @return the rtnReasonCd
     */
    public String getRtnReasonCd() {
        return rtnReasonCd;
    }

    /**
     * @param rtnReasonCd the rtnReasonCd to set
     */
    public void setRtnReasonCd(String rtnReasonCd) {
        this.rtnReasonCd = rtnReasonCd;
    }

    /**
     * @return the orderDate
     */
    public String getOrderDate() {
        return orderDate;
    }

    /**
     * @param orderDate the orderDate to set
     */
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * @return the orderReqDate
     */
    public String getOrderReqDate() {
        return orderReqDate;
    }

    /**
     * @param orderReqDate the orderReqDate to set
     */
    public void setOrderReqDate(String orderReqDate) {
        this.orderReqDate = orderReqDate;
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
     * @return the orderQty
     */
    public Integer getOrderQty() {
        return orderQty;
    }

    /**
     * @param orderQty the orderQty to set
     */
    public void setOrderQty(Integer orderQty) {
        this.orderQty = orderQty;
    }

    /**
     * @return the orderAmt
     */
    public Long getOrderAmt() {
        return orderAmt;
    }

    /**
     * @param orderAmt the orderAmt to set
     */
    public void setOrderAmt(Long orderAmt) {
        this.orderAmt = orderAmt;
    }

    /**
     * @return the orderVat
     */
    public Long getOrderVat() {
        return orderVat;
    }

    /**
     * @param orderVat the orderVat to set
     */
    public void setOrderVat(Long orderVat) {
        this.orderVat = orderVat;
    }

    /**
     * @return the orderTot
     */
    public Long getOrderTot() {
        return orderTot;
    }

    /**
     * @param orderTot the orderTot to set
     */
    public void setOrderTot(Long orderTot) {
        this.orderTot = orderTot;
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
     * @return the inFirstDate
     */
    public String getInFirstDate() {
        return inFirstDate;
    }

    /**
     * @param inFirstDate the inFirstDate to set
     */
    public void setInFirstDate(String inFirstDate) {
        this.inFirstDate = inFirstDate;
    }

    /**
     * @return the inLastDate
     */
    public String getInLastDate() {
        return inLastDate;
    }

    /**
     * @param inLastDate the inLastDate to set
     */
    public void setInLastDate(String inLastDate) {
        this.inLastDate = inLastDate;
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
     * @return the orderRegDt
     */
    public String getOrderRegDt() {
        return orderRegDt;
    }

    /**
     * @param orderRegDt the orderRegDt to set
     */
    public void setOrderRegDt(String orderRegDt) {
        this.orderRegDt = orderRegDt;
    }

    /**
     * @return the orderRegId
     */
    public String getOrderRegId() {
        return orderRegId;
    }

    /**
     * @param orderRegId the orderRegId to set
     */
    public void setOrderRegId(String orderRegId) {
        this.orderRegId = orderRegId;
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
     * @return the vendrChkDt
     */
    public String getVendrChkDt() {
        return vendrChkDt;
    }

    /**
     * @param vendrChkDt the vendrChkDt to set
     */
    public void setVendrChkDt(String vendrChkDt) {
        this.vendrChkDt = vendrChkDt;
    }

    /**
     * @return the vendrChkId
     */
    public String getVendrChkId() {
        return vendrChkId;
    }

    /**
     * @param vendrChkId the vendrChkId to set
     */
    public void setVendrChkId(String vendrChkId) {
        this.vendrChkId = vendrChkId;
    }

    /**
     * @return the endDt
     */
    public String getEndDt() {
        return endDt;
    }

    /**
     * @param endDt the endDt to set
     */
    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    /**
     * @return the endId
     */
    public String getEndId() {
        return endId;
    }

    /**
     * @param endId the endId to set
     */
    public void setEndId(String endId) {
        this.endId = endId;
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
     * @return the orderUnitQty
     */
    public Integer getOrderUnitQty() {
        return orderUnitQty;
    }

    /**
     * @param orderUnitQty the orderUnitQty to set
     */
    public void setOrderUnitQty(Integer orderUnitQty) {
        this.orderUnitQty = orderUnitQty;
    }

    /**
     * @return the orderEtcQty
     */
    public Integer getOrderEtcQty() {
        return orderEtcQty;
    }

    /**
     * @param orderEtcQty the orderEtcQty to set
     */
    public void setOrderEtcQty(Integer orderEtcQty) {
        this.orderEtcQty = orderEtcQty;
    }

    /**
     * @return the orderTotQty
     */
    public Integer getOrderTotQty() {
        return orderTotQty;
    }

    /**
     * @param orderTotQty the orderTotQty to set
     */
    public void setOrderTotQty(Integer orderTotQty) {
        this.orderTotQty = orderTotQty;
    }

    /**
     * @return the prevOrderUnitQty
     */
    public Integer getPrevOrderUnitQty() {
        return prevOrderUnitQty;
    }

    /**
     * @param prevOrderUnitQty the prevOrderUnitQty to set
     */
    public void setPrevOrderUnitQty(Integer prevOrderUnitQty) {
        this.prevOrderUnitQty = prevOrderUnitQty;
    }

    /**
     * @return the prevOrderEtcQty
     */
    public Integer getPrevOrderEtcQty() {
        return prevOrderEtcQty;
    }

    /**
     * @param prevOrderEtcQty the prevOrderEtcQty to set
     */
    public void setPrevOrderEtcQty(Integer prevOrderEtcQty) {
        this.prevOrderEtcQty = prevOrderEtcQty;
    }

    /**
     * @return the prevOrderTotQty
     */
    public Integer getPrevOrderTotQty() {
        return prevOrderTotQty;
    }

    /**
     * @param prevOrderTotQty the prevOrderTotQty to set
     */
    public void setPrevOrderTotQty(Integer prevOrderTotQty) {
        this.prevOrderTotQty = prevOrderTotQty;
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
     * @return the safeStockFg
     */
    public String getSafeStockFg() {
        return safeStockFg;
    }

    /**
     * @param safeStockFg the safeStockFg to set
     */
    public void setSafeStockFg(String safeStockFg) {
        this.safeStockFg = safeStockFg;
    }
}
