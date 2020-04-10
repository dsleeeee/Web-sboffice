package kr.co.solbipos.stock.status.dailyIoStock.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;

public class DailyIoStockVO extends PageVO {

	private static final long serialVersionUID = 6438209115509780020L;

	/** 본사/가맹정 구분 */
	private String orgnFg;
	/** 본사코드 */
    private String hqOfficeCd;
    /** 창고코드 */
    private String storageCd;
    /** 브랜드코드 */
    private String hqBrandCd;
    /** 매장코드 */
    private String storeCd;
    /** 컬럼 헤더 구분 */
    private String ioOccrFg;
    /** 전표변호 */
    private String slipFg;
    /** 일자 */
    private String saleDate;

	public String getHqOfficeCd() {
		return hqOfficeCd;
	}
	public void setHqOfficeCd(String hqOfficeCd) {
		this.hqOfficeCd = hqOfficeCd;
	}
	public String getStorageCd() {
		return storageCd;
	}
	public void setStorageCd(String storageCd) {
		this.storageCd = storageCd;
	}
	public String getHqBrandCd() {
		return hqBrandCd;
	}
	public void setHqBrandCd(String hqBrandCd) {
		this.hqBrandCd = hqBrandCd;
	}
	public String getStoreCd() {
		return storeCd;
	}
	public void setStoreCd(String storeCd) {
		this.storeCd = storeCd;
	}
	public String getOrgnFg() {
		return orgnFg;
	}
	public void setOrgnFg(String orgnFg) {
		this.orgnFg = orgnFg;
	}
	public String getIoOccrFg() {
		return ioOccrFg;
	}
	public void setIoOccrFg(String ioOccrFg) {
		this.ioOccrFg = ioOccrFg;
	}
	public String getSlipFg() {
		return slipFg;
	}
	public void setSlipFg(String slipFg) {
		this.slipFg = slipFg;
	}
	public String getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

}

