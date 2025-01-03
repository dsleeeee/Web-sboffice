package kr.co.solbipos.iostock.orderReturn.rtnOutstockData.service;

import kr.co.solbipos.application.common.service.PageVO;

public class RtnOutstockDataVO extends PageVO {

    private static final long serialVersionUID = 2990625898567699115L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 전표번호 YYMM(4)+SEQ(6) */
    private String slipNo;
    /** 매장코드 */
    private String storeCd;
    /** 사원번호 MD사원 */
    private String empNo;
    /** 전표구분 1:주문 -1:반품 */
    private Integer slipFg;
    /** 전표종류 TB_CM_NMCODE(NMCODE_GRP_CD=') 0:일반 1:물량오류 2:이동 */
    private String slipKind;
    /** 처리구분 TB_CM_NMCODE(NMCODE_GRP_CD=') 1:수주확정 2:출고확정 3:입고확정 */
    private String procFg;
    /** 수정할 처리구분 TB_CM_NMCODE(NMCODE_GRP_CD='110') 00:등록, 10:MD확정, 20:분배마감, 30:전표수거 */
    private String updateProcFg;
    /** 상세건수 */
    private Integer dtlCnt;
    /** 주문수량 주문단위 */
    private Integer orderUnitQty;
    /** 주문수량 나머지 */
    private Integer orderEtcQty;
    /** 주문수량합계 낱개 */
    private Integer orderTotQty;
    /** 주문금액 */
    private Integer orderAmt;
    /** 주문금액VAT */
    private Integer orderVat;
    /** 주문금액합계 */
    private Integer orderTot;
    /** MD수량1 주문단위 */
    private Integer mdUnitQty;
    /** MD수량2 나머지 */
    private Integer mdEtcQty;
    /** MD수량 낱개 */
    private Integer mdTotQty;
    /** MD금액 */
    private Integer mdAmt;
    /** MD금액VAT */
    private Integer mdVat;
    /** MD금액합계 */
    private Integer mdTot;
    /** 매니저수량 주문단위 */
    private Integer mgrUnitQty;
    /** 매니저수량 나머지 */
    private Integer mgrEtcQty;
    /** 매니저수량합계 낱개 */
    private Integer mgrTotQty;
    /** 매니저금액 */
    private Integer mgrAmt;
    /** 매니저금액VAT */
    private Integer mgrVat;
    /** 매니저금액합계 */
    private Integer mgrTot;
    /** 출고일자 수불기준일자 */
    private String outDate;
    /** 출고수량 주문단위 */
    private Integer outUnitQty;
    /** 출고수량 나머지 */
    private Integer outEtcQty;
    /** 출고수량합계 낱개 */
    private Integer outTotQty;
    /** 출고금액 */
    private Integer outAmt;
    /** 출고금액VAT */
    private Integer outVat;
    /** 출고금액합계 */
    private Integer outTot;
    /** 출고일시 */
    private String outDt;
    /** 출고자 */
    private String outId;
    /** 입고일자 수불기준일자 */
    private String inDate;
    /** 입고수량 주문단위 */
    private Integer inUnitQty;
    /** 입고수량 나머지 */
    private Integer inEtcQty;
    /** 입고수량합계 낱개 */
    private Integer inTotQty;
    /** 입고금액 */
    private Integer inAmt;
    /** 입고금액VAT */
    private Integer inVat;
    /** 입고금액합계 */
    private Integer inTot;
    /** 입고일시 */
    private String inDt;
    /** 입고자 */
    private String inId;
    /** 패널티유무 Y:존재 N:미존재 */
    private String penaltyFg;
    /** 비고 */
    private String remark;
    /** 세금계산서발행일자 */
    private String billIssueDate;
    /** 세금계산서발행일시 */
    private String billIssueDt;
    /** 세금계산서발행자 */
    private String billIssueId;
    /** 직납거래처코드 */
    private String vendrCd;
    /** 본사비고 매장은 열람불가 */
    private String hqRemark;
    /** 배송기사코드 */
    private String dlvrCd;
    /** 조회 구분 */
    private String dateFg;
    /** 전표번호 생성을 위한 YYMM 포맷 날짜 */
    private String yymm;
    /** 브랜드코드 */
    private String hqBrandCd;
    /** 출고예약일자 */
    private String reqDate;
    /** 주문전표번호*/
    private String orderSlipNo;
    /** 주문전표번호*/
    private String[] orderSlipNoList;

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
     * @return the slipKind
     */
    public String getSlipKind() {
        return slipKind;
    }

