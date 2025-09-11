package kr.co.solbipos.kookmin.workStudent.workScheduleStore.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name  : WorkScheduleStoreVO.java
 * @Description : 국민대 > 근로학생관리 > 매장별 근무테이블 등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.09.09
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class WorkScheduleStoreVO extends PageVO {
    private static final long serialVersionUID = 6211716677696744407L;

    /** 조회년도 */
    private String srchDate;
    /** 본사코드 */
    private String hqOfficeCd;

    /** 년도 */
    private String termYear;
    /** 학기구분 */
    private String termFg;
    /** 근무코드 */
    private String workSchCode;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 근무요일 */
    private String workDay;
    /** 시작시간 */
    private String startTime;
    /** 종료시간 */
    private String endTime;
    /** 시급 */
    private String hourPay;
    /** 업무 */
    private String workFg;
    /** 근로시간 */
    private String workTime;
    /** 휴게시간 */
    private String breakTime;
    /** 총근로시간 */
    private String totWorkTime;
    /** 비고 */
    private String remark;

    /** 근무요일 일 */
    private Boolean sun;
    /** 근무요일 월 */
    private Boolean mon;
    /** 근무요일 화 */
    private Boolean tue;
    /** 근무요일 수 */
    private Boolean wed;
    /** 근무요일 목 */
    private Boolean thu;
    /** 근무요일 금 */
    private Boolean fri;
    /** 근무요일 토 */
    private Boolean sat;

    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;

    public String getSrchDate() {
        return srchDate;
    }

    public void setSrchDate(String srchDate) {
        this.srchDate = srchDate;
    }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getTermYear() {
        return termYear;
    }

    public void setTermYear(String termYear) {
        this.termYear = termYear;
    }

    public String getTermFg() {
        return termFg;
    }

    public void setTermFg(String termFg) {
        this.termFg = termFg;
    }

    public String getWorkSchCode() {
        return workSchCode;
    }

    public void setWorkSchCode(String workSchCode) {
        this.workSchCode = workSchCode;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getStoreNm() {
        return storeNm;
    }

    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay;
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

    public String getHourPay() {
        return hourPay;
    }

    public void setHourPay(String hourPay) {
        this.hourPay = hourPay;
    }

    public String getWorkFg() {
        return workFg;
    }

    public void setWorkFg(String workFg) {
        this.workFg = workFg;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(String breakTime) {
        this.breakTime = breakTime;
    }

    public String getTotWorkTime() {
        return totWorkTime;
    }

    public void setTotWorkTime(String totWorkTime) {
        this.totWorkTime = totWorkTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
    }

    public Boolean getSun() {
        return sun;
    }

    public void setSun(Boolean sun) {
        this.sun = sun;
    }

    public Boolean getMon() {
        return mon;
    }

    public void setMon(Boolean mon) {
        this.mon = mon;
    }

    public Boolean getTue() {
        return tue;
    }

    public void setTue(Boolean tue) {
        this.tue = tue;
    }

    public Boolean getWed() {
        return wed;
    }

    public void setWed(Boolean wed) {
        this.wed = wed;
    }

    public Boolean getThu() {
        return thu;
    }

    public void setThu(Boolean thu) {
        this.thu = thu;
    }

    public Boolean getFri() {
        return fri;
    }

    public void setFri(Boolean fri) {
        this.fri = fri;
    }

    public Boolean getSat() {
        return sat;
    }

    public void setSat(Boolean sat) {
        this.sat = sat;
    }
}
