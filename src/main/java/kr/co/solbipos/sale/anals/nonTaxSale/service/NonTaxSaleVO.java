package kr.co.solbipos.sale.anals.nonTaxSale.service;

import kr.co.solbipos.application.common.service.PageVO;

public class NonTaxSaleVO extends PageVO {

    private static final long serialVersionUID = 5763530344079074968L;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /** 매장 멀티선택 */
    private String storeCds;

    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;

    /** 일자표시옵션 */
    private String dayOption;

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

    public String getStoreCds() {
        return storeCds;
    }

    public void setStoreCds(String storeCds) {
        this.storeCds = storeCds;
    }

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
    }

    public String getDayOption() {
        return dayOption;
    }

    public void setDayOption(String dayOption) {
        this.dayOption = dayOption;
    }
}
