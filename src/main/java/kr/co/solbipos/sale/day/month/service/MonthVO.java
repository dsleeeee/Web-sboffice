package kr.co.solbipos.sale.day.month.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.sale.day.day.enums.SaleTimeFg;

/**
 * @Class Name : MonthVO.java
 * @Description : 매출관리 > 매출현황 > 기간별매출 > 월별탭
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.12.09  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2019.12.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class MonthVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 회원소속코드 */
    private String membrOrgnCd;

    /** 매장코드 */
    private String[] storeCdList;

    /** 조회매장 */
    private String storeCds;

    /** 조회매장 */
    private String storeCd;

    /** 조회월 */
    private String startMonth;

    /** 조회월 */
    private String endMonth;

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

    /** 팝업호출시 구분 */
    private String gubun;

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

    public String getStartMonth() { return startMonth; }

    public void setStartMonth(String startMonth) { this.startMonth = startMonth; }

    public String getEndMonth() { return endMonth; }

    public void setEndMonth(String endMonth) { this.endMonth = endMonth; }

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

    public SaleTimeFg getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(SaleTimeFg saleTime) {
        this.saleTime = saleTime;
    }

    public String getGubun() { return gubun; }

    public void setGubun(String gubun) { this.gubun = gubun; }
}