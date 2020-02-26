package kr.co.solbipos.sale.status.prod.payFg.service;

import kr.co.solbipos.application.common.service.PageVO;

public class ProdPayFgVO extends PageVO {

    private static final long serialVersionUID = -4091176508608651478L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장코드 array */
    private String arrStoreCd[];
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 결제수단컬럼 */
    private String payCol;
    /** 결제수단 array */
    private String arrPayCol[];
    /** 결제수단별, 동적 컬럼 생성을 위한 쿼리 문자열*/
    private String sQuery1;
    private String sQuery2;
    /** 본사,매장 구분 */
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

    /**
     * @return the prodNm
     */
    public String getProdNm() {
        return prodNm;
    }

    /**
     * @param prodNm the prodNm to set
     */
    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }
    
    /**
     * @return the payCol
     */
    public String getPayCol() {
        return payCol;
    }

    /**
     * @param payCol the payCol to set
     */
    public void setPayCol(String payCol) {
        this.payCol = payCol;
    }
    
    /**
     * @return the arrPayCol
     */
    public String[] getArrPayCol() {
        return arrPayCol;
    }

    /**
     * @param arrPayCol the arrPayCol to set
     */
    public void setArrPayCol(String[] arrPayCol) {
        this.arrPayCol = arrPayCol;
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
    
	public String getOrgnFg() {
		return orgnFg;
	}

	public void setOrgnFg(String orgnFg) {
		this.orgnFg = orgnFg;
	}
}