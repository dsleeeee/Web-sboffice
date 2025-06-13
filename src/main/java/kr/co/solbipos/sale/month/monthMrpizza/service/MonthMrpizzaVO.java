package kr.co.solbipos.sale.month.monthMrpizza.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : MonthMrpizzaVO.java
 * @Description : 미스터피자 > 매출분석 > 월별매출현황(채널별)
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
public class MonthMrpizzaVO extends PageVO {

    private static final long serialVersionUID = 2739996604041101011L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사브랜드코드 */
    private String hqBrandCd;

    /** 매장코드 */
    private String[] storeCdList;

    /** 조회매장 */
    private String storeCds;

    /** 조회월 */
    private String startMonth;
    private String endMonth;

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

    /** 주문채널 구분자 컬럼 */
    private String dlvrInFgCol;

    /** 주문채널 구분자 array */
    private String arrDlvrInFgCol[];

    /** 사용자 아이디 */
    private String userId;

    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;

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
