package kr.co.solbipos.sale.day.dayOfWeek.service;

import kr.co.solbipos.application.common.service.PageVO;

import kr.co.solbipos.sale.day.day.enums.SaleTimeFg;

/**
 * @Class Name : DayOfWeekVO.java
 * @Description : 매출관리 > 매출현황 > 기간별매출 > 요일별탭
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.11.29  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.11.29
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class DayOfWeekVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /**
     * 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** 회원소속코드 */
    private String membrOrgnCd;

    /** 매장코드 */
    private String[] storeCdList;

    /** 조회매장 */
    private String storeCds;

    /** 조회매장 */
    private String storeCd;

    /** 요일 */
    private String yoil;

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

    /** 포스컬럼 */
    private String posCol;
    /** 포스구분 array */
    private String arrPosCol[];
    /** 쿼리문의 PIVOT IN에 사용할 포스구분 컬럼 문자열 */
    private String pivotPosCol;

    /** 매출 발생 시간대 기준, 동적 컬럼 생성을 위한 쿼리 문자열*/
    private String sQuery1;
    private String sQuery2;
    /** 매출 발생 시간대 */
    private SaleTimeFg saleTime;
    /** 검색 시작 시간 */
    private String startTime;
    /** 검색 종료 시간 */
    private String endTime;

    /** 외식테이블컬럼 */
    private String tableCol;
    /** 외식테이블구분 array */
    private String arrTableCol[];
    /** 쿼리문의 PIVOT IN에 사용할 외식테이블구분 컬럼 문자열 */
    private String pivotTableCol;

    /** 외식테이블 코드 */
    private String tableCd;

    /** 코너컬럼 */
    private String cornerCol;
    /** 코너구분 array */
    private String arrCornerCol[];
    /** 쿼리문의 PIVOT IN에 사용할 외식테이블구분 컬럼 문자열 */
    private String pivotCornerCol;

    /** 매장코너 코드 */
    private String storeCornerCd;

    /** 상품명 */
    private String prodCd;

    /** 상품코드 */
    private String prodNm;

    /** 바코드 */
    private String barCd;

    /** 상품분류 LEVEL */
    private String level;
    /** 상품분류코드*/
    private String prodClassCd;

    /** 다중 상품분류코드(string 형) */
    private String strProdClassCd;
    /** 다중 상품분류코드(배열형) */
    private String[] arrProdClassCd;
    /** 쿼리문의 PIVOT IN에 사용할 상품분류별 컬럼 문자열 */
    private String pivotProdClassCol1;
    private String pivotProdClassCol2;
    private String pivotProdClassCol3;

    /** 본사코드 */
    private String hqOfficeCd;

    public String getOrgnFg() { return orgnFg; }

    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

    public String getMembrOrgnCd() {
        return membrOrgnCd;
    }

    public void setMembrOrgnCd(String membrOrgnCd) {
        this.membrOrgnCd = membrOrgnCd;
    }

    public String[] getStoreCdList() {
        return storeCdList;
    }

    public void setStoreCdList(String[] storeCdList) {
        this.storeCdList = storeCdList;
    }

    public String getStoreCds() { return storeCds; }

    public void setStoreCds(String storeCds) { this.storeCds = storeCds; }

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String getYoil() { return yoil; }

    public void setYoil(String yoil) { this.yoil = yoil; }

    public String getPayCol() {
        return payCol;
    }

    public void setPayCol(String payCol) {
        this.payCol = payCol;
    }

    public String[] getArrPayCol() {
        return arrPayCol;
    }

    public void setArrPayCol(String[] arrPayCol) {
        this.arrPayCol = arrPayCol;
    }

    public String getPivotPayCol() { return pivotPayCol; }

    public void setPivotPayCol(String pivotPayCol) { this.pivotPayCol = pivotPayCol; }

    public String getDcCol() { return dcCol; }

    public void setDcCol(String dcCol) { this.dcCol = dcCol; }

    public String[] getArrDcCol() { return arrDcCol; }

    public void setArrDcCol(String[] arrDcCol) {
        this.arrDcCol = arrDcCol;
    }

    public String getPivotDcCol() { return pivotDcCol; }

    public void setPivotDcCol(String pivotDcCol) { this.pivotDcCol = pivotDcCol; }

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

    public SaleTimeFg getSaleTime() { return saleTime; }

    public void setSaleTime(SaleTimeFg saleTime) {
        this.saleTime = saleTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTableCol() { return tableCol; }

    public void setTableCol(String tableCol) { this.tableCol = tableCol; }

    public String[] getArrTableCol() {
        return arrTableCol;
    }

    public void setArrTableCol(String[] arrTableCol) {
        this.arrTableCol = arrTableCol;
    }

    public String getPivotTableCol() { return pivotTableCol; }

    public void setPivotTableCol(String pivotTableCol) { this.pivotTableCol = pivotTableCol; }

    public String getTableCd() {
        return tableCd;
    }

    public void setTableCd(String tableCd) {
        this.tableCd = tableCd;
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

    public String getProdCd() { return prodCd; }

    public void setProdCd(String prodCd) { this.prodCd = prodCd; }

    public String getProdNm() { return prodNm; }

    public void setProdNm(String prodNm) { this.prodNm = prodNm; }

    public String getBarCd() { return barCd; }

    public void setBarCd(String barCd) { this.barCd = barCd; }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getProdClassCd() {
        return prodClassCd;
    }

    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    public String getStrProdClassCd() {
        return strProdClassCd;
    }

    public void setStrProdClassCd(String strProdClassCd) {
        this.strProdClassCd = strProdClassCd;
    }

    public String[] getArrProdClassCd() {
        return arrProdClassCd;
    }

    public void setArrProdClassCd(String[] arrProdClassCd) {
        this.arrProdClassCd = arrProdClassCd;
    }

    public String getPivotProdClassCol1() {
        return pivotProdClassCol1;
    }

    public void setPivotProdClassCol1(String pivotProdClassCol1) {
        this.pivotProdClassCol1 = pivotProdClassCol1;
    }

    public String getPivotProdClassCol2() {
        return pivotProdClassCol2;
    }

    public void setPivotProdClassCol2(String pivotProdClassCol2) {
        this.pivotProdClassCol2 = pivotProdClassCol2;
    }

    public String getPivotProdClassCol3() {
        return pivotProdClassCol3;
    }

    public void setPivotProdClassCol3(String pivotProdClassCol3) { this.pivotProdClassCol3 = pivotProdClassCol3; }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }
}