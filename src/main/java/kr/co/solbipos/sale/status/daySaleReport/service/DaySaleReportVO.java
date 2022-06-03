package kr.co.solbipos.sale.status.daySaleReport.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : DaySaleReportVO.java
 * @Description : 매출관리 > 매출현황2 > 일별매출내역 다운로드(제너시스올떡 분식대장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.12.08  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.12.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class DaySaleReportVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 조회매장 */
    private String storeCd;

    /** 조회매장 */
    private String storeCds;

    /** 매장코드 */
    private String[] storeCdList;

    /** 조회월 */
    private String startMonth;

    /** 조회월 */
    private String endMonth;

    /** 자료생성 월 */
    private String dataCreateMonth;

    /** 자료생성 월의 마지막날 */
    private String dataCreateMonthLastDate;

    /** 요청일자 */
    private String reqDate;

    /** 요청시간 */
    private String reqTime;

    /** 영업시작일 */
    private String fromSaleDate;

    /** 영업최종일 */
    private String toSaleDate;

    /** 엑셀파일명 */
    private String fileName;

    /** 쿼리문의 PIVOT IN에 사용할 날짜 컬럼 문자열 */
    private String pivotDateCol;

    /** 매출 발생 일별 기준, 동적 컬럼 생성을 위한 쿼리 문자열*/
    private String sQuery1;
    private String sQuery2;
    private String sQuery3;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) { this.hqOfficeCd = hqOfficeCd; }

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String getStoreCds() { return storeCds; }

    public void setStoreCds(String storeCds) { this.storeCds = storeCds; }

    public String[] getStoreCdList() {
        return storeCdList;
    }

    public void setStoreCdList(String[] storeCdList) {
        this.storeCdList = storeCdList;
    }

    public String getStartMonth() { return startMonth; }

    public void setStartMonth(String startMonth) { this.startMonth = startMonth; }

    public String getEndMonth() { return endMonth; }

    public void setEndMonth(String endMonth) { this.endMonth = endMonth; }

    public String getDataCreateMonth() { return dataCreateMonth; }

    public void setDataCreateMonth(String dataCreateMonth) { this.dataCreateMonth = dataCreateMonth; }

    public String getDataCreateMonthLastDate() { return dataCreateMonthLastDate; }

    public void setDataCreateMonthLastDate(String dataCreateMonthLastDate) { this.dataCreateMonthLastDate = dataCreateMonthLastDate; }

    public String getReqDate() { return reqDate; }

    public void setReqDate(String reqDate) { this.reqDate = reqDate; }

    public String getReqTime() { return reqTime; }

    public void setReqTime(String reqTime) { this.reqTime = reqTime; }

    public String getFromSaleDate() { return fromSaleDate; }

    public void setFromSaleDate(String fromSaleDate) { this.fromSaleDate = fromSaleDate; }

    public String getToSaleDate() { return toSaleDate; }

    public void setToSaleDate(String toSaleDate) { this.toSaleDate = toSaleDate; }

    public String getFileName() { return fileName; }

    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getPivotDateCol() { return pivotDateCol; }

    public void setPivotDateCol(String pivotDateCol) { this.pivotDateCol = pivotDateCol; }

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
}