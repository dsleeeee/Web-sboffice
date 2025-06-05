package kr.co.solbipos.sale.status.dayProdSaleStore.service;

import kr.co.solbipos.application.common.service.PageVO;

public class DayProdSaleStoreVO extends PageVO {

    private static final long serialVersionUID = -8544811688936151485L;

    /**
     * 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점
     */
    private String orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;;
    /** 시작일자 */
    private String startDate;
    /** 종료일자 */
    private String endDate;
    /** 조회매장 */
    private String storeCds;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 상품분류 */
    private String prodClassCd;
    /** 사이드상품코드 */
    private String sideProdCd;
    /** 사이드상품명 */
    private String sideProdNm;
    /** 사이드상품분류 */
    private String sideProdClassCd;
    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;
    /** 매출일자 */
    private String saleDate;
    /** 구분 (일반판매상품, 사이드상품, 사이드모상품)*/
    private String selTypeFg;
    /** 사이드모상품코드 */
    private String sidePProdCd;
    /** 상품분류코드(배열형) */
    private String arrProdClassCd [];
    /** 사이드상품분류코드(배열형) */
    private String arrSideProdClassCd [];

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

    @Override
    public String getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStoreCds() {
        return storeCds;
    }

    public void setStoreCds(String storeCds) {
        this.storeCds = storeCds;
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

    public String getProdClassCd() {
        return prodClassCd;
    }

    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    public String getSideProdCd() {
        return sideProdCd;
    }

    public void setSideProdCd(String sideProdCd) {
        this.sideProdCd = sideProdCd;
    }

    public String getSideProdNm() {
        return sideProdNm;
    }

    public void setSideProdNm(String sideProdNm) {
        this.sideProdNm = sideProdNm;
    }

    public String getSideProdClassCd() {
        return sideProdClassCd;
    }

    public void setSideProdClassCd(String sideProdClassCd) {
        this.sideProdClassCd = sideProdClassCd;
    }

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getSelTypeFg() {
        return selTypeFg;
    }

    public void setSelTypeFg(String selTypeFg) {
        this.selTypeFg = selTypeFg;
    }

    public String getSidePProdCd() {
        return sidePProdCd;
    }

    public void setSidePProdCd(String sidePProdCd) {
        this.sidePProdCd = sidePProdCd;
    }

    public String[] getArrProdClassCd() {
        return arrProdClassCd;
    }

    public void setArrProdClassCd(String[] arrProdClassCd) {
        this.arrProdClassCd = arrProdClassCd;
    }

    public String[] getArrSideProdClassCd() {
        return arrSideProdClassCd;
    }

    public void setArrSideProdClassCd(String[] arrSideProdClassCd) {
        this.arrSideProdClassCd = arrSideProdClassCd;
    }
}
