package kr.co.solbipos.store.manage.storemanage.service;

import java.util.List;
import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : StoreProductVO.java
 * @Description : 가맹점관리 > 매장관리 > 매장정보관리 > 환경설정
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 06.08
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class StoreProductVO extends CmmVO {

    private static final long serialVersionUID = -241331725446957847L;
    /** 매장코드 */
    private String storeCd;
    /** 주방프린터번호 */
    private String prterNo;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 상품분류코드 */
    private String prodClassCd;
    /** 상품분류명 */
    private String prodClassNm;
    /** 상품 상위분류코드 */
    private String pProdClassCd;
    /** 코너코드 */
    private String cornrCd;
    /** 원산지코드 */
    private String orgplceCd;
    /** 사이드속성분류코드 */
    private String sdattrClassCd;
    /** 사이드선택그룹코드 */
    private String sdselGrpCd;
    /** 포인트적립여부 */
    private String pointSaveYn;
    /** 상품할인구분 */
    private String prodDcFg;
    /** 부가세구분 */
    private String vatFg;
    /** 상품봉사료여부 */
    private String prodTipYn;
    /** 상품포장금액 */
    private String prodPackAmt;
    /** 상품배달금액 */
    private String prodDlvrAmt;
    /** 상품유형구분 */
    private String prodTypeFg;
    /** 판매상품여부 */
    private String saleProdYn;
    /** 재고상품여부 */
    private String stockProdYn;
    /** 사이드상품여부 */
    private String sideProdYn;
    /** 세트상품구분 */
    private String setProdFg;
    /** 가격관리구분 */
    private String prcCtrlFg;
    /** 원가단가 */
    private String costUprc;
    /** 최종원가단가 */
    private String lastCostUprc;
    /** 공급단가 */
    private String splyUprc;
    /** 공급단가사용여부 */
    private String splyUprcUseYn;
    /** 발주상품구분 */
    private String poProdFg;
    /** 발주단위구분 */
    private String poUnitFg;
    /** 발주단위수량 */
    private String poUnitQty;
    /** 발주단위허용구분 */
    private String poUnitAllowFg;
    /** 발주최소수량 */
    private String poMinQty;
    /** 안전재고수량 */
    private String safeStockQty;
    /** 재고단위구분 */
    private String stockUnitFg;
    /** 사용여부  (Y:사용, N:미사용) */
    private UseYn useYn;
    /** 비고 */
    private String remark;
    /** 상품출력 여부 */
    private UseYn printYn;
    /** child items */
    private List<StoreProductVO> items;
    /** 상품분류코드목록*/
    private String arrProdClassCd[];
    
    
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
     * @return the prterNo
     */
    public String getPrterNo() {
        return prterNo;
    }
    /**
     * @param prterNo the prterNo to set
     */
    public void setPrterNo(String prterNo) {
        this.prterNo = prterNo;
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
     * @return the prodClassNm
     */
    public String getProdClassNm() {
        return prodClassNm;
    }
    /**
     * @param prodClassNm the prodClassNm to set
     */
    public void setProdClassNm(String prodClassNm) {
        this.prodClassNm = prodClassNm;
    }
    /**
     * @return the pProdClassCd
     */
    public String getpProdClassCd() {
        return pProdClassCd;
    }
    /**
     * @param pProdClassCd the pProdClassCd to set
     */
    public void setpProdClassCd(String pProdClassCd) {
        this.pProdClassCd = pProdClassCd;
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
    public String getProdPackAmt() {
        return prodPackAmt;
    }
    /**
     * @param prodPackAmt the prodPackAmt to set
     */
    public void setProdPackAmt(String prodPackAmt) {
        this.prodPackAmt = prodPackAmt;
    }
    /**
     * @return the prodDlvrAmt
     */
    public String getProdDlvrAmt() {
        return prodDlvrAmt;
    }
    /**
     * @param prodDlvrAmt the prodDlvrAmt to set
     */
    public void setProdDlvrAmt(String prodDlvrAmt) {
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
    public String getCostUprc() {
        return costUprc;
    }
    /**
     * @param costUprc the costUprc to set
     */
    public void setCostUprc(String costUprc) {
        this.costUprc = costUprc;
    }
    /**
     * @return the lastCostUprc
     */
    public String getLastCostUprc() {
        return lastCostUprc;
    }
    /**
     * @param lastCostUprc the lastCostUprc to set
     */
    public void setLastCostUprc(String lastCostUprc) {
        this.lastCostUprc = lastCostUprc;
    }
    /**
     * @return the splyUprc
     */
    public String getSplyUprc() {
        return splyUprc;
    }
    /**
     * @param splyUprc the splyUprc to set
     */
    public void setSplyUprc(String splyUprc) {
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
    public String getPoUnitQty() {
        return poUnitQty;
    }
    /**
     * @param poUnitQty the poUnitQty to set
     */
    public void setPoUnitQty(String poUnitQty) {
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
    public String getPoMinQty() {
        return poMinQty;
    }
    /**
     * @param poMinQty the poMinQty to set
     */
    public void setPoMinQty(String poMinQty) {
        this.poMinQty = poMinQty;
    }
    /**
     * @return the safeStockQty
     */
    public String getSafeStockQty() {
        return safeStockQty;
    }
    /**
     * @param safeStockQty the safeStockQty to set
     */
    public void setSafeStockQty(String safeStockQty) {
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
    public UseYn getUseYn() {
        return useYn;
    }
    /**
     * @param useYn the useYn to set
     */
    public void setUseYn(UseYn useYn) {
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
    /**
     * @return the printYn
     */
    public UseYn getPrintYn() {
        return printYn;
    }
    /**
     * @param printYn the printYn to set
     */
    public void setPrintYn(UseYn printYn) {
        this.printYn = printYn;
    }
    /**
     * @return the items
     */
    public List<StoreProductVO> getItems() {
        return items;
    }
    /**
     * @param items the items to set
     */
    public void setItems(List<StoreProductVO> items) {
        this.items = items;
    }

    public String[] getArrProdClassCd() {
        return arrProdClassCd;
    }

    public void setArrProdClassCd(String[] arrProdClassCd) {
        this.arrProdClassCd = arrProdClassCd;
    }
}
