package kr.co.solbipos.iostock.order.outstockReqDate.service;

import kr.co.solbipos.application.common.service.PageVO;

public class OutstockReqDateVO extends PageVO {

    private static final long serialVersionUID = -2615284926841281667L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 다중 매장코드 */
    private String[] arrStoreCd;
    /** 매장명 */
    private String storeNm;
    /** 출고요청불가능일 1:일 2:월 3:화 4:수 5:목 6:금 7:토 */
    private String reqNoDay;
    /** 출고요청일 일 */
    private Boolean sun;
    /** 출고요청일 월 */
    private Boolean mon;
    /** 출고요청일 화 */
    private Boolean tue;
    /** 출고요청일 수 */
    private Boolean wed;
    /** 출고요청일 목 */
    private Boolean thu;
    /** 출고요청일 금 */
    private Boolean fri;
    /** 출고요청일 토 */
    private Boolean sat;
    /** 요청일비고 */
    private String daysRemark;
    /** 특정일 */
    private String specificDate;
    /** 출고요청가능여부 Y:가능 N:불가능 */
    private String outstockReqYn;
    /** 특정일비고 */
    private String specificDateRemark;
    /** 대표자명 */
    private String ownerNm;
    /** 시스템상태 */
    private String sysStatFg;
    /** 발주가능여부 */
    private String orderCloseYn;
    /** 요청일 복사할매장 */
    private String copyStoreCd;
    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;

    /** 시작시간 */
    private String orderStartTimeSun;
    private String orderStartTimeMon;
    private String orderStartTimeTue;
    private String orderStartTimeWed;
    private String orderStartTimeThu;
    private String orderStartTimeFri;
    private String orderStartTimeSat;
    private String orderStartTime;

    /** 마감시간 */
    private String orderEndTimeSun;
    private String orderEndTimeMon;
    private String orderEndTimeTue;
    private String orderEndTimeWed;
    private String orderEndTimeThu;
    private String orderEndTimeFri;
    private String orderEndTimeSat;
    private String orderEndTime;



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
     * @return the storeNm
     */
    public String getStoreNm() {
        return storeNm;
    }

    /**
     * @param storeNm the storeNm to set
     */
    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    /**
     * @return the reqNoDay
     */
    public String getReqNoDay() {
        return reqNoDay;
    }

    /**
     * @param reqNoDay the reqNoDay to set
     */
    public void setReqNoDay(String reqNoDay) {
        this.reqNoDay = reqNoDay;
    }

    /**
     * @return the sun
     */
    public Boolean getSun() {
        return sun;
    }

    /**
     * @param sun the sun to set
     */
    public void setSun(Boolean sun) {
        this.sun = sun;
    }

    /**
     * @return the mon
     */
    public Boolean getMon() {
        return mon;
    }

    /**
     * @param mon the mon to set
     */
    public void setMon(Boolean mon) {
        this.mon = mon;
    }

    /**
     * @return the tue
     */
    public Boolean getTue() {
        return tue;
    }

    /**
     * @param tue the tue to set
     */
    public void setTue(Boolean tue) {
        this.tue = tue;
    }

    /**
     * @return the wed
     */
    public Boolean getWed() {
        return wed;
    }

    /**
     * @param wed the wed to set
     */
    public void setWed(Boolean wed) {
        this.wed = wed;
    }

    /**
     * @return the thu
     */
    public Boolean getThu() {
        return thu;
    }

    /**
     * @param thu the thu to set
     */
    public void setThu(Boolean thu) {
        this.thu = thu;
    }

    /**
     * @return the fri
     */
    public Boolean getFri() {
        return fri;
    }

    /**
     * @param fri the fri to set
     */
    public void setFri(Boolean fri) {
        this.fri = fri;
    }

    /**
     * @return the sat
     */
    public Boolean getSat() {
        return sat;
    }

    /**
     * @param sat the sat to set
     */
    public void setSat(Boolean sat) {
        this.sat = sat;
    }

    /**
     * @return the daysRemark
     */
    public String getDaysRemark() {
        return daysRemark;
    }

    /**
     * @param daysRemark the daysRemark to set
     */
    public void setDaysRemark(String daysRemark) {
        this.daysRemark = daysRemark;
    }

    /**
     * @return the specificDate
     */
    public String getSpecificDate() {
        return specificDate;
    }

    /**
     * @param specificDate the specificDate to set
     */
    public void setSpecificDate(String specificDate) {
        this.specificDate = specificDate;
    }

    /**
     * @return the outstockReqYn
     */
    public String getOutstockReqYn() {
        return outstockReqYn;
    }

    /**
     * @param outstockReqYn the outstockReqYn to set
     */
    public void setOutstockReqYn(String outstockReqYn) {
        this.outstockReqYn = outstockReqYn;
    }

    /**
     * @return the specificDateRemark
     */
    public String getSpecificDateRemark() {
        return specificDateRemark;
    }

