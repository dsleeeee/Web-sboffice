package kr.co.solbipos.store.manage.storeview.service;

import kr.co.solbipos.application.common.service.PageVO;

import java.util.Date;

/**
* @Class Name : StoreViewVO.java
* @Description : 가맹점 관리 > 매장관리 > 매장정보조회
* @Modification Information
* @
* @  수정일      수정자              수정내용
* @ ----------  ---------   -------------------------------
* @ 2018.08.09  김영근      최초생성
*
* @author nhn kcp 김영근
* @since 2018. 08.09
* @version 1.0
* @see
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public class StoreViewVO extends PageVO {

    private static final long serialVersionUID = -1652554561773776441L;
    
    /** 본사코드 */
    private String hqOfficeCd;
    /** 본사명 */
    private String hqOfficeNm;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 사업자번호 */
    private String bizNo;
    /** 대표자명 */
    private String ownerNm;
    /** 전화번호 */
    private String telNo;
    /** 상태구분 */
    private String sysStatFg;
    /** 상태구분명 */
    private String sysStatFgNm;
    /** 용도구분 */
    private String clsFg;
    /** 용도구분명 */
    private String clsFgNm;
    /** 포스 수 */
    private int posCnt;
    /** 관리업체코드 */
    private String agencyCd;
    /** 관리업체명 */
    private String agencyNm;
    /** 밴코드 */
    private String vanCd;
    /** 밴명 */
    private String vanNm;
    /** 코너사용구분명 */
    private String cornerUseNm;
    /** 포스로그인일자 */
    private String posLastLoginDt;
    /** 시스템오픈일자 */
    private Date sysOpenDate;
    /** 시스템폐점일자 */
    private String sysClosureDate;
    
    /** 검색조건 조회일자조건(최종로그인, 시스템오픈일, 시스템폐점) */
    private String dateType;

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
     * @return the bizNo
     */
    public String getBizNo() {
        return bizNo;
    }

    /**
     * @param bizNo the bizNo to set
     */
    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
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
     * @return the posCnt
     */
    public int getPosCnt() {
        return posCnt;
    }

    /**
     * @param posCnt the posCnt to set
     */
    public void setPosCnt(int posCnt) {
        this.posCnt = posCnt;
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
     * @return the vanCd
     */
    public String getVanCd() {
        return vanCd;
    }

    /**
     * @param vanCd the vanCd to set
     */
    public void setVanCd(String vanCd) {
        this.vanCd = vanCd;
    }

    /**
     * @return the vanNm
     */
    public String getVanNm() {
        return vanNm;
    }

    /**
     * @param vanNm the vanNm to set
     */
    public void setVanNm(String vanNm) {
        this.vanNm = vanNm;
    }

    /**
     * @return the cornerUseNm
     */
    public String getCornerUseNm() {
        return cornerUseNm;
    }

    /**
     * @param cornerUseNm the cornerUseNm to set
     */
    public void setCornerUseNm(String cornerUseNm) {
        this.cornerUseNm = cornerUseNm;
    }

    /**
     * @return the posLastLoginDt
     */
    public String getPosLastLoginDt() {
        return posLastLoginDt;
    }

    /**
     * @param posLastLoginDt the posLastLoginDt to set
     */
    public void setPosLastLoginDt(String posLastLoginDt) {
        this.posLastLoginDt = posLastLoginDt;
    }

    /**
     * @return the sysOpenDate
     */
    public Date getSysOpenDate() {
        return sysOpenDate;
    }

    /**
     * @param sysOpenDate the sysOpenDate to set
     */
    public void setSysOpenDate(Date sysOpenDate) {
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
     * @return the dateType
     */
    public String getDateType() {
        return dateType;
    }

    /**
     * @param dateType the dateType to set
     */
    public void setDateType(String dateType) {
        this.dateType = dateType;
    }
}
