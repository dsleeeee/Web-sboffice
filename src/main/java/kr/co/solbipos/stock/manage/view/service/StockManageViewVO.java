package kr.co.solbipos.stock.manage.view.service;

import kr.co.solbipos.application.common.service.PageVO;

public class StockManageViewVO extends PageVO {

	private static final long serialVersionUID = 2722329433257568791L;
	
	/** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
	/** 상태 */
    private String hqGbn;
    /** 일자 */
    private String totDate;
    /** 차수 */
    private Integer seqNo;
    /** 진행 */
    private String procFg;
    /** 제목 */
    private String title;
    /** 상품개수 */
    private Integer prodQty;
    private String orgnFg;
    
    public String getHqOfficeCd() {
		return hqOfficeCd;
	}
	public void setHqOfficeCd(String hqOfficeCd) {
		this.hqOfficeCd = hqOfficeCd;
	}
	public String getStoreCd() {
		return storeCd;
	}
	public void setStoreCd(String storeCd) {
		this.storeCd = storeCd;
	}
    public String getHqGbn() {
		return hqGbn;
	}
	public void setHqGbn(String hqGbn) {
		this.hqGbn = hqGbn;
	}
	public String getTotDate() {
		return totDate;
	}
	public void setTotDate(String totDate) {
		this.totDate = totDate;
	}
	public Integer getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}
	public String getProcFg() {
		return procFg;
	}
	public void setProcFg(String procFg) {
		this.procFg = procFg;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getProdQty() {
		return prodQty;
	}
	public void setProdQty(Integer prodQty) {
		this.prodQty = prodQty;
	}
	public String getOrgnFg() {
		return orgnFg;
	}
	public void setOrgnFg(String orgnFg) {
		this.orgnFg = orgnFg;
	}
	
}
