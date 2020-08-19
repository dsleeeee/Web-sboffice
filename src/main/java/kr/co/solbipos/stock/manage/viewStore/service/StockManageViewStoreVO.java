package kr.co.solbipos.stock.manage.viewStore.service;

import kr.co.solbipos.application.common.service.PageVO;

public class StockManageViewStoreVO extends PageVO {

	private static final long serialVersionUID = 2722329433257568791L;

	/** 본사/가맹정 구분 */
	private String orgnFg;
	/** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장코드 Array */
    private String arrStoreCd[];
	/** 상태 */
    private String statusFg;
    /** 진행 */
    private String procFg;

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
	public void setArrStoreCd(String[] arrStoreCd) {
		this.arrStoreCd = arrStoreCd;
	}
	public String getStatusFg() {
		return statusFg;
	}
	public void setStatusFg(String statusFg) {
		this.statusFg = statusFg;
	}
	public String getProcFg() {
		return procFg;
	}
	public void setProcFg(String procFg) {
		this.procFg = procFg;
	}

}
