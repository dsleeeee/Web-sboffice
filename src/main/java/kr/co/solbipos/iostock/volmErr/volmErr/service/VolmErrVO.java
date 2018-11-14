package kr.co.solbipos.iostock.volmErr.volmErr.service;

import kr.co.solbipos.application.common.service.PageVO;

public class VolmErrVO extends PageVO {

    private static final long serialVersionUID = 5954552114499634897L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 전표번호 ( YYMM(4)+SEQ(6) ) */
    private String slipNo;
    /** 본사코드-전표번호에 따른 순번 */
    private Integer seq;
    /** 전표구분 ( 1:주문, -1:반품 ) */
    private Integer slipFg;
    /** 물량오류사유구분 ( CCD_CODEM_T : 115 ) */
    private String errFg;
    /** 분배구분 ( CCD_CODEM_T : 111 ) 0:일반 1:증정 2:전시용 9:기타 */
    private String dstbFg;
    /** 브랜드코드 */
    private String hqBrandCd;
    /** 상품코드 */
    private String prodCd;
    /** 상품 SEQ */
    private String prodSeq;
    /** 발주단위구분 */
    private String poUnitFg;
    /** 발주단위수량 */
    private Integer poUnitQty;
    /** 출고공급단가 */
    private Integer outSplyUprc;
    /** 출고수량(주문단위) */
    private Integer outUnitQty;
    /** 출고수량(나머지) */
    private Integer outEtcQty;
    /** 출고수량합계(낱개) */
    private Integer outTotQty;
    /** 출고금액(OUT_SPLY_UPRC * OUT_TOT_QTY / PO_UNIT_QTY) */
    private Integer outAmt;
    /** 출고금액VAT */
    private Integer outVat;
    /** 출고금액합계 */
    private Integer outTot;
    /** 입고공급단가 */
    private Integer inSplyUprc;
    /** 입고수량(주문단위) */
    private Integer inUnitQty;
    /** 입고수량(나머지) */
    private Integer inEtcQty;
    /** 입고수량합계(낱개) */
    private Integer inTotQty;
    /** 입고금액(IN_SPLY_UPRC * IN_TOT_QTY / PO_UNIT_QTY) */
    private Integer inAmt;
    /** 입고금액VAT */
    private Integer inVat;
    /** 입고금액합계 */
    private Integer inTot;
    /** DTL 비고 */
    private String remark;
    /** HD 비고 */
    private String hdRemark;
    /** 신규생성전표번호 */
    private String newSlipNo;
    /** 매장코드 */
    private String storeCd;
    /** 다중 매장코드 */
    private String[] arrStoreCd;
    /** 매장명 */
    private String storeNm;
    /** 조회 구분 */
    private String dateFg;
    /** 처리구분 ( 0:입력, 1:확정 ) */
    private String procFg;
    /** 처리구분 ( 0:입력, 1:확정 ) */
    private String updateProcFg;
    /** 상세건수 */
    private Integer dtlCnt;
    /** 출고일자(수불기준일자) */
    private String outDate;
    /** 출고일시 */
    private String outDt;
    /** 출고자 */
    private String outId;
    /** 입고일자(수불기준일자) */
    private String inDate;
    /** 입고일시 */
    private String inDt;
    /** 입고자 */
    private String inId;
    /** 확정여부 */
    private String confirmFg;
    /** 신규전표생성여부 */
    private String newSlipNoFg;
    /** 본사신규조정생성여부 */
    private String hqNewAdjustFg;
    /** 매장신규조정생성여부 */
    private String storeNewAdjustFg;
    /** 전표번호 생성을 위한 YYMM 포맷 날짜 */
    private String yymm;



    /** 공통코드 */
    private String nmcodeGrpCd;
    /** 공통코드 아이템1 */
    private String nmcodeItem1;
    /** 테이블 */
    private String selectTable;
    /** 조회할 코드명 */
    private String selectCd;
    /** 조회할 명칭명 */
    private String selectNm;
    /** 조건문 */
    private String selectWhere;



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
     * @return the errFg
     */
    public String getErrFg() {
        return errFg;
    }

