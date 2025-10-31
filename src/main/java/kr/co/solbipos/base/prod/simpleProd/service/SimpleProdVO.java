package kr.co.solbipos.base.prod.simpleProd.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.base.prod.prod.service.enums.ProdNoEnvFg;

/**
 * @Class Name : SimpleProdVO.java
 * @Description : 기초관리 > 상품관리 > 간편상품등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.08.26  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.08.26
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SimpleProdVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /**
     * 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** 회원소속코드 */
    private String membrOrgnCd;

    /** 조회매장 */
    private String storeCd;

    /** 상품코드 */
    private String prodCd;

    /** 상품명 */
    private String prodNm;

    /** 상품분류코드 */
    private String prodClassCd;

    /** 바코드 */
    private String barCd;

    /** 결과검증내용 */
    private String result;

    /** 공급단가 */
    private Double splyUprc;

    /** 원가단가 */
    private Double costUprc;

    /** 세션ID */
    private String sessionId;

    /** 순번 */
    private int seq;

    /** */
    private ProdNoEnvFg prodNoEnv;

    /** 상품유형구분 */
    private String prodTypeFg;

    /** 발주상품구분 */
    private String poProdFg;

    /** 과세여부구분 */
    private String vatFg;

    /** 판매단가 */
    private String saleUprc;

    /** 상품명 중복체크 */
    private String chkProdNm;

    /** 포인트적립여부 */
    private String pointSaveYn;

    /** 상품봉사료여부 */
    private String prodTipYn;

    /** 판매상품여부 */
    private String saleProdYn;

    /** 재고상품여부 */
    private String stockProdYn;

    /** 사이드상품여부 */
    private String sideProdYn;

    /** 세트상품구분 */
    private String setProdFg;

    /** 최종원가단가 */
    private Double lastCostUprc;

    /** 공급단가사용여부 */
    private String splyUprcUseYn;

    /** 발주단위구분 */
    private String poUnitFg;

    /** 발주단위수량 */
    private Integer poUnitQty;

    /** 발주최소수량 */
    private Integer poMinQty;

    /** 안전재고수량 */
    private Integer safeStockQty;

    /** 사용여부 */
    private String useYn;

    /** 거래처코드 */
    private String vendrCd;

    /** 초기재고 */
    private Integer startStockQty;

    /** 구분 */
    private String gubun;

    /** 가격관리구분 */
    private String prcCtrlFg;

    /** 상품등록구분 */
    private String regFg;

    /** 내점가 */
    private String stinSaleUprc;

    /** 배달가 */
    private String dlvrSaleUprc;

    /** 포장가 */
    private String packSaleUprc;

    /** 브랜드코드 */
    private String hqBrandCd;

    /** 비고 */
    private String remark;

    /** 사용자 아이디 */
    private String userId;

    /** 브랜드사용여부 */
    private String brandUseFg;

    /** 보증컵유형 */
    private String depositCupFg;
    /** 포인트사용여부 */
    private String pointUseYn;
    /** 할인여부 */
    private String dcYn;
    /** 부가세포함여부 */
    private String vatIncldYn;
    /** 코너코드 */
    private String cornrCd;


    /** 국민대 생협 */
    /** 상품약칭 */
    private String shPAlias;
    /** 식권구분 */
    private String shPTicketFg;
    /** 제조사 */
    private String shPMakerNm;
    /** 매입VAT */
    private String shPAcquireVat;
    /** 포인트적립율 */
    private String shPPointSaveRate;
    /** 규격 */
    private String shPSpec;
    /** 특정관리 */
    private String shPSpcManage;
    /** 단품코드 */
    private String shPSingleProdCd;
    /** 도서약칭 */
    private String shBAlias;
    /** 출판사 */
    private String shBPublishNm;
    /** 저자1 */
    private String shBAuthor1;
    /** 저자2 */
    private String shBAuthor2;
    /** 역자1 */
    private String shBTranslator1;
    /** 역자2 */
    private String shBTranslator2;
    /** 발행일 */
    private String shBPubDate;
    /** 할인율 */
    private String shBDiscRate;
    /** 매입VAT */
    private String shBAcquireVat;
    /** 포인트적립율 */
    private String shBPointSaveRate;
    /** 규격 */
    private String shBSpec;
    /** 특정관리 */
    private String shBSpcManage;
    /** ISBN */
    private String shBIsbnFg;
    private String shBIsbnCode;
    /** 상품등록주체 구분 */
    private String orgProdFg;

    public String getOrgnFg() { return orgnFg; }

    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

    public String getMembrOrgnCd() { return membrOrgnCd; }

    public void setMembrOrgnCd(String membrOrgnCd) { this.membrOrgnCd = membrOrgnCd; }

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String getProdCd() { return prodCd; }

    public void setProdCd(String prodCd) { this.prodCd = prodCd; }

    public String getProdNm() { return prodNm; }

    public void setProdNm(String prodNm) { this.prodNm = prodNm; }

    public String getProdClassCd() { return prodClassCd; }

    public void setProdClassCd(String prodClassCd) { this.prodClassCd = prodClassCd; }

    public String getBarCd() { return barCd; }

    public void setBarCd(String barCd) { this.barCd = barCd; }

    public String getResult() { return result; }

    public void setResult(String result) { this.result = result; }

    public Double getSplyUprc() { return splyUprc; }

    public void setSplyUprc(Double splyUprc) { this.splyUprc = splyUprc; }

    public Double getCostUprc() { return costUprc; }

    public void setCostUprc(Double costUprc) { this.costUprc = costUprc; }

    public String getSessionId() { return sessionId; }

    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public int getSeq() { return seq; }

    public void setSeq(int seq) { this.seq = seq; }

    public ProdNoEnvFg getProdNoEnv() {
        return prodNoEnv;
    }

    public void setProdNoEnv(ProdNoEnvFg prodNoEnv) {
        this.prodNoEnv = prodNoEnv;
    }

    public String getProdTypeFg() { return prodTypeFg; }

    public void setProdTypeFg(String prodTypeFg) {
        this.prodTypeFg = prodTypeFg;
    }

    public String getPoProdFg() {
        return poProdFg;
    }

    public void setPoProdFg(String poProdFg) {
        this.poProdFg = poProdFg;
    }

    public String getVatFg() { return vatFg; }

    public void setVatFg(String vatFg) {
        this.vatFg = vatFg;
    }

    public String getSaleUprc() { return saleUprc; }

    public void setSaleUprc(String saleUprc) {
        this.saleUprc = saleUprc;
    }

    public String getChkProdNm() { return chkProdNm; }

    public void setChkProdNm(String chkProdNm) {
        this.chkProdNm = chkProdNm;
    }

    public String getPointSaveYn() { return pointSaveYn; }

    public void setPointSaveYn(String pointSaveYn) {
        this.pointSaveYn = pointSaveYn;
    }

    public String getProdTipYn() { return prodTipYn; }

    public void setProdTipYn(String prodTipYn) {
        this.prodTipYn = prodTipYn;
    }

    public String getSaleProdYn() { return saleProdYn; }

    public void setSaleProdYn(String saleProdYn) {
        this.saleProdYn = saleProdYn;
    }

    public String getStockProdYn() { return stockProdYn; }

    public void setStockProdYn(String stockProdYn) {
        this.stockProdYn = stockProdYn;
    }

    public String getSideProdYn() { return sideProdYn; }

    public void setSideProdYn(String sideProdYn) {
        this.sideProdYn = sideProdYn;
    }

    public String getSetProdFg() { return setProdFg; }

    public void setSetProdFg(String setProdFg) {
        this.setProdFg = setProdFg;
    }

    public Double getLastCostUprc() { return lastCostUprc; }

    public void setLastCostUprc(Double lastCostUprc) {
        this.lastCostUprc = lastCostUprc;
    }

    public String getSplyUprcUseYn() { return splyUprcUseYn; }

    public void setSplyUprcUseYn(String splyUprcUseYn) {
        this.splyUprcUseYn = splyUprcUseYn;
    }

    public String getPoUnitFg() { return poUnitFg; }

    public void setPoUnitFg(String poUnitFg) { this.poUnitFg = poUnitFg; }

    public Integer getPoUnitQty() { return poUnitQty; }

    public void setPoUnitQty(Integer poUnitQty) { this.poUnitQty = poUnitQty; }

    public Integer getPoMinQty() { return poMinQty; }

    public void setPoMinQty(Integer poMinQty) { this.poMinQty = poMinQty; }

    public Integer getSafeStockQty() { return safeStockQty; }

    public void setSafeStockQty(Integer safeStockQty) { this.safeStockQty = safeStockQty; }

    public String getUseYn() { return useYn; }

    public void setUseYn(String useYn) { this.useYn = useYn; }

    public String getVendrCd() { return vendrCd; }

    public void setVendrCd(String vendrCd) { this.vendrCd = vendrCd; }

    public Integer getStartStockQty() { return startStockQty; }

    public void setStartStockQty(Integer startStockQty) { this.startStockQty = startStockQty; }

    public String getGubun() { return gubun; }

    public void setGubun(String gubun) { this.gubun = gubun; }

    public String getPrcCtrlFg() {
        return prcCtrlFg;
    }

    public void setPrcCtrlFg(String prcCtrlFg) { this.prcCtrlFg = prcCtrlFg; }

    public String getRegFg() {
        return regFg;
    }

    public void setRegFg(String regFg) {
        this.regFg = regFg;
    }

    public String getStinSaleUprc() {
        return stinSaleUprc;
    }

    public void setStinSaleUprc(String stinSaleUprc) {
        this.stinSaleUprc = stinSaleUprc;
    }

    public String getDlvrSaleUprc() {
        return dlvrSaleUprc;
    }

    public void setDlvrSaleUprc(String dlvrSaleUprc) {
        this.dlvrSaleUprc = dlvrSaleUprc;
    }

    public String getPackSaleUprc() {
        return packSaleUprc;
    }

    public void setPackSaleUprc(String packSaleUprc) {
        this.packSaleUprc = packSaleUprc;
    }

    public String getHqBrandCd() {
        return hqBrandCd;
    }

    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) { this.remark = remark; }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBrandUseFg() {
        return brandUseFg;
    }

    public void setBrandUseFg(String brandUseFg) {
        this.brandUseFg = brandUseFg;
    }

    public String getDepositCupFg() {
        return depositCupFg;
    }

    public void setDepositCupFg(String depositCupFg) {
        this.depositCupFg = depositCupFg;
    }

    public String getPointUseYn() {
        return pointUseYn;
    }

    public void setPointUseYn(String pointUseYn) {
        this.pointUseYn = pointUseYn;
    }

    public String getDcYn() {
        return dcYn;
    }

    public void setDcYn(String dcYn) {
        this.dcYn = dcYn;
    }

    public String getVatIncldYn() {
        return vatIncldYn;
    }

    public void setVatIncldYn(String vatIncldYn) {
        this.vatIncldYn = vatIncldYn;
    }

    public String getCornrCd() {
        return cornrCd;
    }

    public void setCornrCd(String cornrCd) {
        this.cornrCd = cornrCd;
    }

    public String getOrgProdFg() {
        return orgProdFg;
    }

    public void setOrgProdFg(String orgProdFg) {
        this.orgProdFg = orgProdFg;
    }

    public String getShPAlias() {
        return shPAlias;
    }

    public void setShPAlias(String shPAlias) {
        this.shPAlias = shPAlias;
    }

    public String getShPTicketFg() {
        return shPTicketFg;
    }

    public void setShPTicketFg(String shPTicketFg) {
        this.shPTicketFg = shPTicketFg;
    }

    public String getShPMakerNm() {
        return shPMakerNm;
    }

    public void setShPMakerNm(String shPMakerNm) {
        this.shPMakerNm = shPMakerNm;
    }

    public String getShPAcquireVat() {
        return shPAcquireVat;
    }

    public void setShPAcquireVat(String shPAcquireVat) {
        this.shPAcquireVat = shPAcquireVat;
    }

    public String getShPPointSaveRate() {
        return shPPointSaveRate;
    }

    public void setShPPointSaveRate(String shPPointSaveRate) {
        this.shPPointSaveRate = shPPointSaveRate;
    }

    public String getShPSpec() {
        return shPSpec;
    }

    public void setShPSpec(String shPSpec) {
        this.shPSpec = shPSpec;
    }

    public String getShPSpcManage() {
        return shPSpcManage;
    }

    public void setShPSpcManage(String shPSpcManage) {
        this.shPSpcManage = shPSpcManage;
    }

    public String getShPSingleProdCd() {
        return shPSingleProdCd;
    }

    public void setShPSingleProdCd(String shPSingleProdCd) {
        this.shPSingleProdCd = shPSingleProdCd;
    }

    public String getShBAlias() {
        return shBAlias;
    }

    public void setShBAlias(String shBAlias) {
        this.shBAlias = shBAlias;
    }

    public String getShBPublishNm() {
        return shBPublishNm;
    }

    public void setShBPublishNm(String shBPublishNm) {
        this.shBPublishNm = shBPublishNm;
    }

    public String getShBAuthor1() {
        return shBAuthor1;
    }

    public void setShBAuthor1(String shBAuthor1) {
        this.shBAuthor1 = shBAuthor1;
    }

    public String getShBAuthor2() {
        return shBAuthor2;
    }

    public void setShBAuthor2(String shBAuthor2) {
        this.shBAuthor2 = shBAuthor2;
    }

    public String getShBTranslator1() {
        return shBTranslator1;
    }

    public void setShBTranslator1(String shBTranslator1) {
        this.shBTranslator1 = shBTranslator1;
    }

    public String getShBTranslator2() {
        return shBTranslator2;
    }

    public void setShBTranslator2(String shBTranslator2) {
        this.shBTranslator2 = shBTranslator2;
    }

    public String getShBPubDate() {
        return shBPubDate;
    }

    public void setShBPubDate(String shBPubDate) {
        this.shBPubDate = shBPubDate;
    }

    public String getShBDiscRate() {
        return shBDiscRate;
    }

    public void setShBDiscRate(String shBDiscRate) {
        this.shBDiscRate = shBDiscRate;
    }

    public String getShBAcquireVat() {
        return shBAcquireVat;
    }

    public void setShBAcquireVat(String shBAcquireVat) {
        this.shBAcquireVat = shBAcquireVat;
    }

    public String getShBPointSaveRate() {
        return shBPointSaveRate;
    }

    public void setShBPointSaveRate(String shBPointSaveRate) {
        this.shBPointSaveRate = shBPointSaveRate;
    }

    public String getShBSpec() {
        return shBSpec;
    }

    public void setShBSpec(String shBSpec) {
        this.shBSpec = shBSpec;
    }

    public String getShBSpcManage() {
        return shBSpcManage;
    }

    public void setShBSpcManage(String shBSpcManage) {
        this.shBSpcManage = shBSpcManage;
    }

    public String getShBIsbnFg() {
        return shBIsbnFg;
    }

    public void setShBIsbnFg(String shBIsbnFg) {
        this.shBIsbnFg = shBIsbnFg;
    }

    public String getShBIsbnCode() {
        return shBIsbnCode;
    }

    public void setShBIsbnCode(String shBIsbnCode) {
        this.shBIsbnCode = shBIsbnCode;
    }
}