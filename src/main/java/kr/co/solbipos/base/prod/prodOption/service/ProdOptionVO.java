package kr.co.solbipos.base.prod.prodOption.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.pos.loginstatus.enums.SysStatFg;

/**
 * @Class Name : ProdOptionVO.java
 * @Description : 기초관리 > 상품관리 > 옵션관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.19  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.12.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ProdOptionVO extends PageVO {

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

    /** 옵션그룹코드 */
    private String optionGrpCd;

    /** 옵션그룹명 */
    private String optionGrpNm;

    /** 사용여부 */
    private String useYn;

    /** 그룹별옵션코드 */
    private String optionValCd;

    /** 그룹별옵션명 */
    private String optionValNm;

    /** 등록구분 */
    private String regFg;

    /** 맘스터치전용-옵션대응상품코드 */
    private String optProdCd;

    /** 사용자별 브랜드코드(상품) */
    private String[] userProdBrandList;

    /** 사용자별 브랜드코드(상품) */
    private String userProdBrands;

    /** 상품브랜드코드 */
    private String prodHqBrandCd;

    /** 상품코드 */
    private String prodCd;

    /** 상품명 */
    private String prodNm;

    /** 상품분류코드 */
    private String prodClassCd;

    /** 사용자 아이디 */
    private String userId;

    /** 매장상품제한구분 여부 */
    private String storeProdUseFg;


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

    public String getOptionGrpCd() {
        return optionGrpCd;
    }

    public void setOptionGrpCd(String optionGrpCd) {
        this.optionGrpCd = optionGrpCd;
    }

    public String getOptionGrpNm() {
        return optionGrpNm;
    }

    public void setOptionGrpNm(String optionGrpNm) {
        this.optionGrpNm = optionGrpNm;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getOptionValCd() {
        return optionValCd;
    }

    public void setOptionValCd(String optionValCd) {
        this.optionValCd = optionValCd;
    }

    public String getOptionValNm() {
        return optionValNm;
    }

    public void setOptionValNm(String optionValNm) {
        this.optionValNm = optionValNm;
    }

    public String getRegFg() {
        return regFg;
    }

    public void setRegFg(String regFg) {
        this.regFg = regFg;
    }

    public String getOptProdCd() {
        return optProdCd;
    }

    public void setOptProdCd(String optProdCd) {
        this.optProdCd = optProdCd;
    }

    public String[] getUserProdBrandList() {
        return userProdBrandList;
    }

    public void setUserProdBrandList(String[] userProdBrandList) {
        this.userProdBrandList = userProdBrandList;
    }

    public String getUserProdBrands() {
        return userProdBrands;
    }

    public void setUserProdBrands(String userProdBrands) {
        this.userProdBrands = userProdBrands;
    }

    public String getProdHqBrandCd() {
        return prodHqBrandCd;
    }

    public void setProdHqBrandCd(String prodHqBrandCd) {
        this.prodHqBrandCd = prodHqBrandCd;
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

    public String getProdClassCd() {
        return prodClassCd;
    }

    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStoreProdUseFg() {
        return storeProdUseFg;
    }

    public void setStoreProdUseFg(String storeProdUseFg) {
        this.storeProdUseFg = storeProdUseFg;
    }
}