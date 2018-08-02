package kr.co.sample.application.domain;

import kr.co.solbipos.application.common.service.PageVO;

public class ScdShopmTVO extends PageVO {
    
    private static final long serialVersionUID = -3250718214252246552L;
    private String shopCd;
    private String shopNm;
    private String hdShopCd;
    private String shopGroupCd;
    private String shopTypeFg;
    private String deleteYn;
    
    
    /**
     * @return the shopCd
     */
    public String getShopCd() {
        return shopCd;
    }
    /**
     * @param shopCd the shopCd to set
     */
    public void setShopCd(String shopCd) {
        this.shopCd = shopCd;
    }
    /**
     * @return the shopNm
     */
    public String getShopNm() {
        return shopNm;
    }
    /**
     * @param shopNm the shopNm to set
     */
    public void setShopNm(String shopNm) {
        this.shopNm = shopNm;
    }
    /**
     * @return the hdShopCd
     */
    public String getHdShopCd() {
        return hdShopCd;
    }
    /**
     * @param hdShopCd the hdShopCd to set
     */
    public void setHdShopCd(String hdShopCd) {
        this.hdShopCd = hdShopCd;
    }
    /**
     * @return the shopGroupCd
     */
    public String getShopGroupCd() {
        return shopGroupCd;
    }
    /**
     * @param shopGroupCd the shopGroupCd to set
     */
    public void setShopGroupCd(String shopGroupCd) {
        this.shopGroupCd = shopGroupCd;
    }
    /**
     * @return the shopTypeFg
     */
    public String getShopTypeFg() {
        return shopTypeFg;
    }
    /**
     * @param shopTypeFg the shopTypeFg to set
     */
    public void setShopTypeFg(String shopTypeFg) {
        this.shopTypeFg = shopTypeFg;
    }
    /**
     * @return the deleteYn
     */
    public String getDeleteYn() {
        return deleteYn;
    }
    /**
     * @param deleteYn the deleteYn to set
     */
    public void setDeleteYn(String deleteYn) {
        this.deleteYn = deleteYn;
    }
    
}
