package kr.co.solbipos.base.store.emp.empWebMenu.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.sys.auth.authgroup.enums.IncldExcldFg;

/**
 * @Class Name : EmpWebMenuVO.java
 * @Description : 기초관리 > 사원관리 > 메뉴별권한복사
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.24  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.07.24
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public class EmpWebMenuVO extends PageVO {

    private static final long serialVersionUID = -6355479726089763089L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 그룹코드 */
    private String branchCd;
    /** 그룹명 */
    private String branchNm;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 지역코드 */
    private String areaCd;
    /** 매장상태구분 */
    private String sysStatFg;
    /** 영업지역좌표 */
    private String saleArea;
    /** 사원번호 */
    private String empNo;
    /** 사원명 */
    private String empNm;

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

    /** 세션ID */
    private String sessionId;

    /** 순번 */
    private int seq;

    /** 검증결과 */
    private String result;

    /** 명칭코드그룹코드 */
    private String nmcodeGrpCd;

    /** 사원코드 */
    private String originalEmpNo;
    private String targetEmpNo;

    private String resrceCd;
    private IncldExcldFg incldExcldFg;
    private String useYn;

    private String sMenuCd;
    private String sMenuNm;

    private String userId;
    private String mpNo;
    private String userHqBrand;

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getBranchCd() {
        return branchCd;
    }

    public void setBranchCd(String branchCd) {
        this.branchCd = branchCd;
    }

    public String getBranchNm() {
        return branchNm;
    }

    public void setBranchNm(String branchNm) {
        this.branchNm = branchNm;
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

    public String getAreaCd() {
        return areaCd;
    }

    public void setAreaCd(String areaCd) {
        this.areaCd = areaCd;
    }

    public String getSysStatFg() {
        return sysStatFg;
    }

    public void setSysStatFg(String sysStatFg) {
        this.sysStatFg = sysStatFg;
    }

    public String getSaleArea() {
        return saleArea;
    }

    public void setSaleArea(String saleArea) {
        this.saleArea = saleArea;
    }

    public String getEmpNm() {
        return empNm;
    }

    public void setEmpNm(String empNm) {
        this.empNm = empNm;
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getNmcodeGrpCd() {
        return nmcodeGrpCd;
    }

    public void setNmcodeGrpCd(String nmcodeGrpCd) {
        this.nmcodeGrpCd = nmcodeGrpCd;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getOriginalEmpNo() {
        return originalEmpNo;
    }

    public void setOriginalEmpNo(String originalEmpNo) {
        this.originalEmpNo = originalEmpNo;
    }

    public String getTargetEmpNo() {
        return targetEmpNo;
    }

    public void setTargetEmpNo(String targetEmpNo) {
        this.targetEmpNo = targetEmpNo;
    }

    public String getResrceCd() {
        return resrceCd;
    }

    public void setResrceCd(String resrceCd) {
        this.resrceCd = resrceCd;
    }

    public IncldExcldFg getIncldExcldFg() {
        return incldExcldFg;
    }

    public void setIncldExcldFg(IncldExcldFg incldExcldFg) {
        this.incldExcldFg = incldExcldFg;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getsMenuCd() {
        return sMenuCd;
    }

    public void setsMenuCd(String sMenuCd) {
        this.sMenuCd = sMenuCd;
    }

    public String getsMenuNm() {
        return sMenuNm;
    }

    public void setsMenuNm(String sMenuNm) {
        this.sMenuNm = sMenuNm;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMpNo() {
        return mpNo;
    }

    public void setMpNo(String mpNo) {
        this.mpNo = mpNo;
    }

    public String getUserHqBrand() {
        return userHqBrand;
    }

    public void setUserHqBrand(String userHqBrand) {
        this.userHqBrand = userHqBrand;
    }
}
