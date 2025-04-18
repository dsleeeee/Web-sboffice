package kr.co.solbipos.sale.status.prod.hour.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.sale.day.day.enums.SaleTimeFg;

public class ProdHourVO extends PageVO {

    private static final long serialVersionUID = -4091176508608651478L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장코드 array */
    private String arrStoreCd[];
    /** 매출 발생 시간대 기준, 동적 컬럼 생성을 위한 쿼리 문자열*/
    private String sQuery1;
    private String sQuery2;
    private String sQuery3;
    private String sQuery4;
    /** 매출 발생 시간대 */
    private String saleTime;
    /** 본사,매장 구분 */
    private String orgnFg;
    /** 사원번호 */
    private String empNo;
    /** 검색옵션 */
    private String optionFg;
    /** 시간대분류 */
    private String timeSlot;
    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;

    /**
     * @return the hqOfficeCd
     */
    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    /**
     * @param hqOfficeCd the hqOfficeCd to set
     */
    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    /**
     * @return the storeCd
     */
    public String getStoreCd() {
        return storeCd;
    }

    /**
     * @param storeCd the storeCd to set
     */
    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }
    
    /**
     * @return the arrStoreCd
     */
    public String[] getArrStoreCd() {
        return arrStoreCd;
    }

    /**
     * @param arrStoreCd the arrStoreCd to set
     */
    public void setArrStoreCd(String[] arrStoreCd) {
        this.arrStoreCd = arrStoreCd;
    }
    
    /**
     * @param saleTime the saleTime to set
     */
    public String getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(String saleTime) {
        this.saleTime = saleTime;
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

    public String getsQuery3() {
        return sQuery3;
    }

    public void setsQuery3(String sQuery3) {
        this.sQuery3 = sQuery3;
    }

    public String getsQuery4() {
        return sQuery4;
    }

    public void setsQuery4(String sQuery4) {
        this.sQuery4 = sQuery4;
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

    public String getOptionFg() {
        return optionFg;
    }

    public void setOptionFg(String optionFg) {
        this.optionFg = optionFg;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
    }
}
