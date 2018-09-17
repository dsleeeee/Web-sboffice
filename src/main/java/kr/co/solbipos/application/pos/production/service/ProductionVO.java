package kr.co.solbipos.application.pos.production.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : ProductionVO.java
 * @Description : 예외출고 생산량 VO
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.16  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018.09.16
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ProductionVO extends CmmVO {

    /** [매장코드] */
    private String storeCd;
    /** [상품코드] */
    private String prodCd;
    /** [상품명] */
    private String prodNm;
    /** [상품분류코드] */
    private String prodClassCd;
    /** [코너코드] */
    private String cornrCd;
    /** [원산지코드] */
    private String orgplceCd;
    /** [사이드속성분류코드] */
    private String sdattrClassCd;
    /** [사이드선택그룹코드] */
    private String sdselGrpCd;
    /** [포인트적립여부] */
    private String pointSaveYn;
    /** [상품할인구분] 첫번째:일반, 두번째:쿠폰, 세번째:회원, 네번째:제휴, 다섯:현장 */
    private String prodDcFg;
    /** [부가세구분] TB_CM_NMCODE(NMCODE_GRP_CD='039') */
    private String vatFg;
    /** [상품봉사료여부] Y:봉사료포함 N:봉사료미포함 */
    private String prodTipYn;
    /** [상품포장금액] */
    private Integer prodPackAmt;
    /** [상품배달금액] */
    private Integer prodDlvrAmt;
    /** [상품유형구분] TB_CM_NMCODE(NMCODE_GRP_CD='008') */
    private String prodTypeFg;
    /** [판매상품여부] */
    private String saleProdYn;
    /** [재고상품여부] */
    private String stockProdYn;
    /** [사이드상품여부] */
    private String sideProdYn;
    /** [세트상품구분] TB_CM_NMCODE(NMCODE_GRP_CD='009') */
    private String setProdFg;
    /** [가격관리구분] TB_CM_NMCODE(NMCODE_GRP_CD='045') */
    private String prcCtrlFg;
    /** [원가단가] */
    private Integer costUprc;
    /** [최종원가단가] */
    private Integer lastCostUprc;
    /** [공급단가] */
    private Integer splyUprc;
    /** [공급단가사용여부] */
    private String splyUprcUseYn;
    /** [발주상품구분] TB_CM_NMCODE(NMCODE_GRP_CD=') */
    private String poProdFg;
    /** [발주단위구분] */
    private String poUnitFg;
    /** [발주단위수량] */
    private Integer poUnitQty;
    /** [발주단위허용구분] */
    private String poUnitAllowFg;
    /** [발주최소수량] */
    private Integer poMinQty;
    /** [안전재고수량] */
    private Integer safeStockQty;
    /** [재고단위구분] */
    private String stockUnitFg;
    /** [사용여부] */
    private String useYn;
    /** [비고] */
    private String remark;

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
     * @return the cornrCd
     */

    public String getCornrCd() {
        return cornrCd;
    }

    /**
     * @param cornrCd the cornrCd to set
     */
    public void setCornrCd(String cornrCd) {
        this.cornrCd = cornrCd;
    }

    /**
     * @return the orgplceCd
     */

    public String getOrgplceCd() {
        return orgplceCd;
    }

    /**
     * @param orgplceCd the orgplceCd to set
     */
    public void setOrgplceCd(String orgplceCd) {
        this.orgplceCd = orgplceCd;
    }

    /**
     * @return the sdattrClassCd
     */

    public String getSdattrClassCd() {
        return sdattrClassCd;
    }

    /**
     * @param sdattrClassCd the sdattrClassCd to set
     */
    public void setSdattrClassCd(String sdattrClassCd) {
        this.sdattrClassCd = sdattrClassCd;
    }

    /**
     * @return the sdselGrpCd
     */

    public String getSdselGrpCd() {
        return sdselGrpCd;
    }

    /**
     * @param sdselGrpCd the sdselGrpCd to set
     */
    public void setSdselGrpCd(String sdselGrpCd) {
        this.sdselGrpCd = sdselGrpCd;
    }

    /**
     * @return the pointSaveYn
     */

    public String getPointSaveYn() {
        return pointSaveYn;
    }

    /**
     * @param pointSaveYn the pointSaveYn to set
     */
    public void setPointSaveYn(String pointSaveYn) {
        this.pointSaveYn = pointSaveYn;
    }

    /**
     * @return the prodDcFg
     */

    public String getProdDcFg() {
        return prodDcFg;
    }

    /**
     * @param prodDcFg the prodDcFg to set
     */
    public void setProdDcFg(String prodDcFg) {
        this.prodDcFg = prodDcFg;
    }

    /**
     * @return the vatFg
     */

    public String getVatFg() {
        return vatFg;
    }

    /**
     * @param vatFg the vatFg to set
     */
    public void setVatFg(String vatFg) {
        this.vatFg = vatFg;
    }

    /**
     * @return the prodTipYn
     */

    public String getProdTipYn() {
        return prodTipYn;
    }

    /**
     * @param prodTipYn the prodTipYn to set
     */
    public void setProdTipYn(String prodTipYn) {
        this.prodTipYn = prodTipYn;
    }

    /**
     * @return the prodPackAmt
     */

    public Integer getProdPackAmt() {
        return prodPackAmt;
    }

    /**
     * @param prodPackAmt the prodPackAmt to set
     */
    public void setProdPackAmt(Integer prodPackAmt) {
        this.prodPackAmt = prodPackAmt;
    }

    /**
     * @return the prodDlvrAmt
     */

    public Integer getProdDlvrAmt() {
        return prodDlvrAmt;
    }

    /**
     * @param prodDlvrAmt the prodDlvrAmt to set
     */
    public void setProdDlvrAmt(Integer prodDlvrAmt) {
        this.prodDlvrAmt = prodDlvrAmt;
    }

    /**
     * @return the prodTypeFg
     */

    public String getProdTypeFg() {
        return prodTypeFg;
    }

    /**
     * @param prodTypeFg the prodTypeFg to set
     */
    public void setProdTypeFg(String prodTypeFg) {
        this.prodTypeFg = prodTypeFg;
    }

    /**
     * @return the saleProdYn
     */

    public String getSaleProdYn() {
        return saleProdYn;
    }

    /**
     * @param saleProdYn the saleProdYn to set
     */
    public void setSaleProdYn(String saleProdYn) {
        this.saleProdYn = saleProdYn;
    }

    /**
     * @return the stockProdYn
     */

    public String getStockProdYn() {
        return stockProdYn;
    }

    /**
     * @param stockProdYn the stockProdYn to set
     */
    public void setStockProdYn(String stockProdYn) {
        this.stockProdYn = stockProdYn;
    }

    /**
     * @return the sideProdYn
     */

    public String getSideProdYn() {
        return sideProdYn;
    }

    /**
     * @param sideProdYn the sideProdYn to set
     */
    public void setSideProdYn(String sideProdYn) {
        this.sideProdYn = sideProdYn;
    }

    /**
     * @return the setProdFg
     */

    public String getSetProdFg() {
        return setProdFg;
    }

    /**
     * @param setProdFg the setProdFg to set
     */
    public void setSetProdFg(String setProdFg) {
        this.setProdFg = setProdFg;
    }

    /**
     * @return the prcCtrlFg
     */

    public String getPrcCtrlFg() {
        return prcCtrlFg;
    }

    /**
     * @param prcCtrlFg the prcCtrlFg to set
     */
    public void setPrcCtrlFg(String prcCtrlFg) {
        this.prcCtrlFg = prcCtrlFg;
    }

    /**
     * @return the costUprc
     */

    public Integer getCostUprc() {
        return costUprc;
    }

    /**
     * @param costUprc the costUprc to set
     */
    public void setCostUprc(Integer costUprc) {
        this.costUprc = costUprc;
    }

    /**
     * @return the lastCostUprc
     */

    public Integer getLastCostUprc() {
        return lastCostUprc;
    }

    /**
     * @param lastCostUprc the lastCostUprc to set
     */
    public void setLastCostUprc(Integer lastCostUprc) {
        this.lastCostUprc = lastCostUprc;
    }

    /**
     * @return the splyUprc
     */

    public Integer getSplyUprc() {
        return splyUprc;
    }

    /**
     * @param splyUprc the splyUprc to set
     */
    public void setSplyUprc(Integer splyUprc) {
        this.splyUprc = splyUprc;
    }

    /**
     * @return the splyUprcUseYn
     */

    public String getSplyUprcUseYn() {
        return splyUprcUseYn;
    }

    /**
     * @param splyUprcUseYn the splyUprcUseYn to set
     */
    public void setSplyUprcUseYn(String splyUprcUseYn) {
        this.splyUprcUseYn = splyUprcUseYn;
    }

    /**
     * @return the poProdFg
     */

    public String getPoProdFg() {
        return poProdFg;
    }

    /**
     * @param poProdFg the poProdFg to set
     */
    public void setPoProdFg(String poProdFg) {
        this.poProdFg = poProdFg;
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

    public String getPoUnitAllowFg() {
        return poUnitAllowFg;
    }

    /**
     * @param poUnitAllowFg the poUnitAllowFg to set
     */
    public void setPoUnitAllowFg(String poUnitAllowFg) {
        this.poUnitAllowFg = poUnitAllowFg;
    }

    /**
     * @return the poMinQty
     */

    public Integer getPoMinQty() {
        return poMinQty;
    }

    /**
     * @param poMinQty the poMinQty to set
     */
    public void setPoMinQty(Integer poMinQty) {
        this.poMinQty = poMinQty;
    }

    /**
     * @return the safeStockQty
     */

    public Integer getSafeStockQty() {
        return safeStockQty;
    }

    /**
     * @param safeStockQty the safeStockQty to set
     */
    public void setSafeStockQty(Integer safeStockQty) {
        this.safeStockQty = safeStockQty;
    }

    /**
     * @return the stockUnitFg
     */

    public String getStockUnitFg() {
        return stockUnitFg;
    }

    /**
     * @param stockUnitFg the stockUnitFg to set
     */
    public void setStockUnitFg(String stockUnitFg) {
        this.stockUnitFg = stockUnitFg;
    }

    /**
     * @return the useYn
     */

    public String getUseYn() {
        return useYn;
    }

    /**
     * @param useYn the useYn to set
     */
    public void setUseYn(String useYn) {
        this.useYn = useYn;
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
}
