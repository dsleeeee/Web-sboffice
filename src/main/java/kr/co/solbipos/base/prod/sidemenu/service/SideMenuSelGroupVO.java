package kr.co.solbipos.base.prod.sidemenu.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : SideMenuSelGroupVO.java
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
public class SideMenuSelGroupVO extends CmmVO {

    private static final long serialVersionUID = 1793262388267633994L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 본사브랜드코드 */
    private String hqBrandCd;
    /** 매장브랜드코드 */
    private String msBrandCd;
    /** 사이드선택그룹코드 */
    private String sdselGrpCd;
    /** 사이드선택그룹명 */
    private String sdselGrpNm;
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

    /** 고정여부 */
    private String fixProdFg;

    /** 선택그룹사용정보 */
    private String sdselTypeFg;

    /** 사이드선택상품코드*/
    private String sdselProdCd;

    /** 사이드선택상품명*/
    private String sdselProdNm;

    /** 사용자 아이디 */
    private String userId;

    /** 하프앤하프 */
    private String halfAndHalfYn;

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
     * @return the sdselGrpNm
     */
    public String getSdselGrpNm() {
        return sdselGrpNm;
    }

    /**
     * @param sdselGrpNm the sdselGrpNm to set
     */
    public void setSdselGrpNm(String sdselGrpNm) {
        this.sdselGrpNm = sdselGrpNm;
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

    public String getFixProdFg() { return fixProdFg; }

    public void setFixProdFg(String fixProdFg) { this.fixProdFg = fixProdFg; }

    public String getSdselTypeFg() { return sdselTypeFg; }

    public void setSdselTypeFg(String sdselTypeFg) { this.sdselTypeFg = sdselTypeFg; }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHalfAndHalfYn() {
        return halfAndHalfYn;
    }

    public void setHalfAndHalfYn(String halfAndHalfYn) {
        this.halfAndHalfYn = halfAndHalfYn;
    }
}