package kr.co.solbipos.sale.day.day.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.sale.day.day.enums.SaleTimeFg;

public class DayVO extends PageVO {

    private static final long serialVersionUID = 7461756476951960825L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 브랜드코드 */
    private String hqBrandCd;
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

    /** 외식테이블컬럼 */
    private String tableCol;
    /** 외식테이블구분 array */
    private String arrTableCol[];
    /** 쿼리문의 PIVOT IN에 사용할 외식테이블구분 컬럼 문자열 */
    private String pivotTableCol;

    /** 외식테이블 코드 */
    private String tableCd;

    /** POS번호 */
    private String posNo;

    /** 영수증번호 */
    private String billNo;

    /** 코너컬럼 */
    private String cornerCol;
    /** 코너구분 array */
    private String arrCornerCol[];
    /** 쿼리문의 PIVOT IN에 사용할 외식테이블구분 컬럼 문자열 */
    private String pivotCornerCol;

    /** 매장코너 코드 */
    private String storeCornerCd;

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

    public String getHqBrandCd() {
        return hqBrandCd;
    }

    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
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

    public String getTableCol() { return tableCol; }

    public void setTableCol(String tableCol) { this.tableCol = tableCol; }

    public String[] getArrTableCol() { return arrTableCol; }

    public void setArrTableCol(String[] arrTableCol) { this.arrTableCol = arrTableCol; }

    public String getPivotTableCol() { return pivotTableCol; }

    public void setPivotTableCol(String pivotTableCol) { this.pivotTableCol = pivotTableCol; }

    public String getTableCd() {
        return tableCd;
    }

    public void setTableCd(String tableCd) {
        this.tableCd = tableCd;
    }

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) { this.posNo = posNo; }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getCornerCol() { return cornerCol; }

    public void setCornerCol(String cornerCol) { this.cornerCol = cornerCol; }

    public String[] getArrCornerCol() {
        return arrCornerCol;
    }

    public void setArrCornerCol(String[] arrCornerCol) {
        this.arrCornerCol = arrCornerCol;
    }

    public String getPivotCornerCol() { return pivotCornerCol; }

    public void setPivotCornerCol(String pivotCornerCol) { this.pivotCornerCol = pivotCornerCol; }

    public String getStoreCornerCd() { return storeCornerCd; }

    public void setStoreCornerCd(String storeCornerCd) { this.storeCornerCd = storeCornerCd; }
}
