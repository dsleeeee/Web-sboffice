package kr.co.solbipos.sys.bill.template.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : TemplateVO.java
 * @Description : 시스템관리 > 포스출력물관리 > 출력물 샘플
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class TemplateVO extends CmmVO {

    private static final long serialVersionUID = -4022181779669462240L;

    /** 출력물분류코드 */
    private String prtClassCd;
    /** 템플릿코드 */
    private String templtCd;
    /** 템플릿명 */
    private String templtNm;
    /** 출력물폼 */
    private String prtForm;
    /** 출력물분류코드명 */
    private String prtClassNm;
    /** 출력물코드 */
    private String prtCd;
    /** 출력물코드 예제 */
    private String content;

    /** 본사/단독매장 구분 */
    private String storeFg;
    /** 본사/매장코드 : 여기서는 본사/매장코드 같이 사용 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 매장 상태 */
    private String sysStatFg;
    /** 매장 상태명 */
    private String sysStatFgNm;
    /** 매장 용도 */
    private String clsFg;
    /** 매장 용도명 */
    private String clsFgNm;
    /** 출력물 관련 프로시져 실행 결과 */
    private String result;
    /** 출력물폼(영문) */
    private String prtEnForm;
    /** 출력물폼(중문) */
    private String prtCnForm;
    /** 출력물폼(일문 */
    private String prtJpForm;
    /** 언어타입 */
    private String langType;


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

    /**
     * @return the storeFg
     */
    public String getStoreFg() {
        return storeFg;
    }

    /**
     * @param storeFg the storeFg to set
     */
    public void setStoreFg(String storeFg) {
        this.storeFg = storeFg;
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
     * @return the storeNm
     */
    public String getStoreNm() {
        return storeNm;
    }

    /**
     * @param storeNm the storeNm to set
     */
    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    /**
     * @return the sysStatFg
     */
    public String getSysStatFg() {
        return sysStatFg;
    }

    /**
     * @param sysStatFg the sysStatFg to set
     */
    public void setSysStatFg(String sysStatFg) {
        this.sysStatFg = sysStatFg;
    }

    /**
     * @return the sysStatFgNm
     */
    public String getSysStatFgNm() {
        return sysStatFgNm;
    }

    /**
     * @param sysStatFgNm the sysStatFgNm to set
     */
    public void setSysStatFgNm(String sysStatFgNm) {
        this.sysStatFgNm = sysStatFgNm;
    }

    /**
     * @return the clsFg
     */
    public String getClsFg() {
        return clsFg;
    }

    /**
     * @param clsFg the clsFg to set
     */
    public void setClsFg(String clsFg) {
        this.clsFg = clsFg;
    }

    /**
     * @return the clsFgNm
     */
    public String getClsFgNm() {
        return clsFgNm;
    }

    /**
     * @param clsFgNm the clsFgNm to set
     */
    public void setClsFgNm(String clsFgNm) {
        this.clsFgNm = clsFgNm;
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
