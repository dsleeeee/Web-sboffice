package kr.co.solbipos.sale.day.day.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.sale.day.day.enums.SaleTimeFg;

public class DayVO extends PageVO {

    private static final long serialVersionUID = 7461756476951960825L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장코드 array */
    private String arrStoreCd[];
    /** 결제수단컬럼 */
    private String payCol;
    /** 결제수단 array */
    private String arrPayCol[];
    /** 쿼리문의 PIVOT IN에 사용할 결제수단 컬럼 문자열 */
    private String pivotPayCol;
    /** 할인컬럼 */
    private String dcCol;
    /** 할인구분 array */
    private String arrDcCol[];
    /** 쿼리문의 PIVOT IN에 사용할 할인구분 컬럼 문자열 */
    private String pivotDcCol;
    /** 매출일자 */
    private String saleDate;
    /** 매출 발생 시간대 기준, 동적 컬럼 생성을 위한 쿼리 문자열*/
    private String sQuery1;
    private String sQuery2;
    private String sQuery3;
    private String sQuery4;
    /** 매출 발생 시간대 */
    private SaleTimeFg saleTime;

    /** 포스컬럼 */
    private String posCol;
    /** 포스구분 array */
    private String arrPosCol[];
    /** 쿼리문의 PIVOT IN에 사용할 포스구분 컬럼 문자열 */
    private String pivotPosCol;

    /** 팝업호출시 구분 */
    private String gubun;

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
     * @return the payCol
     */
    public String getPayCol() {
        return payCol;
    }

    /**
     * @param payCol the payCol to set
     */
    public void setPayCol(String payCol) {
        this.payCol = payCol;
    }

    /**
     * @return the arrPayCol
     */
    public String[] getArrPayCol() {
        return arrPayCol;
    }

    /**
     * @param arrPayCol the arrPayCol to set
     */
    public void setArrPayCol(String[] arrPayCol) {
        this.arrPayCol = arrPayCol;
    }

    /**
     * @return the pivotPayCol
     */
    public String getPivotPayCol() {
        return pivotPayCol;
    }

    /**
     * @param pivotPayCol the pivotPayCol to set
     */
    public void setPivotPayCol(String pivotPayCol) {
        this.pivotPayCol = pivotPayCol;
    }

    /**
     * @return the dcCol
     */
    public String getDcCol() {
        return dcCol;
    }

    /**
     * @param dcCol the dcCol to set
     */
    public void setDcCol(String dcCol) {
        this.dcCol = dcCol;
    }

    /**
     * @return the arrDcCol
     */
    public String[] getArrDcCol() {
        return arrDcCol;
    }

    /**
     * @param arrDcCol the arrDcCol to set
     */
    public void setArrDcCol(String[] arrDcCol) {
        this.arrDcCol = arrDcCol;
    }

    /**
     * @return the pivotDcCol
     */
    public String getPivotDcCol() {
        return pivotDcCol;
    }

    /**
     * @param pivotDcCol the pivotDcCol to set
     */
    public void setPivotDcCol(String pivotDcCol) {
        this.pivotDcCol = pivotDcCol;
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

    public String getsQuery1() {
        return sQuery1;
    }

    public void setsQuery1(String sQuery1) {
        this.sQuery1 = sQuery1;
    }

    public String getsQuery2() {
        return sQuery2;
    }

    public void setsQuery2(String sQuery2) {
        this.sQuery2 = sQuery2;
    }

    public String getsQuery3() {
        return sQuery3;
    }

    public void setsQuery3(String sQuery3) {
        this.sQuery3 = sQuery3;
    }

    public String getsQuery4() {
        return sQuery4;
    }

    public void setsQuery4(String sQuery4) {
        this.sQuery4 = sQuery4;
    }

    public SaleTimeFg getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(SaleTimeFg saleTime) {
        this.saleTime = saleTime;
    }

    public String getPosCol() { return posCol; }

    public void setPosCol(String posCol) { this.posCol = posCol; }

    public String[] getArrPosCol() {
        return arrPosCol;
    }

    public void setArrPosCol(String[] arrPosCol) {
        this.arrPosCol = arrPosCol;
    }

    public String getPivotPosCol() { return pivotPosCol; }

    public void setPivotPosCol(String pivotPosCol) { this.pivotPosCol = pivotPosCol; }

    public String getGubun() { return gubun; }

    public void setGubun(String gubun) { this.gubun = gubun; }

    public String getYearMonth() { return yearMonth; }

    public void setYearMonth(String yearMonth) { this.yearMonth = yearMonth; }
}
