package kr.co.solbipos.sale.status.table.day.service;

import kr.co.solbipos.application.common.service.PageVO;

public class TableDayVO extends PageVO{

	private static final long serialVersionUID = -6096357033807170793L;

	/** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장코드 array */
    private String arrStoreCd[];
    /** 테이블 코드 */
    private String tableCd;
    /** 테이블 컬럼 */
    private String tblCol;
    /** 테이블 array */
    private String arrTableCd[];
    /** 테이블 기준, 동적 컬럼 생성을 위한 쿼리 문자열*/
    private String sQuery1;
    private String sQuery2;

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
	public String getTableCd() {
		return tableCd;
	}
	public void setTableCd(String tableCd) {
		this.tableCd = tableCd;
	}
	public String getTblCol() {
		return tblCol;
	}
	public void setTblCol(String tblCol) {
		this.tblCol = tblCol;
	}
	public String[] getArrTableCd() {
		return arrTableCd;
	}
	public void setArrTableCd(String[] arrTableCd) {
		this.arrTableCd = arrTableCd;
	}
	public String getsQuery1() {
		return sQuery1;
	}
	public void setsQuery1(String sQuery1) {
		this.sQuery1 = sQuery1;
	}
	public String getsQuery2() {
		return sQuery2;
	}
	public void setsQuery2(String sQuery2) {
		this.sQuery2 = sQuery2;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
