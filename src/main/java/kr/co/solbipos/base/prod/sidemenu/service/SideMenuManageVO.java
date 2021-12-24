package kr.co.solbipos.base.prod.sidemenu.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : SideMenuManageVO.java
 * @Description : 기초관리 > 상품관리 > 사이드메뉴 > 사이드메뉴관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.12.21  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2021.12.21
 * @version 1.0
 * @See
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SideMenuManageVO extends PageVO {

    private static final long serialVersionUID = -1202995321466380758L;

    /** 소속구분 */
    private String orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 상품분류코드 */
    private String prodClassCd;
    /** 브랜드코드 */
    private String hqBrandCd;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 사이드상품여부 */
    private String sideProdYn;
    /** 사이드속성분류코드 */
    private String sdattrClassCd;
    /** 사이드선택그룹코드 */
    private String sdselGrpCd;

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

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

    public String getProdClassCd() {
        return prodClassCd;
    }

    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    public String getHqBrandCd() {
        return hqBrandCd;
    }

    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
    }

    public String getProdCd() {
        return prodCd;
    }

    public void setProdCd(String prodCd) {
        this.prodCd = prodCd;
    }

    public String getProdNm() {
        return prodNm;
    }

    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    public String getSideProdYn() {
        return sideProdYn;
    }

    public void setSideProdYn(String sideProdYn) {
        this.sideProdYn = sideProdYn;
    }

    public String getSdattrClassCd() {
        return sdattrClassCd;
    }

    public void setSdattrClassCd(String sdattrClassCd) {
        this.sdattrClassCd = sdattrClassCd;
    }

    public String getSdselGrpCd() {
        return sdselGrpCd;
    }

    public void setSdselGrpCd(String sdselGrpCd) {
        this.sdselGrpCd = sdselGrpCd;
    }
}
