package kr.co.solbipos.sale.anals.store.prod.service;

import kr.co.solbipos.application.common.service.PageVO;

public class StoreProdVO extends PageVO {

    private static final long serialVersionUID = -4091176508608651478L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 정렬순서 */
    private String chkSort;
    /** 상품상세보기 */
    private String chkProdAll;
    
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
   
    public String getChkSort() {
		return chkSort;
	}

	public void setChkSort(String chkSort) {
		this.chkSort = chkSort;
	}

	public String getChkProdAll() {
		return chkProdAll;
	}

	public void setChkProdAll(String chkProdAll) {
		this.chkProdAll = chkProdAll;
	}
	
}
