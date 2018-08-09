package kr.co.solbipos.store.manage.pwdmanage.service;

import kr.co.solbipos.application.common.service.PageVO;

/**
 * @Class Name : PwdManageVO.java
 * @Description : 가맹점관리 > 매장관리 > 가상 로그인
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
    /** 본사코드 */
    private String hqOfficeCd;
    /** 본사명 */
    private String hqOfficeNm;
    /** 매장코드 */
    private String storeCd;
    /** 매장명 */
    private String storeNm;
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
    
}
