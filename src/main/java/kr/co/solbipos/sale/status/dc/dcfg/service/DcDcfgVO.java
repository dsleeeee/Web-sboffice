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
    /** 코너코드 */
    private String dcCd;
    
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
