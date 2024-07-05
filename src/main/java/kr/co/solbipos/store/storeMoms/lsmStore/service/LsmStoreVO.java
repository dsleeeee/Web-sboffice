package kr.co.solbipos.store.storeMoms.lsmStore.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.base.common.enums.InFg;

/**
 * @Class Name : LsmStoreVO.java
 * @Description : 맘스터치 > 매장관리 > LSM사용매장조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.04.26  권지현      최초생성
 *
 * @author 솔비포스
 * @since 2023.04.26
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public class LsmStoreVO extends PageVO {

    private static final long serialVersionUID = 7461756476951960825L;

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
    /** 브랜드코드 */
    private String hqBrandCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 매장코드 array */
    private String arrStoreCd[];
    /** 사용자별 브랜드코드 */
    private String[] userBrandList;

    /** 사용자별 브랜드코드 */
    private String userBrands;

    /** 매장브랜드코드 */
    private String storeHqBrandCd;

    /** 팀별 */
    private String momsTeam;

    /** AC점포별 */
    private String momsAcShop;

    /** 지역구분 */
    private String momsAreaFg;

    /** 상권 */
    private String momsCommercial;

    /** 점포유형 */
    private String momsShopType;

    /** 매장관리타입 */
    private String momsStoreManageType;

    /** 그룹코드 */
    private String branchCd;

    /** 조회매장 */
    private String storeCds;

    /** 키오스크그룹코드 */
    private String tuClsType;

    /** 키오스크그룹명 */
    private String tuClsTypeNm;

    /** 키오스크카테고리코드 */
    private String tuClsCd;

    /** 포스번호 */
    private String posNo;

    /** 매장(멀티) 조회를 위한 쿼리 문자열*/
    private String storeCdQuery;

    /** 구분값 */
    private String clsFg;

    /** 키맵코드 */
    private String tuKeyCd;

    /** 페이지수 */
    private String tuPage;

    /** Display 순서 */
    private String indexNo;

    /** 상품코드 */
    private String prodCd;

    /** 세션 ID */
    private String sessionId;
    
    /** 터치키그룹코드 */
    private String tukeyGrpCd;

    /** 터치키그룹명 */
    private String tukeyGrpNm;

    /** 터치키코드 */
    private String tukeyCd;

    /** 터치키분류코드 */
    private String tukeyClassCd;

    /** 페이지번호 */
    private String pageNo;

    /** X값 */
    private String x;

    /** Y값 */
    private String y;

    /** 너비 */
    private String width;

    /** 높이 */
    private String height;

    /** 스타일코드 */
    private String styleCd;

    /** 폰트사이즈 */
    private String fontSize;

    /** 폰트색상 */
    private String fontColor;

    /** 바탕색상 */
    private String fillColor;

    /** 이미지명 */
    private String ImgNm;

    /** 입력구분 */
    private InFg inFg;

    /** 환경설정값 */
    private String envstVal;

    /** 처리갯수 */
    private int progressCnt;

    /** 다운로드 구분 */
    private String downFg;

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

    public String getHqBrandCd() {
        return hqBrandCd;
    }

    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
    }

    public String getStoreCd() {
        return storeCd;
    }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getStoreNm() {
        return storeNm;
    }

    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    public String[] getArrStoreCd() {
        return arrStoreCd;
    }

    public void setArrStoreCd(String[] arrStoreCd) {
        this.arrStoreCd = arrStoreCd;
    }

    public String[] getUserBrandList() {
        return userBrandList;
    }

    public void setUserBrandList(String[] userBrandList) {
        this.userBrandList = userBrandList;
    }

    public String getUserBrands() {
        return userBrands;
    }

    public void setUserBrands(String userBrands) {
        this.userBrands = userBrands;
    }

    public String getStoreHqBrandCd() {
        return storeHqBrandCd;
    }

    public void setStoreHqBrandCd(String storeHqBrandCd) {
        this.storeHqBrandCd = storeHqBrandCd;
    }

    public String getMomsTeam() {
        return momsTeam;
    }

    public void setMomsTeam(String momsTeam) {
        this.momsTeam = momsTeam;
    }

    public String getMomsAcShop() {
        return momsAcShop;
    }

    public void setMomsAcShop(String momsAcShop) {
        this.momsAcShop = momsAcShop;
    }

    public String getMomsAreaFg() {
        return momsAreaFg;
    }

    public void setMomsAreaFg(String momsAreaFg) {
        this.momsAreaFg = momsAreaFg;
    }

    public String getMomsCommercial() {
        return momsCommercial;
    }

    public void setMomsCommercial(String momsCommercial) {
        this.momsCommercial = momsCommercial;
    }

    public String getMomsShopType() {
        return momsShopType;
    }

    public void setMomsShopType(String momsShopType) {
        this.momsShopType = momsShopType;
    }

    public String getMomsStoreManageType() {
        return momsStoreManageType;
    }

    public void setMomsStoreManageType(String momsStoreManageType) {
        this.momsStoreManageType = momsStoreManageType;
    }

    public String getBranchCd() {
        return branchCd;
    }

    public void setBranchCd(String branchCd) {
        this.branchCd = branchCd;
    }

    public String getStoreCds() {
        return storeCds;
    }

    public void setStoreCds(String storeCds) {
        this.storeCds = storeCds;
    }

    public String getTuClsType() {
        return tuClsType;
    }

    public void setTuClsType(String tuClsType) {
        this.tuClsType = tuClsType;
    }

    public String getTuClsTypeNm() {
        return tuClsTypeNm;
    }

    public void setTuClsTypeNm(String tuClsTypeNm) {
        this.tuClsTypeNm = tuClsTypeNm;
    }

    public String getStoreCdQuery() {
        return storeCdQuery;
    }

    public void setStoreCdQuery(String storeCdQuery) {
        this.storeCdQuery = storeCdQuery;
    }

    public String getTuClsCd() {
        return tuClsCd;
    }

    public void setTuClsCd(String tuClsCd) {
        this.tuClsCd = tuClsCd;
    }

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    public String getClsFg() {
        return clsFg;
    }

    public void setClsFg(String clsFg) {
        this.clsFg = clsFg;
    }

    public String getTuKeyCd() {
        return tuKeyCd;
    }

    public void setTuKeyCd(String tuKeyCd) {
        this.tuKeyCd = tuKeyCd;
    }

    public String getTuPage() {
        return tuPage;
    }

    public void setTuPage(String tuPage) {
        this.tuPage = tuPage;
    }

    public String getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
    }

    public String getProdCd() {
        return prodCd;
    }

    public void setProdCd(String prodCd) {
        this.prodCd = prodCd;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTukeyGrpCd() {
        return tukeyGrpCd;
    }

    public void setTukeyGrpCd(String tukeyGrpCd) {
        this.tukeyGrpCd = tukeyGrpCd;
    }

    public String getTukeyGrpNm() {
        return tukeyGrpNm;
    }

    public void setTukeyGrpNm(String tukeyGrpNm) {
        this.tukeyGrpNm = tukeyGrpNm;
    }

    public String getTukeyCd() {
        return tukeyCd;
    }

    public void setTukeyCd(String tukeyCd) {
        this.tukeyCd = tukeyCd;
    }

    public String getTukeyClassCd() {
        return tukeyClassCd;
    }

    public void setTukeyClassCd(String tukeyClassCd) {
        this.tukeyClassCd = tukeyClassCd;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getStyleCd() {
        return styleCd;
    }

    public void setStyleCd(String styleCd) {
        this.styleCd = styleCd;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getFillColor() {
        return fillColor;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }

    public String getImgNm() {
        return ImgNm;
    }

    public void setImgNm(String imgNm) {
        ImgNm = imgNm;
    }

    public InFg getInFg() {
        return inFg;
    }

    public void setInFg(InFg inFg) {
        this.inFg = inFg;
    }

    public String getEnvstVal() {
        return envstVal;
    }

    public void setEnvstVal(String envstVal) {
        this.envstVal = envstVal;
    }

    public int getProgressCnt() {
        return progressCnt;
    }

    public void setProgressCnt(int progressCnt) {
        this.progressCnt = progressCnt;
    }

    public String getDownFg() {
        return downFg;
    }

    public void setDownFg(String downFg) {
        this.downFg = downFg;
    }
}
