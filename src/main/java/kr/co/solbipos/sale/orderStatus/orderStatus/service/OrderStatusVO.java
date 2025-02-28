package kr.co.solbipos.sale.orderStatus.orderStatus.service;

import kr.co.solbipos.application.common.service.PageVO;

public class OrderStatusVO extends PageVO {

    private static final long serialVersionUID = 837464472038733732L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 시작일자 */
    private String startDate;
    /** 종료일자 */
    private String endDate;
    /** 영업일자 */
    private String saleDate;
    /** 주문번호 */
    private String orderNo;
    /** 주문구분 */
    private String orderFg;
    /** 주문상세구분 */
    private String orderDtlFg;
    /** 사원번호 */
    private String empNo;
    /** 매장코드 */
    private String[] storeCdList;
    /** 조회매장 */
    private String storeCds;
    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;
    /** 취소구분 */
    private String cancelFg;

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

    @Override
    public String getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderFg() {
        return orderFg;
    }

    public void setOrderFg(String orderFg) {
        this.orderFg = orderFg;
    }

    public String getOrderDtlFg() {
        return orderDtlFg;
    }

    public void setOrderDtlFg(String orderDtlFg) {
        this.orderDtlFg = orderDtlFg;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String[] getStoreCdList() {
        return storeCdList;
    }

    public void setStoreCdList(String[] storeCdList) {
        this.storeCdList = storeCdList;
    }

    public String getStoreCds() {
        return storeCds;
    }

    public void setStoreCds(String storeCds) {
        this.storeCds = storeCds;
    }

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
    }

    public String getCancelFg() {
        return cancelFg;
    }

    public void setCancelFg(String cancelFg) {
        this.cancelFg = cancelFg;
    }
}
