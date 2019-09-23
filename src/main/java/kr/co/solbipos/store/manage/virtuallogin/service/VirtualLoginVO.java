package kr.co.solbipos.store.manage.virtuallogin.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : VirtualLoginVO.java
 * @Description : 가맹점관리 > 매장관리 > 가상 로그인
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 06.08
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class VirtualLoginVO extends PageVO {

    private static final long serialVersionUID = -2211956099659465271L;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 본사명 */
    private String hqOfficeNm;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 용도 */
    private String clsFg;
    /** 용도 */
    private String clsFgNm;
    /** 상태 */
    private String sysStatFg;
    /** 상태 */
    private String sysStatFgNm;
    /** 대표자명 */
    private String ownerNm;
    /** 전화번호 */
    private String telNo;
    /** 휴대전화 */
    private String mpNo;
    /** 관리업체 */
    private String agencyCd;
    /** 관리업체 */
    private String agencyNm;
    /** 시스템오픈일 */
    private String sysOpenDate;
    /** 시스템폐점일 */
    private String sysClosureDate;
    /** 본사로그인 ID */
    private String hqUserId;
    /** 매장로그인 ID */
    private String msUserId;
    /** 관리업체로그인 ID */
    private String cmUserId;
    /** 가상로그인ID */
    private String vUserId;
    /** 세션구분 */
    private String orgnFg;
    /** 총판의 부모 총판 코드 */
    private String pAgencyCd;


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
     * @return the hqOfficeNm
     */
    public String getHqOfficeNm() {
        return hqOfficeNm;
    }
    /**
     * @param hqOfficeNm the hqOfficeNm to set
     */
    public void setHqOfficeNm(String hqOfficeNm) {
        this.hqOfficeNm = hqOfficeNm;
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
     * @return the clsFg
     */
    public String getClsFg() {
        return clsFg;
    }
    /**
     * @param clsFg the clsFg to set
     */
    public void setClsFg(String clsFg) {
        this.clsFg = clsFg;
    }
    /**
     * @return the clsFgNm
     */
    public String getClsFgNm() {
        return clsFgNm;
    }
    /**
     * @param clsFgNm the clsFgNm to set
     */
    public void setClsFgNm(String clsFgNm) {
        this.clsFgNm = clsFgNm;
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
     * @return the sysStatFgNm
     */
    public String getSysStatFgNm() {
        return sysStatFgNm;
    }
    /**
     * @param sysStatFgNm the sysStatFgNm to set
     */
    public void setSysStatFgNm(String sysStatFgNm) {
        this.sysStatFgNm = sysStatFgNm;
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
     * @return the telNo
     */
    public String getTelNo() {
        return telNo;
    }
    /**
     * @param telNo the telNo to set
     */
    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }
    /**
     * @return the mpNo
     */
    public String getMpNo() {
        return mpNo;
    }
    /**
     * @param mpNo the mpNo to set
     */
    public void setMpNo(String mpNo) {
        this.mpNo = mpNo;
    }
    /**
     * @return the agencyCd
     */
    public String getAgencyCd() {
        return agencyCd;
    }
    /**
     * @param agencyCd the agencyCd to set
     */
    public void setAgencyCd(String agencyCd) {
        this.agencyCd = agencyCd;
    }
    /**
     * @return the agencyNm
     */
    public String getAgencyNm() {
        return agencyNm;
    }
    /**
     * @param agencyNm the agencyNm to set
     */
    public void setAgencyNm(String agencyNm) {
        this.agencyNm = agencyNm;
    }
    /**
     * @return the sysOpenDate
     */
    public String getSysOpenDate() {
        return sysOpenDate;
    }
    /**
     * @param sysOpenDate the sysOpenDate to set
     */
    public void setSysOpenDate(String sysOpenDate) {
        this.sysOpenDate = sysOpenDate;
    }
    /**
     * @return the sysClosureDate
     */
    public String getSysClosureDate() {
        return sysClosureDate;
    }
    /**
     * @param sysClosureDate the sysClosureDate to set
     */
    public void setSysClosureDate(String sysClosureDate) {
        this.sysClosureDate = sysClosureDate;
    }
    /**
     * @return the hqUserId
     */
    public String getHqUserId() {
        return hqUserId;
    }
    /**
     * @param hqUserId the hqUserId to set
     */
    public void setHqUserId(String hqUserId) {
        this.hqUserId = hqUserId;
    }
    /**
     * @return the msUserId
     */
    public String getMsUserId() {
        return msUserId;
    }
    /**
     * @param msUserId the msUserId to set
     */
    public void setMsUserId(String msUserId) {
        this.msUserId = msUserId;
    }
    /**
     * @return the cmUserId
     */
    public String getCmUserId() {
        return cmUserId;
    }
    /**
     * @param cmUserId the cmUserId to set
     */
    public void setCmUserId(String cmUserId) {
        this.cmUserId = cmUserId;
    }
    /**
     * @return the vUserId
     */
    public String getvUserId() {
        return vUserId;
    }
    /**
     * @param vUserId the vUserId to set
     */
    public void setvUserId(String vUserId) {
        this.vUserId = vUserId;
    }
    /**
     * @return the orgnFg
     */
    public String getOrgnFg() {
        return orgnFg;
    }
    /**
     * @param orgnFg the orgnFg to set
     */
    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getpAgencyCd() {
        return pAgencyCd;
    }

    public void setpAgencyCd(String pAgencyCd) {
        this.pAgencyCd = pAgencyCd;
    }
}
