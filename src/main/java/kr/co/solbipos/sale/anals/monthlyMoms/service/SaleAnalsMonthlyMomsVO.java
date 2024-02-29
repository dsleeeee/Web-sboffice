package kr.co.solbipos.sale.anals.monthlyMoms.service;

import kr.co.solbipos.application.common.service.PageVO;

public class SaleAnalsMonthlyMomsVO extends PageVO {

    private static final long serialVersionUID = -6084172854998623991L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 본사브랜드코드 */
    private String hqBrandCd;
    /** 매장코드 */
    private String storeCd;
    /** 조회년월 */
    private String reqYearMonth;
    /** 조회일(일요일) */
    private String sun;
    /** 조회일(월요일) */
    private String mon;
    /** 조회일(화요일) */
    private String tue;
    /** 조회일(수요일) */
    private String wed;
    /** 조회일(목요일) */
    private String thu;
    /** 조회일(금요일) */
    private String fri;
    /** 조회일(토요일) */
    private String sat;
    /** 조회일자 */
    private String saleDate;
	/** 본사,매장 구분 */
	private String orgnFg;
	/** 사원번호 */
	private String empNo;

	/** 사용자별 브랜드코드 */
	private String[] userBrandList;

	/** 사용자별 브랜드코드 */
	private String userBrands;

	/** 매장브랜드코드 */
	private String storeHqBrandCd;

	/** 그룹코드 */
	private String branchCd;

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

	public String getHqBrandCd() {
		return hqBrandCd;
	}

	public void setHqBrandCd(String hqBrandCd) {
		this.hqBrandCd = hqBrandCd;
	}

	public String getStoreCd() {
		return storeCd;
	}

	public void setStoreCd(String storeCd) {
		this.storeCd = storeCd;
	}

	public String getReqYearMonth() {
		return reqYearMonth;
	}

	public void setReqYearMonth(String reqYearMonth) {
		this.reqYearMonth = reqYearMonth;
	}

	public String getSun() {
		return sun;
	}

	public void setSun(String sun) {
		this.sun = sun;
	}

	public String getMon() {
		return mon;
	}

	public void setMon(String mon) {
		this.mon = mon;
	}

	public String getTue() {
		return tue;
	}
	public void setTue(String tue) {
		this.tue = tue;
	}

	public String getWed() {
		return wed;
	}

	public void setWed(String wed) {
		this.wed = wed;
	}

	public String getThu() {
		return thu;
	}

	public void setThu(String thu) {
		this.thu = thu;
	}

	public String getFri() {
		return fri;
	}
	public void setFri(String fri) {
		this.fri = fri;
	}

	public String getSat() {
		return sat;
	}

	public void setSat(String sat) {
		this.sat = sat;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	public String getOrgnFg() {
		return orgnFg;
	}

	public void setOrgnFg(String orgnFg) {
		this.orgnFg = orgnFg;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
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

	public String getBranchCd() {
		return branchCd;
	}

	public void setBranchCd(String branchCd) {
		this.branchCd = branchCd;
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