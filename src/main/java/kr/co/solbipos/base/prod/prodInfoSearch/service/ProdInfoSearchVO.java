package kr.co.solbipos.base.prod.prodInfoSearch.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : ProdInfoSearchVO.java
 * @Description : 기초관리 > 상품관리 > 상품구성정보조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.23  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.12.23
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ProdInfoSearchVO extends PageVO {

    /**
     * 소속구분<br>
     * M : 시스템<br>
     * A : 대리점<br>
     * H : 본사<br>
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 매장코드 */
    private String storeCd;

    /** 상품분류 */
    private String prodClassCd;

    /** 상품코드 */
    private String prodCd;

    /** 상품명 */
    private String prodNm;

    /** 상품브랜드코드 */
    private String prodHqBrandCd;

    /** 상품코드 */
    private String[] prodCdList;

    /** 상품코드 */
    private String prodCds;

    /** 사용자별 브랜드코드 */
    private String userBrands;

    /** 사용자별 브랜드코드 */
    private String[] userBrandList;

    /** 터치키그룹코드 */
    private String tukeyGrpCd;

    /** 키오스크 포스번호 */
    private String posNo;

    /** 키오스크 카테고리그룹 */
    private String tuClsType;

    /** 그룹코드 */
    private String sdselGrpCd;

    /** 그룹명 */
    private String sdselGrpNm;

    /** 분류코드 */
    private String sdselClassCd;

    /** 분류명 */
    private String sdselClassNm;
    
    /** 사이드 상품코드 */
    private String sdselProdCd;

    /** 사이드 상품명 */
    private String sdselProdNm;

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

    public String getProdHqBrandCd() {
        return prodHqBrandCd;
    }

    public void setProdHqBrandCd(String prodHqBrandCd) {
        this.prodHqBrandCd = prodHqBrandCd;
    }

    public String[] getProdCdList() {
        return prodCdList;
    }

    public void setProdCdList(String[] prodCdList) {
        this.prodCdList = prodCdList;
    }

    public String getProdCds() {
        return prodCds;
    }

    public void setProdCds(String prodCds) {
        this.prodCds = prodCds;
    }

    public String getUserBrands() {
        return userBrands;
    }

    public void setUserBrands(String userBrands) {
        this.userBrands = userBrands;
    }

    public String[] getUserBrandList() {
        return userBrandList;
    }

    public void setUserBrandList(String[] userBrandList) {
        this.userBrandList = userBrandList;
    }

    public String getTukeyGrpCd() {
        return tukeyGrpCd;
    }

    public void setTukeyGrpCd(String tukeyGrpCd) {
        this.tukeyGrpCd = tukeyGrpCd;
    }

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    public String getTuClsType() {
        return tuClsType;
    }

    public void setTuClsType(String tuClsType) {
        this.tuClsType = tuClsType;
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

    public String getSdselClassCd() {
        return sdselClassCd;
    }

    public void setSdselClassCd(String sdselClassCd) {
        this.sdselClassCd = sdselClassCd;
    }

    public String getSdselClassNm() {
        return sdselClassNm;
    }

    public void setSdselClassNm(String sdselClassNm) {
        this.sdselClassNm = sdselClassNm;
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
}