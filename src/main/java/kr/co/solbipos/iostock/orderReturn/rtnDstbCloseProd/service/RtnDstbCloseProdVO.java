package kr.co.solbipos.iostock.orderReturn.rtnDstbCloseProd.service;

import kr.co.solbipos.application.common.service.PageVO;

public class RtnDstbCloseProdVO extends PageVO {

    private static final long serialVersionUID = -9199275234917327209L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 출고요청일자 */
    private String reqDate;
    /** 순번 본사코드_출고예약일자에 따른 순번 */
    private Integer seq;
    /** 다중 매장코드 */
    private String[] arrStoreCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 사원번호 MD사원 */
    private String empNo;
    /** 전표구분 1:주문 -1:반품 */
    private Integer slipFg;
    /** 분배구분 TB_CM_NMCODE(NMCODE_GRP_CD='111') 0:일반 1:증정 2:전시용 9:기타 */
    private String dstbFg;
    /** 창고코드 */
    private String storageCd;
    /** 브랜드코드 */
    private String hqBrandCd;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 발주단위구분 */
    private String poUnitFg;
    /** 발주단위수량 */
    private Integer poUnitQty;
    /** 주문공급단가 */
    private Integer orderSplyUprc;
    /** 주문수량 주문단위 */
    private Integer orderUnitQty;
    /** 주문수량 나머지 */
    private Integer orderEtcQty;
    /** 주문수량합계 낱개 */
    private Integer orderTotQty;
    /** 주문금액 (ORDER_SPLY_UPRC * ORDER_TOT_QTY / PO_UNIT_QTY) */
    private Long orderAmt;
    /** 주문금액VAT */
    private Long orderVat;
    /** 주문금액합계 */
    private Long orderTot;
    /** MD공급단가 */
    private Integer mdSplyUprc;
    /** MD수량 주문단위 */
    private Integer mdUnitQty;
    /** MD수량 나머지 */
    private Integer mdEtcQty;
    /** MD수량합계 낱개 */
    private Integer mdTotQty;
    /** 이전 MD수량합계 낱개 */
    private Integer prevMdTotQty;
    /** MD금액 (MD_SPLY_UPRC * MD_TOT_QTY / PO_UNIT_QTY) */
    private Long mdAmt;
    /** MD금액VAT */
    private Long mdVat;
    /** MD금액합계 */
    private Long mdTot;
    /** 매니저공급단가 */
    private Integer mgrSplyUprc;
    /** 이전 매니저수량 주문단위 */
    private Integer prevMgrUnitQty;
    /** 이전 매니저수량 나머지 */
    private Integer prevMgrEtcQty;
    /** 이전 매니저수량합계 낱개 */
    private Integer prevMgrTotQty;
    /** 매니저수량 주문단위 */
    private Integer mgrUnitQty;
    /** 매니저수량 나머지 */
    private Integer mgrEtcQty;
    /** 매니저수량합계 낱개 */
    private Integer mgrTotQty;
    /** 매니저금액 (MGR_SPLY_UPRC * MGR_TOT_QTY / PO_UNIT_QTY) */
    private Long mgrAmt;
    /** 매니저금액VAT */
    private Long mgrVat;
    /** 매니저금액합계 */
    private Long mgrTot;
    /** 매니저확정일시 */
    private String mgrDt;
    /** 매니저확정자 */
    private String mgrId;
    /** 처리구분 TB_CM_NMCODE(NMCODE_GRP_CD='110') 00:등록, 10:MD확정, 20:분배마감, 30:전표수거 */
    private String procFg;
    /** 수정할 처리구분 TB_CM_NMCODE(NMCODE_GRP_CD='110') 00:등록, 10:MD확정, 20:분배마감, 30:전표수거 */
    private String updateProcFg;
    /** 주문전표번호 YYMM(4)+SEQ(6) */
    private String slipNo;
    /** 비고 */
    private String remark;
    /** 조회 구분 */
    private String dateFg;
    /** 발주단위허용구분 */
    private Integer poUnitAllowFg;
    /** 상품부가세구분 */
    private String vatFg01;
    /** 출고가-부가세포함여부 */
    private String envst0011;
    /** 확정여부 */
    private Boolean confirmYn;
    /** 바코드 */
    private String barcdCd;

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
     * @return the reqDate
     */
    public String getReqDate() {
        return reqDate;
    }

    /**
     * @param reqDate the reqDate to set
     */
    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    /**
     * @return the seq
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * @param seq the seq to set
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    /**
     * @return the arrStoreCd
     */
    public String[] getArrStoreCd() {
        return arrStoreCd;
    }

