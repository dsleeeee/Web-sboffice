package kr.co.solbipos.iostock.loan.storeLoanManage.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : StoreLoanManageVO.java
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
public class StoreLoanManageVO extends PageVO {

    private static final long serialVersionUID = 3297046431999863620L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 여신한도액 */
    private Integer limitLoanAmt;
    /** 여신사용액 */
    private Integer useLoanAmt;
    /** 여신잔액 (여신잔액 = 여신한도액 - 여신사용액) */
    private Integer currLoanAmt;
    /** 1회주문한도 */
    private Integer maxOrderAmt;
    /** 주문방법 1:여신잔액내에서주문 2:여신잔액있으면 1회주문한도내에서 주문 */
    private String orderFg;
    /** 미출고금액고려여부 Y:사용 N:미사용 */
    private String noOutstockAmtFg;
    /** 비고 */
    private String remark;
    /** 사용가능주문액 */
    private String availableOrderAmt;
    /** 주문중지구분 */
    private Boolean orderCloseYn;

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
     * @return the useLoanAmt
     */
    public Integer getUseLoanAmt() {
        return useLoanAmt;
    }

    /**
     * @param useLoanAmt the useLoanAmt to set
     */
    public void setUseLoanAmt(Integer useLoanAmt) {
        this.useLoanAmt = useLoanAmt;
    }

    /**
     * @return the currLoanAmt
     */
    public Integer getCurrLoanAmt() {
        return currLoanAmt;
    }

    /**
     * @param currLoanAmt the currLoanAmt to set
     */
    public void setCurrLoanAmt(Integer currLoanAmt) {
        this.currLoanAmt = currLoanAmt;
    }

    /**
     * @return the maxOrderAmt
     */
    public Integer getMaxOrderAmt() {
        return maxOrderAmt;
    }

    /**
     * @param maxOrderAmt the maxOrderAmt to set
     */
    public void setMaxOrderAmt(Integer maxOrderAmt) {
        this.maxOrderAmt = maxOrderAmt;
    }

    /**
     * @return the orderFg
     */
    public String getOrderFg() {
        return orderFg;
    }

    /**
     * @param orderFg the orderFg to set
     */
    public void setOrderFg(String orderFg) {
        this.orderFg = orderFg;
    }

    /**
     * @return the noOutstockAmtFg
     */
    public String getNoOutstockAmtFg() {
        return noOutstockAmtFg;
    }

    /**
     * @param noOutstockAmtFg the noOutstockAmtFg to set
     */
    public void setNoOutstockAmtFg(String noOutstockAmtFg) {
        this.noOutstockAmtFg = noOutstockAmtFg;
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
     * @return the availableOrderAmt
     */
    public String getAvailableOrderAmt() {
        return availableOrderAmt;
    }

    /**
     * @param availableOrderAmt the availableOrderAmt to set
     */
    public void setAvailableOrderAmt(String availableOrderAmt) {
        this.availableOrderAmt = availableOrderAmt;
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

}
