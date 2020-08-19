package kr.co.solbipos.stock.status.periodiostock.service;

import kr.co.solbipos.application.common.service.PageVO;

public class PeriodIostockVO extends PageVO {

	private static final long serialVersionUID = -914229093850403323L;
	
	/** 본사/가맹정 구분 */
	private String orgnFg;
	/** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장코드 array */
    private String arrStoreCd[];
	/** 매장명 */
	private String storeNm;
	/** 상품코드 */
	private String prodCd;
	/** 상품명 */
	private String prodNm;
	/** 바코드 */
	private String barcdCd;
	/** 거래처코드 */
	private String vendrCd;
	/** 거래처코드 */
	private String arrVendrCd[];
	/** 분류 */
	private String prodClassCd;
	/** 단위 */
	private String unitFg;
	/** 조회옵션 */
	private String srchOption;
	/** 구분코드
	 * (01: 본사입고, 16: 업체반출, 13: 본사출고, 02: 본사반입, 04: 매장이입, 14: 매장이출, 17: 재고폐기, 21: 재고조정,
	 * 22: 세트생성, 19: 거래처출고, 33: 거래처반품, 03: 매장입고, 12: 매장반품, 06: 사입입고, 18: 사입반품, 11: 매장판매) */
	private String colCode;

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

	public String[] getArrStoreCd() {
		return arrStoreCd;
	}

	public void setArrStoreCd(String arrStoreCd[]) {
		this.arrStoreCd = arrStoreCd;
	}
	
	public String getStoreNm() {
		return storeNm;
	}

	public void setStoreNm(String storeNm) {
		this.storeNm = storeNm;
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

	public String getBarcdCd() {
		return barcdCd;
	}

	public void setBarcdCd(String barcdCd) {
		this.barcdCd = barcdCd;
	}

	public String getVendrCd() {
		return vendrCd;
	}

	public void setVendrCd(String vendrCd) {
		this.vendrCd = vendrCd;
	}
	
	public String[] getArrVendrCd() {
		return arrVendrCd;
	}

	public void setArrVendrCd(String arrVendrCd[]) {
		this.arrVendrCd = arrVendrCd;
	}

	public String getProdClassCd() {
		return prodClassCd;
	}

	public void setProdClassCd(String prodClassCd) {
		this.prodClassCd = prodClassCd;
	}

	public String getUnitFg() {
		return unitFg;
	}

	public void setUnitFg(String unitFg) {
		this.unitFg = unitFg;
	}

	public String getSrchOption() {
		return srchOption;
	}

	public void setSrchOption(String srchOption) {
		this.srchOption = srchOption;
	}

	public String getColCode() {
		return colCode;
	}

	public void setColCode(String colCode) {
		this.colCode = colCode;
	}
}
