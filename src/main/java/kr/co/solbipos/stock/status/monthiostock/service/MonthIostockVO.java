package kr.co.solbipos.stock.status.monthiostock.service;

import kr.co.solbipos.application.common.service.PageVO;

public class MonthIostockVO extends PageVO {
	/** 본사사업장코드 */
    private String hqOfficeCd;
    /** 매장사업장코드 */
    private String storeCd;
    /** 로그인 권한 */
    private String orgnFg;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 바코드코드 */
    private String barcdCd;
    /** 거래처코드 */
    private String vendrCd;
    private String arrVendrCd[];
    /** 상품분류코드 */
    private String prodClassCd;
    /** 단위구분 */
    private String unitFg;

	public String getHqOfficeCd() {
		return hqOfficeCd;
	}

	public void setHqOfficeCd(String hqOfficeCd) {
		this.hqOfficeCd = hqOfficeCd;
	}

	public String getOrgnFg() {
		return orgnFg;
	}

	public void setOrgnFg(String orgnFg) {
		this.orgnFg = orgnFg;
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

	public String getStoreCd() {
		return storeCd;
	}

	public void setStoreCd(String storeCd) {
		this.storeCd = storeCd;
	}
	
	
}
