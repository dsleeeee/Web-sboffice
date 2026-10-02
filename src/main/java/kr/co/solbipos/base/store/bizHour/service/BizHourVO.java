package kr.co.solbipos.base.store.bizHour.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : BizHourVO.java
 * @Description : 기초관리 > 매장관리 > 매장영업시간관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.10.01  김유승      최초생성
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class BizHourVO extends PageVO {

    private static final long serialVersionUID = 6531284926841289001L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 대표자명 */
    private String ownerNm;
    /** 시스템상태 */
    private String sysStatFg;
    /** 매장(멀티) 조회를 위한 쿼리 문자열 */
    private String storeCdQuery;

    /** 요일구분 1:일 2:월 3:화 4:수 5:목 6:금 7:토 */
    private String dayFg;
    /** 특정일 YYYYMMDD */
    private String bizDate;
    /** 영업구분 1:영업 2:휴게 */
    private String bizFg;
    /** 순번 */
    private Integer seq;

    /** 시작시각 HHMM */
    private String startTime;
    /** 종료시각 HHMM */
    private String endTime;

    /** 화면 표시용 시작 시 */
    private String startHour;
    /** 화면 표시용 시작 분 */
    private String startMs;
    /** 화면 표시용 종료 시 */
    private String endHour;
    /** 화면 표시용 종료 분 */
    private String endMs;

    /** 비고(특정일) */
    private String remark;

    /** 시간중복 검증 제외 순번 (수정 저장 시 같은 그룹의 편집 대상 seq 전체) */
    private Integer[] arrExcludeSeq;

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

    public String getStoreNm() {
        return storeNm;
    }

    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    public String getOwnerNm() {
        return ownerNm;
    }

    public void setOwnerNm(String ownerNm) {
        this.ownerNm = ownerNm;
    }

    public String getSysStatFg() {
        return sysStatFg;
    }

    public void setSysStatFg(String sysStatFg) {
        this.sysStatFg = sysStatFg;
    }

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
    }

    public String getDayFg() {
        return dayFg;
    }

    public void setDayFg(String dayFg) {
        this.dayFg = dayFg;
    }

    public String getBizDate() {
        return bizDate;
    }

    public void setBizDate(String bizDate) {
        this.bizDate = bizDate;
    }

    public String getBizFg() {
        return bizFg;
    }

    public void setBizFg(String bizFg) {
        this.bizFg = bizFg;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
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

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getStartMs() {
        return startMs;
    }

    public void setStartMs(String startMs) {
        this.startMs = startMs;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getEndMs() {
        return endMs;
    }

    public void setEndMs(String endMs) {
        this.endMs = endMs;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer[] getArrExcludeSeq() {
        return arrExcludeSeq;
    }

    public void setArrExcludeSeq(Integer[] arrExcludeSeq) {
        this.arrExcludeSeq = arrExcludeSeq;
    }
}
