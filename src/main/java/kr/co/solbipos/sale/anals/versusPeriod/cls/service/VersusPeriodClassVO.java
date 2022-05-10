package kr.co.solbipos.sale.anals.versusPeriod.cls.service;

import kr.co.solbipos.application.common.service.PageVO;

public class VersusPeriodClassVO extends PageVO {

    private static final long serialVersionUID = -4091176508608651478L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장코드 Array */
    private String arrStoreCd[];
    /** 매장코드 */
    private String brandCd;
    /** 매장코드 Array */
    private String arrBrandCd[];
    /** 분류코드 */
    private String prodClassCd;
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
	public String getProdClassCd() {
		return prodClassCd;
	}
	public void setProdClassCd(String prodClassCd) {
		this.prodClassCd = prodClassCd;
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
	public String getBrandCd() {
		return brandCd;
	}
	public void setBrandCd(String brandCd) {
		this.brandCd = brandCd;
	}
	public String[] getArrBrandCd() {
		return arrBrandCd;
	}
	public void setArrBrandCd(String[] arrBrandCd) {
		this.arrBrandCd = arrBrandCd;
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
