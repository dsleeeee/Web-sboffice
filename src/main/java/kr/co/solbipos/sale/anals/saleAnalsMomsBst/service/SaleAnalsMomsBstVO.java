package kr.co.solbipos.sale.anals.saleAnalsMomsBst.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : SaleAnalsMomsBstVO.java
 * @Description : 맘스터치 > 매출분석 > 매출분석(사업전략팀)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.01.27   이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.01.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SaleAnalsMomsBstVO extends PageVO {

    private static final long serialVersionUID = -1591555297893192907L;

    /** 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점 */
    private String orgnFg;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /** 매장코드 리스트 */
    private String[] storeCdList;

    /** 조회매장 */
    private String storeCds;

    /** 매장브랜드코드 */
    private String storeHqBrandCd;

    /** 사용자별 브랜드코드 */
    private String userBrands;

    /** 사용자별 브랜드코드 */
    private String[] userBrandList;

    /** 상품브랜드코드 */
    private String prodHqBrandCd;

    /** 상품코드 */
    private String[] prodCdList;

    /** 상품코드 */
    private String prodCds;

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

    /** 지사코드 */
    private String branchCd;

    /** 상품분류 */
    private String prodClassCd;

    /** 상품표시옵션 */
    private String prodOption;

    /** 상품코드 */
    private String prodCd;

    /** 상품명 */
    private String prodNm;

    /** 동적 컬럼 생성을 위한 쿼리 문자열*/
    private String sQuery1;
    private String sQuery2;

    /** 화면출력 */
    private String viewType;

    /** 일/월/년 구분 */
    private String dayGubun;

    /** 리스트 출력 타입 (all:전체, part:일부) */
    private String listType;

    /** 배달포장구분 */
    private String dlvrInFg;

    /** 배달포장구분 리스트 */
    private String[] dlvrInFgList;

    /** 배달앱 구분 코드*/
    private String dlvrOrderFg;

    /** 배달앱 구분 코드 리스트*/
    private String [] dlvrOrderFgList;

    /** 매출시작일자 */
    private String saleStartDate;

    /** 매출종료일자 */
    private String saleEndDate;


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

    public String[] getStoreCdList() {
        return storeCdList;
    }

    public void setStoreCdList(String[] storeCdList) {
        this.storeCdList = storeCdList;
    }

    public String getStoreCds() {
        return storeCds;
    }

    public void setStoreCds(String storeCds) {
        this.storeCds = storeCds;
    }

    public String getStoreHqBrandCd() {
        return storeHqBrandCd;
    }

    public void setStoreHqBrandCd(String storeHqBrandCd) {
        this.storeHqBrandCd = storeHqBrandCd;
    }

    public String getUserBrands() {
        return userBrands;
    }

    public void setUserBrands(String userBrands) {
        this.userBrands = userBrands;
    }

    public String[] getUserBrandList() {
        return userBrandList;
    }

    public void setUserBrandList(String[] userBrandList) {
        this.userBrandList = userBrandList;
    }

    public String getProdHqBrandCd() {
        return prodHqBrandCd;
    }

    public void setProdHqBrandCd(String prodHqBrandCd) {
        this.prodHqBrandCd = prodHqBrandCd;
    }

    public String[] getProdCdList() {
        return prodCdList;
    }

    public void setProdCdList(String[] prodCdList) {
        this.prodCdList = prodCdList;
    }

    public String getProdCds() {
        return prodCds;
    }

    public void setProdCds(String prodCds) {
        this.prodCds = prodCds;
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

    public String getProdClassCd() {
        return prodClassCd;
    }

    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    public String getProdOption() {
        return prodOption;
    }

    public void setProdOption(String prodOption) {
        this.prodOption = prodOption;
    }

    public String getProdCd() {
        return prodCd;
    }

    public void setProdCd(String prodCd) {
        this.prodCd = prodCd;
    }

    public String getProdNm() {
        return prodNm;
    }

    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    public String getsQuery1() {
        return sQuery1;
    }

    public void setsQuery1(String sQuery1) {
        this.sQuery1 = sQuery1;
    }

    public String getsQuery2() {
        return sQuery2;
    }

    public void setsQuery2(String sQuery2) {
        this.sQuery2 = sQuery2;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public String getDayGubun() {
        return dayGubun;
    }

    public void setDayGubun(String dayGubun) {
        this.dayGubun = dayGubun;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public String getDlvrInFg() {
        return dlvrInFg;
    }

    public void setDlvrInFg(String dlvrInFg) {
        this.dlvrInFg = dlvrInFg;
    }

    public String[] getDlvrInFgList() {
        return dlvrInFgList;
    }

    public void setDlvrInFgList(String[] dlvrInFgList) {
        this.dlvrInFgList = dlvrInFgList;
    }

    public String getDlvrOrderFg() {
        return dlvrOrderFg;
    }

    public void setDlvrOrderFg(String dlvrOrderFg) {
        this.dlvrOrderFg = dlvrOrderFg;
    }

    public String[] getDlvrOrderFgList() {
        return dlvrOrderFgList;
    }

    public void setDlvrOrderFgList(String[] dlvrOrderFgList) {
        this.dlvrOrderFgList = dlvrOrderFgList;
    }

    public String getSaleStartDate() {
        return saleStartDate;
    }

    public void setSaleStartDate(String saleStartDate) {
        this.saleStartDate = saleStartDate;
    }

    public String getSaleEndDate() {
        return saleEndDate;
    }

    public void setSaleEndDate(String saleEndDate) {
        this.saleEndDate = saleEndDate;
    }
}
