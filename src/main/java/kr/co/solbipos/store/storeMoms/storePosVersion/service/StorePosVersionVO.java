package kr.co.solbipos.store.storeMoms.storePosVersion.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.pos.confg.verrecv.enums.VerRecvFg;

/**
 * @Class Name : StorePosVersionVO.java
 * @Description : 맘스터치 > 매장관리 > 매장포스버전현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.30  권지현      최초생성
 *
 * @author 솔비포스
 * @since 2023.03.30
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public class StorePosVersionVO extends PageVO {

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

    /** 매장그룹 */
    private String momsStoreFg01;

    /** 매장그룹2 */
    private String momsStoreFg02;

    /** 매장그룹3 */
    private String momsStoreFg03;

    /** 매장그룹4 */
    private String momsStoreFg04;

    /** 매장그룹5 */
    private String momsStoreFg05;

    /** 적용매장 */
    private String registFg;

    /** 버전선택 */
    private String selectVer;

    /** 버전선택 */
    private String selectVerCd;

    /** 최종매출일 */
    private String lastSale;

    /** 포스메인여부 */
    private String mainVal;

    /** 포스용도 */
    private String subVal;

    /** 버전체크 */
    private String verChk;

    /** 포스로그인일자 */
    private String posLogDt;

    /** 패치여부 */
    private String patchFg;

    /** 포스번호 */
    private String posNo;

    /** 버전수신구분 */
    private VerRecvFg verRecvFg;

    /** 버전수신일시 */
    private String verRecvDt;

    /** 프로시져 결과 */
    private String result;

    /** 버전등록 범위 선택여부 */
    private String confFg;

    /** 시작일자 */
    private String startDate;

    /** 종료일자 */
    private String endDate;

    /** 특정에러메세지 */
    private String patchErrMsg;

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

    public String getMomsStoreFg01() { return momsStoreFg01; }

    public void setMomsStoreFg01(String momsStoreFg01) { this.momsStoreFg01 = momsStoreFg01; }

    public String getMomsStoreFg02() { return momsStoreFg02; }

    public void setMomsStoreFg02(String momsStoreFg02) { this.momsStoreFg02 = momsStoreFg02; }

    public String getMomsStoreFg03() { return momsStoreFg03; }

    public void setMomsStoreFg03(String momsStoreFg03) { this.momsStoreFg03 = momsStoreFg03; }

    public String getMomsStoreFg04() { return momsStoreFg04; }

    public void setMomsStoreFg04(String momsStoreFg04) { this.momsStoreFg04 = momsStoreFg04; }

    public String getMomsStoreFg05() { return momsStoreFg05; }

    public void setMomsStoreFg05(String momsStoreFg05) { this.momsStoreFg05 = momsStoreFg05; }

    public String getRegistFg() {
        return registFg;
    }

    public void setRegistFg(String registFg) {
        this.registFg = registFg;
    }

    public String getSelectVer() {
        return selectVer;
    }

    public void setSelectVer(String selectVer) {
        this.selectVer = selectVer;
    }

    public String getSelectVerCd() {
        return selectVerCd;
    }

    public void setSelectVerCd(String selectVerCd) {
        this.selectVerCd = selectVerCd;
    }

    public String getLastSale() {
        return lastSale;
    }

    public void setLastSale(String lastSale) {
        this.lastSale = lastSale;
    }

    public String getMainVal() {
        return mainVal;
    }

    public void setMainVal(String mainVal) {
        this.mainVal = mainVal;
    }

    public String getSubVal() {
        return subVal;
    }

    public void setSubVal(String subVal) {
        this.subVal = subVal;
    }

    public String getVerChk() {
        return verChk;
    }

    public void setVerChk(String verChk) {
        this.verChk = verChk;
    }

    public String getPosLogDt() {
        return posLogDt;
    }

    public void setPosLogDt(String posLogDt) {
        this.posLogDt = posLogDt;
    }

    public String getPatchFg() {
        return patchFg;
    }

    public void setPatchFg(String patchFg) {
        this.patchFg = patchFg;
    }

    public String getPosNo() {
        return posNo;
    }

    public void setPosNo(String posNo) {
        this.posNo = posNo;
    }

    public VerRecvFg getVerRecvFg() {
        return verRecvFg;
    }

    public void setVerRecvFg(VerRecvFg verRecvFg) {
        this.verRecvFg = verRecvFg;
    }

    public String getVerRecvDt() {
        return verRecvDt;
    }

    public void setVerRecvDt(String verRecvDt) {
        this.verRecvDt = verRecvDt;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getConfFg() {
        return confFg;
    }

    public void setConfFg(String confFg) {
        this.confFg = confFg;
    }

    public String startDate() {
        return startDate;
    }

    public void startDate(String startDate) {
        this.startDate = startDate;
    }

    public String endDate() {
        return endDate;
    }

    public void endDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPatchErrMsg() {
        return patchErrMsg;
    }

    public void setPatchErrMsg(String patchErrMsg) {
        this.patchErrMsg = patchErrMsg;
    }
}