package kr.co.solbipos.base.prod.prodPrintYn.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : ProdPrintYnVO.java
 * @Description : 기초관리 > 상품관리2 > 출력여부관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.06.28  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.06.28
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ProdPrintYnVO extends PageVO {

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

    /** 매장코드 */
    private String storeCd;

    /** 옵션그룹코드 */
    private String optionGrpCd;

    /** 옵션그룹명 */
    private String optionGrpNm;

    /** 그룹별옵션코드 */
    private String optionValCd;

    /** 그룹별옵션명 */
    private String optionValNm;

    /**  출력여부 */
    private String printYn;

    /** 사이드선택그룹코드 */
    private String sdselGrpCd;

    /** 사이드선택그룹명 */
    private String sdselGrpNm;

    /** 사이드선택분류코드 */
    private String sdselClassCd;

    /** 사이드선택분류명 */
    private String sdselClassNm;

    /** 사이드선택상품코드 */
    private String sdselProdCd;

    /** 사이드선택상품명 */
    private String sdselProdNm;

    /** 고정상품구분 */
    private String fixProdFg;

    public String getOrgnFg() { return orgnFg; }

    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) { this.storeCd = storeCd; }

    public String getOptionGrpCd() {
        return optionGrpCd;
    }

    public void setOptionGrpCd(String optionGrpCd) {
        this.optionGrpCd = optionGrpCd;
    }

    public String getOptionGrpNm() {
        return optionGrpNm;
    }

    public void setOptionGrpNm(String optionGrpNm) {
        this.optionGrpNm = optionGrpNm;
    }

    public String getOptionValCd() {
        return optionValCd;
    }

    public void setOptionValCd(String optionValCd) {
        this.optionValCd = optionValCd;
    }

    public String getOptionValNm() {
        return optionValNm;
    }

    public void setOptionValNm(String optionValNm) {
        this.optionValNm = optionValNm;
    }

    public String getPrintYn() { return printYn; }

    public void setPrintYn(String printYn) { this.printYn = printYn; }

    public String getSdselGrpCd() {
        return sdselGrpCd;
    }

    public void setSdselGrpCd(String sdselGrpCd) {
        this.sdselGrpCd = sdselGrpCd;
    }

    public String getSdselGrpNm() {
        return sdselGrpNm;
    }

    public void setSdselGrpNm(String sdselGrpNm) {
        this.sdselGrpNm = sdselGrpNm;
    }

    public String getSdselClassCd() {
        return sdselClassCd;
    }

    public void setSdselClassCd(String sdselClassCd) {
        this.sdselClassCd = sdselClassCd;
    }

    public String getSdselClassNm() {
        return sdselClassNm;
    }

    public void setSdselClassNm(String sdselClassNm) {
        this.sdselClassNm = sdselClassNm;
    }

    public String getSdselProdCd() {
        return sdselProdCd;
    }

    public void setSdselProdCd(String sdselProdCd) {
        this.sdselProdCd = sdselProdCd;
    }

    public String getSdselProdNm() {
        return sdselProdNm;
    }

    public void setSdselProdNm(String sdselProdNm) {
        this.sdselProdNm = sdselProdNm;
    }

    public String getFixProdFg() {
        return fixProdFg;
    }

    public void setFixProdFg(String fixProdFg) {
        this.fixProdFg = fixProdFg;
    }
}