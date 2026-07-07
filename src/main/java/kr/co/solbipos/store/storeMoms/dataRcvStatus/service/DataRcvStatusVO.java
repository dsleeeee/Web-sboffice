package kr.co.solbipos.store.storeMoms.dataRcvStatus.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : DataRcvStatusVO.java
 * @Description : 맘스터치 > 매장관리 > 자료수신현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.03  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.03
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public class DataRcvStatusVO extends PageVO {

    private static final long serialVersionUID = 1L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 소속구분 */
    private String orgnFg;

    /** 시작일자 */
    private String startDate;

    /** 종료일자 */
    private String endDate;

    /** 매장브랜드코드 */
    private String storeHqBrandCd;

    /** 사용자별 브랜드코드 */
    private String userBrands;

    /** 사용자별 브랜드코드 array */
    private String[] userBrandList;

    /** 매장코드 */
    private String storeCd;

    /** 매장코드 array (멀티매장선택) */
    private String storeCds;

    /** 매장코드 배열 */
    private String[] arrStoreCd;

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

    /** 포스번호 */
    private String posNo;

    /** 일련번호 */
    private String logSeq;

    /** 로그일자 */
    private String logDate;

    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;

    public String getHqOfficeCd() { return hqOfficeCd; }
    public void setHqOfficeCd(String hqOfficeCd) { this.hqOfficeCd = hqOfficeCd; }

    public String getOrgnFg() { return orgnFg; }
    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public String getStoreHqBrandCd() { return storeHqBrandCd; }
    public void setStoreHqBrandCd(String storeHqBrandCd) { this.storeHqBrandCd = storeHqBrandCd; }

    public String getUserBrands() { return userBrands; }
    public void setUserBrands(String userBrands) { this.userBrands = userBrands; }

    public String[] getUserBrandList() { return userBrandList; }
    public void setUserBrandList(String[] userBrandList) { this.userBrandList = userBrandList; }

    public String getStoreCd() { return storeCd; }
    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String getStoreCds() { return storeCds; }
    public void setStoreCds(String storeCds) { this.storeCds = storeCds; }

    public String[] getArrStoreCd() { return arrStoreCd; }
    public void setArrStoreCd(String[] arrStoreCd) { this.arrStoreCd = arrStoreCd; }

    public String getMomsTeam() { return momsTeam; }
    public void setMomsTeam(String momsTeam) { this.momsTeam = momsTeam; }

    public String getMomsAcShop() { return momsAcShop; }
    public void setMomsAcShop(String momsAcShop) { this.momsAcShop = momsAcShop; }

    public String getMomsAreaFg() { return momsAreaFg; }
    public void setMomsAreaFg(String momsAreaFg) { this.momsAreaFg = momsAreaFg; }

    public String getMomsCommercial() { return momsCommercial; }
    public void setMomsCommercial(String momsCommercial) { this.momsCommercial = momsCommercial; }

    public String getMomsShopType() { return momsShopType; }
    public void setMomsShopType(String momsShopType) { this.momsShopType = momsShopType; }

    public String getMomsStoreManageType() { return momsStoreManageType; }
    public void setMomsStoreManageType(String momsStoreManageType) { this.momsStoreManageType = momsStoreManageType; }

    public String getBranchCd() { return branchCd; }
    public void setBranchCd(String branchCd) { this.branchCd = branchCd; }

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

    public String getPosNo() { return posNo; }
    public void setPosNo(String posNo) { this.posNo = posNo; }

    public String getLogSeq() { return logSeq; }
    public void setLogSeq(String logSeq) { this.logSeq = logSeq; }

    public String getLogDate() { return logDate; }
    public void setLogDate(String logDate) { this.logDate = logDate; }

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
    }
}
