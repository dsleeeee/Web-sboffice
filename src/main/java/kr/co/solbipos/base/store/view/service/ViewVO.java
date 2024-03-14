package kr.co.solbipos.base.store.view.service;

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
*
*  Copyright (C) by SOLBIPOS CORP. All right reserved.
*/
public class ViewVO extends PageVO {

    private static final long serialVersionUID = -1652554561773776441L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 매장형태 */
    private String     storeType;
    /** 매장형태명 */
    private String     storeTypeNm;
    /** 매장그룹 */
    private String clsFg;
    /** 매장그룹명 */
    private String clsFgNm;
    /** 사업자번호 */
    private String bizNo;
    /** 대표자명 */
    private String ownerNm;
    /** 전화번호 */
    private String telNo;
    /** 휴대폰 */
    private String mpNo;
    /** 주소 */
    private String address;
    /** 상태구분 */
    private String sysStatFg;
    /** 상태구분명 */
    private String sysStatFgNm;
    /** 포스현재버전 */
    private String posVerNo;
    /** 시스템오픈일자 */
    private Date sysOpenDate;
    /** 시스템폐점일자 */
    private String sysClosureDate;
    /** 기준매장코드 */
    private String originalStoreCd;
    /** 사용자별 브랜드코드 */
    private String[] userBrandList;
    /** 사용자별 브랜드코드 */
    private String userBrands;
    /** 매장브랜드코드 */
    private String storeHqBrandCd;

    /** 팀별 */
    private String momsTeam;

    /** AC점포별 */
    private String momsAcShop;

    /** 지역구분 */
    private String momsAreaFg;

    /** 상권 */
    private String momsCommercial;

    /** 점포유형 */
    private String momsShopType;

    /** 매장관리타입 */
    private String momsStoreManageType;

    /** 그룹코드 */
    private String branchCd;

    /** 환경설정 [1250 맘스터치] */
    private String momsEnvstVal;

    /** 매장그룹 */
    private String momsStoreFg01;

    /** 매장그룹2 */
    private String momsStoreFg02;

    /** 매장그룹3 */
    private String momsStoreFg03;

    /** 매장그룹4 */
    private String momsStoreFg04;

    /** 매장그룹5 */
    private String momsStoreFg05;

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
     * @return the storeType
     */
    public String getStoreType() {
        return storeType;
    }
    /**
     * @param storeType the storeType to set
     */
    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }
    /**
     * @return the storeTypeNm
     */
    public String getStoreTypeNm() {
        return storeTypeNm;
    }
    /**
     * @param storeTypeNm the storeTypeNm to set
     */
    public void setStoreTypeNm(String storeTypeNm) {
        this.storeTypeNm = storeTypeNm;
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
     * @return the address
     */
    public String getAddress() {
        return address;
    }
    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
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
     * @return the posVerNo
     */
    public String getPosVerNo() {
        return posVerNo;
    }
    /**
     * @param posVerNo the posVerNo to set
     */
    public void setPosVerNo(String posVerNo) {
        this.posVerNo = posVerNo;
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

    public String getOriginalStoreCd() {
        return originalStoreCd;
    }

    public void setOriginalStoreCd(String originalStoreCd) {
        this.originalStoreCd = originalStoreCd;
    }

    public String[] getUserBrandList() {
        return userBrandList;
    }

    public void setUserBrandList(String[] userBrandList) {
        this.userBrandList = userBrandList;
    }

    public String getUserBrands() {
        return userBrands;
    }

    public void setUserBrands(String userBrands) {
        this.userBrands = userBrands;
    }

    public String getStoreHqBrandCd() {
        return storeHqBrandCd;
    }

    public void setStoreHqBrandCd(String storeHqBrandCd) {
        this.storeHqBrandCd = storeHqBrandCd;
    }

    public String getMomsTeam() {
        return momsTeam;
    }

    public void setMomsTeam(String momsTeam) {
        this.momsTeam = momsTeam;
    }

    public String getMomsAcShop() {
        return momsAcShop;
    }

    public void setMomsAcShop(String momsAcShop) {
        this.momsAcShop = momsAcShop;
    }

    public String getMomsAreaFg() {
        return momsAreaFg;
    }

    public void setMomsAreaFg(String momsAreaFg) {
        this.momsAreaFg = momsAreaFg;
    }

    public String getMomsCommercial() {
        return momsCommercial;
    }

    public void setMomsCommercial(String momsCommercial) {
        this.momsCommercial = momsCommercial;
    }

    public String getMomsShopType() {
        return momsShopType;
    }

    public void setMomsShopType(String momsShopType) {
        this.momsShopType = momsShopType;
    }

    public String getMomsStoreManageType() {
        return momsStoreManageType;
    }

    public void setMomsStoreManageType(String momsStoreManageType) {
        this.momsStoreManageType = momsStoreManageType;
    }

    public String getBranchCd() {
        return branchCd;
    }

    public void setBranchCd(String branchCd) {
        this.branchCd = branchCd;
    }

    public String getMomsEnvstVal() {
        return momsEnvstVal;
    }

    public void setMomsEnvstVal(String momsEnvstVal) {
        this.momsEnvstVal = momsEnvstVal;
    }

    public String getMomsStoreFg01() { return momsStoreFg01; }

    public void setMomsStoreFg01(String momsStoreFg01) { this.momsStoreFg01 = momsStoreFg01; }

    public String getMomsStoreFg02() { return momsStoreFg02; }

    public void setMomsStoreFg02(String momsStoreFg02) { this.momsStoreFg02 = momsStoreFg02; }

    public String getMomsStoreFg03() { return momsStoreFg03; }

    public void setMomsStoreFg03(String momsStoreFg03) { this.momsStoreFg03 = momsStoreFg03; }

    public String getMomsStoreFg04() { return momsStoreFg04; }

    public void setMomsStoreFg04(String momsStoreFg04) { this.momsStoreFg04 = momsStoreFg04; }

    public String getMomsStoreFg05() { return momsStoreFg05; }

    public void setMomsStoreFg05(String momsStoreFg05) { this.momsStoreFg05 = momsStoreFg05; }

}
