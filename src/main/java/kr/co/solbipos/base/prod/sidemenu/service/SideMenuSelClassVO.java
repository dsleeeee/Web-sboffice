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

    /** 변경전 적용매장구분 */
    private String oldRegStoreFg;

    /** 매장명 */
    private String storeNm;

    /** 팀별 */
    private String momsTeam;

    /** AC점포별 */
    private String momsAcShop;

    /** 지역구분 */
    private String momsAreaFg;

    /** 상권 */
    private String momsCommercial;

    /** 점포유형 */
    private String momsShopType;

    /** 매장관리타입 */
    private String momsStoreManageType;

    /** 그룹코드 */
    private String branchCd;

    /** 매장그룹 */
    private String momsStoreFg01;

    /** 매장그룹2 */
    private String momsStoreFg02;

    /** 매장그룹3 */
    private String momsStoreFg03;

    /** 매장그룹4 */
    private String momsStoreFg04;

    /** 매장그룹5 */
    private String momsStoreFg05;

    /** 상단표기여부 */
    private String topYn;

    /** 펼치기여부 */
    private String expandYn;

    /** ERP상품맵핑여부 */
    private String mappingYn;

    /** 선택팝업 */
    private String popUpClassYn;

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

    public String getOldRegStoreFg() { return oldRegStoreFg; }

    public void setOldRegStoreFg(String oldRegStoreFg) { this.oldRegStoreFg = oldRegStoreFg; }

    public String getStoreNm() {
        return storeNm;
    }

    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    public String getMomsTeam() {
        return momsTeam;
    }

    public void setMomsTeam(String momsTeam) {
        this.momsTeam = momsTeam;
    }

    public String getMomsAcShop() {
        return momsAcShop;
    }

    public void setMomsAcShop(String momsAcShop) {
        this.momsAcShop = momsAcShop;
    }

    public String getMomsAreaFg() {
        return momsAreaFg;
    }

    public void setMomsAreaFg(String momsAreaFg) {
        this.momsAreaFg = momsAreaFg;
    }

    public String getMomsCommercial() {
        return momsCommercial;
    }

    public void setMomsCommercial(String momsCommercial) {
        this.momsCommercial = momsCommercial;
    }

    public String getMomsShopType() {
        return momsShopType;
    }

    public void setMomsShopType(String momsShopType) {
        this.momsShopType = momsShopType;
    }

    public String getMomsStoreManageType() {
        return momsStoreManageType;
    }

    public void setMomsStoreManageType(String momsStoreManageType) {
        this.momsStoreManageType = momsStoreManageType;
    }

    public String getBranchCd() {
        return branchCd;
    }

    public void setBranchCd(String branchCd) {
        this.branchCd = branchCd;
    }

    public String getMomsStoreFg01() { return momsStoreFg01; }

    public void setMomsStoreFg01(String momsStoreFg01) { this.momsStoreFg01 = momsStoreFg01; }

    public String getMomsStoreFg02() { return momsStoreFg02; }

    public void setMomsStoreFg02(String momsStoreFg02) { this.momsStoreFg02 = momsStoreFg02; }

    public String getMomsStoreFg03() { return momsStoreFg03; }

    public void setMomsStoreFg03(String momsStoreFg03) { this.momsStoreFg03 = momsStoreFg03; }

    public String getMomsStoreFg04() { return momsStoreFg04; }

    public void setMomsStoreFg04(String momsStoreFg04) { this.momsStoreFg04 = momsStoreFg04; }

    public String getMomsStoreFg05() { return momsStoreFg05; }

    public void setMomsStoreFg05(String momsStoreFg05) { this.momsStoreFg05 = momsStoreFg05; }

    public String getTopYn() { return topYn; }

    public void setTopYn(String topYn) { this.topYn = topYn; }

    public String getExpandYn() { return expandYn; }

    public void setExpandYn(String expandYn) { this.expandYn = expandYn; }

    public String getMappingYn() { return mappingYn; }

    public void setMappingYn(String mappingYn) { this.mappingYn = mappingYn; }

    public String getPopUpClassYn() {
        return popUpClassYn;
    }

    public void setPopUpClassYn(String popUpClassYn) {
        this.popUpClassYn = popUpClassYn;
    }
}