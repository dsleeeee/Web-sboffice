package kr.co.solbipos.iostock.loan.storeLoanInfo.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : StoreLoanInfoVO.java
 * @Description : 수불관리 > 수주관리 > 매장여신관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.20  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 08.20
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class StoreLoanInfoVO extends PageVO {
    private static final long serialVersionUID = 2084373375176340871L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 다중 매장코드 */
    private String[] arrStoreCd;
    /** 매장명 */
    private String storeNm;
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
}
