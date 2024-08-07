package kr.co.solbipos.sale.status.barcd.service;

import kr.co.solbipos.application.common.service.PageVO;

public class BarcdVO extends PageVO {

    private static final long serialVersionUID = -4091176508608651478L;

    /** 매장코드 */
    private String storeCd;
    private String arrStoreCd[];
    /** 영업일자 */
    private String saleDate;
    /** 코너 전체 선택 콤보박스 */
    private boolean isAll;
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
    /** POS번호 */
    private String barcdCd;
    /** 테이블 기준, 동적 컬럼 생성을 위한 쿼리 문자열*/
    private String sQuery1;
    /** 접속 매장 권한(본사, 가맹점)*/
    private String orgnFg;
    /** 상품명*/
    private String prodNm;
	/** 사원번호 */
	private String empNo;
    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;
    
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
	public String getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
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
	public String getOrgnFg() {
		return orgnFg;
	}
	public void setOrgnFg(String orgnFg) {
		this.orgnFg = orgnFg;
	}
	public String getProdNm() {
		return prodNm;
	}
	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}
	public String getBarcdCd() {
		return barcdCd;
	}
	public void setBarcdCd(String barcdCd) {
		this.barcdCd = barcdCd;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
    }
}
