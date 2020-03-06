package kr.co.solbipos.store.manage.pwdmanage.service;

import kr.co.solbipos.application.common.service.PageVO;
import kr.co.solbipos.application.session.auth.enums.UserStatFg;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.store.manage.pwdmanage.enums.EmpOrgnFg;
import kr.co.solbipos.store.manage.pwdmanage.enums.PwdChgFg;

/**
 * @Class Name : PwdManageVO.java
 * @Description : 가맹점관리 > 매장관리 > 비밀번호 임의변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public class PwdManageVO extends PageVO {

    private static final long serialVersionUID = 6990091657881306739L;
    /** 대리점코드 */
    private String agencyCd;
    /** 대리점명 */
    private String agencyNm;
    /** 대리점의 부모 대리점 코드 */
    private String pAgencyCd;
    /** 본사코드 */
    private String hqOfficeCd;
    /** 본사명 */
    private String hqOfficeNm;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
    /** 사원번호 */
    private String empNo;
    /** 사용자ID */
    private String userId;
    /** 사용자명 */
    private String userNm;
    /** 재직구분 */
    private String serviceFg;
    /** 웹사용여부 */
    private String webUseYn;
    /** 휴대폰번호 */
    private String mpNo;
    /** 이메일주소 */
    private String emailAddr;
    /** 주소 */
    private String addr;
    /** 새비밀번호 */
    private String newPassword;
    /** 새비밀번호 확인 */
    private String confirmPassword;
    /** 기존비밀번호 */
    private String priorPwd;
    /** 등록IP */
    private String regIp;
    /** 최종 비밀번호 변경일시 */
    private String lastPwdChgDt;
    /** 비밀번호 변경구분 */
    private PwdChgFg pwdChgFg;
    /** 소속구분 */
    private OrgnFg orgnFg;
    /** 조회 사원 구분 */
    private EmpOrgnFg empOrgnFg;
    /** 사용자상태구분 */
    private UserStatFg userStatFg;
    /** 조회용 본사코드 */
    private String srchHqOfficeCd;
    /** 조회용 매장코드 */
    private String srchStoreCd;
    /** 조회용 총판/대리점 코드 */
    private String srchAgencyCd;


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

    public String getAgencyNm() {
        return agencyNm;
    }

    public void setAgencyNm(String agencyNm) {
        this.agencyNm = agencyNm;
    }

    public String getpAgencyCd() {
        return pAgencyCd;
    }

    public void setpAgencyCd(String pAgencyCd) {
        this.pAgencyCd = pAgencyCd;
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
     * @return the webUseYn
     */
    public String getWebUseYn() {
        return webUseYn;
    }
    /**
     * @param webUseYn the webUseYn to set
     */
    public void setWebUseYn(String webUseYn) {
        this.webUseYn = webUseYn;
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

    /**
     * @return the orgnFg
     */

    public OrgnFg getOrgnFg() {
        return orgnFg;
    }

    /**
     * @param orgnFg the orgnFg to set
     */
    public void setOrgnFg(OrgnFg orgnFg) {
        this.orgnFg = orgnFg;
    }

    /**
     * @return the empOrgnFg
     */

    public EmpOrgnFg getEmpOrgnFg() {
        return empOrgnFg;
    }

    /**
     * @param empOrgnFg the empOrgnFg to set
     */
    public void setEmpOrgnFg(EmpOrgnFg empOrgnFg) {
        this.empOrgnFg = empOrgnFg;
    }

    public UserStatFg getUserStatFg() {
        return userStatFg;
    }

    public void setUserStatFg(UserStatFg userStatFg) {
        this.userStatFg = userStatFg;
    }

    public String getSrchHqOfficeCd() {
        return srchHqOfficeCd;
    }

    public void setSrchHqOfficeCd(String srchHqOfficeCd) {
        this.srchHqOfficeCd = srchHqOfficeCd;
    }

    public String getSrchStoreCd() {
        return srchStoreCd;
    }

    public void setSrchStoreCd(String srchStoreCd) {
        this.srchStoreCd = srchStoreCd;
    }

    public String getSrchAgencyCd() {
        return srchAgencyCd;
    }

    public void setSrchAgencyCd(String srchAgencyCd) {
        this.srchAgencyCd = srchAgencyCd;
    }
}
