package kr.co.solbipos.sale.anals.versusPeriod.hour.service;

import kr.co.solbipos.application.common.service.PageVO;

public class VersusPeriodHourVO extends PageVO {

    private static final long serialVersionUID = -4091176508608651478L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장코드  Array */
    private String arrStoreCd[];
    /** 대비기간 시작일자 */
    private String compStartDate;
    /** 대비기간 마감일자 */
    private String compEndDate;

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
	public String getCompStartDate() {
		return compStartDate;
	}
	public void setCompStartDate(String compStartDate) {
		this.compStartDate = compStartDate;
	}
	public String getCompEndDate() {
		return compEndDate;
	}
	public void setCompEndDate(String compEndDate) {
		this.compEndDate = compEndDate;
	}
	public String[] getArrStoreCd() {
		return arrStoreCd;
	}
	public void setArrStoreCd(String[] arrStoreCd) {
		this.arrStoreCd = arrStoreCd;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
