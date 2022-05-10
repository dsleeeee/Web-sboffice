package kr.co.solbipos.sale.status.appr.payMethod.mcoupon.service;

import kr.co.solbipos.application.common.service.PageVO;

public class ApprMcouponVO extends PageVO {

    private static final long serialVersionUID = -4091176508608651478L;

    /** 매장코드 */
    private String storeCd;
    private String arrStoreCd[];
    /** 영업일자 */
    private String saleDate;
    /** 코너코드 */
    private String cornrCd;
    private String arrCornrCd[];
    private String arrStoreCornr[];
	

	/** POS번호 */
    private String posNo;
    private String arrPosNo[];
    private String arrStorePos[];
    /** 승인구분 */
    private String apprFg;
    private String saleFg;
    /** 승인처리 */
    private String apprProcFg;
    private String cashBillApprProcFg;
    /** 코너 전체 선택 콤보박스 */
    private boolean isAll;
    /** 상품코드 */
    private String prodCd;
    /** 본사사업장코드 */
    private String hqOfficeCd;
    /** 본사브랜드코드 */
    private String hqBrandCd;
    /**할부구분*/

	/** 본사,매장 구분 */
	private String orgnFg;
	/** 사원번호 */
	private String empNo;
    /** 테이블 기준, 동적 컬럼 생성을 위한 쿼리 문자열*/
    private String sQuery1;
    
    

	public String getStoreCd() {
		return storeCd;
	}

	public void setStoreCd(String storeCd) {
		this.storeCd = storeCd;
	}

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	public String[] getArrStoreCd() {
		return arrStoreCd;
	}

	public void setArrStoreCd(String[] arrStoreCd) {
		this.arrStoreCd = arrStoreCd;
	}

	public String getCornrCd() {
		return cornrCd;
	}

	public void setCornrCd(String cornrCd) {
		this.cornrCd = cornrCd;
	}

	public String[] getArrCornrCd() {
		return arrCornrCd;
	}

	public void setArrCornrCd(String[] arrCornrCd) {
		this.arrCornrCd = arrCornrCd;
	}

	public String[] getArrStoreCornr() {
		return arrStoreCornr;
	}

	public void setArrStoreCornr(String[] arrStoreCornr) {
		this.arrStoreCornr = arrStoreCornr;
	}
		
	public String getPosNo() {
		return posNo;
	}

	public void setPosNo(String posNo) {
		this.posNo = posNo;
	}

	public String[] getArrPosNo() {
		return arrPosNo;
	}

	public void setArrPosNo(String[] arrPosNo) {
		this.arrPosNo = arrPosNo;
	}
	
	public String[] getArrStorePos() {
		return arrStorePos;
	}

	public void setArrStorePos(String[] arrStorePos) {
		this.arrStorePos = arrStorePos;
	}
	
	public String getApprFg() {
		return apprFg;
	}

	public void setApprFg(String apprFg) {
		this.apprFg = apprFg;
	}

	public String getApprProcFg() {
		return apprProcFg;
	}

	public void setApprProcFg(String apprProcFg) {
		this.apprProcFg = apprProcFg;
	}

	public boolean isAll() {
		return isAll;
	}

	public void setAll(boolean isAll) {
		this.isAll = isAll;
	}

	public String getProdCd() {
		return prodCd;
	}

	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}

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

	public String getsQuery1() {
		return sQuery1;
	}

	public void setsQuery1(String sQuery1) {
		this.sQuery1 = sQuery1;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSaleFg() {
		return saleFg;
	}

	public void setSaleFg(String saleFg) {
		this.saleFg = saleFg;
	}

	public String getCashBillApprProcFg() {
		return cashBillApprProcFg;
	}

	public void setCashBillApprProcFg(String cashBillApprProcFg) {
		this.cashBillApprProcFg = cashBillApprProcFg;
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
