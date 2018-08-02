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
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class TemplateVO extends CmmVO {
    
    private static final long serialVersionUID = -4652244117741649825L;
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
    
}