    /**
     * @param specificDateRemark the specificDateRemark to set
     */
    public void setSpecificDateRemark(String specificDateRemark) {
        this.specificDateRemark = specificDateRemark;
    }

    /**
     * @return the ownerNm
     */
    public String getOwnerNm() {
        return ownerNm;
    }

    /**
     * @param ownerNm the ownerNm to set
     */
    public void setOwnerNm(String ownerNm) {
        this.ownerNm = ownerNm;
    }

    /**
     * @return the sysStatFg
     */
    public String getSysStatFg() {
        return sysStatFg;
    }

    /**
     * @param sysStatFg the sysStatFg to set
     */
    public void setSysStatFg(String sysStatFg) {
        this.sysStatFg = sysStatFg;
    }

    /**
     * @return the orderCloseYn
     */
    public String getOrderCloseYn() {
        return orderCloseYn;
    }

    /**
     * @param orderCloseYn the orderCloseYn to set
     */
    public void setOrderCloseYn(String orderCloseYn) {
        this.orderCloseYn = orderCloseYn;
    }

    /**
     * @return the copyStoreCd
     */
    public String getCopyStoreCd() {
        return copyStoreCd;
    }

    /**
     * @param copyStoreCd the copyStoreCd to set
     */
    public void setCopyStoreCd(String copyStoreCd) {
        this.copyStoreCd = copyStoreCd;
    }

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
    }

    public String getOrderStartTimeSun() {
        return orderStartTimeSun;
    }

    public void setOrderStartTimeSun(String orderStartTimeSun) {
        this.orderStartTimeSun = orderStartTimeSun;
    }

    public String getOrderStartTimeMon() {
        return orderStartTimeMon;
    }

    public void setOrderStartTimeMon(String orderStartTimeMon) {
        this.orderStartTimeMon = orderStartTimeMon;
    }

    public String getOrderStartTimeTue() {
        return orderStartTimeTue;
    }

    public void setOrderStartTimeTue(String orderStartTimeTue) {
        this.orderStartTimeTue = orderStartTimeTue;
    }

    public String getOrderStartTimeWed() {
        return orderStartTimeWed;
    }

    public void setOrderStartTimeWed(String orderStartTimeWed) {
        this.orderStartTimeWed = orderStartTimeWed;
    }

    public String getOrderStartTimeThu() {
        return orderStartTimeThu;
    }

    public void setOrderStartTimeThu(String orderStartTimeThu) {
        this.orderStartTimeThu = orderStartTimeThu;
    }

    public String getOrderStartTimeFri() {
        return orderStartTimeFri;
    }

    public void setOrderStartTimeFri(String orderStartTimeFri) {
        this.orderStartTimeFri = orderStartTimeFri;
    }

    public String getOrderStartTimeSat() {
        return orderStartTimeSat;
    }

    public void setOrderStartTimeSat(String orderStartTimeSat) {
        this.orderStartTimeSat = orderStartTimeSat;
    }

    public String getOrderEndTimeSun() {
        return orderEndTimeSun;
    }

    public void setOrderEndTimeSun(String orderEndTimeSun) {
        this.orderEndTimeSun = orderEndTimeSun;
    }

    public String getOrderEndTimeMon() {
        return orderEndTimeMon;
    }

    public void setOrderEndTimeMon(String orderEndTimeMon) {
        this.orderEndTimeMon = orderEndTimeMon;
    }

    public String getOrderEndTimeTue() {
        return orderEndTimeTue;
    }

    public void setOrderEndTimeTue(String orderEndTimeTue) {
        this.orderEndTimeTue = orderEndTimeTue;
    }

    public String getOrderEndTimeWed() {
        return orderEndTimeWed;
    }

    public void setOrderEndTimeWed(String orderEndTimeWed) {
        this.orderEndTimeWed = orderEndTimeWed;
    }

    public String getOrderEndTimeThu() {
        return orderEndTimeThu;
    }

    public void setOrderEndTimeThu(String orderEndTimeThu) {
        this.orderEndTimeThu = orderEndTimeThu;
    }

    public String getOrderEndTimeFri() {
        return orderEndTimeFri;
    }

    public void setOrderEndTimeFri(String orderEndTimeFri) {
        this.orderEndTimeFri = orderEndTimeFri;
    }

    public String getOrderEndTimeSat() {
        return orderEndTimeSat;
    }

    public void setOrderEndTimeSat(String orderEndTimeSat) {
        this.orderEndTimeSat = orderEndTimeSat;
    }

    public String getOrderStartTime() {
        return orderStartTime;
    }

    public void setOrderStartTime(String orderStartTime) {
        this.orderStartTime = orderStartTime;
    }

    public String getOrderEndTime() {
        return orderEndTime;
    }

    public void setOrderEndTime(String orderEndTime) {
        this.orderEndTime = orderEndTime;
    }
}
