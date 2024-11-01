package kr.co.solbipos.base.prod.artiseeProdMapping.service;

import kr.co.solbipos.application.common.service.PageVO;
/**
 * @Class Name : ArtiseeProdMappingVO.java
 * @Description : 보나비 - 상품관리 - 아티제상품코드맵핑
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.09.27  김유승       최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.09.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class ArtiseeProdMappingVO extends PageVO {

    private static final long serialVersionUID = -6162850453091635524L;
    
    /** 본사코드 */
    private String hqOfficeCd;
    
    /** 사용자아이디 */
    private String userId;

    /** 상품코드 */
    private String prodCd;

    /** 상품명 */
    private String prodNm;

    /** 맵핑코드 */
    private String mappingCd;

    /** 맵핑스트링 */
    private String mappingString;

    /** 등록일시 */
    private String regDt;

    /** 등록아이디 */
    private String regId;

    /** 수정일시 */
    private String modDt;

    /** 수정아이디 */
    private String modId;

    /** 체크여부 */
    private String chkDt;

    /** 시작일자 */
    private String startDate;

    /** 종료일자 */
    private String endDate;

    /** 상품분류코드 */
    private String prodClassCd;

    /** 바코드코드 */
    private String barCd;

    /** 사용여부 */
    private String useYn;

    /** 상품유형 */
    private String prodTypeFg;

    /** 등록여부 */
    private String regYn;

    /** ERP 상품코드 */
    private String erpProdCd;

    /** 상품브랜드코드 */
    private String prodHqBrandCd;

    /** 사용자별 브랜드코드(상품) */
    private String userProdBrands;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getChkDt() {
        return chkDt;
    }

    public void setChkDt(String chkDt) {
        this.chkDt = chkDt;
    }

    @Override
    public String getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getProdClassCd() {
        return prodClassCd;
    }

    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    public String getBarCd() {
        return barCd;
    }

    public void setBarCd(String barCd) {
        this.barCd = barCd;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getProdTypeFg() {
        return prodTypeFg;
    }

    public void setProdTypeFg(String prodTypeFg) {
        this.prodTypeFg = prodTypeFg;
    }

    public String getRegYn() {
        return regYn;
    }

    public void setRegYn(String regYn) {
        this.regYn = regYn;
    }

    public String getProdHqBrandCd() {
        return prodHqBrandCd;
    }

    public void setProdHqBrandCd(String prodHqBrandCd) {
        this.prodHqBrandCd = prodHqBrandCd;
    }

    public String getUserProdBrands() {
        return userProdBrands;
    }

    public void setUserProdBrands(String userProdBrands) {
        this.userProdBrands = userProdBrands;
    }

    public String getMappingCd() {
        return mappingCd;
    }

    public void setMappingCd(String mappingCd) {
        this.mappingCd = mappingCd;
    }

    public String getErpProdCd() {
        return erpProdCd;
    }

    public void setErpProdCd(String erpProdCd) {
        this.erpProdCd = erpProdCd;
    }

    public String getMappingString() {
        return mappingString;
    }

    public void setMappingString(String mappingString) {
        this.mappingString = mappingString;
    }

    @Override
    public String getRegDt() {
        return regDt;
    }

    @Override
    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    @Override
    public String getRegId() {
        return regId;
    }

    @Override
    public void setRegId(String regId) {
        this.regId = regId;
    }

    @Override
    public String getModDt() {
        return modDt;
    }

    @Override
    public void setModDt(String modDt) {
        this.modDt = modDt;
    }

    @Override
    public String getModId() {
        return modId;
    }

    @Override
    public void setModId(String modId) {
        this.modId = modId;
    }
}
