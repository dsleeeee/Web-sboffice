package kr.co.solbipos.base.prod.prodBatchChange.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : ProdBatchChangeVO.java
 * @Description : 기초관리 > 상품관리 > 상품정보일괄변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.04.28  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 20201.04.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ProdBatchChangeVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /**
     * 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** 본사코드 */
    private String hqOfficeCd;

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

    /** 판매상품여부 */
    private String saleProdYn;

    /** 판매상품여부 */
    private String saleProdYnChg;

    /** 포인트적립여부 */
    private String pointSaveYn;

    /** 포인트적립여부 */
    private String pointSaveYnChg;

    /** 가격관리구분 */
    private String prcCtrlFg;

    /** 가격관리구분 */
    private String prcCtrlFgChg;

    /** 상품등록구분 */
    private String regFg;

    /** 매핑상품코드 */
    private String mapProdCd;

    /** 매핑상품코드 */
    private String mapProdCdChg;

    /** 브랜드코드 */
    private String hqBrandCd;

    public String getOrgnFg() { return orgnFg; }

    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

    public String getHqOfficeCd() { return hqOfficeCd; }

    public void setHqOfficeCd(String hqOfficeCd) { this.hqOfficeCd = hqOfficeCd; }

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

    public String getSaleProdYn() { return saleProdYn; }

    public void setSaleProdYn(String saleProdYn) { this.saleProdYn = saleProdYn; }

    public String getSaleProdYnChg() { return saleProdYnChg; }

    public void setSaleProdYnChg(String saleProdYnChg) { this.saleProdYnChg = saleProdYnChg; }

    public String getPointSaveYn() { return pointSaveYn; }

    public void setPointSaveYn(String pointSaveYn) { this.pointSaveYn = pointSaveYn; }

    public String getPointSaveYnChg() { return pointSaveYnChg; }

    public void setPointSaveYnChg(String pointSaveYnChg) { this.pointSaveYnChg = pointSaveYnChg; }

    public String getPrcCtrlFg() { return prcCtrlFg; }

    public void setPrcCtrlFg(String prcCtrlFg) { this.prcCtrlFg = prcCtrlFg; }

    public String getPrcCtrlFgChg() { return prcCtrlFgChg; }

    public void setPrcCtrlFgChg(String prcCtrlFgChg) { this.prcCtrlFgChg = prcCtrlFgChg; }

    public String getRegFg() {
        return regFg;
    }

    public void setRegFg(String regFg) {
        this.regFg = regFg;
    }

    public String getMapProdCd() {
        return mapProdCd;
    }

    public void setMapProdCd(String mapProdCd) {
        this.mapProdCd = mapProdCd;
    }

    public String getMapProdCdChg() {
        return mapProdCdChg;
    }

    public void setMapProdCdChg(String mapProdCdChg) {
        this.mapProdCdChg = mapProdCdChg;
    }

    public String getHqBrandCd() {
        return hqBrandCd;
    }

    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
    }
}