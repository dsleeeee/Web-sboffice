package kr.co.solbipos.sale.anals.dayTemporaryDtlSale.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.common.service.PageVO;

import java.util.List;

/**
 * @Class Name  : DayTemporaryDtlSaleVO.java
 * @Description : 맘스터치 > 매출분석2 > 일별 가승인 상세매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.05  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.08.05
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public class DayTemporaryDtlSaleVO extends PageVO {

    private static final long serialVersionUID = 2727794116824286805L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /** 매장 멀티선택 */
    private String storeCds;

    /** 매장(멀티) 조회를 위한 쿼리 문자열 */
    private String storeCdQuery;

    /** 매장브랜드코드 */
    private String storeHqBrandCd;

    /** 사용자별 브랜드코드 */
    private String[] userBrandList;

    /** 사용자별 브랜드코드 */
    private String userBrands;

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

    /** 매장별가승인매출 코드 목록(피벗 컬럼 생성용) */
    private List<DefaultMap<Object>> temporaryPayColList;

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

    public String getStoreCds() {
        return storeCds;
    }

    public void setStoreCds(String storeCds) {
        this.storeCds = storeCds;
    }

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
    }

    public String getStoreHqBrandCd() {
        return storeHqBrandCd;
    }

    public void setStoreHqBrandCd(String storeHqBrandCd) {
        this.storeHqBrandCd = storeHqBrandCd;
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

    public List<DefaultMap<Object>> getTemporaryPayColList() {
        return temporaryPayColList;
    }

    public void setTemporaryPayColList(List<DefaultMap<Object>> temporaryPayColList) {
        this.temporaryPayColList = temporaryPayColList;
    }
}
