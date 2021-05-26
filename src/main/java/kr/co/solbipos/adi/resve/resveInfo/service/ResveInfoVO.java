package kr.co.solbipos.adi.resve.resveInfo.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : ResveInfoVO.java
 * @Description : 부가서비스 > 예약관리 > 예약현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.20  권지현      최초생성
 * RESVEINFO
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.05.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ResveInfoVO extends PageVO {

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
    /** 매장코드 */
    private String storeCd;
    private String arrStoreCd[];
    /** 매장명 */
    private String storeNm;
    /** 등록 시작 날짜 */
    private String startDate;
    /** 등록 종료 날짜 */
    private String endDate;
    /** 예약 시작 날짜 */
    private String resveStartDate;
    /** 예약 종료 날짜 */
    private String resveEndDate;
    /** 예약경로 */
    private String resveInFg;
    /** 포스번호 */
    private String posNo;
    /** 예약번호 */
    private String resveNo;
    /** 예약자명 */
    private String resveGuestNm;
    /** 예약자전화번호 */
    private String resveGuesTelNo;
    /** 예약고객수 */
    private String resveGuesCnt;
    /** 예약메모 */
    private String resveMemo;
    /** 등록일시 */
    private String regDt;
    /** 등록아이디 */
    private String regId;
    /** 수정일시 */
    private String modDt;
    /** 수정아이디 */
    private String modId;

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

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

    public String[] getArrStoreCd() {
        return arrStoreCd;
    }

    public void setArrStoreCd(String[] arrStoreCd) {
        this.arrStoreCd = arrStoreCd;
    }

    public String getStoreNm() {
        return storeNm;
    }

    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
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

    public String getResveStartDate() {
        return resveStartDate;
    }

    public void setResveStartDate(String resveStartDate) {
        this.resveStartDate = resveStartDate;
    }

    public String getResveEndDate() {
        return resveEndDate;
    }

    public void setResveEndDate(String resveEndDate) {
        this.resveEndDate = resveEndDate;
    }

    public String getResveInFg() {
        return resveInFg;
    }

    public void setResveInFg(String resveInFg) {
        this.resveInFg = resveInFg;
    }

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    public String getResveNo() {
        return resveNo;
    }

    public void setResveNo(String resveNo) {
        this.resveNo = resveNo;
    }

    public String getResveGuestNm() {
        return resveGuestNm;
    }

    public void setResveGuestNm(String resveGuestNm) {
        this.resveGuestNm = resveGuestNm;
    }

    public String getResveGuesTelNo() {
        return resveGuesTelNo;
    }

    public void setResveGuesTelNo(String resveGuesTelNo) {
        this.resveGuesTelNo = resveGuesTelNo;
    }

    public String getResveGuesCnt() {
        return resveGuesCnt;
    }

    public void setResveGuesCnt(String resveGuesCnt) {
        this.resveGuesCnt = resveGuesCnt;
    }

    public String getResveMemo() {
        return resveMemo;
    }

    public void setResveMemo(String resveMemo) {
        this.resveMemo = resveMemo;
    }

    @Override
    public String getRegDt() {
        return regDt;
    }

    @Override
    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    @Override
    public String getRegId() {
        return regId;
    }

    @Override
    public void setRegId(String regId) {
        this.regId = regId;
    }

    @Override
    public String getModDt() {
        return modDt;
    }

    @Override
    public void setModDt(String modDt) {
        this.modDt = modDt;
    }

    @Override
    public String getModId() {
        return modId;
    }

    @Override
    public void setModId(String modId) {
        this.modId = modId;
    }
}