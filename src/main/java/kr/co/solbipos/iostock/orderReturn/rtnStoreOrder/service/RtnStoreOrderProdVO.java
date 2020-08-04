package kr.co.solbipos.iostock.orderReturn.rtnStoreOrder.service;

import kr.co.solbipos.application.common.service.PageVO;

public class RtnStoreOrderProdVO extends PageVO {

    private static final long serialVersionUID = -2562407537859583846L;
    
    /** 출고예약일자 */
    private String reqDate;
    /** 매장코드 */
    private String storeCd;
    /** 전표구분 ( 1:주문, -1:반품 ) */
    private Integer slipFg;
    /** 상품코드 */
    private String prodCd;
    /** 발주단위구분 */
    private String poUnitFg;
    /** 발주단위수량 */
    private Integer poUnitQty;
    /** 주문공급단가 */
    private Integer orderSplyUprc;
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
    /** 창고 */
    private String storageCd;
    /** 출고창고 */
    private String outStorageCd;
	public String getReqDate() {
		return reqDate;
	}
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}
	public String getStoreCd() {
		return storeCd;
	}
	public void setStoreCd(String storeCd) {
		this.storeCd = storeCd;
	}
	public Integer getSlipFg() {
		return slipFg;
	}
	public void setSlipFg(Integer slipFg) {
		this.slipFg = slipFg;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getPoUnitFg() {
		return poUnitFg;
	}
	public void setPoUnitFg(String poUnitFg) {
		this.poUnitFg = poUnitFg;
	}
	public Integer getPoUnitQty() {
		return poUnitQty;
	}
	public void setPoUnitQty(Integer poUnitQty) {
		this.poUnitQty = poUnitQty;
	}
	public Integer getOrderSplyUprc() {
		return orderSplyUprc;
	}
	public void setOrderSplyUprc(Integer orderSplyUprc) {
		this.orderSplyUprc = orderSplyUprc;
	}
	public Integer getOrderUnitQty() {
		return orderUnitQty;
	}
	public void setOrderUnitQty(Integer orderUnitQty) {
		this.orderUnitQty = orderUnitQty;
	}
	public Integer getOrderEtcQty() {
		return orderEtcQty;
	}
	public void setOrderEtcQty(Integer orderEtcQty) {
		this.orderEtcQty = orderEtcQty;
	}
	public Integer getOrderTotQty() {
		return orderTotQty;
	}
	public void setOrderTotQty(Integer orderTotQty) {
		this.orderTotQty = orderTotQty;
	}
	public Long getOrderAmt() {
		return orderAmt;
	}
	public void setOrderAmt(Long orderAmt) {
		this.orderAmt = orderAmt;
	}
	public Long getOrderVat() {
		return orderVat;
	}
	public void setOrderVat(Long orderVat) {
		this.orderVat = orderVat;
	}
	public Long getOrderTot() {
		return orderTot;
	}
	public void setOrderTot(Long orderTot) {
		this.orderTot = orderTot;
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
    
    
    
    
	
}
