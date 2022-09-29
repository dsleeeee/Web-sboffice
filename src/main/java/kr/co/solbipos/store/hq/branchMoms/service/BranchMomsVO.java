package kr.co.solbipos.store.hq.branchMoms.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.pos.loginstatus.enums.SysStatFg;
import kr.co.solbipos.sys.auth.authgroup.enums.IncldExcldFg;

/**
 * @Class Name : BranchMomsVO.java
 * @Description : 가맹점관리 > 본사정보 > 본사-지사 관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.23  권지현      최초생성
 *
 * @author 솔비포스 web개발팀 권지현
 * @since 2022.09.23
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class BranchMomsVO extends PageVO {

    private static final long serialVersionUID = 2082579494594853632L;

    /** 본사코드 */
    private String hqOfficeCd;
    /** 지사코드 */
    private String branchCd;
    /** 지사명 */
    private String branchNm;
    /** 지사장명 */
    private String branchOwnerNm;
    /** 전화번호 */
    private String telNo;
    /** 휴대번호 */
    private String phoneNo;
    /** 이메일 */
    private String email;
    /** 우편번호 */
    private String postNo;
    /** 주소 */
    private String addr;
    /** 상세주소 */
    private String addrDtl;
    /** 사용여부 */
    private String useYn;
    /** 원산지표기 */
    private String orgplceInfo;

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

    public String getBranchOwnerNm() {
        return branchOwnerNm;
    }

    public void setBranchOwnerNm(String branchOwnerNm) {
        this.branchOwnerNm = branchOwnerNm;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getOrgplceInfo() {
        return orgplceInfo;
    }

    public void setOrgplceInfo(String orgplceInfo) {
        this.orgplceInfo = orgplceInfo;
    }
}
