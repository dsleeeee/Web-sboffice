package kr.co.solbipos.iostock.move.hqStoreMove.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;

public class HqStoreMoveVO extends PageVO {

    private static final long serialVersionUID = 8013623600434420897L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 전표번호(YYMM(4)+SEQ(6)) */
    private String slipNo;
    /** 매장이동일자 */
    private String moveDate;
    /** 이출매장코드 */
    private String outStoreCd;
    /** 이입매장코드 */
    private String inStoreCd;
    /** 이동처리구분 0:등록 1:이출 2:이입 3:본사확정 */
    private String procFg;
    /** 이동배송구분 0:본사행랑 1:택배 2:직접수령 */
    private String dlvrFg;
    /** 상세건수 */
    private Integer dtlCnt;
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
    /** 이출공급단가 */
    private Integer outSplyUprc;
    /** 이출수량(주문단위) */
    private Integer outUnitQty;
    /** 이출수량(나머지) */
    private Integer outEtcQty;
    /** 이출수량 */
    private Integer outTotQty;
    /** 이전이출수량 */
    private Integer prevOutTotQty;
    /** 이출금액 */
    private Integer outAmt;
    /** 이출금액VAT */
    private Integer outVat;
    /** 이출금액합계 */
    private Integer outTot;
    /** 이출확정일시 */
    private String outConfmDt;
    /** 이출확정자 */
    private String outConfmId;
    /** 이입공급단가 */
    private Integer inSplyUprc;
    /** 이입수량(주문단위) */
    private Integer inUnitQty;
    /** 이입수량(나머지) */
    private Integer inEtcQty;
    /** 이입수량 */
    private Integer inTotQty;
    /** 이입금액 */
    private Integer inAmt;
    /** 이입금액VAT */
    private Integer inVat;
    /** 이입금액합계 */
    private Integer inTot;
    /** 이입확정일시 */
    private String inConfmDt;
    /** 이입확정자 */
    private String inConfmId;
    /** 본사확정일시 */
    private String hqConfmDt;
    /** 본사확정자 */
    private String hqConfmId;
    /** 이출전표번호 */
    private String outSlipNo;
    /** 이입전표번호 */
    private String inSlipNo;
    /** 매장이동비고 */
    private String remark;
    /** 입력구분 */
    private OrgnFg regFg;
    /** 확정여부 */
    private String confirmFg;
    /** 전표번호 생성을 위한 YYMM 포맷 날짜 */
    private String yymm;

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
     * @return the moveDate
     */
    public String getMoveDate() {
        return moveDate;
    }

    /**
     * @param moveDate the moveDate to set
     */
    public void setMoveDate(String moveDate) {
        this.moveDate = moveDate;
    }

    /**
     * @return the outStoreCd
     */
    public String getOutStoreCd() {
        return outStoreCd;
    }

    /**
     * @param outStoreCd the outStoreCd to set
     */
    public void setOutStoreCd(String outStoreCd) {
        this.outStoreCd = outStoreCd;
    }

    /**
     * @return the inStoreCd
     */
    public String getInStoreCd() {
        return inStoreCd;
    }

    /**
     * @param inStoreCd the inStoreCd to set
     */
    public void setInStoreCd(String inStoreCd) {
        this.inStoreCd = inStoreCd;
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
     * @return the dlvrFg
     */
    public String getDlvrFg() {
        return dlvrFg;
    }

    /**
     * @param dlvrFg the dlvrFg to set
     */
    public void setDlvrFg(String dlvrFg) {
        this.dlvrFg = dlvrFg;
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
     * @return the outSplyUprc
     */
    public Integer getOutSplyUprc() {
        return outSplyUprc;
    }

    /**
     * @param outSplyUprc the outSplyUprc to set
     */
    public void setOutSplyUprc(Integer outSplyUprc) {
        this.outSplyUprc = outSplyUprc;
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
     * @return the prevOutTotQty
     */
    public Integer getPrevOutTotQty() {
        return prevOutTotQty;
    }

    /**
     * @param prevOutTotQty the prevOutTotQty to set
     */
    public void setPrevOutTotQty(Integer prevOutTotQty) {
        this.prevOutTotQty = prevOutTotQty;
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
     * @return the outConfmDt
     */
    public String getOutConfmDt() {
        return outConfmDt;
    }

    /**
     * @param outConfmDt the outConfmDt to set
     */
    public void setOutConfmDt(String outConfmDt) {
        this.outConfmDt = outConfmDt;
    }

    /**
     * @return the outConfmId
     */
    public String getOutConfmId() {
        return outConfmId;
    }

    /**
     * @param outConfmId the outConfmId to set
     */
    public void setOutConfmId(String outConfmId) {
        this.outConfmId = outConfmId;
    }

    /**
     * @return the inSplyUprc
     */
    public Integer getInSplyUprc() {
        return inSplyUprc;
    }

    /**
     * @param inSplyUprc the inSplyUprc to set
     */
    public void setInSplyUprc(Integer inSplyUprc) {
        this.inSplyUprc = inSplyUprc;
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
     * @return the inConfmDt
     */
    public String getInConfmDt() {
        return inConfmDt;
    }

    /**
     * @param inConfmDt the inConfmDt to set
     */
    public void setInConfmDt(String inConfmDt) {
        this.inConfmDt = inConfmDt;
    }

    /**
     * @return the inConfmId
     */
    public String getInConfmId() {
        return inConfmId;
    }

    /**
     * @param inConfmId the inConfmId to set
     */
    public void setInConfmId(String inConfmId) {
        this.inConfmId = inConfmId;
    }

    /**
     * @return the hqConfmDt
     */
    public String getHqConfmDt() {
        return hqConfmDt;
    }

    /**
     * @param hqConfmDt the hqConfmDt to set
     */
    public void setHqConfmDt(String hqConfmDt) {
        this.hqConfmDt = hqConfmDt;
    }

    /**
     * @return the hqConfmId
     */
    public String getHqConfmId() {
        return hqConfmId;
    }

    /**
     * @param hqConfmId the hqConfmId to set
     */
    public void setHqConfmId(String hqConfmId) {
        this.hqConfmId = hqConfmId;
    }

    /**
     * @return the outSlipNo
     */
    public String getOutSlipNo() {
        return outSlipNo;
    }

    /**
     * @param outSlipNo the outSlipNo to set
     */
    public void setOutSlipNo(String outSlipNo) {
        this.outSlipNo = outSlipNo;
    }

    /**
     * @return the inSlipNo
     */
    public String getInSlipNo() {
        return inSlipNo;
    }

    /**
     * @param inSlipNo the inSlipNo to set
     */
    public void setInSlipNo(String inSlipNo) {
        this.inSlipNo = inSlipNo;
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
     * @return the regFg
     */
    public OrgnFg getRegFg() {
        return regFg;
    }

    /**
     * @param regFg the regFg to set
     */
    public void setRegFg(OrgnFg regFg) {
        this.regFg = regFg;
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
}
