package kr.co.solbipos.stock.curr.storeCurr.service;

import kr.co.solbipos.application.common.service.PageVO;

public class StoreCurrVO extends PageVO {

    private static final long serialVersionUID = -5398533977092106922L;

    /** 본사코드 */
    
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
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
    /** 누적매장입고수량 ( 03: + ) */
    private Integer accStoreInQty;
    /** 누적매장반품수량 ( 12: - ) */
    private Integer accStoreOutQty;
    /** 누적매장판매수량 ( 11: - ) */
    private Integer accStoreSaleQty;
    /** 누적매장이입수량 ( 04: + ) */
    private Integer accMoveInQty;
    /** 누적매장이출수량 ( 14: - ) */
    private Integer accMoveOutQty;
    /** 누적재고폐기수량 ( 17: - ) */
    private Integer accDisuseQty;
    /** 누적재고조정수량 ( 21: +/- ) */
    private Integer accAdjQty;
    /** 누적세트구성수량 ( 22: + ) */
    private Integer accSetInQty;
    /** 누적세트해제수량 ( 23: - ) */
    private Integer accSetOutQty;
    /** 누적사입입고수량 ( 06: + ) */
    private Integer accPurchsInQty;
    /** 누적사입반품수량 ( 18: - ) */
    private Integer accPurchsOutQty;
    /** 최초입고일자 */
    private String firstInDate;
    /** 최종입고일자 */
    private String lastInDate;
    /** 최초판매일자 */
    private String firstSaleDate;
    /** 최종판매일자 */
    private String lastSaleDate;
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
     * @return the accStoreInQty
     */
    public Integer getAccStoreInQty() {
        return accStoreInQty;
    }

    /**
     * @param accStoreInQty the accStoreInQty to set
     */
    public void setAccStoreInQty(Integer accStoreInQty) {
        this.accStoreInQty = accStoreInQty;
    }

    /**
     * @return the accStoreOutQty
     */
    public Integer getAccStoreOutQty() {
        return accStoreOutQty;
    }

    /**
     * @param accStoreOutQty the accStoreOutQty to set
     */
    public void setAccStoreOutQty(Integer accStoreOutQty) {
        this.accStoreOutQty = accStoreOutQty;
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
     * @return the accMoveInQty
     */
    public Integer getAccMoveInQty() {
        return accMoveInQty;
    }

    /**
     * @param accMoveInQty the accMoveInQty to set
     */
    public void setAccMoveInQty(Integer accMoveInQty) {
        this.accMoveInQty = accMoveInQty;
    }

    /**
     * @return the accMoveOutQty
     */
    public Integer getAccMoveOutQty() {
        return accMoveOutQty;
    }

    /**
     * @param accMoveOutQty the accMoveOutQty to set
     */
    public void setAccMoveOutQty(Integer accMoveOutQty) {
        this.accMoveOutQty = accMoveOutQty;
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
     * @return the accPurchsInQty
     */
    public Integer getAccPurchsInQty() {
        return accPurchsInQty;
    }

    /**
     * @param accPurchsInQty the accPurchsInQty to set
     */
    public void setAccPurchsInQty(Integer accPurchsInQty) {
        this.accPurchsInQty = accPurchsInQty;
    }

    /**
     * @return the accPurchsOutQty
     */
    public Integer getAccPurchsOutQty() {
        return accPurchsOutQty;
    }

    /**
     * @param accPurchsOutQty the accPurchsOutQty to set
     */
    public void setAccPurchsOutQty(Integer accPurchsOutQty) {
        this.accPurchsOutQty = accPurchsOutQty;
    }

    /**
     * @return the firstInDate
     */
    public String getFirstInDate() {
        return firstInDate;
    }

    /**
     * @param firstInDate the firstInDate to set
     */
    public void setFirstInDate(String firstInDate) {
        this.firstInDate = firstInDate;
    }

    /**
     * @return the lastInDate
     */
    public String getLastInDate() {
        return lastInDate;
    }

    /**
     * @param lastInDate the lastInDate to set
     */
    public void setLastInDate(String lastInDate) {
        this.lastInDate = lastInDate;
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
}
