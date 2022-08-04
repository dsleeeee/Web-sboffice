package kr.co.solbipos.iostock.orderReturn.rtnStoreOrder.service;

import kr.co.solbipos.application.common.service.PageVO;

public class RtnStoreOrderDtlVO extends PageVO {

    private static final long serialVersionUID = -2562407537859583846L;

    /** 출고예약일자 */
    private String reqDate;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 전표구분 ( 1:주문, -1:반품 ) */
    private Integer slipFg;
    /** 브랜드코드 */
    private String hqBrandCd;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 상품바코드 */
    private String barcdCd;
    /** 상품분류 */
    private String prodClassCd;
    /** 발주단위구분 */
    private String poUnitFg;
    /** 발주단위수량 */
    private Integer poUnitQty;
    /** 발주단위허용구분 */
    private Integer poUnitAllowFg;
    /** 주문공급단가 */
    private Integer orderSplyUprc;
    /** 이전 주문수량(주문단위) */
    private Integer prevOrderUnitQty;
    /** 이전 주문수량(나머지) */
    private Integer prevOrderEtcQty;
    /** 이전 주문수량합계(낱개) */
    private Integer prevOrderTotQty;
    /** 주문수량(주문단위) */
    private Integer orderUnitQty;
    /** 주문수량(나머지) */
    private Integer orderEtcQty;
    /** 주문수량합계(낱개) */
    private Integer orderTotQty;
    /** 주문금액(ORDER_SPLY_UPRC * ORDER_TOT_QTY / PO_UNIT_QTY) */
    private Long orderAmt;
    /** 주문금액VAT */
    private Long orderVat;
    /** 주문금액합계 */
    private Long orderTot;
    /** MD공급단가 */
    private Integer mdSplyUprc;
    /** MD수량(주문단위) */
    private Integer mdUnitQty;
    /** MD수량(나머지) */
    private Integer mdEtcQty;
    /** MD수량합계(낱개) */
    private Integer mdTotQty;
    /** MD금액(MD_SPLY_UPRC * MD_TOT_QTY / PO_UNIT_QTY) */
    private Long mdAmt;
    /** MD금액VAT */
    private Long mdVat;
    /** MD금액합계 */
    private Long mdTot;
    /** 비고 */
    private String remark;
    /** HD 비고 */
    private String hdRemark;
    /** 발주최소수량 */
    private String poMinQty;
    /** 상품부가세구분 */
    private String vatFg01;
    /** 출고가-부가세포함여부 */
    private String envst0011;
    /** 옵션1 */
    private String option1;
    /** 옵션2 */
    private String option2;
    /** 판매금액 */
    private String saleUprc;
    /** 창고코드 */
    private String storageCd;
    
    /** 창고 갯수 */
    //private int	storageCnt;

    /** 창고별 배열값-창고코드 */
    private String  arrStorageCd;

    /** 창고별 배열값-창고명 */
    private String  arrStorageNm;

    /** 창고별 배열값-입고수량 주문단위 */
    private String  arrOrderUnitQty;

    /** 창고별 배열값-입고수량 나머지 */
    private String  arrOrderEtcQty;

    /** 창고별 배열값-입고수량 합계 */
    private String  arrOrderTotQty;

    /** 창고별 배열값-입고금액 */
    private String  arrOrderAmt;

    /** 창고별 배열값-입고금액 부가세 */
    private String  arrOrderVat;

    /** 창고별 배열값-입고금액 합계 */
    private String  arrOrderTot;
    
    /** 출고창고 */
    private String outStorageCd;
    
    /** 거래처코드 */
    private String vendrCd;
    
    /**
     * @return the reqDate
     */
    public String getReqDate() {
        return reqDate;
    }

    /**
     * @param reqDate the reqDate to set
     */
    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

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
     * @return the slipFg
     */
    public Integer getSlipFg() {
        return slipFg;
    }

