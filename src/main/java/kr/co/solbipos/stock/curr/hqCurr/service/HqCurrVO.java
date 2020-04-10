package kr.co.solbipos.stock.curr.hqCurr.service;

import kr.co.solbipos.application.common.service.PageVO;

public class HqCurrVO extends PageVO {

    private static final long serialVersionUID = 7415423262726371936L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 창고코드 */
    private String storageCd;
    /** 브랜드코드 */
    private String hqBrandCd;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 현재재고수량 */
    private Integer currQty;
    /** 업체발주수량 ( 31: + ) */
    private Integer accVendrOrderQty;
    /** 본사입고수량 ( 01: + ) */
    private Integer accVendrInQty;
    /** 업체반출수량 ( 16: - ) */
    private Integer accVendrOutQty;
    /** 본사출고수량 ( 13: - ) */
    private Integer accHqOutQty;
    /** 본사반입수량 ( 02: + ) */
    private Integer accHqInQty;
    /** 매장이입수량 ( 04: + ) */
    private Integer accStoreMoveInQty;
    /** 매장이출수량 ( 14: - ) */
    private Integer accStoreMoveOutQty;
    /** 재고폐기수량 ( 17: - ) */
    private Integer accDisuseQty;
    /** 재고조정수량 ( 21: +/- ) */
    private Integer accAdjQty;
    /** 세트구성수량 ( 22: + ) */
    private Integer accSetInQty;
    /** 세트해제수량 ( 23: - ) */
    private Integer accSetOutQty;
    /** 누적매장판매수량 ( 11: - ) */
    private Integer accStoreSaleQty;
    /** 최초입고일자 */
    private String firstVendrInDate;
    /** 최종입고일자 */
    private String lastVendrInDate;
    /** 최초출고일자 */
    private String firstHqOutDate;
    /** 최종출고일자 */
    private String lastHqOutDate;
    /** 최초판매일자 */
    private String firstSaleDate;
    /** 최종판매일자 */
    private String lastSaleDate;
    /** 본사매출거래처발주수량 ( 19: - ) */
    private Integer accSaleVendrOutQty;
    /** 본사매출거래처반품수량 ( 33: + ) */
    private Integer accSaleVendrInQty;
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
    /** 중량,수량상품구분 */
    private String weightFg;
    /** 안전재고구분 */
    private String safeStockFg;
    /** 로그인 권한(본사:HQ / 매장:STORE) */
    private String orgnFg;
    /** 매장코드 */
    private String storeCd;

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
     * @return the currQty
     */
    public Integer getCurrQty() {
        return currQty;
    }

    /**
     * @param currQty the currQty to set
     */
    public void setCurrQty(Integer currQty) {
        this.currQty = currQty;
    }

    /**
     * @return the accVendrOrderQty
     */
    public Integer getAccVendrOrderQty() {
        return accVendrOrderQty;
    }

    /**
     * @param accVendrOrderQty the accVendrOrderQty to set
     */
    public void setAccVendrOrderQty(Integer accVendrOrderQty) {
        this.accVendrOrderQty = accVendrOrderQty;
    }

    /**
     * @return the accVendrInQty
     */
    public Integer getAccVendrInQty() {
        return accVendrInQty;
    }

    /**
     * @param accVendrInQty the accVendrInQty to set
     */
    public void setAccVendrInQty(Integer accVendrInQty) {
        this.accVendrInQty = accVendrInQty;
    }

    /**
     * @return the accVendrOutQty
     */
    public Integer getAccVendrOutQty() {
        return accVendrOutQty;
    }

    /**
     * @param accVendrOutQty the accVendrOutQty to set
     */
    public void setAccVendrOutQty(Integer accVendrOutQty) {
        this.accVendrOutQty = accVendrOutQty;
    }

    /**
     * @return the accHqOutQty
     */
    public Integer getAccHqOutQty() {
        return accHqOutQty;
    }

    /**
     * @param accHqOutQty the accHqOutQty to set
     */
    public void setAccHqOutQty(Integer accHqOutQty) {
        this.accHqOutQty = accHqOutQty;
    }

    /**
     * @return the accHqInQty
     */
    public Integer getAccHqInQty() {
        return accHqInQty;
    }

    /**
     * @param accHqInQty the accHqInQty to set
     */
    public void setAccHqInQty(Integer accHqInQty) {
        this.accHqInQty = accHqInQty;
    }

    /**
     * @return the accStoreMoveInQty
     */
    public Integer getAccStoreMoveInQty() {
        return accStoreMoveInQty;
    }

    /**
     * @param accStoreMoveInQty the accStoreMoveInQty to set
     */
    public void setAccStoreMoveInQty(Integer accStoreMoveInQty) {
        this.accStoreMoveInQty = accStoreMoveInQty;
    }

    /**
     * @return the accStoreMoveOutQty
     */
    public Integer getAccStoreMoveOutQty() {
        return accStoreMoveOutQty;
    }

