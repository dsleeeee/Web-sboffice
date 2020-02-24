package kr.co.solbipos.sale.status.pos.prod.service;

import kr.co.solbipos.application.common.service.PageVO;

public class PosProdVO extends PageVO {

    private static final long serialVersionUID = -4091176508608651478L;

    /** 매장코드 */
    private String storeCd;
    /** 매장코드 array */
    private String arrStoreCd[];

	/** 매출일자 */
    private String saleDate;

    /** 포스번호 */
    private String posNo;
    private String arrPosNo[];
    private String arrStorePos[];

    /** 매출일자상품코드 */
    private String prodCd;
	/** 매출일자상품명 */
    private String prodNm;
	/** 본사사업장코드 */
    private String hqOfficeCd;
    /** 본사브랜드코드 */
    private String hqBrandCd;
    /** 총매출합계 */
    private String totSaleAmt;
    /** 총할인합계 */
    private String totDcAmt;
    /** 실매출합계 */
    private String totRealSaleAmt;
    /** 수량합계 */
    private String totSaleCnt;
    /** 영업매장수 */
    private String saleStoreCnt;

    /** 조회매장포스수 */
    private int storePosCnt;
    
    /** 본사,매장 구분 */
    private String orgnFg;
    
	public String getStoreCd() {
		return storeCd;
	}
	public void setStoreCd(String storeCd) {
		this.storeCd = storeCd;
	}
	public String getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}
	public String getPosNo() {
		return posNo;
	}
	public void setPosNo(String posNo) {
		this.posNo = posNo;
	}
	public String[] getArrPosNo() {
		return arrPosNo;
	}
	public void setArrPosNo(String[] arrPosNo) {
		this.arrPosNo = arrPosNo;
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
	public String getTotSaleAmt() {
		return totSaleAmt;
	}
	public void setTotSaleAmt(String totSaleAmt) {
		this.totSaleAmt = totSaleAmt;
	}
	public String getTotDcAmt() {
		return totDcAmt;
	}
	public void setTotDcAmt(String totDcAmt) {
		this.totDcAmt = totDcAmt;
	}
	public String getTotRealSaleAmt() {
		return totRealSaleAmt;
	}
	public void setTotRealSaleAmt(String totRealSaleAmt) {
		this.totRealSaleAmt = totRealSaleAmt;
	}
	public String getTotSaleCnt() {
		return totSaleCnt;
	}
	public void setTotSaleCnt(String totSaleCnt) {
		this.totSaleCnt = totSaleCnt;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String[] getArrStorePos() {
		return arrStorePos;
	}
	public void setArrStorePos(String[] arrStorePos) {
		this.arrStorePos = arrStorePos;
	}
	public String getSaleStoreCnt() {
		return saleStoreCnt;
	}
	public void setSaleStoreCnt(String saleStoreCnt) {
		this.saleStoreCnt = saleStoreCnt;
	}
	public String[] getArrStoreCd() {
		return arrStoreCd;
	}
	public void setArrStoreCd(String[] arrStoreCd) {
		this.arrStoreCd = arrStoreCd;
	}
	public int getStorePosCnt() {
		return storePosCnt;
	}
	public void setStorePosCnt(int storePosCnt) {
		this.storePosCnt = storePosCnt;
	}
	public String getOrgnFg() {
		return orgnFg;
	}
	public void setOrgnFg(String orgnFg) {
		this.orgnFg = orgnFg;
	}
	
}
