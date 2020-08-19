package kr.co.solbipos.stock.curr.storageHqCurr.service;

import kr.co.solbipos.application.common.service.PageVO;

public class StorageHqCurrVO extends PageVO {

    private static final long serialVersionUID = 7415423262726371936L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 창고코드 */
    private String storageCd;
    /** 창고코드 array */
    private String arrStorageCd[];
    /** 브랜드코드 */
    private String hqBrandCd;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 단위구분 */
    private String unitFg;
    /** 바코드 */
    private String barcdCd;
    /** 거래처코드 */
    private String vendrCd;
    /** 거래처코드 array */
    private String arrVendrCd[];
    /** 상품분류코드 */
    private String prodClassCd;
    /** 로그인 권한(본사:HQ / 매장:STORE) */
    private String orgnFg;
    /** 매장코드 */
    private String storeCd;
    /** 안전재고 미만 조회구분 */
    private String safeStockFg;
    
    /** 동적 컬럼 생성을 위한 쿼리 문자열*/
    private String sQuery1;
    private String sQuery2;

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
     * @return the storageCd
     */
    public String getStorageCd() {
        return storageCd;
    }

    /**
     * @param storageCd the storageCd to set
     */
    public void setStorageCd(String storageCd) {
        this.storageCd = storageCd;
    }

    /**
     * @return the hqBrandCd
     */
    public String getHqBrandCd() {
        return hqBrandCd;
    }

    /**
     * @param hqBrandCd the hqBrandCd to set
     */
    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
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
     * @return the unitFg
     */
    public String getUnitFg() {
        return unitFg;
    }

    /**
     * @param unitFg the unitFg to set
     */
    public void setUnitFg(String unitFg) {
        this.unitFg = unitFg;
    }

    /**
     * @return the barcdCd
     */
    public String getBarcdCd() {
        return barcdCd;
    }

    /**
     * @param barcdCd the barcdCd to set
     */
    public void setBarcdCd(String barcdCd) {
        this.barcdCd = barcdCd;
    }

    /**
     * @return the vendrCd
     */
    public String getVendrCd() {
        return vendrCd;
    }

    /**
     * @param vendrCd the vendrCd to set
     */
    public void setVendrCd(String vendrCd) {
        this.vendrCd = vendrCd;
    }

    /**
     * @return the arrVendrCd
     */
    public String[] getArrVendrCd() {
        return arrVendrCd;
    }

    /**
     * @param arrVendrCd the arrVendrCd to set
     */
    public void setArrVendrCd(String[] arrVendrCd) {
        this.arrVendrCd = arrVendrCd;
    }

    /**
     * @return the prodClassCd
     */
    public String getProdClassCd() {
        return prodClassCd;
    }

    /**
     * @param prodClassCd the prodClassCd to set
     */
    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

	public String getOrgnFg() {
		return orgnFg;
	}

	public void setOrgnFg(String orgnFg) {
		this.orgnFg = orgnFg;
	}

	public String getStoreCd() {
		return storeCd;
	}

	public void setStoreCd(String storeCd) {
		this.storeCd = storeCd;
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

	public String[] getArrStorageCd() {
		return arrStorageCd;
	}

	public void setArrStorageCd(String arrStorageCd[]) {
		this.arrStorageCd = arrStorageCd;
	}

	public String getSafeStockFg() {
		return safeStockFg;
	}

	public void setSafeStockFg(String safeStockFg) {
		this.safeStockFg = safeStockFg;
	}
}
