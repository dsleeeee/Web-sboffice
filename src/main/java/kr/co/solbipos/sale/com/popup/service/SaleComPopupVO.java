package kr.co.solbipos.sale.com.popup.service;

import kr.co.solbipos.application.common.service.PageVO;

public class SaleComPopupVO extends PageVO {

    private static final long serialVersionUID = -4091176508608651478L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 테이블코드 */
    private String tblCd;
    /** 매출일 */
    private String saleDate;
    /** 코너코드 */
    private String cornrCd;    
    /** 풍목코드 */
    private String prodCd;
    /** 조회월 */
    private String saleMonth;
    /** 팝업체크 */
    private String chkPop;
    /** 사원번호 */
    private String empNo;
    /** 요일 */
    private String yoil;
    /** 포스번호 */
    private String posNo;
    /** 영수증번호 */
    private String billNo;
    /** 매장코드 array */
    private String arrStoreCd[];
    /** 코너코드 array */
    private String arrCornrCd[];
    /** 대분류코드 */
    private String prodClassCd;
    /** 상품코드 */
    private String srchProdCd;
    /** 상품명 */
    private String srchProdNm;
    
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
     * @return the tblCd
     */
	public String getTblCd() {
		return tblCd;
	}
    /**
     * @param tblCd the tblCd to set
     */
	public void setTblCd(String tblCd) {
		this.tblCd = tblCd;
	}
    /**
     * @return the saleDate
     */
	public String getSaleDate() {
		return saleDate;
	}
    /**
     * @param saleDate the saleDate to set
     */
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}
    /**
     * @return the cornrCd
     */
	public String getCornrCd() {
		return cornrCd;
	}
    /**
     * @param cornrCd the cornrCd to set
     */
	public void setCornrCd(String cornrCd) {
		this.cornrCd = cornrCd;
	}
    
    /**
     * @return the prodCd
     */
	public String getProdCd() {
		return prodCd;
	}
    /**
     * @param prodCd the prodCd to set
     */
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}

	public String getSaleMonth() {
		return saleMonth;
	}

	public void setSaleMonth(String saleMonth) {
		this.saleMonth = saleMonth;
	}

	public String getChkPop() {
		return chkPop;
	}

	public void setChkPop(String chkPop) {
		this.chkPop = chkPop;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getYoil() {
		return yoil;
	}

	public void setYoil(String yoil) {
		this.yoil = yoil;
	}
    
    public String getPosNo() {
		return posNo;
	}

	public void setPosNo(String posNo) {
		this.posNo = posNo;
	}
	
	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
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
     * @param arrCornrCd the arrCornrCd to set
     */
	public String[] getArrCornrCd() {
		return arrCornrCd;
	}

	public void setArrCornrCd(String arrCornrCd[]) {
		this.arrCornrCd = arrCornrCd;
	}

	public String getProdClassCd() {
		return prodClassCd;
	}

	public void setProdClassCd(String prodClassCd) {
		this.prodClassCd = prodClassCd;
	}

	public String getSrchProdCd() {
		return srchProdCd;
	}

	public void setSrchProdCd(String srchProdCd) {
		this.srchProdCd = srchProdCd;
	}

	public String getSrchProdNm() {
		return srchProdNm;
	}

	public void setSrchProdNm(String srchProdNm) {
		this.srchProdNm = srchProdNm;
	}
	
}
