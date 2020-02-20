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
    /** 테이블코드 array */
    private String arrTblCd[];
    /** 매장테이블코드 array */
    private String arrStoreTbl[];
    /** 코너코드 array */
    private String arrCornrCd[];
    /** 대분류코드 */
    private String prodClassCd;
    /** 상품코드 */
    private String srchPopProdCd;
    /** 상품명 */
    private String srchPopProdNm;
    /** 요일 */
    private String saleDay;
    /** 년월 */
    private String saleYm;
    /** 시간 */
    private String saleHour;
    /** 본사,매장구분 */
    private String orgnFg;
    
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

    
    public String[] getArrTblCd() {
		return arrTblCd;
	}

	public void setArrTblCd(String[] arrTblCd) {
		this.arrTblCd = arrTblCd;
	}
	

	public String[] getArrStoreTbl() {
		return arrStoreTbl;
	}

	public void setArrStoreTbl(String[] arrStoreTbl) {
		this.arrStoreTbl = arrStoreTbl;
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

	public String getSaleDay() {
		return saleDay;
	}

	public void setSaleDay(String saleDay) {
		this.saleDay = saleDay;
	}

	public String getSaleYm() {
		return saleYm;
	}

	public void setSaleYm(String saleYm) {
		this.saleYm = saleYm;
	}

	public String getSrchPopProdCd() {
		return srchPopProdCd;
	}

	public void setSrchPopProdCd(String srchPopProdCd) {
		this.srchPopProdCd = srchPopProdCd;
	}

	public String getSrchPopProdNm() {
		return srchPopProdNm;
	}

	public void setSrchPopProdNm(String srchPopProdNm) {
		this.srchPopProdNm = srchPopProdNm;
	}

	public String getOrgnFg() {
		return orgnFg;
	}

	public void setOrgnFg(String orgnFg) {
		this.orgnFg = orgnFg;
	}

	public String getSaleHour() {
		return saleHour;
	}

	public void setSaleHour(String saleHour) {
		this.saleHour = saleHour;
	}
	
}
