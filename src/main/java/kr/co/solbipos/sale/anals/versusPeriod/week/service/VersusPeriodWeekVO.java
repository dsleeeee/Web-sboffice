package kr.co.solbipos.sale.anals.versusPeriod.week.service;

import kr.co.solbipos.application.common.service.PageVO;

public class VersusPeriodWeekVO extends PageVO {

    private static final long serialVersionUID = -4091176508608651478L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장코드  Array */
    private String arrStoreCd[];
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 상품분류코드 */
    private String prodDayCd;
    /** 대비기간 시작일자 */
    private String compStartDate;
    /** 대비기간 마감일자 */
    private String compEndDate;
	/** 본사,매장 구분 */
	private String orgnFg;
	/** 사원번호 */
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
	public String getProdDayCd() {
		return prodDayCd;
	}
	public void setProdDayCd(String prodDayCd) {
		this.prodDayCd = prodDayCd;
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

	public String getOrgnFg() {
		return orgnFg;
	}

	public void setOrgnFg(String orgnFg) {
		this.orgnFg = orgnFg;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
}
