package kr.co.solbipos.sale.store.storeMonthChannelMrpizza.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.sale.day.day.enums.SaleTimeFg;

/**
 * @Class Name : StoreMonthChannelMrpizzaVO.java
 * @Description : 미스터피자 > 매장분석 > 매장-월별매출현황(채널별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.06.10  이다솜      최초생성
 *
 * @author 솔비포스 개발실 개발1팀 이다솜
 * @since 2025.06.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public class StoreMonthChannelMrpizzaVO extends PageVO {

    private static final long serialVersionUID = 3790570990077002286L;

    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;
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
    /** 모바일페이상세컬럼 */
    private String mpayCol;
    /** 모바일페이상세 array */
    private String arrMpayCol[];
    /** 쿼리문의 PIVOT IN에 사용할 모바일페이상세 컬럼 문자열 */
    private String pivotMpayCol;
    /** 할인컬럼 */
    private String dcCol;
    /** 할인구분 array */
    private String arrDcCol[];
    /** 쿼리문의 PIVOT IN에 사용할 할인구분 컬럼 문자열 */
    private String pivotDcCol;
    /** 매출일자 */
    private String saleDate;

    /** 조회월 */
    private String startMonth;

    /** 조회월 */
    private String endMonth;
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

    /** 상품분류 LEVEL */
    private String level;
    /** 다중 상품분류코드(string 형) */
    private String strProdClassCd;
    /** 다중 상품분류코드(배열형) */
    private String[] arrProdClassCd;
    /** 상품분류코드*/
    private String prodClassCd;
    /** 상위상품 분류코드 */
    private String pProdClassCd;
    /** 쿼리문의 PIVOT IN에 사용할 상품분류별 컬럼 문자열 */
    private String pivotProdClassCol1;
    private String pivotProdClassCol2;
    private String pivotProdClassCol3;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 바코드 */
    private String barCd;
    /** 검색 시작 날짜 */
    private String startDate;
    /** 검색 종료 날짜 */
    private String endDate;
    /** 검색 시작 시간 */
    private String startTime;
    /** 검색 종료 시간 */
    private String endTime;
    /** 매장코드 */
    private String[] storeCdList;
    /** 조회매장 */
    private String storeCds;

    /** 사원번호 */
    private String empNo;

    /** 검색옵션 */
    private String option;
    /** 시간대분류 */
    private String timeSlot;

    /** 주문채널 구분자 컬럼 */
    private String dlvrInFgCol;

    /** 주문채널 구분자 array */
    private String arrDlvrInFgCol[];

    /** 사용자 아이디 */
    private String userId;

    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getHqBrandCd() {
        return hqBrandCd;
    }

    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String[] getArrStoreCd() {
        return arrStoreCd;
    }

    public void setArrStoreCd(String[] arrStoreCd) {
        this.arrStoreCd = arrStoreCd;
    }

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

    public String getPivotPayCol() {
        return pivotPayCol;
    }

    public void setPivotPayCol(String pivotPayCol) {
        this.pivotPayCol = pivotPayCol;
    }

    public String getMpayCol() {
        return mpayCol;
    }

    public void setMpayCol(String mpayCol) {
        this.mpayCol = mpayCol;
    }

    public String[] getArrMpayCol() {
        return arrMpayCol;
    }

    public void setArrMpayCol(String[] arrMpayCol) {
        this.arrMpayCol = arrMpayCol;
    }

    public String getPivotMpayCol() {
        return pivotMpayCol;
    }

    public void setPivotMpayCol(String pivotMpayCol) {
        this.pivotMpayCol = pivotMpayCol;
    }

    public String getDcCol() {
        return dcCol;
    }

    public void setDcCol(String dcCol) {
        this.dcCol = dcCol;
    }

    public String[] getArrDcCol() {
        return arrDcCol;
    }

    public void setArrDcCol(String[] arrDcCol) {
        this.arrDcCol = arrDcCol;
    }

    public String getPivotDcCol() {
        return pivotDcCol;
    }

    public void setPivotDcCol(String pivotDcCol) {
        this.pivotDcCol = pivotDcCol;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
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

    public String getPosCol() {
        return posCol;
    }

    public void setPosCol(String posCol) {
        this.posCol = posCol;
    }

    public String[] getArrPosCol() {
        return arrPosCol;
    }

    public void setArrPosCol(String[] arrPosCol) {
        this.arrPosCol = arrPosCol;
    }

    public String getPivotPosCol() {
        return pivotPosCol;
    }

    public void setPivotPosCol(String pivotPosCol) {
        this.pivotPosCol = pivotPosCol;
    }

    public String getGubun() {
        return gubun;
    }

    public void setGubun(String gubun) {
        this.gubun = gubun;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getTableCol() {
        return tableCol;
    }

    public void setTableCol(String tableCol) {
        this.tableCol = tableCol;
    }

    public String[] getArrTableCol() {
        return arrTableCol;
    }

    public void setArrTableCol(String[] arrTableCol) {
        this.arrTableCol = arrTableCol;
    }

    public String getPivotTableCol() {
        return pivotTableCol;
    }

    public void setPivotTableCol(String pivotTableCol) {
        this.pivotTableCol = pivotTableCol;
    }

    public String getTableCd() {
        return tableCd;
    }

    public void setTableCd(String tableCd) {
        this.tableCd = tableCd;
    }

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getCornerCol() {
        return cornerCol;
    }

    public void setCornerCol(String cornerCol) {
        this.cornerCol = cornerCol;
    }

    public String[] getArrCornerCol() {
        return arrCornerCol;
    }

    public void setArrCornerCol(String[] arrCornerCol) {
        this.arrCornerCol = arrCornerCol;
    }

    public String getPivotCornerCol() {
        return pivotCornerCol;
    }

    public void setPivotCornerCol(String pivotCornerCol) {
        this.pivotCornerCol = pivotCornerCol;
    }

    public String getStoreCornerCd() {
        return storeCornerCd;
    }

    public void setStoreCornerCd(String storeCornerCd) {
        this.storeCornerCd = storeCornerCd;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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

    public String getProdClassCd() {
        return prodClassCd;
    }

    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    public String getpProdClassCd() {
        return pProdClassCd;
    }

    public void setpProdClassCd(String pProdClassCd) {
        this.pProdClassCd = pProdClassCd;
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

    public void setPivotProdClassCol3(String pivotProdClassCol3) {
        this.pivotProdClassCol3 = pivotProdClassCol3;
    }

    public String getProdCd() {
        return prodCd;
    }

    public void setProdCd(String prodCd) {
        this.prodCd = prodCd;
    }

    public String getProdNm() {
        return prodNm;
    }

    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    public String getBarCd() {
        return barCd;
    }

    public void setBarCd(String barCd) {
        this.barCd = barCd;
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

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getDlvrInFgCol() {
        return dlvrInFgCol;
    }

    public void setDlvrInFgCol(String dlvrInFgCol) {
        this.dlvrInFgCol = dlvrInFgCol;
    }

    public String[] getArrDlvrInFgCol() {
        return arrDlvrInFgCol;
    }

    public void setArrDlvrInFgCol(String[] arrDlvrInFgCol) {
        this.arrDlvrInFgCol = arrDlvrInFgCol;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
    }
}
