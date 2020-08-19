package kr.co.solbipos.stock.com.popup.service;

import kr.co.solbipos.application.common.service.PageVO;

public class StockComPopupVO extends PageVO {

    private static final long serialVersionUID = -4091176508608651478L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;

    /** 동적 컬럼 생성을 위한 쿼리 문자열*/
    private String sQuery1;
    private String sQuery2;
    private String sQuery3;

    /** 구분코드
	 * (01: 본사입고, 16: 업체반출, 13: 본사출고, 02: 본사반입, 04: 매장이입, 14: 매장이출, 17: 재고폐기, 21: 재고조정,
	 * 22: 세트생성, 19: 거래처출고, 33: 거래처반품, 03: 매장입고, 12: 매장반품, 06: 사입입고, 18: 사입반품, 11: 매장판매) */
	private String colCode;
	
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

	public String getsQuery3() {
		return sQuery3;
	}

	public void setsQuery3(String sQuery3) {
		this.sQuery3 = sQuery3;
	}

	public String getColCode() {
		return colCode;
	}

	public void setColCode(String colCode) {
		this.colCode = colCode;
	}
	
}
