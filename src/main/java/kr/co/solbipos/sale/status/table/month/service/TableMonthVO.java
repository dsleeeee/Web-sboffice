package kr.co.solbipos.sale.status.table.month.service;

import kr.co.solbipos.application.common.service.PageVO;

public class TableMonthVO extends PageVO {

	private static final long serialVersionUID = 5765375181662061923L;

	/** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장코드 array */
    private String arrStoreCd[];
    /** 콤보박스 테이블 코드 */
    private String tableCd;
    /** 그리드 헤더 테이블코드 */
    private String tblCol;
    /** 그리드 코드 테이블 코드 array */
    private String arrTableCd[];
    /** 테이블 기준, 동적 컬럼 생성을 위한 쿼리 문자열*/
    private String sQuery1;

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

}
