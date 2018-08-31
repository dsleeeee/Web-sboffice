package kr.co.solbipos.store.manage.storemanage.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.pos.confg.loginstatus.enums.SysStatFg;

/**
 * @Class Name : StoreManageVO.java
 * @Description : 가맹점관리 > 매장관리 > 매장정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 06.08
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class StoreManageVO extends PageVO {

    private static final long serialVersionUID = 74163511582334938L;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 대표자명 */
    private String ownerNm;
    /** 본사사업장코드 */
    private String hqOfficeCd;
    /** 본사사업장명 */
    private String hqOfficeNm;
    /** 본사브랜드코드 */
    private String hqBrandCd;
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
    /** 용도구분  */
    private String clsFg;
    /** 시스템상태구분 */
    private SysStatFg sysStatFg;
    /** 시스템오픈일자 */
    private String sysOpenDate;
    /** 시스템폐점일자 */
    private String sysClosureDate;
    /** 밴사코드 */
    private String vanCd;
    /** 대리점코드 */
    private String agencyCd;
    /** 시스템 비고 */
    private String sysRemark;
    /** 본사 비고 */
    private String hdRemark;
    /** 비고 */
    private String remark;
    /** 설치포스수  */
    private String installPosCnt;
    /** 매장환경 복사 대상이 되는 본사  */
    private String copyHqOfficeCd;
    /** 매장환경 복사 대상이 되는 매장 */
    private String copyStoreCd;
    /** 터치키 입력 구분 (H:본사, S:매장) - 매장 등록시 사용 */
    private String inFg;
    /** 복사할 환경값 */
    private String copyChkVal;
    /** 포스번호 */
    private int posNo;
    /** 웹 사용자 아이디 */
    private String userId;
    /** 웹 사용자 패스워드 */
    private String userPwd;
    /** 포스 사원번호 */
    private String posEmpNo;
    /** 포스 사용자 패스워드 */
    private String posUserPwd;
    /** 코너 사용여부 */
    private String cornerUseYn;


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
     * @return the hqBrandCd
     */
    public String getHqBrandCd() {
        return hqBrandCd;
    }
    /**
     * @param hqBrandCd the hqBrandCd to set
     */
    public void setHqBrandCd(String hqBrandCd) {
        this.hqBrandCd = hqBrandCd;
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
     * @return the vanCd
     */
    public String getVanCd() {
        return vanCd;
    }
    /**
     * @param vanCd the vanCd to set
     */
    public void setVanCd(String vanCd) {
        this.vanCd = vanCd;
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
     * @return the sysRemark
     */
    public String getSysRemark() {
        return sysRemark;
    }
    /**
     * @param sysRemark the sysRemark to set
     */
    public void setSysRemark(String sysRemark) {
        this.sysRemark = sysRemark;
    }
    /**
     * @return the hdRemark
     */
    public String getHdRemark() {
        return hdRemark;
    }
    /**
     * @param hdRemark the hdRemark to set
     */
    public void setHdRemark(String hdRemark) {
        this.hdRemark = hdRemark;
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
     * @return the installPosCnt
     */
    public String getInstallPosCnt() {
        return installPosCnt;
    }
    /**
     * @param installPosCnt the installPosCnt to set
     */
    public void setInstallPosCnt(String installPosCnt) {
        this.installPosCnt = installPosCnt;
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
     * @return the inFg
     */
    public String getInFg() {
        return inFg;
    }
    /**
     * @param inFg the inFg to set
     */
    public void setInFg(String inFg) {
        this.inFg = inFg;
    }
    /**
     * @return the copyChkVal
     */
    public String getCopyChkVal() {
        return copyChkVal;
    }
    /**
     * @param copyChkVal the copyChkVal to set
     */
    public void setCopyChkVal(String copyChkVal) {
        this.copyChkVal = copyChkVal;
    }
    /**
     * @return the posNo
     */
    public int getPosNo() {
        return posNo;
    }
    /**
     * @param posNo the posNo to set
     */
    public void setPosNo(int posNo) {
        this.posNo = posNo;
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
     * @return the userPwd
     */

    public String getUserPwd() {
        return userPwd;
    }

    /**
     * @param userPwd the userPwd to set
     */
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    /**
     * @return the posEmpNo
     */

    public String getPosEmpNo() {
        return posEmpNo;
    }

    /**
     * @param posEmpNo the posEmpNo to set
     */
    public void setPosEmpNo(String posEmpNo) {
        this.posEmpNo = posEmpNo;
    }

    /**
     * @return the posUserPwd
     */

    public String getPosUserPwd() {
        return posUserPwd;
    }

    /**
     * @param posUserPwd the posUserPwd to set
     */
    public void setPosUserPwd(String posUserPwd) {
        this.posUserPwd = posUserPwd;
    }

    /**
     * @return the cornerUseYn
     */

    public String getCornerUseYn() {
        return cornerUseYn;
    }

    /**
     * @param cornerUseYn the cornerUseYn to set
     */
    public void setCornerUseYn(String cornerUseYn) {
        this.cornerUseYn = cornerUseYn;
    }
}
