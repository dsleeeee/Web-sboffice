package kr.co.solbipos.base.price.salePrice.service;

import kr.co.solbipos.application.common.service.PageVO;

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
}
