package kr.co.solbipos.base.output.postemplate.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : PosTemplateVO.java
 * @Description : 기초관리 > 출력물관리 > 포스출력물관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.10.04  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class PosTemplateVO extends CmmVO {

    private static final long serialVersionUID = -5313540332914369004L;

    /** 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점 */
    private String orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 출력물분류코드 */
    private String prtClassCd;
    /** 등록구분 */
    private String templtRegFg;
    /** 템플릿코드 */
    private String templtCd;
    /** 템플릿명 */
    private String templtNm;
    /** 출력물폼 */
    private String prtForm;
    /** 적용등록구분 : 실제출력물에서 저장처리 위해 */
    private String applyTempltRegFg;
    /** 적용템플릿 : 실제출력물에서 저장처리 위해 */
    private String applyTempltCd;
    /** 출력물분류코드명 */
    private String prtClassNm;
    /** 출력물코드 */
    private String prtCd;
    /** 출력물코드 예제 */
    private String content;
    /** 매장상태 */
    private String sysStatFg;

    /** 출력물 관련 프로시져 실행 결과 */
    private String result;

    /** 환경설정 [1250 맘스터치] */
    private String momsEnvstVal;

    /** 사용자별 브랜드코드(매장) */
    private String[] userBrandList;

    /** 사용자별 브랜드코드 */
    private String userBrands;

    /** 매장브랜드코드 */
    private String storeHqBrandCd;

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

    /** 출력물폼(영문) */
    private String prtEnForm;

    /** 출력물폼(중문) */
    private String prtCnForm;

    /** 출력물폼(일문 */
    private String prtJpForm;

    /** 언어타입 */
    private String langType;

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

    public String getStoreNm() {
        return storeNm;
    }

    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    /**
     * @return the prtClassCd
     */
    public String getPrtClassCd() {
        return prtClassCd;
    }

    /**
     * @param prtClassCd the prtClassCd to set
     */
    public void setPrtClassCd(String prtClassCd) {
        this.prtClassCd = prtClassCd;
    }

    /**
     * @return the templtRegFg
     */
    public String getTempltRegFg() {
        return templtRegFg;
    }

    /**
     * @param templtRegFg the templtRegFg to set
     */
    public void setTempltRegFg(String templtRegFg) {
        this.templtRegFg = templtRegFg;
    }

    /**
     * @return the templtCd
     */
    public String getTempltCd() {
        return templtCd;
    }

    /**
     * @param templtCd the templtCd to set
     */
    public void setTempltCd(String templtCd) {
        this.templtCd = templtCd;
    }

    /**
     * @return the templtNm
     */
    public String getTempltNm() {
        return templtNm;
    }

    /**
     * @param templtNm the templtNm to set
     */
    public void setTempltNm(String templtNm) {
        this.templtNm = templtNm;
    }

    /**
     * @return the prtForm
     */
    public String getPrtForm() {
        return prtForm;
    }

    /**
     * @param prtForm the prtForm to set
     */
    public void setPrtForm(String prtForm) {
        this.prtForm = prtForm;
    }

    /**
     * @return the applyTempltRegFg
     */
    public String getApplyTempltRegFg() {
        return applyTempltRegFg;
    }

    /**
     * @param applyTempltRegFg the applyTempltRegFg to set
     */
    public void setApplyTempltRegFg(String applyTempltRegFg) {
        this.applyTempltRegFg = applyTempltRegFg;
    }

    /**
     * @return the applyTempltCd
     */
    public String getApplyTempltCd() {
        return applyTempltCd;
    }

    /**
     * @param applyTempltCd the applyTempltCd to set
     */
    public void setApplyTempltCd(String applyTempltCd) {
        this.applyTempltCd = applyTempltCd;
    }

    /**
     * @return the prtClassNm
     */
    public String getPrtClassNm() {
        return prtClassNm;
    }

    /**
     * @param prtClassNm the prtClassNm to set
     */
    public void setPrtClassNm(String prtClassNm) {
        this.prtClassNm = prtClassNm;
    }

    /**
     * @return the prtCd
     */
    public String getPrtCd() {
        return prtCd;
    }

    /**
     * @param prtCd the prtCd to set
     */
    public void setPrtCd(String prtCd) {
        this.prtCd = prtCd;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    public String getSysStatFg() {
        return sysStatFg;
    }

    public void setSysStatFg(String sysStatFg) {
        this.sysStatFg = sysStatFg;
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

    public String getMomsEnvstVal() {
        return momsEnvstVal;
    }

    public void setMomsEnvstVal(String momsEnvstVal) {
        this.momsEnvstVal = momsEnvstVal;
    }

    public String[] getUserBrandList() {
        return userBrandList;
    }

    public void setUserBrandList(String[] userBrandList) {
        this.userBrandList = userBrandList;
    }

    public String getUserBrands() {
        return userBrands;
    }

    public void setUserBrands(String userBrands) {
        this.userBrands = userBrands;
    }

    public String getStoreHqBrandCd() {
        return storeHqBrandCd;
    }

    public void setStoreHqBrandCd(String storeHqBrandCd) {
        this.storeHqBrandCd = storeHqBrandCd;
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

    public String getPrtEnForm() {
        return prtEnForm;
    }

    public void setPrtEnForm(String prtEnForm) {
        this.prtEnForm = prtEnForm;
    }

    public String getPrtCnForm() {
        return prtCnForm;
    }

    public void setPrtCnForm(String prtCnForm) {
        this.prtCnForm = prtCnForm;
    }

    public String getPrtJpForm() {
        return prtJpForm;
    }

    public void setPrtJpForm(String prtJpForm) {
        this.prtJpForm = prtJpForm;
    }

    public String getLangType() {
        return langType;
    }

    public void setLangType(String langType) {
        this.langType = langType;
    }
}
