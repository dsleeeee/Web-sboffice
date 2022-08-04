package kr.co.solbipos.iostock.orderReturn.rtnStoreOrder.service;

import kr.co.solbipos.application.common.service.PageVO;

public class RtnStoreOrderVO extends PageVO {

    private static final long serialVersionUID = -4978511974970761038L;

    /** 출고요청일자 */
    private String reqDate;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 사원번호(MD사원) */
    private String empNo;
    /** 전표구분 ( 1:주문, -1:반품 ) */
    private Integer slipFg;
    /** 주문수량(주문단위) */
    private Integer orderUnitQty;
    /** 주문수량(나머지) */
    private Integer orderEtcQty;
    /** 주문수량합계(낱개) */
    private Integer orderTotQty;
    /** 주문금액 */
    private Integer orderAmt;
    /** 주문금액VAT */
    private Integer orderVat;
    /** 주문금액합계 */
    private Integer orderTot;
    /** 상세건수 */
    private Integer dtlCnt;
    /** 처리구분 ( CCD_CODEM_T : 110 ) 00:등록, 10:등록완료, 20:분배완료 */
    private String procFg;
    /** MD수량(주문단위) */
    private Integer mdUnitQty;
    /** MD수량(나머지) */
    private Integer mdEtcQty;
    /** MD수량합계(낱개) */
    private Integer mdTotQty;
    /** MD금액 */
    private Integer mdAmt;
    /** MD금액VAT */
    private Integer mdVat;
    /** MD금액합계 */
    private Integer mdTot;
    /** 비고 */
    private String remark;
    /** 조회 구분 */
    private String dateFg;
    /** 수발주옵션 */
    private String envst1042;
    /** 거래처코드 */
    private String vendrCd;
    /** 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점 */
    private String orgnFg;


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
     * @return the envst1042
     */
    public String getEnvst1042() {
        return envst1042;
    }

    /**
     * @param envst1042 the envst1042 to set
     */
    public void setEnvst1042(String envst1042) {
        this.envst1042 = envst1042;
    }

    public String getVendrCd() {
        return vendrCd;
    }

    public void setVendrCd(String vendrCd) {
        this.vendrCd = vendrCd;
    }

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }
}
