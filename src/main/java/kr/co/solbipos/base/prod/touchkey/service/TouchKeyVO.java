package kr.co.solbipos.base.prod.touchkey.service;

import kr.co.solbipos.application.common.service.CmmVO;
import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.base.common.enums.InFg;
import kr.co.solbipos.pos.loginstatus.enums.SysStatFg;

/**
 * @Class Name : TouchKeyVO.java
 * @Description : 기초관리 - 상품관리 - 판매터치키등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.05.01  조병준      최초생성
 *
 * @author NHN한국사이버결제 KCP 조병준
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class TouchKeyVO extends PageVO {

    private static final long serialVersionUID = 5453851450384224044L;

    /** 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점 */
    private String orgnFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 매장코드 */
    private String storeCd;
    /** 매장코드 array */
    private String arrStoreCd[];
    /** 터치키분류코드 */
    private String tukeyClassCd;
    /** 터치키코드 */
    private String tukeyCd;
    /** 터치키구분 : 01:버튼, 02:상품명태그, 03:금액태그 */
    private String tukeyFg;
    /** 상품분류 */
    private String prodClassCd;
    /** 상품코드 */
    private String prodCd;
    /** 페이지번호 */
    private Long pageNo = 0L;
    /** X */
    private Long x = 0L;
    /** Y */
    private Long y = 0L;
    /** 폭 */
    private Long width = 0L;
    /** 높이 */
    private Long height = 0L;
    /** 스타일코드 */
    private String styleCd;
    /** 이미지명 */
    private String imgNm;
    /** 입력구분 H:본사, S:매장 */
    private InFg inFg;

    /** 폰트크기 */
    private Integer fontSize;
    /** 폰트색 */
    private String fontColor;
    /** 채움색 */
    private String fillColor;

    /** 복사할 매장 코드 */
    private String copyStoreCd;

    /** 판매가격구분 (본사판매가 :1, 매장판매가:2) */
    private String salePrcFg;

    /** 조회용 가맹점명 */
    private String storeNm;

    /** 터치키그룹코드 : 시즌,행사별 등 일종의 템플릿 */
    private String tukeyGrpCd;
    private String tukeyGrpNm;

    /** 상품명  */
    private String prodNm;

    /** 시스템상태구분 */
    private SysStatFg sysStatFg;

    /** 용도구분  */
    private String clsFg;

    /** 복사기준 터치키 그룹코드 */
    private String copyTukeyGrpCd;

    /** 브랜드 코드 */
    private String hqBrandCd;

    /** 매장수정허용여부 */
    private String storeModYn;

    /** 명칭코드코드 */
    private String nmcodeCd;

    /** 환경설정 [1250 맘스터치] */
    private String momsEnvstVal;

    /** 사용자별 브랜드코드(매장) */
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

    /** 포스번호 */
    private String posNo;

    /** 환경설정값 */
    private String envstVal;

    /** 터치키매장적용여부 */
    private String chkApplyStore;

    /** 매장코드(멀티선택) */
    private String storeCds;

    /** 포스번호(멀티선택) */
    private String posNos;

    /** 프로시져 실행 결과 */
    private String result;

    /** 조회구분 */
    private String gubun;

    private String userId;

    /** 사용자별 브랜드코드(상품) */
    private String[] userProdBrandList;

    /** 사용자별 브랜드코드(상품) */
    private String userProdBrands;

    /** 상품브랜드코드 */
    private String prodHqBrandCd;

    /** 매장그룹 */
    private String momsStoreFg01;

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

    public String[] getArrStoreCd() {
        return arrStoreCd;
    }

    public void setArrStoreCd(String[] arrStoreCd) {
        this.arrStoreCd = arrStoreCd;
    }

    /**
     * @return the tukeyClassCd
     */
    public String getTukeyClassCd() {
        return tukeyClassCd;
    }
    /**
     * @param tukeyClassCd the tukeyClassCd to set
     */
    public void setTukeyClassCd(String tukeyClassCd) {
        this.tukeyClassCd = tukeyClassCd;
    }
    /**
     * @return the tukeyCd
     */
    public String getTukeyCd() {
        return tukeyCd;
    }
    /**
     * @param tukeyCd the tukeyCd to set
     */
    public void setTukeyCd(String tukeyCd) {
        this.tukeyCd = tukeyCd;
    }

    /**
     * @return the tukeyFg
     */
    public String getTukeyFg() {
        return tukeyFg;
    }

    /**
     * @param tukeyFg the tukeyFg to set
     */
    public void setTukeyFg(String tukeyFg) {
        this.tukeyFg = tukeyFg;
    }

    /**
     * @return the prodClassCd
     */
    public String getProdClassCd() {
        return prodClassCd;
    }

    /**
     * @param prodClassCd the prodClassCd to set
     */
    public void setProdClassCd(String prodClassCd) {
        this.prodClassCd = prodClassCd;
    }

    /**
     * @return the prodCd
     */
    public String getProdCd() {
        return prodCd;
    }
    /**
     * @param prodCd the prodCd to set
     */
    public void setProdCd(String prodCd) {
        this.prodCd = prodCd;
    }
    /**
     * @return the pageNo
     */
    public Long getPageNo() {
        return pageNo;
    }
    /**
     * @param pageNo the pageNo to set
     */
    public void setPageNo(Long pageNo) {
        this.pageNo = pageNo;
    }
    /**
     * @return the x
     */
    public Long getX() {
        return x;
    }
    /**
     * @param x the x to set
     */
    public void setX(Long x) {
        this.x = x;
    }
    /**
     * @return the y
     */
    public Long getY() {
        return y;
    }
    /**
     * @param y the y to set
     */
    public void setY(Long y) {
        this.y = y;
    }
    /**
     * @return the width
     */
    public Long getWidth() {
        return width;
    }
    /**
     * @param width the width to set
     */
    public void setWidth(Long width) {
        this.width = width;
    }
    /**
     * @return the height
     */
    public Long getHeight() {
        return height;
    }
    /**
     * @param height the height to set
     */
    public void setHeight(Long height) {
        this.height = height;
    }

    /**
     * @return the styleCd
     */
    public String getStyleCd() {
        return styleCd;
    }

    /**
     * @param styleCd the styleCd to set
     */
    public void setStyleCd(String styleCd) {
        this.styleCd = styleCd;
    }

    /**
     * @return the imgNm
     */
    public String getImgNm() {
        return imgNm;
    }

    /**
     * @param imgNm the imgNm to set
     */
    public void setImgNm(String imgNm) {
        this.imgNm = imgNm;
    }

    /**
     * @return the inFg
     */
    public InFg getInFg() {
        return inFg;
    }
    /**
     * @param inFg the inFg to set
     */
    public void setInFg(InFg inFg) {
        this.inFg = inFg;
    }
    /**
     * @return the fontSize
     */
    public Integer getFontSize() {
        return fontSize;
    }
    /**
     * @param fontSize the fontSize to set
     */
    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }
    /**
     * @return the fontColor
     */
    public String getFontColor() {
        return fontColor;
    }
    /**
     * @param fontColor the fontColor to set
     */
    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }
    /**
     * @return the fillColor
     */
    public String getFillColor() {
        return fillColor;
    }
    /**
     * @param fillColor the fillColor to set
     */
    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }

    /**
     * @return the copyStoreCd
     */

    public String getCopyStoreCd() {
        return copyStoreCd;
    }

    /**
     * @param copyStoreCd the copyStoreCd to set
     */
    public void setCopyStoreCd(String copyStoreCd) {
        this.copyStoreCd = copyStoreCd;
    }

    /**
     * @return the salePrcFg
     */

    public String getSalePrcFg() {
        return salePrcFg;
    }

    /**
     * @param salePrcFg the salePrcFg to set
     */
    public void setSalePrcFg(String salePrcFg) {
        this.salePrcFg = salePrcFg;
    }

    /**
     * @return the storeNm
     */
    public String getStoreNm() {
        return storeNm;
    }

    /**
     * @param storeNm the storeNm to set
     */
    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
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

    public String getProdNm() {
        return prodNm;
    }

    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }

    public SysStatFg getSysStatFg() {
        return sysStatFg;
    }

    public void setSysStatFg(SysStatFg sysStatFg) {
        this.sysStatFg = sysStatFg;
    }

    public String getClsFg() {
        return clsFg;
    }

    public void setClsFg(String clsFg) {
        this.clsFg = clsFg;
    }

    public String getCopyTukeyGrpCd() {
        return copyTukeyGrpCd;
    }

    public void setCopyTukeyGrpCd(String copyTukeyGrpCd) {
        this.copyTukeyGrpCd = copyTukeyGrpCd;
    }

    public String getHqBrandCd() {
        return hqBrandCd;
    }

    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
    }

    public String getStoreModYn() {
        return storeModYn;
    }

    public void setStoreModYn(String storeModYn) {
        this.storeModYn = storeModYn;
    }

    public String getNmcodeCd() {
        return nmcodeCd;
    }

    public void setNmcodeCd(String nmcodeCd) {
        this.nmcodeCd = nmcodeCd;
    }

    public String getMomsEnvstVal() {
        return momsEnvstVal;
    }

    public void setMomsEnvstVal(String momsEnvstVal) {
        this.momsEnvstVal = momsEnvstVal;
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

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    public String getEnvstVal() {
        return envstVal;
    }

    public void setEnvstVal(String envstVal) {
        this.envstVal = envstVal;
    }

    public String getChkApplyStore() {
        return chkApplyStore;
    }

    public void setChkApplyStore(String chkApplyStore) {
        this.chkApplyStore = chkApplyStore;
    }

    public String getStoreCds() {
        return storeCds;
    }

    public void setStoreCds(String storeCds) {
        this.storeCds = storeCds;
    }

    public String getPosNos() {
        return posNos;
    }

    public void setPosNos(String posNos) {
        this.posNos = posNos;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getGubun() {
        return gubun;
    }

    public void setGubun(String gubun) {
        this.gubun = gubun;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getProdHqBrandCd() {  return prodHqBrandCd; }

    public void setProdHqBrandCd(String prodHqBrandCd) {
        this.prodHqBrandCd = prodHqBrandCd;
    }

    public String getMomsStoreFg01() { return momsStoreFg01; }

    public void setMomsStoreFg01(String momsStoreFg01) { this.momsStoreFg01 = momsStoreFg01; }
}
