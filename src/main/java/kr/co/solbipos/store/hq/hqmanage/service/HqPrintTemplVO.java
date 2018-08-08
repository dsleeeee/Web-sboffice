package kr.co.solbipos.store.hq.hqmanage.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : HqBrandVO.java
 * @Description : 가맹점관리 > 본사정보 > 본사정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 06.08
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class HqPrintTemplVO extends CmmVO {

    private static final long serialVersionUID = -9209204862785671429L;
    /** 본사브랜드코드 */
    private String hqOfficeCd;
    /** 출력물분류코드 */
    private String prtClassCd;
    /** 템플릿코드 */
    private String templtCd;
    /** 템플릿명 */
    private String templtNm;
    /** 출력물폼 */
    private String prtForm;


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

}
