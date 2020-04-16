package kr.co.solbipos.sale.anals.monthly.service;

import kr.co.solbipos.application.common.service.PageVO;

public class SaleAnalsMonthlyVO extends PageVO {

    private static final long serialVersionUID = -6084172854998623991L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 본사브랜드코드 */
    private String hqBrandCd;
    /** 매장코드 */
    private String storeCd;
    /** 조회년월 */
    private String reqYearMonth;
    /** 조회일(일요일) */
    private String sun;
    /** 조회일(월요일) */
    private String mon;
    /** 조회일(화요일) */
    private String tue;
    /** 조회일(수요일) */
    private String wed;
    /** 조회일(목요일) */
    private String thu;
    /** 조회일(금요일) */
    private String fri;
    /** 조회일(토요일) */
    private String sat;
    /** 조회일자 */
    private String saleDate;

	public String getHqOfficeCd() {
		return hqOfficeCd;
	}

	public void setHqOfficeCd(String hqOfficeCd) {
		this.hqOfficeCd = hqOfficeCd;
	}

	public String getHqBrandCd() {
		return hqBrandCd;
	}

	public void setHqBrandCd(String hqBrandCd) {
		this.hqBrandCd = hqBrandCd;
	}

	public String getStoreCd() {
		return storeCd;
	}

	public void setStoreCd(String storeCd) {
		this.storeCd = storeCd;
	}

	public String getReqYearMonth() {
		return reqYearMonth;
	}

	public void setReqYearMonth(String reqYearMonth) {
		this.reqYearMonth = reqYearMonth;
	}

	public String getSun() {
		return sun;
	}

	public void setSun(String sun) {
		this.sun = sun;
	}

	public String getMon() {
		return mon;
	}

	public void setMon(String mon) {
		this.mon = mon;
	}

	public String getTue() {
		return tue;
	}
	public void setTue(String tue) {
		this.tue = tue;
	}

	public String getWed() {
		return wed;
	}

	public void setWed(String wed) {
		this.wed = wed;
	}

	public String getThu() {
		return thu;
	}

	public void setThu(String thu) {
		this.thu = thu;
	}

	public String getFri() {
		return fri;
	}
	public void setFri(String fri) {
		this.fri = fri;
	}

	public String getSat() {
		return sat;
	}

	public void setSat(String sat) {
		this.sat = sat;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

}
