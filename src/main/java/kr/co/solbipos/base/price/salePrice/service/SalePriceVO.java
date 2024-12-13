package kr.co.solbipos.base.price.salePrice.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.base.prod.prod.service.enums.WorkModeFg;

/**
 * @Class Name : SalePriceVO.java
 * @Description : 기초관리 - 가격관리 - 판매가격관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.24  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.12.24
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SalePriceVO extends PageVO {

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /** 매장코드 */
    private String[] storeCdList;

    /** 조회매장 */
    private String storeCds;

    /** 매장코드 */
    private String[] saveStoreCdList;

    /** 조회매장 */
    private String saveStoreCds;

    /** 상품코드 */
    private String prodCd;

    /** 상품명 */
    private String prodNm;

    /** 변경전 매장 판매가 */
    private String saleUprcP;

    /** 매장판매가 */
    private String saleUprc;

    /** 매장공급가 */
    private String storeSplyUprc;

    /** 본사마진금액 */
    private String hqMarginAmt;

    /** 본사마진율 */
    private String hqMarginRate;

    /** 현재판매금액 */
    private String saleUprcSmt;

    /** 매장마진금액 */
    private String storeMarginAmt;

    /** 매장마진율 */
    private String storeMarginRate;

    /** 주문단위수량 */
    private String poUnitQty;

    /** 주문단위수량 */
    private String prcCtrlFg;

    /** 조회 매장 */
    private String arrStoreCd[];

    /** 변경 판매가 선택 옵션 (H:본사 , S:매장) */
    private String saleAmtOption;

    /** 판매가 적용 시작일 */
    private String startDate;

    /** 판매가 적용 마지막일 */
    private String endDate;

    /** 상품분류코드 */
    private String prodClassCd;

    /** 내점가 */
    private String stinSaleUprc;

    /** 배달가 */
    private String dlvrSaleUprc;

    /** 포장가 */
    private String packSaleUprc;

    /** 전매장적용 구분 */
    private String applyFg;

    /**
     * workMode<br>
     * 1 : 상품정보수정<br>
     * 2 : 신규상품등록<br>
     * 3 : 매장등록<br>
     */
    private WorkModeFg workMode;

    /** 프로시져 실행 결과 */
    private String result;

    /** 사용자 아이디 */
    private String userId;

    /** 사용자별 브랜드코드(상품) */
    private String[] userProdBrandList;

    /** 사용자별 브랜드코드(상품) */
    private String userProdBrands;

    /** 상품브랜드코드 */
    private String prodHqBrandCd;

    /** 세션ID */
    private String sessionId;

    /** 순번 */
    private int seq;

    /** 사용자별 브랜드코드(매장) */
    private String[] userBrandList;

    /** 사용자별 브랜드코드(매장) */
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

    /** 엑셀업로드 구분 (H:본사판매가, S:매장판매가) */
    private String salePriceOrgnFg;

    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;

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

    /** 사용여부 */
    private String useYn;

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

    public String[] getSaveStoreCdList() {
        return saveStoreCdList;
    }

    public void setSaveStoreCdList(String[] saveStoreCdList) {
        this.saveStoreCdList = saveStoreCdList;
    }

    public String getSaveStoreCds() {
        return saveStoreCds;
    }

    public void setSaveStoreCds(String saveStoreCds) {
        this.saveStoreCds = saveStoreCds;
    }

    /**
     * @return the prodCd
     */

    public String getProdCd() {
        return prodCd;
    }

    /**
     * @param prodCd the prodCd to set
     */
    public void setProdCd(String prodCd) {
        this.prodCd = prodCd;
    }

    public String getProdNm() {
        return prodNm;
    }

    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    /**
     * @return the saleUprcP
     */

    public String getSaleUprcP() {
        return saleUprcP;
    }

    /**
     * @param saleUprcP the saleUprcP to set
     */
    public void setSaleUprcP(String saleUprcP) {
        this.saleUprcP = saleUprcP;
    }

    /**
     * @return the saleUprc
     */

    public String getSaleUprc() {
        return saleUprc;
    }

    /**
     * @param saleUprc the saleUprc to set
     */
    public void setSaleUprc(String saleUprc) {
        this.saleUprc = saleUprc;
    }

    /**
     * @return the storeSplyUprc
     */

    public String getStoreSplyUprc() {
        return storeSplyUprc;
    }

    /**
     * @param storeSplyUprc the storeSplyUprc to set
     */
    public void setStoreSplyUprc(String storeSplyUprc) {
        this.storeSplyUprc = storeSplyUprc;
    }

    /**
     * @return the hqMarginAmt
     */

    public String getHqMarginAmt() {
        return hqMarginAmt;
    }

    /**
     * @param hqMarginAmt the hqMarginAmt to set
     */
    public void setHqMarginAmt(String hqMarginAmt) {
        this.hqMarginAmt = hqMarginAmt;
    }

    /**
     * @return the hqMarginRate
     */

    public String getHqMarginRate() {
        return hqMarginRate;
    }

    /**
     * @param hqMarginRate the hqMarginRate to set
     */
    public void setHqMarginRate(String hqMarginRate) {
        this.hqMarginRate = hqMarginRate;
    }

    /**
     * @return the saleUprcSmt
     */

    public String getSaleUprcSmt() {
        return saleUprcSmt;
    }

    /**
     * @param saleUprcSmt the saleUprcSmt to set
     */
    public void setSaleUprcSmt(String saleUprcSmt) {
        this.saleUprcSmt = saleUprcSmt;
    }

    /**
     * @return the storeMarginAmt
     */

    public String getStoreMarginAmt() {
        return storeMarginAmt;
    }

    /**
     * @param storeMarginAmt the storeMarginAmt to set
     */
    public void setStoreMarginAmt(String storeMarginAmt) {
        this.storeMarginAmt = storeMarginAmt;
    }

    /**
     * @return the storeMarginRate
     */

    public String getStoreMarginRate() {
        return storeMarginRate;
    }

    /**
     * @param storeMarginRate the storeMarginRate to set
     */
    public void setStoreMarginRate(String storeMarginRate) {
        this.storeMarginRate = storeMarginRate;
    }

    /**
     * @return the poUnitQty
     */

    public String getPoUnitQty() {
        return poUnitQty;
    }

    /**
     * @param poUnitQty the poUnitQty to set
     */
    public void setPoUnitQty(String poUnitQty) {
        this.poUnitQty = poUnitQty;
    }

    /**
     * @return the prcCtrlFg
     */

    public String getPrcCtrlFg() {
        return prcCtrlFg;
    }

    /**
     * @param prcCtrlFg the prcCtrlFg to set
     */
    public void setPrcCtrlFg(String prcCtrlFg) {
        this.prcCtrlFg = prcCtrlFg;
    }

    /**
     * @return the arrStoreCd
     */

    public String[] getArrStoreCd() {
        return arrStoreCd;
    }

    /**
     * @param arrStoreCd the arrStoreCd to set
     */
    public void setArrStoreCd(String[] arrStoreCd) {
        this.arrStoreCd = arrStoreCd;
    }

    /**
     * @return the saleAmtOption
     */

    public String getSaleAmtOption() {
        return saleAmtOption;
    }

    /**
     * @param saleAmtOption the saleAmtOption to set
     */
    public void setSaleAmtOption(String saleAmtOption) {
        this.saleAmtOption = saleAmtOption;
    }

    /**
     * @return the startDate
     */

    @Override public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    @Override public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */

    @Override public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    @Override public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getProdClassCd() {
        return prodClassCd;
    }

    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    public String getStinSaleUprc() {
        return stinSaleUprc;
    }

    public void setStinSaleUprc(String stinSaleUprc) {
        this.stinSaleUprc = stinSaleUprc;
    }

    public String getDlvrSaleUprc() {
        return dlvrSaleUprc;
    }

    public void setDlvrSaleUprc(String dlvrSaleUprc) {
        this.dlvrSaleUprc = dlvrSaleUprc;
    }

    public String getPackSaleUprc() {
        return packSaleUprc;
    }

    public void setPackSaleUprc(String packSaleUprc) {
        this.packSaleUprc = packSaleUprc;
    }

    public String getApplyFg() {
        return applyFg;
    }

    public void setApplyFg(String applyFg) {
        this.applyFg = applyFg;
    }

    public WorkModeFg getWorkMode() {
        return workMode;
    }

    public void setWorkMode(WorkModeFg workMode) {
        this.workMode = workMode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String[] getUserProdBrandList() {
        return userProdBrandList;
    }

    public void setUserProdBrandList(String[] userProdBrandList) {
        this.userProdBrandList = userProdBrandList;
    }

    public String getUserProdBrands() {
        return userProdBrands;
    }

    public void setUserProdBrands(String userProdBrands) {
        this.userProdBrands = userProdBrands;
    }

    public String getProdHqBrandCd() {
        return prodHqBrandCd;
    }

    public void setProdHqBrandCd(String prodHqBrandCd) {
        this.prodHqBrandCd = prodHqBrandCd;
    }

    public String getSessionId() { return sessionId; }

    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public int getSeq() { return seq; }

    public void setSeq(int seq) { this.seq = seq; }

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

    public String getSalePriceOrgnFg() {
        return salePriceOrgnFg;
    }

    public void setSalePriceOrgnFg(String salePriceOrgnFg) {
        this.salePriceOrgnFg = salePriceOrgnFg;
    }

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
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

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
}
