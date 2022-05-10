package kr.co.solbipos.sale.anals.abc.abc.service;

import kr.co.solbipos.application.common.service.PageVO;

public class AbcVO extends PageVO {

	private static final long serialVersionUID = 1010193287179267051L;
	
	/** 본사/가맹정 구분 */
	private String orgnFg;
	/** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장코드 Array*/
    private String[] arrStoreCd;
    /** 누적판매비율A */
    private String gradeA;
    /** 누적판매비율B */
    private String gradeB;
    /** 누적판매비율C */
    private String gradeC;
    /** 정렬구분 */
    private String sortFg;
	/** 사원번호 */
	private String empNo;
    
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
	public String getGradeA() {
		return gradeA;
	}
	public void setGradeA(String gradeA) {
		this.gradeA = gradeA;
	}
	public String getGradeB() {
		return gradeB;
	}
	public void setGradeB(String gradeB) {
		this.gradeB = gradeB;
	}
	public String getGradeC() {
		return gradeC;
	}
	public void setGradeC(String gradeC) {
		this.gradeC = gradeC;
	}
	public String getSortFg() {
		return sortFg;
	}
	public void setSortFg(String sortFg) {
		this.sortFg = sortFg;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
}
