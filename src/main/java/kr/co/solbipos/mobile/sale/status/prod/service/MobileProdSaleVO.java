package kr.co.solbipos.mobile.sale.status.prod.service;

import kr.co.solbipos.application.common.service.PageVO;

public class MobileProdSaleVO extends PageVO {

    private static final long serialVersionUID = -2874078033874862149L;

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
    /** 매장코드 */
    private String storeCd;
    /** 검색 시작 날짜 */
    private String startDate;
    /** 검색 종료 날짜 */
    private String endDate;
    /** 매장코드 (조회용) */
    private String srchStoreCd;
    /** 다중매장코드 array */
    private String arrStoreCd[];
    /** 당일 Best3 매출 조회 여부 */
    private String todayBest3Fg;

    public String getOrgnFg() { return orgnFg; }

    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
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

    public String getTodayBest3Fg() {
        return todayBest3Fg;
    }

    public void setTodayBest3Fg(String todayBest3Fg) {
        this.todayBest3Fg = todayBest3Fg;
    }
}
