package kr.co.solbipos.iostock.cmm.service;

import kr.co.solbipos.application.common.service.PageVO;

public class IostockCmmVO extends PageVO {

    private static final long serialVersionUID = -6387982658080046166L;

    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    private String hqOfficeNm;
    /** 브랜드코드 */
    private String hqBrandCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 공통코드 */
    private String nmcodeGrpCd;
    /** 공통코드 아이템1 */
    private String nmcodeItem1;
    /** 테이블 */
    private String selectTable;
    /** 조회할 코드명 */
    private String selectCd;
    /** 조회할 명칭명 */
    private String selectNm;
    /** 조건문 */
    private String selectWhere;
    /** 처리구분 */
    private String procFg;
    
    /** 창고코드 */
    private String storageCd;

    /** 사원번호 */
    private String empNo;

    /** 매장브랜드코드 */
    private String storeHqBrandCd;

    /** 사용자 아이디 */
    private String userId;

    /** 사용자별 브랜드코드 */
    private String[] userBrandList;

    /** 사용자별 브랜드코드 */
    private String userBrands;

    /** 사용자별 본사 공통코드 */
    private String userHqNmcodeCd;

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

    /** 상품브랜드코드 */
    private String prodHqBrandCd;

    /** 상품분류 */
    private String prodClassCd;

    /** 환경설정코드 */
    private String envstCd;

    /** 프로모션코드 */
    private String promotionCd;

    /** 프로모션명 */
    private String promotionNm;

    /** 판매가변경제한매장 */
    private String storeChgNot;

    /** 사이드선택그룹코드 */
    private String sdselGrpCd;

    /** 사이드선택그룹명 */
    private String sdselGrpNm;

    /** 사이드선택분류코드 */
    private String sdselClassCd;

    /** 사이드선택분류명 */
    private String sdselClassNm;

    /** 사이드선택상품코드 */
    private String sdselProdCd;

    /** 사이드선택상품명 */
    private String sdselProdNm;

    /** 사원명 */
    private String empNm;

    /** 휴대폰번호 */
    private String mpNo;

    /** 관리브랜드 */
    private String userHqBrand;

    /** 적용매장구분 */
    private String regStoreFg;

    /** 업로드매장 */
    private String uploadStoreCds;
    /** 업로드매장 */
    private String[] uploadStoreCdList;


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

    public String getHqOfficeNm() {
        return hqOfficeNm;
    }

    public void setHqOfficeNm(String hqOfficeNm) {
        this.hqOfficeNm = hqOfficeNm;
    }

    public String getHqBrandCd() {
        return hqBrandCd;
    }

    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
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

    public String getProdCd() { return prodCd; }

    public void setProdCd(String prodCd) { this.prodCd = prodCd; }

    public String getProdNm() { return prodNm; }

    public void setProdNm(String prodNm) { this.prodNm = prodNm; }

    /**
     * @return the nmcodeGrpCd
     */
    public String getNmcodeGrpCd() {
        return nmcodeGrpCd;
    }

    /**
     * @param nmcodeGrpCd the nmcodeGrpCd to set
     */
    public void setNmcodeGrpCd(String nmcodeGrpCd) {
        this.nmcodeGrpCd = nmcodeGrpCd;
    }

    /**
     * @return the nmcodeItem1
     */
    public String getNmcodeItem1() {
        return nmcodeItem1;
    }

    /**
     * @param nmcodeItem1 the nmcodeItem1 to set
     */
    public void setNmcodeItem1(String nmcodeItem1) {
        this.nmcodeItem1 = nmcodeItem1;
    }

    /**
     * @return the selectTable
     */
    public String getSelectTable() {
        return selectTable;
    }

    /**
     * @param selectTable the selectTable to set
     */
    public void setSelectTable(String selectTable) {
        this.selectTable = selectTable;
    }

    /**
     * @return the selectCd
     */
    public String getSelectCd() {
        return selectCd;
    }

    /**
     * @param selectCd the selectCd to set
     */
    public void setSelectCd(String selectCd) {
        this.selectCd = selectCd;
    }

    /**
     * @return the selectNm
     */
    public String getSelectNm() {
        return selectNm;
    }

    /**
     * @param selectNm the selectNm to set
     */
    public void setSelectNm(String selectNm) {
        this.selectNm = selectNm;
    }

    /**
     * @return the selectWhere
     */
    public String getSelectWhere() {
        return selectWhere;
    }

