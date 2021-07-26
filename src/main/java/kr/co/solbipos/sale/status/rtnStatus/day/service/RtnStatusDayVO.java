package kr.co.solbipos.sale.status.rtnStatus.day.service;

import kr.co.solbipos.application.common.service.PageVO;

public class RtnStatusDayVO extends PageVO {

    private static final long serialVersionUID = -4091176508608651478L;

    /** 매장코드 */
    private String storeCd;
    private String arrStoreCd[];
    /** 영업일자 */
    private String saleDate;
    /** 쿼리문의 PIVOT IN에 사용할 코너코드 array*/
    private String pivotCornrCol[];
    /** 상품코드 */
    private String prodCd;
    /** 본사사업장코드 */
    private String hqOfficeCd;
    /** 본사브랜드코드 */
    private String hqBrandCd;
    /** 총판매수량 */
    private String totSaleQty;
    /** 총판매금액 */
    private String totBrandCd;
    /** 총할인금액 */
    private String totDcAmt;
    /** 총봉사료금액 */
    private String totTipAmt;
    /** 총에누리금액 */
    private String totEtcAmt;
    /** 실판매금액 */
    private String realSaleAmt;
    /** 결제금액 */
    private String payAmt;
    /** 할인금액 */
    private String dcAmt;
    /** POS번호 */
    private String posNo;
    /** 테이블 기준, 동적 컬럼 생성을 위한 쿼리 문자열*/
    private String sQuery1;
    /** 반품구분 */
    private String saleYn;
    /** 접속권한(본사, 매장) */
    private String orgnFg;

	/** 전체기간체크 */
	private boolean chkDt;

	/** 검색옵션 */
	private String option1;
	private String option2;

	/** 결제수단컬럼 */
	private String payCol;
	/** 결제수단 array */
	private String arrPayCol[];
	/** 쿼리문의 PIVOT IN에 사용할 결제수단 컬럼 문자열 */
	private String pivotPayCol;
	/** 할인컬럼 */
	private String dcCol;
	/** 할인구분 array */
	private String arrDcCol[];
	/** 쿼리문의 PIVOT IN에 사용할 할인구분 컬럼 문자열 */
	private String pivotDcCol;
    
    public String getStoreCd() {
		return storeCd;
	}
	public void setStoreCd(String storeCd) {
		this.storeCd = storeCd;
	}
	public String[] getArrStoreCd() {
		return arrStoreCd;
	}
	public void setArrStoreCd(String arrStoreCd[]) {
		this.arrStoreCd = arrStoreCd;
	}
	public String getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}
	public String[] getPivotCornrCol() {
		return pivotCornrCol;
	}
	public void setPivotCornrCol(String pivotCornrCol[]) {
		this.pivotCornrCol = pivotCornrCol;
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
	public String getTotSaleQty() {
		return totSaleQty;
	}
	public void setTotSaleQty(String totSaleQty) {
		this.totSaleQty = totSaleQty;
	}
	public String getTotBrandCd() {
		return totBrandCd;
	}
	public void setTotBrandCd(String totBrandCd) {
		this.totBrandCd = totBrandCd;
	}
	public String getTotDcAmt() {
		return totDcAmt;
	}
	public void setTotDcAmt(String totDcAmt) {
		this.totDcAmt = totDcAmt;
	}
	public String getTotTipAmt() {
		return totTipAmt;
	}
	public void setTotTipAmt(String totTipAmt) {
		this.totTipAmt = totTipAmt;
	}
	public String getTotEtcAmt() {
		return totEtcAmt;
	}
	public void setTotEtcAmt(String totEtcAmt) {
		this.totEtcAmt = totEtcAmt;
	}
	public String getRealSaleAmt() {
		return realSaleAmt;
	}
	public void setRealSaleAmt(String realSaleAmt) {
		this.realSaleAmt = realSaleAmt;
	}
	public String getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}
	public String getDcAmt() {
		return dcAmt;
	}
	public void setDcAmt(String dcAmt) {
		this.dcAmt = dcAmt;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getPosNo() {
		return posNo;
	}
	public void setPosNo(String posNo) {
		this.posNo = posNo;
	}
	public String getsQuery1() {
		return sQuery1;
	}
	public void setsQuery1(String sQuery1) {
		this.sQuery1 = sQuery1;
	}
	public String getSaleYn() {
		return saleYn;
	}
	public void setSaleYn(String saleYn) {
		this.saleYn = saleYn;
	}
	public String getOrgnFg() {
		return orgnFg;
	}
	public void setOrgnFg(String orgnFg) {
		this.orgnFg = orgnFg;
	}

	public boolean getChkDt() { return chkDt; }

	public void setChkDt(boolean chkDt) { this.chkDt = chkDt; }

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getPayCol() {
		return payCol;
	}

	public void setPayCol(String payCol) {
		this.payCol = payCol;
	}

	public String[] getArrPayCol() {
		return arrPayCol;
	}

	public void setArrPayCol(String[] arrPayCol) {
		this.arrPayCol = arrPayCol;
	}

	public String getPivotPayCol() {
		return pivotPayCol;
	}

	public void setPivotPayCol(String pivotPayCol) {
		this.pivotPayCol = pivotPayCol;
	}

	public String getDcCol() {
		return dcCol;
	}

	public void setDcCol(String dcCol) {
		this.dcCol = dcCol;
	}

	public String[] getArrDcCol() {
		return arrDcCol;
	}

	public void setArrDcCol(String[] arrDcCol) {
		this.arrDcCol = arrDcCol;
	}

	public String getPivotDcCol() {
		return pivotDcCol;
	}

	public void setPivotDcCol(String pivotDcCol) {
		this.pivotDcCol = pivotDcCol;
	}
}