    /**
     * @param slipKind the slipKind to set
     */
    public void setSlipKind(String slipKind) {
        this.slipKind = slipKind;
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
    public Integer getOrderAmt() {
        return orderAmt;
    }

    /**
     * @param orderAmt the orderAmt to set
     */
    public void setOrderAmt(Integer orderAmt) {
        this.orderAmt = orderAmt;
    }

    /**
     * @return the orderVat
     */
    public Integer getOrderVat() {
        return orderVat;
    }

    /**
     * @param orderVat the orderVat to set
     */
    public void setOrderVat(Integer orderVat) {
        this.orderVat = orderVat;
    }

    /**
     * @return the orderTot
     */
    public Integer getOrderTot() {
        return orderTot;
    }

    /**
     * @param orderTot the orderTot to set
     */
    public void setOrderTot(Integer orderTot) {
        this.orderTot = orderTot;
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
     * @return the mdAmt
     */
    public Integer getMdAmt() {
        return mdAmt;
    }

    /**
     * @param mdAmt the mdAmt to set
     */
    public void setMdAmt(Integer mdAmt) {
        this.mdAmt = mdAmt;
    }

    /**
     * @return the mdVat
     */
    public Integer getMdVat() {
        return mdVat;
    }

    /**
     * @param mdVat the mdVat to set
     */
    public void setMdVat(Integer mdVat) {
        this.mdVat = mdVat;
    }

    /**
     * @return the mdTot
     */
    public Integer getMdTot() {
        return mdTot;
    }

    /**
     * @param mdTot the mdTot to set
     */
    public void setMdTot(Integer mdTot) {
        this.mdTot = mdTot;
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
    public Integer getMgrAmt() {
        return mgrAmt;
    }

    /**
     * @param mgrAmt the mgrAmt to set
     */
    public void setMgrAmt(Integer mgrAmt) {
        this.mgrAmt = mgrAmt;
    }

    /**
     * @return the mgrVat
     */
    public Integer getMgrVat() {
        return mgrVat;
    }

    /**
     * @param mgrVat the mgrVat to set
     */
    public void setMgrVat(Integer mgrVat) {
        this.mgrVat = mgrVat;
    }

    /**
     * @return the mgrTot
     */
    public Integer getMgrTot() {
        return mgrTot;
    }

    /**
     * @param mgrTot the mgrTot to set
     */
    public void setMgrTot(Integer mgrTot) {
        this.mgrTot = mgrTot;
    }

    /**
     * @return the outDate
     */
    public String getOutDate() {
        return outDate;
    }

    /**
     * @param outDate the outDate to set
     */
    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    /**
     * @return the outUnitQty
     */
    public Integer getOutUnitQty() {
        return outUnitQty;
    }

    /**
     * @param outUnitQty the outUnitQty to set
     */
    public void setOutUnitQty(Integer outUnitQty) {
        this.outUnitQty = outUnitQty;
    }

    /**
     * @return the outEtcQty
     */
    public Integer getOutEtcQty() {
        return outEtcQty;
    }

    /**
     * @param outEtcQty the outEtcQty to set
     */
    public void setOutEtcQty(Integer outEtcQty) {
        this.outEtcQty = outEtcQty;
    }

    /**
     * @return the outTotQty
     */
    public Integer getOutTotQty() {
        return outTotQty;
    }

    /**
     * @param outTotQty the outTotQty to set
     */
    public void setOutTotQty(Integer outTotQty) {
        this.outTotQty = outTotQty;
    }

    /**
     * @return the outAmt
     */
    public Integer getOutAmt() {
        return outAmt;
    }

    /**
     * @param outAmt the outAmt to set
     */
    public void setOutAmt(Integer outAmt) {
        this.outAmt = outAmt;
    }

    /**
     * @return the outVat
     */
    public Integer getOutVat() {
        return outVat;
    }

    /**
     * @param outVat the outVat to set
     */
    public void setOutVat(Integer outVat) {
        this.outVat = outVat;
    }

    /**
     * @return the outTot
     */
    public Integer getOutTot() {
        return outTot;
    }

    /**
     * @param outTot the outTot to set
     */
    public void setOutTot(Integer outTot) {
        this.outTot = outTot;
    }

    /**
     * @return the outDt
     */
    public String getOutDt() {
        return outDt;
    }

    /**
     * @param outDt the outDt to set
     */
    public void setOutDt(String outDt) {
        this.outDt = outDt;
    }

    /**
     * @return the outId
     */
    public String getOutId() {
        return outId;
    }

    /**
     * @param outId the outId to set
     */
    public void setOutId(String outId) {
        this.outId = outId;
    }

    /**
     * @return the inDate
     */
    public String getInDate() {
        return inDate;
    }

    /**
     * @param inDate the inDate to set
     */
    public void setInDate(String inDate) {
        this.inDate = inDate;
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
    public Integer getInAmt() {
        return inAmt;
    }

    /**
     * @param inAmt the inAmt to set
     */
    public void setInAmt(Integer inAmt) {
        this.inAmt = inAmt;
    }

    /**
     * @return the inVat
     */
    public Integer getInVat() {
        return inVat;
    }

    /**
     * @param inVat the inVat to set
     */
    public void setInVat(Integer inVat) {
        this.inVat = inVat;
    }

    /**
     * @return the inTot
     */
    public Integer getInTot() {
        return inTot;
    }

    /**
     * @param inTot the inTot to set
     */
    public void setInTot(Integer inTot) {
        this.inTot = inTot;
    }

    /**
     * @return the inDt
     */
    public String getInDt() {
        return inDt;
    }

    /**
     * @param inDt the inDt to set
     */
    public void setInDt(String inDt) {
        this.inDt = inDt;
    }

    /**
     * @return the inId
     */
    public String getInId() {
        return inId;
    }

    /**
     * @param inId the inId to set
     */
    public void setInId(String inId) {
        this.inId = inId;
    }

    /**
     * @return the penaltyFg
     */
    public String getPenaltyFg() {
        return penaltyFg;
    }

    /**
     * @param penaltyFg the penaltyFg to set
     */
    public void setPenaltyFg(String penaltyFg) {
        this.penaltyFg = penaltyFg;
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
     * @return the billIssueDate
     */
    public String getBillIssueDate() {
        return billIssueDate;
    }

    /**
     * @param billIssueDate the billIssueDate to set
     */
    public void setBillIssueDate(String billIssueDate) {
        this.billIssueDate = billIssueDate;
    }

    /**
     * @return the billIssueDt
     */
    public String getBillIssueDt() {
        return billIssueDt;
    }

    /**
     * @param billIssueDt the billIssueDt to set
     */
    public void setBillIssueDt(String billIssueDt) {
        this.billIssueDt = billIssueDt;
    }

    /**
     * @return the billIssueId
     */
    public String getBillIssueId() {
        return billIssueId;
    }

    /**
     * @param billIssueId the billIssueId to set
     */
    public void setBillIssueId(String billIssueId) {
        this.billIssueId = billIssueId;
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
     * @return the hqRemark
     */
    public String getHqRemark() {
        return hqRemark;
    }

    /**
     * @param hqRemark the hqRemark to set
     */
    public void setHqRemark(String hqRemark) {
        this.hqRemark = hqRemark;
    }

    /**
     * @return the dlvrCd
     */
    public String getDlvrCd() {
        return dlvrCd;
    }

    /**
     * @param dlvrCd the dlvrCd to set
     */
    public void setDlvrCd(String dlvrCd) {
        this.dlvrCd = dlvrCd;
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

	public String getReqDate() {
		return reqDate;
	}

	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}

    public String getOrderSlipNo() {
        return orderSlipNo;
    }

    public void setOrderSlipNo(String orderSlipNo) {
        this.orderSlipNo = orderSlipNo;
    }

    public String[] getOrderSlipNoList() {
        return orderSlipNoList;
    }

    public void setOrderSlipNoList(String[] orderSlipNoList) {
        this.orderSlipNoList = orderSlipNoList;
    }
}