    /**
     * @param selectWhere the selectWhere to set
     */
    public void setSelectWhere(String selectWhere) {
        this.selectWhere = selectWhere;
    }

    /**
     * @return the procFg
     */
    public String getProcFg() {
        return procFg;
    }

    /**
     * @param procFg the procFg to set
     */
    public void setProcFg(String procFg) {
        this.procFg = procFg;
    }

	public String getStorageCd() {
		return storageCd;
	}

	public void setStorageCd(String storageCd) {
		this.storageCd = storageCd;
	}

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getStoreHqBrandCd() {
        return storeHqBrandCd;
    }

    public void setStoreHqBrandCd(String storeHqBrandCd) { this.storeHqBrandCd = storeHqBrandCd; }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) { this.userId = userId; }

    public String[] getUserBrandList() {
        return userBrandList;
    }

    public void setUserBrandList(String[] userBrandList) {
        this.userBrandList = userBrandList;
    }

    public String getUserBrands() {
        return userBrands;
    }

    public void setUserBrands(String userBrands) { this.userBrands = userBrands; }

    public String getUserHqNmcodeCd() {
        return userHqNmcodeCd;
    }

    public void setUserHqNmcodeCd(String userHqNmcodeCd) { this.userHqNmcodeCd = userHqNmcodeCd; }

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

    public String getProdHqBrandCd() {
        return prodHqBrandCd;
    }

    public void setProdHqBrandCd(String prodHqBrandCd) { this.prodHqBrandCd = prodHqBrandCd; }

    public String getProdClassCd() {
        return prodClassCd;
    }

    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    public String getEnvstCd() {
        return envstCd;
    }

    public void setEnvstCd(String envstCd) {
        this.envstCd = envstCd;
    }

    public String getPromotionCd() {
        return promotionCd;
    }

    public void setPromotionCd(String promotionCd) {
        this.promotionCd = promotionCd;
    }

    public String getPromotionNm() {
        return promotionNm;
    }

    public void setPromotionNm(String promotionNm) {
        this.promotionNm = promotionNm;
    }

    public String getStoreChgNot() {
        return storeChgNot;
    }

    public void setStoreChgNot(String storeChgNot) {
        this.storeChgNot = storeChgNot;
    }

    public String getSdselGrpCd() {
        return sdselGrpCd;
    }

    public void setSdselGrpCd(String sdselGrpCd) {
        this.sdselGrpCd = sdselGrpCd;
    }

    public String getSdselGrpNm() {
        return sdselGrpNm;
    }

    public void setSdselGrpNm(String sdselGrpNm) {
        this.sdselGrpNm = sdselGrpNm;
    }

    public String getSdselClassCd() {
        return sdselClassCd;
    }

    public void setSdselClassCd(String sdselClassCd) {
        this.sdselClassCd = sdselClassCd;
    }

    public String getSdselClassNm() {
        return sdselClassNm;
    }

    public void setSdselClassNm(String sdselClassNm) {
        this.sdselClassNm = sdselClassNm;
    }

    public String getSdselProdCd() {
        return sdselProdCd;
    }

    public void setSdselProdCd(String sdselProdCd) {
        this.sdselProdCd = sdselProdCd;
    }

    public String getSdselProdNm() {
        return sdselProdNm;
    }

    public void setSdselProdNm(String sdselProdNm) {
        this.sdselProdNm = sdselProdNm;
    }

    public String getEmpNm() {
        return empNm;
    }

    public void setEmpNm(String empNm) {
        this.empNm = empNm;
    }

    public String getMpNo() {
        return mpNo;
    }

    public void setMpNo(String mpNo) {
        this.mpNo = mpNo;
    }

    public String getUserHqBrand() {
        return userHqBrand;
    }

    public void setUserHqBrand(String userHqBrand) {
        this.userHqBrand = userHqBrand;
    }

    public String getRegStoreFg() { return regStoreFg; }

    public void setRegStoreFg(String regStoreFg) { this.regStoreFg = regStoreFg; }

    public String getUploadStoreCds() {
        return uploadStoreCds;
    }

    public void setUploadStoreCds(String uploadStoreCds) {
        this.uploadStoreCds = uploadStoreCds;
    }

    public String[] getUploadStoreCdList() {
        return uploadStoreCdList;
    }

    public void setUploadStoreCdList(String[] uploadStoreCdList) {
        this.uploadStoreCdList = uploadStoreCdList;
    }
}