    /**
     * @param arrStoreCd the arrStoreCd to set
     */
    public void setArrStoreCd(String[] arrStoreCd) {
        this.arrStoreCd = arrStoreCd;
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
     * @return the storeNm
     */
    public String getStoreNm() {
        return storeNm;
    }

    /**
     * @param storeNm the storeNm to set
     */
    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    /**
     * @return the empNo
     */
    public String getEmpNo() {
        return empNo;
    }

    /**
     * @param empNo the empNo to set
     */
    public void setEmpNo(String empNo) {
        this.empNo = empNo;
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
     * @return the dstbFg
     */
    public String getDstbFg() {
        return dstbFg;
    }

    /**
     * @param dstbFg the dstbFg to set
     */
    public void setDstbFg(String dstbFg) {
        this.dstbFg = dstbFg;
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
     * @return the orderSplyUprc
     */
    public Integer getOrderSplyUprc() {
        return orderSplyUprc;
    }

    /**
     * @param orderSplyUprc the orderSplyUprc to set
     */
    public void setOrderSplyUprc(Integer orderSplyUprc) {
        this.orderSplyUprc = orderSplyUprc;
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
     * @return the mdSplyUprc
     */
    public Integer getMdSplyUprc() {
        return mdSplyUprc;
    }

    /**
     * @param mdSplyUprc the mdSplyUprc to set
     */
    public void setMdSplyUprc(Integer mdSplyUprc) {
        this.mdSplyUprc = mdSplyUprc;
    }

    /**
     * @return the mdUnitQty
     */
    public Integer getMdUnitQty() {
        return mdUnitQty;
    }

    /**
     * @param mdUnitQty the mdUnitQty to set
     */
    public void setMdUnitQty(Integer mdUnitQty) {
        this.mdUnitQty = mdUnitQty;
    }

    /**
     * @return the mdEtcQty
     */
    public Integer getMdEtcQty() {
        return mdEtcQty;
    }

    /**
     * @param mdEtcQty the mdEtcQty to set
     */
    public void setMdEtcQty(Integer mdEtcQty) {
        this.mdEtcQty = mdEtcQty;
    }

    /**
     * @return the mdTotQty
     */
    public Integer getMdTotQty() {
        return mdTotQty;
    }

    /**
     * @param mdTotQty the mdTotQty to set
     */
    public void setMdTotQty(Integer mdTotQty) {
        this.mdTotQty = mdTotQty;
    }

    /**
     * @return the prevMdTotQty
     */
    public Integer getPrevMdTotQty() {
        return prevMdTotQty;
    }

    /**
     * @param prevMdTotQty the prevMdTotQty to set
     */
    public void setPrevMdTotQty(Integer prevMdTotQty) {
        this.prevMdTotQty = prevMdTotQty;
    }

    /**
     * @return the mdAmt
     */
    public Long getMdAmt() {
        return mdAmt;
    }

    /**
     * @param mdAmt the mdAmt to set
     */
    public void setMdAmt(Long mdAmt) {
        this.mdAmt = mdAmt;
    }

    /**
     * @return the mdVat
     */
    public Long getMdVat() {
        return mdVat;
    }

    /**
     * @param mdVat the mdVat to set
     */
    public void setMdVat(Long mdVat) {
        this.mdVat = mdVat;
    }

    /**
     * @return the mdTot
     */
    public Long getMdTot() {
        return mdTot;
    }

    /**
     * @param mdTot the mdTot to set
     */
    public void setMdTot(Long mdTot) {
        this.mdTot = mdTot;
    }

    /**
     * @return the mgrSplyUprc
     */
    public Integer getMgrSplyUprc() {
        return mgrSplyUprc;
    }

    /**
     * @param mgrSplyUprc the mgrSplyUprc to set
     */
    public void setMgrSplyUprc(Integer mgrSplyUprc) {
        this.mgrSplyUprc = mgrSplyUprc;
    }

    /**
     * @return the prevMgrUnitQty
     */
    public Integer getPrevMgrUnitQty() {
        return prevMgrUnitQty;
    }

    /**
     * @param prevMgrUnitQty the prevMgrUnitQty to set
     */
    public void setPrevMgrUnitQty(Integer prevMgrUnitQty) {
        this.prevMgrUnitQty = prevMgrUnitQty;
    }

    /**
     * @return the prevMgrEtcQty
     */
    public Integer getPrevMgrEtcQty() {
        return prevMgrEtcQty;
    }

    /**
     * @param prevMgrEtcQty the prevMgrEtcQty to set
     */
    public void setPrevMgrEtcQty(Integer prevMgrEtcQty) {
        this.prevMgrEtcQty = prevMgrEtcQty;
    }

    /**
     * @return the prevMgrTotQty
     */
    public Integer getPrevMgrTotQty() {
        return prevMgrTotQty;
    }

    /**
     * @param prevMgrTotQty the prevMgrTotQty to set
     */
    public void setPrevMgrTotQty(Integer prevMgrTotQty) {
        this.prevMgrTotQty = prevMgrTotQty;
    }

    /**
     * @return the mgrUnitQty
     */
    public Integer getMgrUnitQty() {
        return mgrUnitQty;
    }

    /**
     * @param mgrUnitQty the mgrUnitQty to set
     */
    public void setMgrUnitQty(Integer mgrUnitQty) {
        this.mgrUnitQty = mgrUnitQty;
    }

    /**
     * @return the mgrEtcQty
     */
    public Integer getMgrEtcQty() {
        return mgrEtcQty;
    }

    /**
     * @param mgrEtcQty the mgrEtcQty to set
     */
    public void setMgrEtcQty(Integer mgrEtcQty) {
        this.mgrEtcQty = mgrEtcQty;
    }

    /**
     * @return the mgrTotQty
     */
    public Integer getMgrTotQty() {
        return mgrTotQty;
    }

    /**
     * @param mgrTotQty the mgrTotQty to set
     */
    public void setMgrTotQty(Integer mgrTotQty) {
        this.mgrTotQty = mgrTotQty;
    }

    /**
     * @return the mgrAmt
     */
    public Long getMgrAmt() {
        return mgrAmt;
    }

    /**
     * @param mgrAmt the mgrAmt to set
     */
    public void setMgrAmt(Long mgrAmt) {
        this.mgrAmt = mgrAmt;
    }

    /**
     * @return the mgrVat
     */
    public Long getMgrVat() {
        return mgrVat;
    }

    /**
     * @param mgrVat the mgrVat to set
     */
    public void setMgrVat(Long mgrVat) {
        this.mgrVat = mgrVat;
    }

    /**
     * @return the mgrTot
     */
    public Long getMgrTot() {
        return mgrTot;
    }

    /**
     * @param mgrTot the mgrTot to set
     */
    public void setMgrTot(Long mgrTot) {
        this.mgrTot = mgrTot;
    }

    /**
     * @return the mgrDt
     */
    public String getMgrDt() {
        return mgrDt;
    }

    /**
     * @param mgrDt the mgrDt to set
     */
    public void setMgrDt(String mgrDt) {
        this.mgrDt = mgrDt;
    }

    /**
     * @return the mgrId
     */
    public String getMgrId() {
        return mgrId;
    }

    /**
     * @param mgrId the mgrId to set
     */
    public void setMgrId(String mgrId) {
        this.mgrId = mgrId;
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
     * @return the updateProcFg
     */
    public String getUpdateProcFg() {
        return updateProcFg;
    }

    /**
     * @param updateProcFg the updateProcFg to set
     */
    public void setUpdateProcFg(String updateProcFg) {
        this.updateProcFg = updateProcFg;
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
     * @return the poUnitAllowFg
     */
    public Integer getPoUnitAllowFg() {
        return poUnitAllowFg;
    }

    /**
     * @param poUnitAllowFg the poUnitAllowFg to set
     */
    public void setPoUnitAllowFg(Integer poUnitAllowFg) {
        this.poUnitAllowFg = poUnitAllowFg;
    }

    /**
     * @return the vatFg01
     */
    public String getVatFg01() {
        return vatFg01;
    }

    /**
     * @param vatFg01 the vatFg01 to set
     */
    public void setVatFg01(String vatFg01) {
        this.vatFg01 = vatFg01;
    }

    /**
     * @return the envst0011
     */
    public String getEnvst0011() {
        return envst0011;
    }

    /**
     * @param envst0011 the envst0011 to set
     */
    public void setEnvst0011(String envst0011) {
        this.envst0011 = envst0011;
    }

    /**
     * @return the confirmYn
     */
    public Boolean getConfirmYn() {
        return confirmYn;
    }

    /**
     * @param confirmYn the confirmYn to set
     */
    public void setConfirmYn(Boolean confirmYn) {
        this.confirmYn = confirmYn;
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
}