    /**
     * @param accStoreMoveOutQty the accStoreMoveOutQty to set
     */
    public void setAccStoreMoveOutQty(Integer accStoreMoveOutQty) {
        this.accStoreMoveOutQty = accStoreMoveOutQty;
    }

    /**
     * @return the accDisuseQty
     */
    public Integer getAccDisuseQty() {
        return accDisuseQty;
    }

    /**
     * @param accDisuseQty the accDisuseQty to set
     */
    public void setAccDisuseQty(Integer accDisuseQty) {
        this.accDisuseQty = accDisuseQty;
    }

    /**
     * @return the accAdjQty
     */
    public Integer getAccAdjQty() {
        return accAdjQty;
    }

    /**
     * @param accAdjQty the accAdjQty to set
     */
    public void setAccAdjQty(Integer accAdjQty) {
        this.accAdjQty = accAdjQty;
    }

    /**
     * @return the accSetInQty
     */
    public Integer getAccSetInQty() {
        return accSetInQty;
    }

    /**
     * @param accSetInQty the accSetInQty to set
     */
    public void setAccSetInQty(Integer accSetInQty) {
        this.accSetInQty = accSetInQty;
    }

    /**
     * @return the accSetOutQty
     */
    public Integer getAccSetOutQty() {
        return accSetOutQty;
    }

    /**
     * @param accSetOutQty the accSetOutQty to set
     */
    public void setAccSetOutQty(Integer accSetOutQty) {
        this.accSetOutQty = accSetOutQty;
    }

    /**
     * @return the accStoreSaleQty
     */
    public Integer getAccStoreSaleQty() {
        return accStoreSaleQty;
    }

    /**
     * @param accStoreSaleQty the accStoreSaleQty to set
     */
    public void setAccStoreSaleQty(Integer accStoreSaleQty) {
        this.accStoreSaleQty = accStoreSaleQty;
    }

    /**
     * @return the firstVendrInDate
     */
    public String getFirstVendrInDate() {
        return firstVendrInDate;
    }

    /**
     * @param firstVendrInDate the firstVendrInDate to set
     */
    public void setFirstVendrInDate(String firstVendrInDate) {
        this.firstVendrInDate = firstVendrInDate;
    }

    /**
     * @return the lastVendrInDate
     */
    public String getLastVendrInDate() {
        return lastVendrInDate;
    }

    /**
     * @param lastVendrInDate the lastVendrInDate to set
     */
    public void setLastVendrInDate(String lastVendrInDate) {
        this.lastVendrInDate = lastVendrInDate;
    }

    /**
     * @return the firstHqOutDate
     */
    public String getFirstHqOutDate() {
        return firstHqOutDate;
    }

    /**
     * @param firstHqOutDate the firstHqOutDate to set
     */
    public void setFirstHqOutDate(String firstHqOutDate) {
        this.firstHqOutDate = firstHqOutDate;
    }

    /**
     * @return the lastHqOutDate
     */
    public String getLastHqOutDate() {
        return lastHqOutDate;
    }

    /**
     * @param lastHqOutDate the lastHqOutDate to set
     */
    public void setLastHqOutDate(String lastHqOutDate) {
        this.lastHqOutDate = lastHqOutDate;
    }

    /**
     * @return the firstSaleDate
     */
    public String getFirstSaleDate() {
        return firstSaleDate;
    }

    /**
     * @param firstSaleDate the firstSaleDate to set
     */
    public void setFirstSaleDate(String firstSaleDate) {
        this.firstSaleDate = firstSaleDate;
    }

    /**
     * @return the lastSaleDate
     */
    public String getLastSaleDate() {
        return lastSaleDate;
    }

    /**
     * @param lastSaleDate the lastSaleDate to set
     */
    public void setLastSaleDate(String lastSaleDate) {
        this.lastSaleDate = lastSaleDate;
    }

    /**
     * @return the accSaleVendrOutQty
     */
    public Integer getAccSaleVendrOutQty() {
        return accSaleVendrOutQty;
    }

    /**
     * @param accSaleVendrOutQty the accSaleVendrOutQty to set
     */
    public void setAccSaleVendrOutQty(Integer accSaleVendrOutQty) {
        this.accSaleVendrOutQty = accSaleVendrOutQty;
    }

    /**
     * @return the accSaleVendrInQty
     */
    public Integer getAccSaleVendrInQty() {
        return accSaleVendrInQty;
    }

    /**
     * @param accSaleVendrInQty the accSaleVendrInQty to set
     */
    public void setAccSaleVendrInQty(Integer accSaleVendrInQty) {
        this.accSaleVendrInQty = accSaleVendrInQty;
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

    /**
     * @return the weightFg
     */
    public String getWeightFg() {
        return weightFg;
    }

    /**
     * @param weightFg the weightFg to set
     */
    public void setWeightFg(String weightFg) {
        this.weightFg = weightFg;
    }

    /**
     * @return the safeStockFg
     */
    public String getSafeStockFg() {
        return safeStockFg;
    }

    /**
     * @param safeStockFg the safeStockFg to set
     */
    public void setSafeStockFg(String safeStockFg) {
        this.safeStockFg = safeStockFg;
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
}
