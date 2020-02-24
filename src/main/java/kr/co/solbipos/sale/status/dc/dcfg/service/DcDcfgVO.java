package kr.co.solbipos.sale.status.dc.dcfg.service;

import kr.co.solbipos.application.common.service.PageVO;

public class DcDcfgVO extends PageVO {

	private static final long serialVersionUID = 1L;

	/** 본사사업장코드 */
    private String hqOfficeCd;
	/** 매장코드 */
    private String storeCd;
    /** 매장코드 array */
    private String arrStoreCd[];
    /** 할인유형코드 */
    private String dcCd;
    /** 할인유형코드 */
    private String arrDcCd[];
    /** 본사,매장 구분 */
    private String orgnFg;

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
	public String getDcCd() {
		return dcCd;
	}
	public void setDcCd(String dcCd) {
		this.dcCd = dcCd;
	}
	public String[] getArrDcCd() {
		return arrDcCd;
	}
	public void setArrDcCd(String[] arrDcCd) {
		this.arrDcCd = arrDcCd;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getOrgnFg() {
		return orgnFg;
	}
	public void setOrgnFg(String orgnFg) {
		this.orgnFg = orgnFg;
	}
	
}
