package kr.co.solbipos.sale.cmmSalePopup.dayPayInfo.service;

import kr.co.solbipos.application.common.service.PageVO;

public class DayPayInfoVO extends PageVO {

    private static final long serialVersionUID = -8575865020250823752L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장코드 array */
    private String arrStoreCd[];
    /** 매출일자 */
    private String saleDate;
    /**
     * 소속 그룹 코드
     * 프렌차이즈인 경우 본사코드, 단독매장인 경우 자기자신의 매장코드, 시스템 or 대리점인 경우 AGENCY_CD
     */
    private String orgnGrpCd;

    /** 팝업호출시 구분 */
    private String gubun;

    /** 팝업호출시 구분 */
    private String cashGubun;

    /** 년월 */
    private String yearMonth;


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
     * @return the saleDate
     */
    public String getSaleDate() {
        return saleDate;
    }

    /**
     * @param saleDate the saleDate to set
     */
    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    /**
     * @return the orgnGrpCd
     */
    public String getOrgnGrpCd() {
        return orgnGrpCd;
    }

    /**
     * @param orgnGrpCd the orgnGrpCd to set
     */
    public void setOrgnGrpCd(String orgnGrpCd) {
        this.orgnGrpCd = orgnGrpCd;
    }

    public String getGubun() { return gubun; }

    public void setGubun(String gubun) { this.gubun = gubun; }

    public String getCashGubun() {
        return cashGubun;
    }

    public void setCashGubun(String cashGubun) {
        this.cashGubun = cashGubun;
    }

    public String getYearMonth() { return yearMonth; }

    public void setYearMonth(String yearMonth) { this.yearMonth = yearMonth; }
}
