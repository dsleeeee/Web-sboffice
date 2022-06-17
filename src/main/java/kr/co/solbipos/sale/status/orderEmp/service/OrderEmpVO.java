package kr.co.solbipos.sale.status.orderEmp.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : OrderEmpVO.java
 * @Description : 매출관리 > 매출현황2 > 주문자현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.06.10  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2022.06.10
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public class OrderEmpVO extends PageVO {

    private static final long serialVersionUID = -4091176508608651478L;

    /** 본사사업장코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    private String arrStoreCd[];
    
    /**판매자 번호*/
    private String empNo;
	private String orgnFg;

	/** 상품코드 */
	private String prodCd;
	/** 상품명 */
	private String prodNm;
	/** 상품분류코드 */
	private String prodClassCd;
	/** 판매자전체 */
	private String empChk;
	/** 판매자별, 동적 컬럼 생성을 위한 쿼리 문자열*/
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

	public String getOrgnFg() {
		return orgnFg;
	}

	public void setOrgnFg(String orgnFg) {
		this.orgnFg = orgnFg;
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

	public String getProdClassCd() {
		return prodClassCd;
	}

	public void setProdClassCd(String prodClassCd) {
		this.prodClassCd = prodClassCd;
	}

	public String getEmpChk() {
		return empChk;
	}

	public void setEmpChk(String empChk) {
		this.empChk = empChk;
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
}
