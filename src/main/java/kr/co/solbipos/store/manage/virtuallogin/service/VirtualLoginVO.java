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
    /** 권한확인 유저 아이디 버추얼로그인 대상*/
    private String userIdCkeck;
    /** 권한확인 유저 아이디 버추얼로그인 실행자*/
    private String vUserIdCkeck;
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

    /** 관리자 아이디 */
    private String systemId;

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

    public String getUserIdCkeck() { return userIdCkeck; }

    public void setUserIdCkeck(String userIdCkeck) { this.userIdCkeck = userIdCkeck; }

    public String getvUserIdCkeck() { return vUserIdCkeck; }

    public void setvUserIdCkeck(String vUserIdCkeck) { this.vUserIdCkeck = vUserIdCkeck; }

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

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
}
