package kr.co.solbipos.store.hq.hqmanage.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.pos.confg.loginstatus.enums.SysStatFg;
import kr.co.solbipos.sys.auth.authgroup.enums.IncldExcldFg;

/**
 * @Class Name : HqManageVO.java
 * @Description : 가맹점관리 > 본사정보 > 본사정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 06.08
 * @version 1.0
 * @see
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class HqManageVO extends PageVO {

    private static final long serialVersionUID = 2082579494594853632L;
    /** 본사매장 구분 (H:본사, S:매장) */
    private String storeFg;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 본사명 */
    private String hqOfficeNm;
    /** 매장명 */
    private String storeCd;
    /** 대표자명 */
    private String ownerNm;
    /** 사업자번호 */
    private String bizNo;
    /** 사업자유형코드 */
    private String bizTypeCd;
    /** 사업자항목코드 */
    private String bizItemCd;
    /** 사업자매장명 */
    private String bizStoreNm;
    /** 전화번호 */
    private String telNo;
    /** 팩스번호 */
    private String faxNo;
    /** 이메일주소 */
    private String emailAddr;
    /** 홈페이지주소 */
    private String hmpgAddr;
    /** 우편번호 */
    private String postNo;
    /** 주소 */
    private String addr;
    /** 주소상세 */
    private String addrDtl;
    /** 지역코드 */
    private String areaCd;
    /** 시스템상태구분  */
    private SysStatFg sysStatFg;
    /** 시스템오픈일자 */
    private String sysOpenDate;
    /** 시스템폐점일자 */
    private String sysClosureDate;
    /** 대리점코드 */
    private String agencyCd;
    /** 용도구분  */
    private String clsFg;
    /** 비고 */
    private String remark;
    /** 복사 본사 */
    private String copyHqOfficeCd;
    /** 사용자ID */
    private String userId;
    /** 포함 제외 여부 */
    private IncldExcldFg incldExcldFg;
    
    
    /**
     * @return the storeFg
     */
    public String getStoreFg() {
        return storeFg;
    }
    /**
     * @param storeFg the storeFg to set
     */
    public void setStoreFg(String storeFg) {
        this.storeFg = storeFg;
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
     * @return the hqOfficeNm
     */
    public String getHqOfficeNm() {
        return hqOfficeNm;
    }
    /**
     * @param hqOfficeNm the hqOfficeNm to set
     */
    public void setHqOfficeNm(String hqOfficeNm) {
        this.hqOfficeNm = hqOfficeNm;
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
    /**
     * @return the ownerNm
     */
    public String getOwnerNm() {
        return ownerNm;
    }
    /**
     * @param ownerNm the ownerNm to set
     */
    public void setOwnerNm(String ownerNm) {
        this.ownerNm = ownerNm;
    }
    /**
     * @return the bizNo
     */
    public String getBizNo() {
        return bizNo;
    }
    /**
     * @param bizNo the bizNo to set
     */
    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }
    /**
     * @return the bizTypeCd
     */
    public String getBizTypeCd() {
        return bizTypeCd;
    }
    /**
     * @param bizTypeCd the bizTypeCd to set
     */
    public void setBizTypeCd(String bizTypeCd) {
        this.bizTypeCd = bizTypeCd;
    }
    /**
     * @return the bizItemCd
     */
    public String getBizItemCd() {
        return bizItemCd;
    }
    /**
     * @param bizItemCd the bizItemCd to set
     */
    public void setBizItemCd(String bizItemCd) {
        this.bizItemCd = bizItemCd;
    }
    /**
     * @return the bizStoreNm
     */
    public String getBizStoreNm() {
        return bizStoreNm;
    }
    /**
     * @param bizStoreNm the bizStoreNm to set
     */
    public void setBizStoreNm(String bizStoreNm) {
        this.bizStoreNm = bizStoreNm;
    }
    /**
     * @return the telNo
     */
    public String getTelNo() {
        return telNo;
    }
    /**
     * @param telNo the telNo to set
     */
    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }
    /**
     * @return the faxNo
     */
    public String getFaxNo() {
        return faxNo;
    }
    /**
     * @param faxNo the faxNo to set
     */
    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }
    /**
     * @return the emailAddr
     */
    public String getEmailAddr() {
        return emailAddr;
    }
    /**
     * @param emailAddr the emailAddr to set
     */
    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }
    /**
     * @return the hmpgAddr
     */
    public String getHmpgAddr() {
        return hmpgAddr;
    }
    /**
     * @param hmpgAddr the hmpgAddr to set
     */
    public void setHmpgAddr(String hmpgAddr) {
        this.hmpgAddr = hmpgAddr;
    }
    /**
     * @return the postNo
     */
    public String getPostNo() {
        return postNo;
    }
    /**
     * @param postNo the postNo to set
     */
    public void setPostNo(String postNo) {
        this.postNo = postNo;
    }
    /**
     * @return the addr
     */
    public String getAddr() {
        return addr;
    }
    /**
     * @param addr the addr to set
     */
    public void setAddr(String addr) {
        this.addr = addr;
    }
    /**
     * @return the addrDtl
     */
    public String getAddrDtl() {
        return addrDtl;
    }
    /**
     * @param addrDtl the addrDtl to set
     */
    public void setAddrDtl(String addrDtl) {
        this.addrDtl = addrDtl;
    }
    /**
     * @return the areaCd
     */
    public String getAreaCd() {
        return areaCd;
    }
    /**
     * @param areaCd the areaCd to set
     */
    public void setAreaCd(String areaCd) {
        this.areaCd = areaCd;
    }
    /**
     * @return the sysStatFg
     */
    public SysStatFg getSysStatFg() {
        return sysStatFg;
    }
    /**
     * @param sysStatFg the sysStatFg to set
     */
    public void setSysStatFg(SysStatFg sysStatFg) {
        this.sysStatFg = sysStatFg;
    }
    /**
     * @return the sysOpenDate
     */
    public String getSysOpenDate() {
        return sysOpenDate;
    }
    /**
     * @param sysOpenDate the sysOpenDate to set
     */
    public void setSysOpenDate(String sysOpenDate) {
        this.sysOpenDate = sysOpenDate;
    }
    /**
     * @return the sysClosureDate
     */
    public String getSysClosureDate() {
        return sysClosureDate;
    }
    /**
     * @param sysClosureDate the sysClosureDate to set
     */
    public void setSysClosureDate(String sysClosureDate) {
        this.sysClosureDate = sysClosureDate;
    }
    /**
     * @return the agencyCd
     */
    public String getAgencyCd() {
        return agencyCd;
    }
    /**
     * @param agencyCd the agencyCd to set
     */
    public void setAgencyCd(String agencyCd) {
        this.agencyCd = agencyCd;
    }
    /**
     * @return the clsFg
     */
    public String getClsFg() {
        return clsFg;
    }
    /**
     * @param clsFg the clsFg to set
     */
    public void setClsFg(String clsFg) {
        this.clsFg = clsFg;
    }
    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }
    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /**
     * @return the copyHqOfficeCd
     */
    public String getCopyHqOfficeCd() {
        return copyHqOfficeCd;
    }
    /**
     * @param copyHqOfficeCd the copyHqOfficeCd to set
     */
    public void setCopyHqOfficeCd(String copyHqOfficeCd) {
        this.copyHqOfficeCd = copyHqOfficeCd;
    }
    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**
     * @return the incldExcldFg
     */
    public IncldExcldFg getIncldExcldFg() {
        return incldExcldFg;
    }
    /**
     * @param incldExcldFg the incldExcldFg to set
     */
    public void setIncldExcldFg(IncldExcldFg incldExcldFg) {
        this.incldExcldFg = incldExcldFg;
    }
    
}
