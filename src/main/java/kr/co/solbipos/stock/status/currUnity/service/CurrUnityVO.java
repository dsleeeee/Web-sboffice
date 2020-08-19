package kr.co.solbipos.stock.status.currUnity.service;

import kr.co.solbipos.application.common.service.PageVO;

public class CurrUnityVO extends PageVO {

	private static final long serialVersionUID = 6438209115509780020L;

	/** 본사코드 */
    private String hqOfficeCd;
    /** 창고코드 */
    private String storageCd;
    /** 브랜드코드 */
    private String hqBrandCd;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 바코드 */
    private String barcdCd;
    /** 거래처코드 */
    private String vendrCd;
    /** 거래처코드 array */
    private String arrVendrCd[];
    /** 상품분류코드 */
    private String prodClassCd;

	public String getHqOfficeCd() {
		return hqOfficeCd;
	}

	public void setHqOfficeCd(String hqOfficeCd) {
		this.hqOfficeCd = hqOfficeCd;
	}

	public String getStorageCd() {
		return storageCd;
	}

	public void setStorageCd(String storageCd) {
		this.storageCd = storageCd;
	}

	public String getHqBrandCd() {
		return hqBrandCd;
	}

	public void setHqBrandCd(String hqBrandCd) {
		this.hqBrandCd = hqBrandCd;
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

	public void setArrVendrCd(String[] arrVendrCd) {
		this.arrVendrCd = arrVendrCd;
	}

	public String getProdClassCd() {
		return prodClassCd;
	}

	public void setProdClassCd(String prodClassCd) {
		this.prodClassCd = prodClassCd;
	}

}
