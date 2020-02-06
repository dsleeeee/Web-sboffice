package kr.co.solbipos.sale.anals.versusPeriod.cls.service;

import kr.co.solbipos.application.common.service.PageVO;

public class VersusPeriodClassVO extends PageVO {

    private static final long serialVersionUID = -4091176508608651478L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장코드 array */
    private String arrStoreCd[];
    /** 분류코드 */
    private String prodClassCd;

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
	public void setArrStoreCd(String[] arrStoreCd) {
		this.arrStoreCd = arrStoreCd;
	}
	public String getProdClassCd() {
		return prodClassCd;
	}
	public void setProdClassCd(String prodClassCd) {
		this.prodClassCd = prodClassCd;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
