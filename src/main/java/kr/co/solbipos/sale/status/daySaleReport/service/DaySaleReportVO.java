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
}