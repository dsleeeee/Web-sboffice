package kr.co.solbipos.base.store.emp.system.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.base.store.emp.system.service.enums.AdminFg;


/**
 * @Class Name : HqEmpVO.java
 * @Description : 시스템관리 > 사원관리 > 사원정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.11.21  김지은      최초생성 수정
 *
 * @author 솔비포스 김지은
 * @since 2018. 11.21
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SystemEmpVO extends PageVO {

    /** 본사사업장코드 */
    private String hqOfficeCd;

    /** 사원번호 */
    private String empNo;

    /** 사원명 */
    private String empNm;

    /** 사원비밀번호(포스에서 사용) */
    private String empPwd;

    /** 현재 비밀번호 (비밀번호 변경시 사용) */
    private String currentPwd;

    /** 사용자 비밀번호(웹에서 사용) */
    private String userPwd;

    /** 사용자 비밀번호 확인*/
    private String userPwdCfm;

    /** 사용자 비밀번호 변경여부 */
    private Boolean pwdChgFg;

    /** 변경전 사용자 비밀번호 */
    private String priorPwd;

    /** 웹사용여부 */
    private UseYn webUseYn;

    /** 사용자아이디 */
    private String userId;

    /** 휴대폰번호 */
    private String mpNo;

    /** 재직구분 */
    private String serviceFg;

    /** 재직구분 */
    private String serviceFgNm;

    /** SMS수신여부 */
    private String smsRecvYn;

    /** 관리자구분 */
    private AdminFg adminFg;

    /** 매핑사원코드 */
    private String mapEmpNo;

    /** 관리업체코드 */
    private String agencyCd;

    /** 관리업체명 */
    private String agencyNm;

    /** 사용여부 */
    private UseYn useYn;

    /** 비고 */
    private String remark;

    /** 등록아이피 */
    private String regIp;

    /** 권한 그룹 코드 */
    private String authGrpCd;

    /** 전체기간 체크 */
    private boolean chkDt;

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
     * @return the empNo
     */

    public String getEmpNo() {
        return empNo;
    }

    /**
     * @param empNo the empNo to set
     */
    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    /**
     * @return the empNm
     */

    public String getEmpNm() {
        return empNm;
    }

    /**
     * @param empNm the empNm to set
     */
    public void setEmpNm(String empNm) {
        this.empNm = empNm;
    }

    /**
     * @return the empPwd
     */

    public String getEmpPwd() {
        return empPwd;
    }

    /**
     * @param empPwd the empPwd to set
     */
    public void setEmpPwd(String empPwd) {
        this.empPwd = empPwd;
    }

    /**
     * @return the currentPwd
     */

    public String getCurrentPwd() {
        return currentPwd;
    }

    /**
     * @param currentPwd the currentPwd to set
     */
    public void setCurrentPwd(String currentPwd) {
        this.currentPwd = currentPwd;
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
     * @return the userPwdCfm
     */

    public String getUserPwdCfm() {
        return userPwdCfm;
    }

    /**
     * @param userPwdCfm the userPwdCfm to set
     */
    public void setUserPwdCfm(String userPwdCfm) {
        this.userPwdCfm = userPwdCfm;
    }

    /**
     * @return the pwdChgFg
     */

    public Boolean getPwdChgFg() {
        return pwdChgFg;
    }

    /**
     * @param pwdChgFg the pwdChgFg to set
     */
    public void setPwdChgFg(Boolean pwdChgFg) {
        this.pwdChgFg = pwdChgFg;
    }

    /**
     * @return the priorPwd
     */

    public String getPriorPwd() {
        return priorPwd;
    }

    /**
     * @param priorPwd the priorPwd to set
     */
    public void setPriorPwd(String priorPwd) {
        this.priorPwd = priorPwd;
    }

    /**
     * @return the webUseYn
     */

    public UseYn getWebUseYn() {
        return webUseYn;
    }

    /**
     * @param webUseYn the webUseYn to set
     */
    public void setWebUseYn(UseYn webUseYn) {
        this.webUseYn = webUseYn;
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
     * @return the mpNo
     */

    public String getMpNo() {
        return mpNo;
    }

    /**
     * @param mpNo the mpNo to set
     */
    public void setMpNo(String mpNo) {
        this.mpNo = mpNo;
    }

    /**
     * @return the serviceFg
     */

    public String getServiceFg() {
        return serviceFg;
    }

    /**
     * @param serviceFg the serviceFg to set
     */
    public void setServiceFg(String serviceFg) {
        this.serviceFg = serviceFg;
    }

    /**
     * @return the serviceFgNm
     */

    public String getServiceFgNm() {
        return serviceFgNm;
    }

    /**
     * @param serviceFgNm the serviceFgNm to set
     */
    public void setServiceFgNm(String serviceFgNm) {
        this.serviceFgNm = serviceFgNm;
    }

    /**
     * @return the smsRecvYn
     */

    public String getSmsRecvYn() {
        return smsRecvYn;
    }

    /**
     * @param smsRecvYn the smsRecvYn to set
     */
    public void setSmsRecvYn(String smsRecvYn) {
        this.smsRecvYn = smsRecvYn;
    }



    /**
     * @return the adminFg
     */

    public AdminFg getAdminFg() {
        return adminFg;
    }

    /**
     * @param adminFg the adminFg to set
     */
    public void setAdminFg(AdminFg adminFg) {
        this.adminFg = adminFg;
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
     * @return the agencyNm
     */

    public String getAgencyNm() {
        return agencyNm;
    }

    /**
     * @param agencyNm the agencyNm to set
     */
    public void setAgencyNm(String agencyNm) {
        this.agencyNm = agencyNm;
    }

    /**
     * @return the useYn
     */

    public UseYn getUseYn() {
        return useYn;
    }

    /**
     * @param useYn the useYn to set
     */
    public void setUseYn(UseYn useYn) {
        this.useYn = useYn;
    }

    /**
     * @return the mapEmpNo
     */

    public String getMapEmpNo() {
        return mapEmpNo;
    }

    /**
     * @param mapEmpNo the mapEmpNo to set
     */
    public void setMapEmpNo(String mapEmpNo) {
        this.mapEmpNo = mapEmpNo;
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
     * @return the regIp
     */

    public String getRegIp() {
        return regIp;
    }

    /**
     * @param regIp the regIp to set
     */
    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }
    /**
     * @return the authGrpCd
     */

    public String getAuthGrpCd() {
        return authGrpCd;
    }

    /**
     * @param authGrpCd the authGrpCd to set
     */
    public void setAuthGrpCd(String authGrpCd) {
        this.authGrpCd = authGrpCd;
    }

    /**
     * @return the chkDt
     */

    public boolean isChkDt() {
        return chkDt;
    }

    /**
     * @param chkDt the chkDt to set
     */
    public void setChkDt(boolean chkDt) {
        this.chkDt = chkDt;
    }
}
