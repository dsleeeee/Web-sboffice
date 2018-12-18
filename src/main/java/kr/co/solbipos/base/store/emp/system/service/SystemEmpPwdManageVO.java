package kr.co.solbipos.base.store.emp.system.service;

import kr.co.common.data.enums.UseYn;
import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.base.store.emp.system.service.enums.AdminFg;
import kr.co.solbipos.store.manage.pwdmanage.enums.PwdChgFg;


/**
 * @Class Name : SystemEmpPwdManageVO.java
 * @Description : 시스템관리 > 사원관리 > 비밀번호 임의변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.12.12  김지은      최초생성
 *
 * @author 솔비포스 김지은
 * @since 2018. 12.12
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class SystemEmpPwdManageVO extends PageVO {

    /**
     * 사원번호
     */
    private String empNo;

    /**
     * 사원명
     */
    private String empNm;

    /**
     * 웹사용여부
     */
    private UseYn webUseYn;

    /**
     * 사용자아이디
     */
    private String userId;

    /**
     * 사용자명
     */
    private String userNm;


    /**
     * 휴대폰번호
     */
    private String mpNo;

    /**
     * 재직구분
     */
    private String serviceFg;

    /**
     * 재직구분
     */
    private String serviceFgNm;

    /**
     * SMS수신여부
     */
    private String smsRecvYn;

    /**
     * 관리자구분
     */
    private AdminFg adminFg;

    /**
     * 관리업체코드
     */
    private String agencyCd;

    /**
     * 관리업체명
     */
    private String agencyNm;

    /**
     * 사용여부
     */
    private UseYn useYn;

    /**
     * 새비밀번호
     */
    private String newPassword;

    /**
     * 새비밀번호 확인
     */
    private String confirmPassword;

    /**
     * 기존비밀번호
     */
    private String priorPwd;

    /**
     * 등록IP
     */
    private String regIp;

    /**
     * 최종 비밀번호 변경일시
     */
    private String lastPwdChgDt;

    /**
     * 비밀번호 변경구분
     */
    private PwdChgFg pwdChgFg;


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
     * @return the userNm
     */

    public String getUserNm() {
        return userNm;
    }

    /**
     * @param userNm the userNm to set
     */
    public void setUserNm(String userNm) {
        this.userNm = userNm;
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
     * @return the newPassword
     */

    public String getNewPassword() {
        return newPassword;
    }

    /**
     * @param newPassword the newPassword to set
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * @return the confirmPassword
     */

    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * @param confirmPassword the confirmPassword to set
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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
     * @return the lastPwdChgDt
     */

    public String getLastPwdChgDt() {
        return lastPwdChgDt;
    }

    /**
     * @param lastPwdChgDt the lastPwdChgDt to set
     */
    public void setLastPwdChgDt(String lastPwdChgDt) {
        this.lastPwdChgDt = lastPwdChgDt;
    }

    /**
     * @return the pwdChgFg
     */

    public PwdChgFg getPwdChgFg() {
        return pwdChgFg;
    }

    /**
     * @param pwdChgFg the pwdChgFg to set
     */
    public void setPwdChgFg(PwdChgFg pwdChgFg) {
        this.pwdChgFg = pwdChgFg;
    }
}
