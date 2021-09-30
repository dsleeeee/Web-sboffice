package kr.co.solbipos.mobile.sale.status.rtnStatus.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : MobileRtnSaleVO.java
 * @Description : (모바일) 매출현황 > 반품현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.27  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.09.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class MobileRtnStatusVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /**
     * 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 조회매장 */
    private String storeCd;

    /** 매장코드 (조회용) */
    private String srchStoreCd;

    /** 다중매장코드 array */
    private String arrStoreCd[];

    /** 검색 시작 시간 */
    private String startTime;

    /** 검색 종료 시간 */
    private String endTime;

    /** 동적 컬럼 생성을 위한 쿼리 문자열*/
    private String sQuery1;
    private String sQuery2;

    public String getOrgnFg() { return orgnFg; }

    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String getSrchStoreCd() {
        return srchStoreCd;
    }

    public void setSrchStoreCd(String srchStoreCd) {
        this.srchStoreCd = srchStoreCd;
    }

    public String[] getArrStoreCd() {
        return arrStoreCd;
    }

    public void setArrStoreCd(String[] arrStoreCd) {
        this.arrStoreCd = arrStoreCd;
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

    public String getsQuery1() { return sQuery1; }

    public void setsQuery1(String sQuery1) { this.sQuery1 = sQuery1; }

    public String getsQuery2() { return sQuery2; }

    public void setsQuery2(String sQuery2) { this.sQuery2 = sQuery2; }
}