    /**
     * @param slipFg the slipFg to set
     */
    public void setSlipFg(Integer slipFg) {
        this.slipFg = slipFg;
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
     * @return the poUnitFg
     */
    public String getPoUnitFg() {
        return poUnitFg;
    }

    /**
     * @param poUnitFg the poUnitFg to set
     */
    public void setPoUnitFg(String poUnitFg) {
        this.poUnitFg = poUnitFg;
    }

    /**
     * @return the poUnitQty
     */
    public Integer getPoUnitQty() {
        return poUnitQty;
    }

    /**
     * @param poUnitQty the poUnitQty to set
     */
    public void setPoUnitQty(Integer poUnitQty) {
        this.poUnitQty = poUnitQty;
    }

    /**
     * @return the poUnitAllowFg
     */
    public Integer getPoUnitAllowFg() {
        return poUnitAllowFg;
    }

    /**
     * @param poUnitAllowFg the poUnitAllowFg to set
     */
    public void setPoUnitAllowFg(Integer poUnitAllowFg) {
        this.poUnitAllowFg = poUnitAllowFg;
    }

    /**
     * @return the orderSplyUprc
     */
    public Integer getOrderSplyUprc() {
        return orderSplyUprc;
    }

    /**
     * @param orderSplyUprc the orderSplyUprc to set
     */
    public void setOrderSplyUprc(Integer orderSplyUprc) {
        this.orderSplyUprc = orderSplyUprc;
    }

    /**
     * @return the prevOrderUnitQty
     */
    public Integer getPrevOrderUnitQty() {
        return prevOrderUnitQty;
    }

    /**
     * @param prevOrderUnitQty the prevOrderUnitQty to set
     */
    public void setPrevOrderUnitQty(Integer prevOrderUnitQty) {
        this.prevOrderUnitQty = prevOrderUnitQty;
    }

    /**
     * @return the prevOrderEtcQty
     */
    public Integer getPrevOrderEtcQty() {
        return prevOrderEtcQty;
    }

    /**
     * @param prevOrderEtcQty the prevOrderEtcQty to set
     */
    public void setPrevOrderEtcQty(Integer prevOrderEtcQty) {
        this.prevOrderEtcQty = prevOrderEtcQty;
    }

    /**
     * @return the prevOrderTotQty
     */
    public Integer getPrevOrderTotQty() {
        return prevOrderTotQty;
    }

    /**
     * @param prevOrderTotQty the prevOrderTotQty to set
     */
    public void setPrevOrderTotQty(Integer prevOrderTotQty) {
        this.prevOrderTotQty = prevOrderTotQty;
    }

    /**
     * @return the orderUnitQty
     */
    public Integer getOrderUnitQty() {
        return orderUnitQty;
    }

    /**
     * @param orderUnitQty the orderUnitQty to set
     */
    public void setOrderUnitQty(Integer orderUnitQty) {
        this.orderUnitQty = orderUnitQty;
    }

    /**
     * @return the orderEtcQty
     */
    public Integer getOrderEtcQty() {
        return orderEtcQty;
    }

    /**
     * @param orderEtcQty the orderEtcQty to set
     */
    public void setOrderEtcQty(Integer orderEtcQty) {
        this.orderEtcQty = orderEtcQty;
    }

    /**
     * @return the orderTotQty
     */
    public Integer getOrderTotQty() {
        return orderTotQty;
    }

    /**
     * @param orderTotQty the orderTotQty to set
     */
    public void setOrderTotQty(Integer orderTotQty) {
        this.orderTotQty = orderTotQty;
    }

    /**
     * @return the orderAmt
     */
    public Long getOrderAmt() {
        return orderAmt;
    }

    /**
     * @param orderAmt the orderAmt to set
     */
    public void setOrderAmt(Long orderAmt) {
        this.orderAmt = orderAmt;
    }

    /**
     * @return the orderVat
     */
    public Long getOrderVat() {
        return orderVat;
    }

    /**
     * @param orderVat the orderVat to set
     */
    public void setOrderVat(Long orderVat) {
        this.orderVat = orderVat;
    }

    /**
     * @return the orderTot
     */
    public Long getOrderTot() {
        return orderTot;
    }

    /**
     * @param orderTot the orderTot to set
     */
    public void setOrderTot(Long orderTot) {
        this.orderTot = orderTot;
    }

    /**
     * @return the mdSplyUprc
     */
    public Integer getMdSplyUprc() {
        return mdSplyUprc;
    }

    /**
     * @param mdSplyUprc the mdSplyUprc to set
     */
    public void setMdSplyUprc(Integer mdSplyUprc) {
        this.mdSplyUprc = mdSplyUprc;
    }

    /**
     * @return the mdUnitQty
     */
    public Integer getMdUnitQty() {
        return mdUnitQty;
    }

    /**
     * @param mdUnitQty the mdUnitQty to set
     */
    public void setMdUnitQty(Integer mdUnitQty) {
        this.mdUnitQty = mdUnitQty;
    }

    /**
     * @return the mdEtcQty
     */
    public Integer getMdEtcQty() {
        return mdEtcQty;
    }

    /**
     * @param mdEtcQty the mdEtcQty to set
     */
    public void setMdEtcQty(Integer mdEtcQty) {
        this.mdEtcQty = mdEtcQty;
    }

    /**
     * @return the mdTotQty
     */
    public Integer getMdTotQty() {
        return mdTotQty;
    }

    /**
     * @param mdTotQty the mdTotQty to set
     */
    public void setMdTotQty(Integer mdTotQty) {
        this.mdTotQty = mdTotQty;
    }

    /**
     * @return the mdAmt
     */
    public Long getMdAmt() {
        return mdAmt;
    }

    /**
     * @param mdAmt the mdAmt to set
     */
    public void setMdAmt(Long mdAmt) {
        this.mdAmt = mdAmt;
    }

    /**
     * @return the mdVat
     */
    public Long getMdVat() {
        return mdVat;
    }

    /**
     * @param mdVat the mdVat to set
     */
    public void setMdVat(Long mdVat) {
        this.mdVat = mdVat;
    }

    /**
     * @return the mdTot
     */
    public Long getMdTot() {
        return mdTot;
    }

    /**
     * @param mdTot the mdTot to set
     */
    public void setMdTot(Long mdTot) {
        this.mdTot = mdTot;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the hdRemark
     */
    public String getHdRemark() {
        return hdRemark;
    }

    /**
     * @param hdRemark the hdRemark to set
     */
    public void setHdRemark(String hdRemark) {
        this.hdRemark = hdRemark;
    }

    /**
     * @return the poMinQty
     */
    public String getPoMinQty() {
        return poMinQty;
    }

    /**
     * @param poMinQty the poMinQty to set
     */
    public void setPoMinQty(String poMinQty) {
        this.poMinQty = poMinQty;
    }

    /**
     * @return the vatFg01
     */
    public String getVatFg01() {
        return vatFg01;
    }

    /**
     * @param vatFg01 the vatFg01 to set
     */
    public void setVatFg01(String vatFg01) {
        this.vatFg01 = vatFg01;
    }

    /**
     * @return the envst0011
     */
    public String getEnvst0011() {
        return envst0011;
    }

    /**
     * @param envst0011 the envst0011 to set
     */
    public void setEnvst0011(String envst0011) {
        this.envst0011 = envst0011;
    }

    /**
     * @return the option1
     */
    public String getOption1() {
        return option1;
    }

    /**
     * @param option1 the option1 to set
     */
    public void setOption1(String option1) {
        this.option1 = option1;
    }

    /**
     * @return the option2
     */
    public String getOption2() {
        return option2;
    }

    /**
     * @param option2 the option2 to set
     */
    public void setOption2(String option2) {
        this.option2 = option2;
    }

    /**
     * @return the saleUprc
     */
    public String getSaleUprc() {
        return saleUprc;
    }

    /**
     * @param saleUprc the saleUprc to set
     */
    public void setSaleUprc(String saleUprc) {
        this.saleUprc = saleUprc;
    }
    
	public String getArrStorageCd() {
		return arrStorageCd;
	}
	public void setArrStorageCd(String arrStorageCd) {
		this.arrStorageCd = arrStorageCd;
	}
	public String getArrStorageNm() {
		return arrStorageNm;
	}
	public void setArrStorageNm(String arrStorageNm) {
		this.arrStorageNm = arrStorageNm;
	}
	public String getArrOrderUnitQty() {
		return arrOrderUnitQty;
	}

	public void setArrOrderUnitQty(String arrOrderUnitQty) {
		this.arrOrderUnitQty = arrOrderUnitQty;
	}

	public String getArrOrderEtcQty() {
		return arrOrderEtcQty;
	}

	public void setArrOrderEtcQty(String arrOrderEtcQty) {
		this.arrOrderEtcQty = arrOrderEtcQty;
	}

	public String getArrOrderTotQty() {
		return arrOrderTotQty;
	}

	public void setArrOrderTotQty(String arrOrderTotQty) {
		this.arrOrderTotQty = arrOrderTotQty;
	}

	public String getArrOrderAmt() {
		return arrOrderAmt;
	}

	public void setArrOrderAmt(String arrOrderAmt) {
		this.arrOrderAmt = arrOrderAmt;
	}

	public String getArrOrderVat() {
		return arrOrderVat;
	}

	public void setArrOrderVat(String arrOrderVat) {
		this.arrOrderVat = arrOrderVat;
	}

	public String getArrOrderTot() {
		return arrOrderTot;
	}

	public void setArrOrderTot(String arrOrderTot) {
		this.arrOrderTot = arrOrderTot;
	}

	public String getStorageCd() {
		return storageCd;
	}

	public void setStorageCd(String storageCd) {
		this.storageCd = storageCd;
	}

	public String getOutStorageCd() {
		return outStorageCd;
	}

	public void setOutStorageCd(String outStorageCd) {
		this.outStorageCd = outStorageCd;
	}

    public String getVendrCd() {
        return vendrCd;
    }

    public void setVendrCd(String vendrCd) {
        this.vendrCd = vendrCd;
    }
}
