package kr.co.solbipos.base.prod.kioskKeyMap.service;

import kr.co.solbipos.application.common.service.PageVO;

public class KioskKeyMapVO extends PageVO {

    private static final long serialVersionUID = -448836635464575392L;

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
    /** POS 번호 */
    private String posNo;
    /** 복사원본 POS 번호 */
    private String orgPosNo;
    /** 카테고리코드 */
    private String tuClsCd;
    /** 카테고리명 */
    private String tuClsNm;
    /** 페이지수 */
    private String tuPage;
    /** x */
    private String x;
    /** y */
    private String y;
    /** 폭 */
    private String width;
    /** 높이 */
    private String height;
    /** Display 순서 */
    private String indexNo;
    /** 용도구분  */
    private String clsFg;
    /** 판매가격구분 (본사판매가 :1, 매장판매가:2) */
    private String salePrcFg;
    /** 전체기간 여부 */
    private boolean chkDt;
    /** 상품코드 */
    private String prodCd;
    /** 상품명 */
    private String prodNm;
    /** 상품분류코드 */
    private String prodClassCd;
    /** 바코드 */
    private String barCd;
    /** 사용여부 */
    private String useYn;
    /** 상품유형구분 */
    private String prodTypeFg;
    /** 등록여부 */
    private String regYn;
    /** 키맵코드 */
    private String tuKeyCd;
    /** 카테고리그룹 */
    private String tuClsType;
    private String tuClsTypeNm;
    /** 복사원본의 카테고리그룹 */
    private String orgTuClsType;
    /** 매장명 */
    private String storeNm;
    /**  상태 */
    private String sysStatFg;
    /** 직접입력여부 */
    private String dirctInYn;
    /** 포스구분 */
    private String posFg;
    /** 환경설정코드 */
    private String envstCd;
    /** 환경설정값 */
    private String envstVal;
    /** 키맵매장적용여부 */
    private String chkTuClsTypeStore;
    /** 추천메뉴코드 */
    private String recmdCd;
    /** 추천메뉴타입 */
    private String recmdType;
    /** 표시형태 */
    private String dispType;
    /** 추가타입 */
    private String addType;
    /** 등록구분 */
    private String regFg;
    /** 단일메뉴코드 */
    private String recmdProdCd;
    /** 추천메뉴표시순서 */
    private String index;

    /** 키오스크터치분류키설명 */
    private String clsMemo;

    /** KIOSK중분류사용 */
    private String tuMClsFg;

    /** 카테고리코드 */
    private String tuMClsCd;

    /** 카테고리명 */
    private String tuMClsNm;

    /** 키오스크터치분류키설명 */
    private String mmClsMemo;

    /** 매장수정허용여부 */
    private String storeModYn;

    /** 명칭코드코드 */
    private String nmcodeCd;

    /** 사용자별 브랜드코드(매장) */
    private String[] userBrandList;

    /** 사용자별 브랜드코드(매장) */
    private String userBrands;

    /** 매장브랜드코드 */
    private String storeHqBrandCd;

    /** 사용자별 브랜드코드(상품) */
    private String[] userProdBrandList;

    /** 사용자별 브랜드코드(상품) */
    private String userProdBrands;

    /** 상품브랜드코드 */
    private String prodHqBrandCd;

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

    /** 기준매장코드 */
    private String originalStoreCd;

    private String userId;

    /** 매장그룹 */
    private String momsStoreFg01;

    /** 프로시져 실행 결과 */
    private String result;

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

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    public String getOrgPosNo() {
        return orgPosNo;
    }

    public void setOrgPosNo(String orgPosNo) {
        this.orgPosNo = orgPosNo;
    }

    public String getTuClsCd() {
        return tuClsCd;
    }

    public void setTuClsCd(String tuClsCd) {
        this.tuClsCd = tuClsCd;
    }

    public String getTuClsNm() {
        return tuClsNm;
    }

    public void setTuClsNm(String tuClsNm) {
        this.tuClsNm = tuClsNm;
    }

    public String getTuPage() {
        return tuPage;
    }