    /**
     * @param errFg the errFg to set
     */
    public void setErrFg(String errFg) {
        this.errFg = errFg;
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
     * @return the prodSeq
     */
    public String getProdSeq() {
        return prodSeq;
    }

    /**
     * @param prodSeq the prodSeq to set
     */
    public void setProdSeq(String prodSeq) {
        this.prodSeq = prodSeq;
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
     * @return the hdRemark
     */
    public String getHdRemark() {
        return hdRemark;
    }

    /**
     * @param hdRemark the hdRemark to set
     */
    public void setHdRemark(String hdRemark) {
        this.hdRemark = hdRemark;
    }

    /**
     * @return the newSlipNo
     */
    public String getNewSlipNo() {
        return newSlipNo;
    }

    /**
     * @param newSlipNo the newSlipNo to set
     */
    public void setNewSlipNo(String newSlipNo) {
        this.newSlipNo = newSlipNo;
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
     * @return the newSlipNoFg
     */
    public String getNewSlipNoFg() {
        return newSlipNoFg;
    }

    /**
     * @param newSlipNoFg the newSlipNoFg to set
     */
    public void setNewSlipNoFg(String newSlipNoFg) {
        this.newSlipNoFg = newSlipNoFg;
    }

    /**
     * @return the hqNewAdjustFg
     */
    public String getHqNewAdjustFg() {
        return hqNewAdjustFg;
    }

    /**
     * @param hqNewAdjustFg the hqNewAdjustFg to set
     */
    public void setHqNewAdjustFg(String hqNewAdjustFg) {
        this.hqNewAdjustFg = hqNewAdjustFg;
    }

    /**
     * @return the storeNewAdjustFg
     */
    public String getStoreNewAdjustFg() {
        return storeNewAdjustFg;
    }

    /**
     * @param storeNewAdjustFg the storeNewAdjustFg to set
     */
    public void setStoreNewAdjustFg(String storeNewAdjustFg) {
        this.storeNewAdjustFg = storeNewAdjustFg;
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
     * @return the nmcodeGrpCd
     */
    public String getNmcodeGrpCd() {
        return nmcodeGrpCd;
    }

    /**
     * @param nmcodeGrpCd the nmcodeGrpCd to set
     */
    public void setNmcodeGrpCd(String nmcodeGrpCd) {
        this.nmcodeGrpCd = nmcodeGrpCd;
    }

    /**
     * @return the nmcodeItem1
     */
    public String getNmcodeItem1() {
        return nmcodeItem1;
    }

    /**
     * @param nmcodeItem1 the nmcodeItem1 to set
     */
    public void setNmcodeItem1(String nmcodeItem1) {
        this.nmcodeItem1 = nmcodeItem1;
    }

    /**
     * @return the selectTable
     */
    public String getSelectTable() {
        return selectTable;
    }

    /**
     * @param selectTable the selectTable to set
     */
    public void setSelectTable(String selectTable) {
        this.selectTable = selectTable;
    }

    /**
     * @return the selectCd
     */
    public String getSelectCd() {
        return selectCd;
    }

    /**
     * @param selectCd the selectCd to set
     */
    public void setSelectCd(String selectCd) {
        this.selectCd = selectCd;
    }

    /**
     * @return the selectNm
     */
    public String getSelectNm() {
        return selectNm;
    }

    /**
     * @param selectNm the selectNm to set
     */
    public void setSelectNm(String selectNm) {
        this.selectNm = selectNm;
    }

    /**
     * @return the selectWhere
     */
    public String getSelectWhere() {
        return selectWhere;
    }

    /**
     * @param selectWhere the selectWhere to set
     */
    public void setSelectWhere(String selectWhere) {
        this.selectWhere = selectWhere;
    }
}
