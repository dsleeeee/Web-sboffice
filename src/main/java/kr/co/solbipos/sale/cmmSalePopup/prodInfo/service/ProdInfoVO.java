package kr.co.solbipos.sale.cmmSalePopup.prodInfo.service;

import kr.co.solbipos.application.common.service.PageVO;

public class ProdInfoVO extends PageVO {

    private static final long serialVersionUID = -7954821071282556829L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장코드 array */
    private String arrStoreCd[];
    /** 매출일자 */
    private String saleDate;

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

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

}
