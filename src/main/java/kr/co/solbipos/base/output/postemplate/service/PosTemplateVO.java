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
}
