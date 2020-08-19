package kr.co.solbipos.stock.status.storeperiod.service;

import kr.co.solbipos.application.common.service.PageVO;

public class StorePeriodVO extends PageVO {

	private static final long serialVersionUID = 4183263951326875767L;
	
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
	/** 거래처 */
	private String vendrCd;
	/** 거래처코드 array */
    private String arrVendrCd[];
	/** 분류 */
	private String prodClassCd;
	/** 단위 */
	private String unitFg;
	/** 조회옵션 */
	private String srchOption;

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
}
