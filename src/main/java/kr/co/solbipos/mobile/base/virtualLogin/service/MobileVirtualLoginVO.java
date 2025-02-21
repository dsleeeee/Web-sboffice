package kr.co.solbipos.mobile.base.virtualLogin.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : MobileVirtualLoginVO.java
 * @Description : 모바일 > 기초관리 > 가상 로그인
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.02.06  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2025.02.06
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class MobileVirtualLoginVO extends PageVO {

    private static final long serialVersionUID = 2012302568974101652L;

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

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getHqOfficeNm() {
        return hqOfficeNm;
    }

    public void setHqOfficeNm(String hqOfficeNm) {
        this.hqOfficeNm = hqOfficeNm;
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

    public String getClsFg() {
        return clsFg;
    }

    public void setClsFg(String clsFg) {
        this.clsFg = clsFg;
    }

    public String getClsFgNm() {
        return clsFgNm;
    }

    public void setClsFgNm(String clsFgNm) {
        this.clsFgNm = clsFgNm;
    }

    public String getSysStatFg() {
        return sysStatFg;
    }

    public void setSysStatFg(String sysStatFg) {
        this.sysStatFg = sysStatFg;
    }

    public String getSysStatFgNm() {
        return sysStatFgNm;
    }

    public void setSysStatFgNm(String sysStatFgNm) {
        this.sysStatFgNm = sysStatFgNm;
    }

    public String getOwnerNm() {
        return ownerNm;
    }

    public void setOwnerNm(String ownerNm) {
        this.ownerNm = ownerNm;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getMpNo() {
        return mpNo;
    }

    public void setMpNo(String mpNo) {
        this.mpNo = mpNo;
    }

    public String getAgencyCd() {
        return agencyCd;
    }

    public void setAgencyCd(String agencyCd) {
        this.agencyCd = agencyCd;
    }

    public String getAgencyNm() {
        return agencyNm;
    }

    public void setAgencyNm(String agencyNm) {
        this.agencyNm = agencyNm;
    }

    public String getSysOpenDate() {
        return sysOpenDate;
    }

    public void setSysOpenDate(String sysOpenDate) {
        this.sysOpenDate = sysOpenDate;
    }

    public String getSysClosureDate() {
        return sysClosureDate;
    }

    public void setSysClosureDate(String sysClosureDate) {
        this.sysClosureDate = sysClosureDate;
    }

    public String getHqUserId() {
        return hqUserId;
    }

    public void setHqUserId(String hqUserId) {
        this.hqUserId = hqUserId;
    }

    public String getMsUserId() {
        return msUserId;
    }

    public void setMsUserId(String msUserId) {
        this.msUserId = msUserId;
    }

    public String getCmUserId() {
        return cmUserId;
    }

    public void setCmUserId(String cmUserId) {
        this.cmUserId = cmUserId;
    }

    public String getvUserId() {
        return vUserId;
    }

    public void setvUserId(String vUserId) {
        this.vUserId = vUserId;
    }

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getpAgencyCd() {
        return pAgencyCd;
    }

    public void setpAgencyCd(String pAgencyCd) {
        this.pAgencyCd = pAgencyCd;
    }

    public String getUserIdCkeck() {
        return userIdCkeck;
    }

    public void setUserIdCkeck(String userIdCkeck) {
        this.userIdCkeck = userIdCkeck;
    }

    public String getvUserIdCkeck() {
        return vUserIdCkeck;
    }

    public void setvUserIdCkeck(String vUserIdCkeck) {
        this.vUserIdCkeck = vUserIdCkeck;
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

    public String getMomsStoreFg01() {
        return momsStoreFg01;
    }

    public void setMomsStoreFg01(String momsStoreFg01) {
        this.momsStoreFg01 = momsStoreFg01;
    }

    public String getMomsStoreFg02() {
        return momsStoreFg02;
    }

    public void setMomsStoreFg02(String momsStoreFg02) {
        this.momsStoreFg02 = momsStoreFg02;
    }

    public String getMomsStoreFg03() {
        return momsStoreFg03;
    }

    public void setMomsStoreFg03(String momsStoreFg03) {
        this.momsStoreFg03 = momsStoreFg03;
    }

    public String getMomsStoreFg04() {
        return momsStoreFg04;
    }

    public void setMomsStoreFg04(String momsStoreFg04) {
        this.momsStoreFg04 = momsStoreFg04;
    }

    public String getMomsStoreFg05() {
        return momsStoreFg05;
    }

    public void setMomsStoreFg05(String momsStoreFg05) {
        this.momsStoreFg05 = momsStoreFg05;
    }
}
