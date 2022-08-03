package kr.co.solbipos.store.manage.migDataMapping.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : MigDataMappingVO.java
 * @Description : 기초관리 > 매장정보관리 > OKPOS-KCP 데이터 이관
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.07.16  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2020.07.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class MigDataMappingVO extends PageVO {

    private static final long serialVersionUID = 4567094904301269212L;

    /**
     * 소속구분
     * M : 시스템
     * A : 대리점
     * H : 본사
     * S : 매장, 가맹점
     */
    private String orgnFg;

    /** OKPOS-KCP 본사코드 */
    private String okposHqOfficeCd;

    /** OKPOS-KCP 본사명 */
    private String okposHqOfficeNm;

    /** OKPOS-KCP 매장코드 */
    private String okposStoreCd;

    /** OKPOS-KCP 매장명 */
    private String okposStoreNm;

    /** SOLBI 매장코드 */
    private String solbiStoreCd;

    /** 아이디 */
    private String userId;

    /** 비밀번호 */
    private String userPwd;

    /** OKPOS VAN대리점 코드 */
    private String corpCd;

    /** 날짜 */
    private String date;

    /** 복사할 환경값 */
    private String copyChkVal;

    /** 대표자명 */
    private String ownerNm;

    /** 매장코드 */
    private String storeCd;

    /** 매장명 */
    private String storeNm;

    /** 본사코드 */
    private String hqOfficeCd;

    /** 본사명 */
    private String hqOfficeNm;

    /** 본사브랜드코드 */
    private String hqBrandCd;

    /** 사업자번호 */
    private String bizNo;
    private String bizNo1;
    private String bizNo2;
    private String bizNo3;

    /** 사업자매장명 */
    private String bizStoreNm;

    /** 전화번호 */
    private String telNo;

    /** 우편번호 */
    private String postNo;

    /** 주소 */
    private String addr;

    /** 주소상세 */
    private String addrDtl;

    /** 지역구분 */
    private String areaCd;

    /** 용도구분 */
    private String clsFg;

    /** 시스템상태구분 */
    private String sysStatFg;

    /** 시스템오픈일자 */
    private String sysOpenDate;

    /** 시스템폐점일자 */
    private String sysClosureDate;

    /** 밴사코드 */
    private String vanCd;

    /** 대리점코드 */
    private String agencyCd;

    /** 대리점의 부모 대리점 코드 */
    private String pAgencyCd;

    /** 시스템비고 */
    private String sysRemark;

    /** 설치포스수 */
    private String installPosCnt;

    /** 터치키 입력 구분 (H:본사, S:매장) - 매장 등록시 사용 */
    private String inFg;

    /** 포스번호 */
    private String posNo;

    /** 포스 사원번호 */
    private String posEmpNo;

    /** 포스 사용자 패스워드 */
    private String posUserPwd;

    /** 코너 사용여부 */
    private String cornerUseYn;

    /** 매장환경 복사 대상이 되는 본사  */
    private String copyHqOfficeCd;

    /**  매장환경 복사 대상이 되는 매장 */
    private String copyStoreCd;

    /**  매장형태 */
    private String storeType;

    /**  직영구분 */
    private String directManageYn;

    /** 매장코드 채번방식 */
    private String storeCdInputType;

    public String getOrgnFg() { return orgnFg; }

    public void setOrgnFg(String orgnFg) { this.orgnFg = orgnFg; }

    public String getOkposHqOfficeCd() { return okposHqOfficeCd; }

    public void setOkposHqOfficeCd(String okposHqOfficeCd) { this.okposHqOfficeCd = okposHqOfficeCd; }

    public String getOkposHqOfficeNm() { return okposHqOfficeNm; }

    public void setOkposHqOfficeNm(String okposHqOfficeNm) { this.okposHqOfficeNm = okposHqOfficeNm; }

    public String getOkposStoreCd() { return okposStoreCd; }

    public void setOkposStoreCd(String okposStoreCd) { this.okposStoreCd = okposStoreCd; }

    public String getOkposStoreNm() { return okposStoreNm; }

    public void setOkposStoreNm(String okposStoreNm) { this.okposStoreNm = okposStoreNm; }

    public String getSolbiStoreCd() { return solbiStoreCd; }

    public void setSolbiStoreCd(String solbiStoreCd) { this.solbiStoreCd = solbiStoreCd; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getUserPwd() { return userPwd; }

    public void setUserPwd(String userPwd) { this.userPwd = userPwd; }

    public String getCorpCd() { return corpCd; }

    public void setCorpCd(String corpCd) { this.corpCd = corpCd; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getCopyChkVal() {
        return copyChkVal;
    }

    public void setCopyChkVal(String copyChkVal) {
        this.copyChkVal = copyChkVal;
    }

    public String getOwnerNm() {
        return ownerNm;
    }

    public void setOwnerNm(String ownerNm) {
        this.ownerNm = ownerNm;
    }

    public String getStoreCd() { return storeCd; }

    public void setStoreCd(String storeCd) {
        this.storeCd = storeCd;
    }

    public String getStoreNm() {
        return storeNm;
    }

    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    public String getHqOfficeCd() {
        return hqOfficeCd;
    }

    public void setHqOfficeCd(String hqOfficeCd) {
        this.hqOfficeCd = hqOfficeCd;
    }

    public String getHqOfficeNm() { return hqOfficeNm; }

    public void setHqOfficeNm(String hqOfficeNm) { this.hqOfficeNm = hqOfficeNm; }

    public String getHqBrandCd() { return hqBrandCd; }

    public void setHqBrandCd(String hqBrandCd) { this.hqBrandCd = hqBrandCd; }

    public String getBizNo() { return bizNo; }

    public void setBizNo(String bizNo) { this.bizNo = bizNo; }

    public String getBizNo1() { return bizNo1; }

    public void setBizNo1(String bizNo1) { this.bizNo1 = bizNo1; }

    public String getBizNo2() { return bizNo2; }

    public void setBizNo2(String bizNo2) { this.bizNo2 = bizNo2; }

    public String getBizNo3() { return bizNo3; }

    public void setBizNo3(String bizNo3) { this.bizNo3 = bizNo3; }

    public String getBizStoreNm() { return bizStoreNm; }

    public void setBizStoreNm(String bizStoreNm) { this.bizStoreNm = bizStoreNm; }

    public String getTelNo() { return telNo; }

    public void setTelNo(String telNo) { this.telNo = telNo; }

    public String getPostNo() { return postNo; }

    public void setPostNo(String postNo) { this.postNo = postNo; }

    public String getAddr() { return addr; }

    public void setAddr(String addr) { this.addr = addr; }

    public String getAddrDtl() { return addrDtl; }

    public void setAddrDtl(String addrDtl) { this.addrDtl = addrDtl; }

    public String getAreaCd() { return areaCd; }

    public void setAreaCd(String areaCd) { this.areaCd = areaCd; }

    public String getClsFg() { return clsFg; }

    public void setClsFg(String clsFg) { this.clsFg = clsFg; }

    public String getSysStatFg() { return sysStatFg; }

    public void setSysStatFg(String sysStatFg) { this.sysStatFg = sysStatFg; }

    public String getSysOpenDate() { return sysOpenDate; }

    public void setSysOpenDate(String sysOpenDate) { this.sysOpenDate = sysOpenDate; }

    public String getSysClosureDate() { return sysClosureDate; }

    public void setSysClosureDate(String sysClosureDate) { this.sysClosureDate = sysClosureDate; }

    public String getVanCd() { return vanCd; }

    public void setVanCd(String vanCd) { this.vanCd = vanCd; }

    public String getAgencyCd() { return agencyCd; }

    public void setAgencyCd(String agencyCd) { this.agencyCd = agencyCd; }

    public String getPAgencyCd() { return pAgencyCd; }

    public void setPAgencyCd(String pAgencyCd) { this.pAgencyCd = pAgencyCd; }

    public String getSysRemark() { return sysRemark; }

    public void setSysRemark(String sysRemark) { this.sysRemark = sysRemark; }

    public String getInstallPosCnt() { return installPosCnt; }

    public void setInstallPosCnt(String installPosCnt) { this.installPosCnt = installPosCnt; }

    public String getInFg() { return inFg; }

    public void setInFg(String inFg) { this.inFg = inFg; }

    public String getPosNo() { return posNo; }

    public void setPosNo(String posNo) { this.posNo = posNo; }

    public String getPosEmpNo() { return posEmpNo; }

    public void setPosEmpNo(String posEmpNo) { this.posEmpNo = posEmpNo; }

    public String getPosUserPwd() { return posUserPwd; }

    public void setPosUserPwd(String posUserPwd) { this.posUserPwd = posUserPwd; }

    public String getCornerUseYn() { return cornerUseYn; }

    public void setCornerUseYn(String cornerUseYn) { this.cornerUseYn = cornerUseYn; }

    public String getCopyHqOfficeCd() { return copyHqOfficeCd; }

    public void setCopyHqOfficeCd(String copyHqOfficeCd) { this.copyHqOfficeCd = copyHqOfficeCd; }

    public String getCopyStoreCd() { return copyStoreCd; }

    public void setCopyStoreCd(String copyStoreCd) { this.copyStoreCd = copyStoreCd; }

    public String getStoreType() { return storeType; }

    public void setStoreType(String storeType) { this.storeType = storeType; }

    public String getDirectManageYn() { return directManageYn; }

    public void setDirectManageYn(String directManageYn) { this.directManageYn = directManageYn; }

    public String getStoreCdInputType() {
        return storeCdInputType;
    }

    public void setStoreCdInputType(String storeCdInputType) {
        this.storeCdInputType = storeCdInputType;
    }
}