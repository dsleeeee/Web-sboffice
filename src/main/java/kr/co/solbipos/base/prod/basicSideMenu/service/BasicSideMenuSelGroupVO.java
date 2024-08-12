package kr.co.solbipos.base.prod.basicSideMenu.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : BasicSideMenuSelGroupVO.java
 * @Description : 기초관리 > 상품관리 > (기준)사이드메뉴 선택그룹
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.08.07  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2024.08.07
 * @version 1.0
 * @See
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class BasicSideMenuSelGroupVO extends PageVO {

    private static final long serialVersionUID = 7283473015795330736L;

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

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getHqBrandCd() {
        return hqBrandCd;
    }

    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
    }

    public String getMsBrandCd() {
        return msBrandCd;
    }

    public void setMsBrandCd(String msBrandCd) {
        this.msBrandCd = msBrandCd;
    }

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

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFixProdFg() {
        return fixProdFg;
    }

    public void setFixProdFg(String fixProdFg) {
        this.fixProdFg = fixProdFg;
    }

    public String getSdselTypeFg() {
        return sdselTypeFg;
    }

    public void setSdselTypeFg(String sdselTypeFg) {
        this.sdselTypeFg = sdselTypeFg;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