    public void setTuPage(String tuPage) {
        this.tuPage = tuPage;
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

    public String getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
    }

    public String getClsFg() {
        return clsFg;
    }

    public void setClsFg(String clsFg) {
        this.clsFg = clsFg;
    }

    public String getSalePrcFg() {
        return salePrcFg;
    }

    public void setSalePrcFg(String salePrcFg) {
        this.salePrcFg = salePrcFg;
    }

    public boolean isChkDt() {
        return chkDt;
    }

    public void setChkDt(boolean chkDt) {
        this.chkDt = chkDt;
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

    public String getTuKeyCd() {
        return tuKeyCd;
    }

    public void setTuKeyCd(String tuKeyCd) {
        this.tuKeyCd = tuKeyCd;
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

    public String getOrgTuClsType() {
        return orgTuClsType;
    }

    public void setOrgTuClsType(String orgTuClsType) {
        this.orgTuClsType = orgTuClsType;
    }

    public String getStoreNm() {
        return storeNm;
    }

    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    public String getSysStatFg() {
        return sysStatFg;
    }

    public void setSysStatFg(String sysStatFg) {
        this.sysStatFg = sysStatFg;
    }

    public String getDirctInYn() {
        return dirctInYn;
    }

    public void setDirctInYn(String dirctInYn) {
        this.dirctInYn = dirctInYn;
    }

    public String getPosFg() {
        return posFg;
    }

    public void setPosFg(String posFg) {
        this.posFg = posFg;
    }

    public String getEnvstCd() {
        return envstCd;
    }

    public void setEnvstCd(String envstCd) {
        this.envstCd = envstCd;
    }

    public String getEnvstVal() {
        return envstVal;
    }

    public void setEnvstVal(String envstVal) {
        this.envstVal = envstVal;
    }

    public String getChkTuClsTypeStore() {
        return chkTuClsTypeStore;
    }

    public void setChkTuClsTypeStore(String chkTuClsTypeStore) {
        this.chkTuClsTypeStore = chkTuClsTypeStore;
    }

    public String getRecmdCd() {
        return recmdCd;
    }

    public void setRecmdCd(String recmdCd) {
        this.recmdCd = recmdCd;
    }

    public String getRecmdType() {
        return recmdType;
    }

    public void setRecmdType(String recmdType) {
        this.recmdType = recmdType;
    }

    public String getDispType() {
        return dispType;
    }

    public void setDispType(String dispType) {
        this.dispType = dispType;
    }

    public String getAddType() {
        return addType;
    }

    public void setAddType(String addType) {
        this.addType = addType;
    }

    public String getRegFg() {
        return regFg;
    }

    public void setRegFg(String regFg) {
        this.regFg = regFg;
    }

    public String getRecmdProdCd() {
        return recmdProdCd;
    }

    public void setRecmdProdCd(String recmdProdCd) {
        this.recmdProdCd = recmdProdCd;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getClsMemo() {
        return clsMemo;
    }

    public void setClsMemo(String clsMemo) {
        this.clsMemo = clsMemo;
    }

    public String getTuMClsFg() {
        return tuMClsFg;
    }

    public void setTuMClsFg(String tuMClsFg) { this.tuMClsFg = tuMClsFg; }

    public String getTuMClsCd() {
        return tuMClsCd;
    }

    public void setTuMClsCd(String tuMClsCd) { this.tuMClsCd = tuMClsCd; }

    public String getTuMClsNm() {
        return tuMClsNm;
    }

    public void setTuMClsNm(String tuMClsNm) { this.tuMClsNm = tuMClsNm; }

    public String getMmClsMemo() { return mmClsMemo; }

    public void setMmClsMemo(String mmClsMemo) { this.mmClsMemo = mmClsMemo; }

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

    public String getOriginalStoreCd() {
        return originalStoreCd;
    }

    public void setOriginalStoreCd(String originalStoreCd) {
        this.originalStoreCd = originalStoreCd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMomsStoreFg01() { return momsStoreFg01; }

    public void setMomsStoreFg01(String momsStoreFg01) { this.momsStoreFg01 = momsStoreFg01; }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}