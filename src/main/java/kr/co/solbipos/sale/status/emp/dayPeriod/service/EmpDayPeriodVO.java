package kr.co.solbipos.sale.status.emp.dayPeriod.service;

import kr.co.solbipos.application.common.service.PageVO;

public class EmpDayPeriodVO extends PageVO {

    private static final long serialVersionUID = -4091176508608651478L;

    /** 본사사업장코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    private String arrStoreCd[];
    
    /**판매자 번호*/
    private String empNo;
    
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
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
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
