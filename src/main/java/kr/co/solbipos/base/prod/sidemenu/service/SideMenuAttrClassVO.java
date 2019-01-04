package kr.co.solbipos.base.prod.sidemenu.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : SideMenuAttrClassVO.java
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
public class SideMenuAttrClassVO extends CmmVO {

    private static final long serialVersionUID = 7116734554143813867L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 본사브랜드코드 */
    private String hqBrandCd;
    /** 매장브랜드코드 */
    private String msBrandCd;
    /** 사이드속성분류코드 */
    private String sdattrClassCd;
    /** 사이드속성분류명 */
    private String sdattrClassNm;
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
     * @return the hqBrandCd
     */
    public String getHqBrandCd() {
        return hqBrandCd;
    }

    /**
     * @param hqBrandCd the hqBrandCd to set
     */
    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
    }

    /**
     * @return the msBrandCd
     */
    public String getMsBrandCd() {
        return msBrandCd;
    }

    /**
     * @param msBrandCd the msBrandCd to set
     */
    public void setMsBrandCd(String msBrandCd) {
        this.msBrandCd = msBrandCd;
    }

    /**
     * @return the sdattrClassCd
     */
    public String getSdattrClassCd() {
        return sdattrClassCd;
    }

    /**
     * @param sdattrClassCd the sdattrClassCd to set
     */
    public void setSdattrClassCd(String sdattrClassCd) {
        this.sdattrClassCd = sdattrClassCd;
    }

    /**
     * @return the sdattrClassNm
     */
    public String getSdattrClassNm() {
        return sdattrClassNm;
    }

    /**
     * @param sdattrClassNm the sdattrClassNm to set
     */
    public void setSdattrClassNm(String sdattrClassNm) {
        this.sdattrClassNm = sdattrClassNm;
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
}
