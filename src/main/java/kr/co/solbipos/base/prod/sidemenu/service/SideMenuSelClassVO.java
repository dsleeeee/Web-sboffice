package kr.co.solbipos.base.prod.sidemenu.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : SideMenuSelClassVO.java
 * @Description : 기초관리 > 상품관리 > 사이드메뉴
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.14  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @See
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SideMenuSelClassVO extends CmmVO {

    private static final long serialVersionUID = 5178287084932475311L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 사이드선택그룹코드 */
    private String sdselGrpCd;
    /** 사이드선택분류코드 */
    private String sdselClassCd;
    /** 사이드선택분류명 */
    private String sdselClassNm;
    /** 사이드선택수량 */
    private Integer sdselQty;
    /** 표기순번 */
    private Integer dispSeq;
    /** 사용여부 */
    private String useYn;

    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** 프로시져 결과 */
    private String result;

    /** 필수선택여부 */
    private String requireYn;

    /** 복사할 사이드선택그룹코드 */
    private String copySdselGrpCd;

    /** 복사할 사이드 선택분류코드 */
    private String copySdselClassCd;

    /** 적용할 사이드 선택그룹코드 */
    private String applySdselGrpCd;

    /** 적용할 사이드 선택분류코드 */
    private String applySdselClassCd;

    /** 적용할 사이드 표시순서 */
    private String applyDispSeq;

    /** 적용매장구분 */
    private String regStoreFg;

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
     * @return the sdselClassCd
     */
    public String getSdselClassCd() {
        return sdselClassCd;
    }

    /**
     * @param sdselClassCd the sdselClassCd to set
     */
    public void setSdselClassCd(String sdselClassCd) {
        this.sdselClassCd = sdselClassCd;
    }

    /**
     * @return the sdselClassNm
     */
    public String getSdselClassNm() {
        return sdselClassNm;
    }

    /**
     * @param sdselClassNm the sdselClassNm to set
     */
    public void setSdselClassNm(String sdselClassNm) {
        this.sdselClassNm = sdselClassNm;
    }

    /**
     * @return the sdselQty
     */
    public Integer getSdselQty() {
        return sdselQty;
    }

    /**
     * @param sdselQty the sdselQty to set
     */
    public void setSdselQty(Integer sdselQty) {
        this.sdselQty = sdselQty;
    }

    /**
     * @return the dispSeq
     */
    public Integer getDispSeq() {
        return dispSeq;
    }

    /**
     * @param dispSeq the dispSeq to set
     */
    public void setDispSeq(Integer dispSeq) {
        this.dispSeq = dispSeq;
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
     * @return the orgnFg
     */
    public String getOrgnFg() {
        return orgnFg;
    }

    /**
     * @param orgnFg the orgnFg to set
     */
    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    /**
     * @return the result
     */

    public String getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }

    public String getRequireYn() { return requireYn; }

    public void setRequireYn(String requireYn) { this.requireYn = requireYn; }


    public String getCopySdselGrpCd() { return copySdselGrpCd; }

    public void setCopySdselGrpCd(String copySdselGrpCd) { this.copySdselGrpCd = copySdselGrpCd; }

    public String getCopySdselClassCd() { return copySdselClassCd; }

    public void setCopySdselClassCd(String copySdselClassCd) { this.copySdselClassCd = copySdselClassCd; }

    public String getApplySdselGrpCd() { return applySdselGrpCd; }

    public void setApplySdselGrpCd(String applySdselGrpCd) { this.applySdselGrpCd = applySdselGrpCd; }

    public String getApplySdselClassCd() { return applySdselClassCd; }

    public void setApplySdselClassCd(String applySdselClassCd) { this.applySdselClassCd = applySdselClassCd; }

    public String getApplyDispSeq() { return applyDispSeq; }

    public void setApplyDispSeq(String applyDispSeq) { this.applyDispSeq = applyDispSeq; }

    public String getRegStoreFg() { return regStoreFg; }

    public void setRegStoreFg(String regStoreFg) { this.regStoreFg = regStoreFg; }
}