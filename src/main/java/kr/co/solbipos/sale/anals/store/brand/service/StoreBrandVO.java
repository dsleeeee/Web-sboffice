package kr.co.solbipos.sale.anals.store.brand.service;

import kr.co.solbipos.application.common.service.PageVO;

public class StoreBrandVO extends PageVO {

    private static final long serialVersionUID = -4091176508608651478L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장코드 Array*/
    private String[] arrStoreCd;
    /** 정렬순서 */
    private String chkSort;
    /** 결제수단전체 */
    private String chkPay;
    /** 정렬숫자 */
    private String rowNum;
    /** 판매자별, 동적 컬럼 생성을 위한 쿼리 문자열*/
    private String sQuery1;
    private String sQuery2;

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
   
    
    public String[] getArrStoreCd() {
		return arrStoreCd;
	}

	public void setArrStoreCd(String[] arrStoreCd) {
		this.arrStoreCd = arrStoreCd;
	}

	public String getChkSort() {
		return chkSort;
	}

	public void setChkSort(String chkSort) {
		this.chkSort = chkSort;
	}

	public String getChkPay() {
		return chkPay;
	}

	public void setChkPay(String chkPay) {
		this.chkPay = chkPay;
	}
	
	public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
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
}
