package kr.co.solbipos.sale.status.appr.cashBill.service;

import kr.co.solbipos.application.common.service.PageVO;

public class ApprCashBillVO extends PageVO {

    private static final long serialVersionUID = -4091176508608651478L;

    /** 매장코드 */
    private String storeCd;
    /** 영업일자 */
    private String saleDate;
    /** 매장코드 array*/
    private String arrStoreCol[];
    /** 코너코드 */
    private String cornrCd;
    /** 코너코드 array*/
    private String arrCornrCol[];
    /** POS번호 */
    private String posNo;
    /** POS번호 array*/
    private String arrPosCol[];
    /** 승인구분 */
    private String apprFg;
    /** 승인처리 */
    private String apprProcFg;
    /** 코너 전체 선택 콤보박스 */
    private boolean isAll;
    /** 상품코드 */
    private String prodCd;
    /** 본사사업장코드 */
    private String hqOfficeCd;
    /** 본사브랜드코드 */
    private String hqBrandCd;
    
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

	public String[] getArrStoreCol() {
		return arrStoreCol;
	}

	public void setArrStoreCol(String[] arrStoreCol) {
		this.arrStoreCol = arrStoreCol;
	}

	public String getCornrCd() {
		return cornrCd;
	}

	public void setCornrCd(String cornrCd) {
		this.cornrCd = cornrCd;
	}

	public String[] getArrCornrCol() {
		return arrCornrCol;
	}

	public void setArrCornrCol(String[] arrCornrCol) {
		this.arrCornrCol = arrCornrCol;
	}

	public String getPosNo() {
		return posNo;
	}

	public void setPosNo(String posNo) {
		this.posNo = posNo;
	}

	public String[] getArrPosCol() {
		return arrPosCol;
	}

	public void setArrPosCol(String[] arrPosCol) {
		this.arrPosCol = arrPosCol;
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
}
