package kr.co.solbipos.sale.status.storeProdSaleReport.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : StoreProdSaleReportVO.java
 * @Description : 매출관리 > 매출현황2 > 기간별 매장-상품 매출 다운로드(고봉민)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.12.01  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.12.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class StoreProdSaleReportVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /** 매장명 */
    private String storeNm;

    /** 조회월 */
    private String startMonth;

    /** 조회월 */
    private String endMonth;

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

    /** 그룹코드 */
    private String branchCd;

    /** 그룹명 */
    private String branchNm;

    /** 지역코드 */
    private String areaCd;

    /** 지역명 */
    private String areaNm;

    /** 매장상태 */
    private String sysStatFg;

    /** 사업자번호 */
    private String bizNo;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) { this.hqOfficeCd = hqOfficeCd; }

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String getStoreNm() { return storeNm; }

    public void setStoreNm(String storeNm) { this.storeNm = storeNm; }

    public String getStartMonth() { return startMonth; }

    public void setStartMonth(String startMonth) { this.startMonth = startMonth; }

    public String getEndMonth() { return endMonth; }

    public void setEndMonth(String endMonth) { this.endMonth = endMonth; }

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

    public String getBranchCd() { return branchCd; }

    public void setBranchCd(String branchCd) { this.branchCd = branchCd; }

    public String getBranchNm() { return branchNm; }

    public void setBranchNm(String branchNm) { this.branchNm = branchNm; }

    public String getAreaCd() { return areaCd; }

    public void setAreaCd(String areaCd) { this.areaCd = areaCd; }

    public String getAreaNm() { return areaNm; }

    public void setAreaNm(String areaNm) { this.areaNm = areaNm; }

    public String getSysStatFg() { return sysStatFg; }

    public void setSysStatFg(String sysStatFg) { this.sysStatFg = sysStatFg; }

    public String getBizNo() { return bizNo; }

    public void setBizNo(String bizNo) { this.bizNo = bizNo; }
}