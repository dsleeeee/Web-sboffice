package kr.co.solbipos.pos.license.instlAgency.service;

import kr.co.solbipos.application.common.service.CmmVO;

/**
 * @Class Name : InstlManageVO.java
 * @Description : 포스관리 > 설치관리 > 설치관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2019.10.15  이다솜      최초생성
 *
 * @author 솔비포스 차세대개발실 이다솜
 * @since 2019.10.15
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public class InstlAgencyVO extends CmmVO {

    /** 대리점코드 */
    private String agencyCd;
    /** 대리점명 */
    private String agencyNm;
    /** 대표자명 */
    private String ownerNm;
    /** 대리점의 부모 대리점 코드 */
    private String pAgencyCd;
    /** 사업자번호 */
    private String bizNo;
    /** 사업자 유형코드 */
    private String bizTypeCd;
    /** 사업자 항목코드 */
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
    /** 비고 */
    private String remark;
    /** 저장타입 */
    private String saveType;
    /** 사원번호 */
    private String empNo;
    /** 사원명 */
    private String empNm;
    /** 사용여부 */
    private String useYn;
    /** 재직여부 */
    private String serviceFg;
    /** 웹사용여부 */
    private String webUseYn;
    /** 웹사용자ID */
    private String userId;
    /** 비밀번호 */
    private String userPwd;
    /** 휴대폰번호 */
    private String mpNo;
    /** SMS수신여부 */
    private String smsRecvYn;
    /** 매핑사원코드 */
    private String mapEmpNo;
    /** 관리자구분 */
    private String adminFg;
    /** 소속구분 */
    private String orgnFg;
    /** 그룹 코드 */
    private String authGrpCd;
    /** 총판구분 */
    private String agencyType;

    /** 대리점코드(검색용) */
    private String srchAgencyCd;
    /** 대리점명(검색용) */
    private String srchAgencyNm;


    public String getAgencyCd() {
        return agencyCd;
    }

    public void setAgencyCd(String agencyCd) {
        this.agencyCd = agencyCd;
    }

    public String getAgencyNm() {
        return agencyNm;
    }

    public void setAgencyNm(String agencyNm) {
        this.agencyNm = agencyNm;
    }

    public String getOwnerNm() {
        return ownerNm;
    }

    public void setOwnerNm(String ownerNm) {
        this.ownerNm = ownerNm;
    }

    public String getpAgencyCd() {
        return pAgencyCd;
    }

    public void setpAgencyCd(String pAgencyCd) {
        this.pAgencyCd = pAgencyCd;
    }

    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getBizTypeCd() {
        return bizTypeCd;
    }

    public void setBizTypeCd(String bizTypeCd) {
        this.bizTypeCd = bizTypeCd;
    }

    public String getBizItemCd() {
        return bizItemCd;
    }

    public void setBizItemCd(String bizItemCd) {
        this.bizItemCd = bizItemCd;
    }

    public String getBizStoreNm() {
        return bizStoreNm;
    }

    public void setBizStoreNm(String bizStoreNm) {
        this.bizStoreNm = bizStoreNm;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    public String getHmpgAddr() {
        return hmpgAddr;
    }

    public void setHmpgAddr(String hmpgAddr) {
        this.hmpgAddr = hmpgAddr;
    }

    public String getPostNo() {
        return postNo;
    }

    public void setPostNo(String postNo) {
        this.postNo = postNo;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddrDtl() {
        return addrDtl;
    }

    public void setAddrDtl(String addrDtl) {
        this.addrDtl = addrDtl;
    }

    public String getAreaCd() {
        return areaCd;
    }

    public void setAreaCd(String areaCd) {
        this.areaCd = areaCd;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSaveType() {
        return saveType;
    }

    public void setSaveType(String saveType) {
        this.saveType = saveType;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpNm() {
        return empNm;
    }

    public void setEmpNm(String empNm) {
        this.empNm = empNm;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getServiceFg() {
        return serviceFg;
    }

    public void setServiceFg(String serviceFg) {
        this.serviceFg = serviceFg;
    }

    public String getWebUseYn() {
        return webUseYn;
    }

    public void setWebUseYn(String webUseYn) {
        this.webUseYn = webUseYn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getMpNo() {
        return mpNo;
    }

    public void setMpNo(String mpNo) {
        this.mpNo = mpNo;
    }

    public String getSmsRecvYn() {
        return smsRecvYn;
    }

    public void setSmsRecvYn(String smsRecvYn) {
        this.smsRecvYn = smsRecvYn;
    }

    public String getMapEmpNo() {
        return mapEmpNo;
    }

    public void setMapEmpNo(String mapEmpNo) {
        this.mapEmpNo = mapEmpNo;
    }

    public String getAdminFg() {
        return adminFg;
    }

    public void setAdminFg(String adminFg) {
        this.adminFg = adminFg;
    }

    public String getOrgnFg() {
        return orgnFg;
    }

    public void setOrgnFg(String orgnFg) {
        this.orgnFg = orgnFg;
    }

    public String getAuthGrpCd() {
        return authGrpCd;
    }

    public void setAuthGrpCd(String authGrpCd) {
        this.authGrpCd = authGrpCd;
    }

    public String getAgencyType() {
        return agencyType;
    }

    public void setAgencyType(String agencyType) {
        this.agencyType = agencyType;
    }

    public String getSrchAgencyCd() {
        return srchAgencyCd;
    }

    public void setSrchAgencyCd(String srchAgencyCd) {
        this.srchAgencyCd = srchAgencyCd;
    }

    public String getSrchAgencyNm() {
        return srchAgencyNm;
    }

    public void setSrchAgencyNm(String srchAgencyNm) {
        this.srchAgencyNm = srchAgencyNm;
    }
}
