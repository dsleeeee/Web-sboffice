package kr.co.solbipos.iostock.loan.loanInfo.service;

import kr.co.solbipos.application.common.service.PageVO;

public class LoanInfoVO extends PageVO {

    private static final long serialVersionUID = 5577863877944931208L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 다중 매장코드 */
    private String[] arrStoreCd;
    /** 매장명 */
    private String storeNm;
    /** 조회 시작일 */
    private String startDate;
    /** 조회 종료일 */
    private String endDate;
    /** 여신일자 */
    private String loanDate;
    /** 출고금액 */
    private Integer outAmt;
    /** 입금액 */
    private Integer inAmt;
    /** 여신한도금액 */
    private Integer limitLoanAmt;
    /** 주문중지구분 */
    private Boolean orderCloseYn;
    /** 비고 */
    private String remark;
    /** 조회 구분 */
    private String dateFg;

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
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the loanDate
     */
    public String getLoanDate() {
        return loanDate;
    }

    /**
     * @param loanDate the loanDate to set
     */
    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
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
     * @return the limitLoanAmt
     */
    public Integer getLimitLoanAmt() {
        return limitLoanAmt;
    }

    /**
     * @param limitLoanAmt the limitLoanAmt to set
     */
    public void setLimitLoanAmt(Integer limitLoanAmt) {
        this.limitLoanAmt = limitLoanAmt;
    }

    /**
     * @return the orderCloseYn
     */
    public Boolean getOrderCloseYn() {
        return orderCloseYn;
    }

    /**
     * @param orderCloseYn the orderCloseYn to set
     */
    public void setOrderCloseYn(Boolean orderCloseYn) {
        this.orderCloseYn = orderCloseYn;
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
}
